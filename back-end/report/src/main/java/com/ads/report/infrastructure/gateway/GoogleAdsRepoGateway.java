package com.ads.report.infrastructure.gateway;

import com.ads.report.application.gateway.GoogleAdsGateway;
import com.ads.report.domain.account.AccountMetrics;
import com.ads.report.domain.campaign.CampaignKeywordMetrics;
import com.ads.report.domain.campaign.CampaignMetrics;
import com.ads.report.domain.campaign.CampaignTitleAndDescription;
import com.ads.report.domain.campaign.CampaignPerDay;
import com.ads.report.domain.manager.ManagerAccountInfo;
import com.google.ads.googleads.lib.GoogleAdsClient;
import com.google.ads.googleads.v17.common.AdTextAsset;
import com.google.ads.googleads.v17.services.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of GoogleAdsGateway interface.
 * <p>
 * This class implements the interface contract with the goal of
 * requesting data from API, by using the google ads client.
 * <p/>
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public class GoogleAdsRepoGateway implements GoogleAdsGateway {

    @Autowired
    private GoogleAdsClient googleAdsClient;

    /**
     * Test the connection with the adwords client.
     *
     * @return The status and a list of accessible customer accounts.
     * @throws RuntimeException If fails to connect.
     * */
    @Override
    public List<String> testConnection() throws RuntimeException {
        // Create the CustomerServiceClient
        try (CustomerServiceClient customerServiceClient = googleAdsClient.getLatestVersion().createCustomerServiceClient()) {
            final List<String> response = new ArrayList<>();
            // Creates request
            ListAccessibleCustomersRequest request = ListAccessibleCustomersRequest.newBuilder().build();
            // Call the API to list the accessible clients
            ListAccessibleCustomersResponse listed = customerServiceClient.listAccessibleCustomers(request);
            response.add("Successful connection.");
            response.addAll(listed.getResourceNamesList());
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to MCC: " + e.getMessage());
        }
    }

    /**
     * Get general information of manager account.
     *
     * @param managerAccountId The id of an adwords customer (client).
     *
     * @return A ManagerAccountInfo type object.
     * @throws RuntimeException If fails to request the data.
     */
    @Override
    public ManagerAccountInfo getManagerAccount(String managerAccountId) {
        // Connect to google ads service client
        try (GoogleAdsServiceClient client = googleAdsClient.getLatestVersion().createGoogleAdsServiceClient()) {
            String query = """
                SELECT
                    customer.id,
                    customer.descriptive_name,
                    customer.currency_code,
                    customer.time_zone,
                    customer.test_account,
                    customer.status,
                    customer.manager,
                    customer.auto_tagging_enabled,
                    customer.tracking_url_template,
                    customer.final_url_suffix,
                    customer.conversion_tracking_setting.conversion_tracking_id,
                    customer.conversion_tracking_setting.conversion_tracking_status
                FROM customer
            """;
            // Build a new request with the customerId and query
            SearchGoogleAdsRequest request = SearchGoogleAdsRequest.newBuilder()
                .setCustomerId(managerAccountId)
                .setQuery(query)
                .build();
            GoogleAdsRow row = client.search(request).iterateAll().iterator().next(); // Expects unique return
            return new ManagerAccountInfo(
                String.valueOf(row.getCustomer().getId()),
                row.getCustomer().getDescriptiveName(),
                row.getCustomer().getCurrencyCode(),
                row.getCustomer().getTimeZone(),
                row.getCustomer().getTestAccount(),
                row.getCustomer().getStatus().name(),
                row.getCustomer().getManager(),
                row.getCustomer().getAutoTaggingEnabled(),
                row.getCustomer().hasTrackingUrlTemplate() ? row.getCustomer().getTrackingUrlTemplate() : null,
                row.getCustomer().hasFinalUrlSuffix() ? row.getCustomer().getFinalUrlSuffix() : null,
                row.getCustomer().getConversionTrackingSetting().hasConversionTrackingId()
                    ? row.getCustomer().getConversionTrackingSetting().getConversionTrackingId() : null,
                row.getCustomer().getConversionTrackingSetting().getConversionTrackingStatus().name()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error searching account information: " + e.getMessage(), e);
        }
    }

    /**
     *
     * Get campaigns and it's metrics.
     *
     * @param customerId The id of an adwords customer (client).
     *
     * @return The status and a list of CampaignMetrics objects.
     * @throws RuntimeException If fails to request the data.
     */
    @Override
    public List<CampaignMetrics> getCampaignMetrics(String customerId, String startDate, String endDate, boolean active) {
        // Connect to google ads service client
        try (GoogleAdsServiceClient client = googleAdsClient.getLatestVersion().createGoogleAdsServiceClient()) {
            String isActive = active ? "metrics.impressions > '0'" : "metrics.impressions >= '0'";
            List<CampaignMetrics> campaignMetricsList = new ArrayList<>();
            String query = String.format("""
                SELECT
                    segments.date,
                    segments.day_of_week,
                    campaign.id,
                    campaign.name,
                    ad_group.name,
                    campaign.status,
                    metrics.impressions,
                    metrics.clicks,
                    metrics.cost_micros,
                    metrics.conversions,
                    metrics.ctr,
                    metrics.average_cpc,
                    metrics.cost_per_conversion
                FROM ad_group
                WHERE %s
                AND segments.date BETWEEN '%s' AND '%s'
                ORDER BY segments.date ASC, metrics.conversions DESC
            """, isActive, startDate, endDate);
            // Build a new request with the customerId and query
            SearchGoogleAdsRequest request = SearchGoogleAdsRequest.newBuilder()
                .setCustomerId(customerId)
                .setQuery(query)
                .build();
            // Iterating GoogleAdsRow objects to convert to CampaignMetrics
            for (GoogleAdsRow r: client.search(request).iterateAll()) {
                CampaignMetrics campaignMetrics = new CampaignMetrics(
                    r.getSegments().getDate(),
                    r.getSegments().getDayOfWeek().name(),
                    r.getCampaign().getId(),
                    r.getCampaign().getName(),
                    r.getAdGroup().getName(),
                    r.getCampaign().getStatus().toString(),
                    r.getMetrics().getImpressions(),
                    r.getMetrics().getClicks(),
                    r.getMetrics().getCostMicros() / 1_000_000.0, // Converts micros to monetary units
                    r.getMetrics().getConversions(),             // Conversions total
                    r.getMetrics().getCtr(),                     // CTR (Click-through rate)
                    r.getMetrics().getAverageCpc() / 1_000_000.0, // CPC (convert to monetary units)
                    r.getMetrics().getCostPerConversion() / 1_000_000.0 // CPC (convert to monetary units)
                );
                campaignMetricsList.add(campaignMetrics);
            }
            return campaignMetricsList;
        } catch (Exception e) {
            throw new RuntimeException("Error searching metrics: " + e.getMessage());
        }
    }

    /**
     * Get general information of manager account.
     *
     * @param customerId The id of an adwords customer (client).
     * @param startDate The start date of the analysis period.
     * @param endDate The end date of the analysis period.
     *
     * @return A ManagerAccountInfo type object.
     * @throws RuntimeException If fails to request the data.
     */
    @Override
    public List<AccountMetrics> getAccountMetrics(String customerId, String startDate, String endDate) {
        List<AccountMetrics> accountMetricsList = new ArrayList<>();
        String query = String.format("""
            SELECT
                customer.id,
                customer.descriptive_name,
                metrics.impressions,
                metrics.clicks,
                metrics.cost_micros,
                metrics.conversions,
                metrics.ctr,
                metrics.average_cpc,
                metrics.cost_per_conversion
            FROM customer
            WHERE segments.date BETWEEN '%s' AND '%s'
        """, startDate, endDate);
        try (GoogleAdsServiceClient client = googleAdsClient.getLatestVersion().createGoogleAdsServiceClient()) {
            // Build a new request with the customerId and query
            SearchGoogleAdsRequest request = SearchGoogleAdsRequest.newBuilder()
                .setCustomerId(customerId)
                .setQuery(query)
                .build();
            // Iterating GoogleAdsRow objects to convert to CampaignMetrics
            for (GoogleAdsRow r: client.search(request).iterateAll()) {
                AccountMetrics accountMetrics = new AccountMetrics(
                    r.getCustomer().getId(),
                    r.getCustomer().getDescriptiveName(),
                    r.getMetrics().getImpressions(),
                    r.getMetrics().getClicks(),
                    r.getMetrics().getCostMicros() / 1_000_000.0,
                    r.getMetrics().getConversions(),
                    r.getMetrics().getCtr(),
                    r.getMetrics().getAverageCpc() / 1_000_000.0,
                    r.getMetrics().getCostPerConversion() / 1_000_000.0
                );
                accountMetricsList.add(accountMetrics);
            }
            return accountMetricsList;
        } catch (Exception e) {
            throw new RuntimeException("Error searching account metrics: " + e.getMessage(), e);
        }
    }

    /**
     * This method allows the user to send client account metrics, separated per days,
     * directly from google ads to google sheets.
     *
     * <p>
     * Here the user can pass a adwords customer id, a start date, end date,
     * a spreadsheet id and tab, to send metrics per day directly without needing
     * to download a csv.
     * <p/>
     *
     * @param customerId The id of an adwords customer (client).
     * @param startDate The start date of the analysis period.
     * @param endDate The end date of the analysis period.
     * @return Returns a list of TotalPerDay object.
     */
    @Override
    public List<CampaignPerDay> getTotalPerDay(String customerId, String startDate, String endDate) {
        List<CampaignPerDay> campaignPerDays = new ArrayList<>();
        String query = String.format("""
            SELECT
              segments.date,
              metrics.impressions,
              metrics.clicks,
              metrics.conversions,
              metrics.cost_micros,
              segments.hour,
              segments.day_of_week
            FROM customer
            WHERE segments.date BETWEEN '%s' AND '%s'
            ORDER BY segments.date ASC, metrics.conversions DESC
        """, startDate, endDate);
        try (GoogleAdsServiceClient client = googleAdsClient.getLatestVersion().createGoogleAdsServiceClient()) {
            // Build a new request with the customerId and query
            SearchGoogleAdsRequest request = SearchGoogleAdsRequest.newBuilder()
                .setCustomerId(customerId)
                .setQuery(query)
                .build();
            // Iterating GoogleAdsRow objects to convert to TotalPerDay
            for (GoogleAdsRow r: client.search(request).iterateAll()) {
                CampaignPerDay campaignPerDay = new CampaignPerDay(
                    r.getSegments().getDate(),
                    r.getMetrics().getImpressions(),
                    r.getMetrics().getClicks(),
                    r.getMetrics().getConversions(),
                    r.getMetrics().getCostMicros() / 1_000_000.0,
                    r.getSegments().getHour(),
                    r.getSegments().getDayOfWeek().name()
                );
                campaignPerDays.add(campaignPerDay);
            }
            return campaignPerDays;
        } catch (Exception e) {
            throw new RuntimeException("Error searching per day metrics: " + e.getMessage(), e);
        }
    }

    /**
     * Implementation to get all keyword metrics from an account.
     *
     * @param customerId The id of an adwords customer (client).
     * @param startDate The start date of the analysis period.
     * @param endDate The end date of the analysis period.
     * @return A list of KeywordMetrics object.
     */
    @Override
    public List<CampaignKeywordMetrics> getKeywordMetrics(String customerId, String startDate, String endDate, boolean active) {
        String isActive = active ? "metrics.impressions > '0'" : "metrics.impressions >= '0'";
        List<CampaignKeywordMetrics> campaignKeywordMetrics = new ArrayList<>();
        String query = String.format("""
            SELECT
              segments.date,
              segments.day_of_week,
              campaign.name,
              ad_group.name,
              ad_group_criterion.keyword.text,
              ad_group_criterion.keyword.match_type,
              metrics.impressions,
              metrics.clicks,
              metrics.cost_micros,
              metrics.average_cpc,
              metrics.conversions,
              metrics.conversions_from_interactions_rate
            FROM keyword_view
            WHERE segments.date >= '%s' AND segments.date <= '%s'
            AND %s
            ORDER BY segments.date ASC, metrics.conversions DESC
        """, startDate, endDate, isActive);
        try (GoogleAdsServiceClient client = googleAdsClient.getLatestVersion().createGoogleAdsServiceClient()) {
            // Build a new request with the customerId and query
            SearchGoogleAdsRequest request = SearchGoogleAdsRequest.newBuilder()
                .setCustomerId(customerId)
                .setQuery(query)
                .build();
            // Iterating GoogleAdsRow objects to convert to TotalPerDay
            for (GoogleAdsRow r: client.search(request).iterateAll()) {
                // Calculates conversion rate
//                BigDecimal conversionRate = BigDecimal.ZERO;
//                if (r.getMetrics().getClicks() > 0) {
//                    conversionRate = BigDecimal.valueOf(r.getMetrics().getConversions())
//                        .divide(BigDecimal.valueOf(r.getMetrics().getClicks()), 2, RoundingMode.HALF_UP)
//                        .multiply(BigDecimal.valueOf(100));
//                }
                // Create KeywordMetrics object
                CampaignKeywordMetrics keywordMetric = new CampaignKeywordMetrics(
                    r.getSegments().getDate(),
                    r.getSegments().getDayOfWeek().name(),
                    r.getCampaign().getName(),
                    r.getAdGroup().getName(),
                    r.getAdGroupCriterion().getKeyword().getText(),
                    r.getAdGroupCriterion().getKeyword().getMatchType().toString(),
                    r.getMetrics().getImpressions(),
                    r.getMetrics().getClicks(),
                    r.getMetrics().getCostMicros() / 1_000_000.0,
                    r.getMetrics().getAverageCpc() / 1_000_000.0,
                    r.getMetrics().getConversions(),
                    r.getMetrics().getConversionsFromInteractionsRate()
                );
                campaignKeywordMetrics.add(keywordMetric);
            }
            return campaignKeywordMetrics;
        } catch (Exception e) {
            throw new RuntimeException("Error searching keyword metrics: " + e.getMessage(), e);
        }
    }

    /**
     * Implementation to get all campaigns, ad groups, titles and descriptions, and its metrics.
     *
     * @param customerId The id of an adwords customer (client).
     * @param startDate The start date of the analysis period.
     * @param endDate The end date of the analysis period.
     * @return A list of AdTitleAndDescriptionInfo object.
     */
    @Override
    public List<CampaignTitleAndDescription> getAdTitleAndDescriptions(String customerId, String startDate, String endDate) {
        List<CampaignTitleAndDescription> campaignTitleAndDescriptions = new ArrayList<>();
        String query = String.format("""
            SELECT
                segments.date,
                campaign.name,
                ad_group.name,
                ad_group_ad.ad.responsive_search_ad.headlines,
                ad_group_ad.ad.responsive_search_ad.descriptions,
                ad_group_ad.ad.expanded_text_ad.headline_part1,
                ad_group_ad.ad.expanded_text_ad.headline_part2,
                ad_group_ad.ad.expanded_text_ad.description,
                metrics.clicks,
                metrics.impressions,
                metrics.conversions
            FROM ad_group_ad
            WHERE segments.date >= '%s' AND segments.date <= '%s' AND ad_group_ad.status != 'REMOVED'
            ORDER BY segments.date ASC, metrics.conversions DESC
        """, startDate, endDate);
        try (GoogleAdsServiceClient client = googleAdsClient.getLatestVersion().createGoogleAdsServiceClient()) {
            // Build a new request with the customerId and query
            SearchGoogleAdsRequest request = SearchGoogleAdsRequest.newBuilder()
                .setCustomerId(customerId)
                .setQuery(query)
                .build();
            // Iterating GoogleAdsRow objects to convert to TotalPerDay
            for (GoogleAdsRow r : client.search(request).iterateAll()) {
                List<String> responsiveHeadlines = new ArrayList<>();
                List<String> responsiveDescriptions = new ArrayList<>();
                // Verifies ad type.
                if (r.getAdGroupAd().getAd().hasResponsiveSearchAd()) {
                    // Process responsive ads.
                    responsiveHeadlines = r.getAdGroupAd()
                        .getAd()
                        .getResponsiveSearchAd()
                        .getHeadlinesList()
                        .stream()
                        .map(AdTextAsset::getText)
                        .toList();
                    responsiveDescriptions = r.getAdGroupAd()
                        .getAd()
                        .getResponsiveSearchAd()
                        .getDescriptionsList()
                        .stream()
                        .map(AdTextAsset::getText)
                        .toList();
                }
                // Build domain object.
                CampaignTitleAndDescription adInfo = new CampaignTitleAndDescription(
                    r.getSegments().getDate(),
                    r.getCampaign().getName(),
                    r.getAdGroup().getName(),
                    responsiveHeadlines,
                    responsiveDescriptions,
                    r.getMetrics().getClicks(),
                    r.getMetrics().getImpressions(),
                    r.getMetrics().getConversions()
                );
                campaignTitleAndDescriptions.add(adInfo);
            }
            return campaignTitleAndDescriptions;
        } catch (Exception e) {
            throw new RuntimeException("Error searching Ad metrics: " + e.getMessage(), e);
        }
    }
}
