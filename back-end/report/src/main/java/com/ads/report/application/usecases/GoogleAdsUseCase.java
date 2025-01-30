package com.ads.report.application.usecases;

import com.ads.report.application.gateway.GoogleAdsGateway;
import com.ads.report.domain.account.AccountMetrics;
import com.ads.report.domain.campaign.CampaignKeywordMetrics;
import com.ads.report.domain.campaign.CampaignMetrics;
import com.ads.report.domain.campaign.CampaignTitleAndDescription;
import com.ads.report.domain.campaign.CampaignPerDay;
import com.ads.report.domain.manager.ManagerAccountInfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The use cases of google ads api calls.
 * <p>
 * This class uses the interface contract to call the implementations.
 * <p/>
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public class GoogleAdsUseCase {

    private final GoogleAdsGateway googleAdsGateway;

    public GoogleAdsUseCase(GoogleAdsGateway googleAdsGateway) {
        this.googleAdsGateway = googleAdsGateway;
    }

    /**
     * Get campaigns and it's metrics.
     *
     * @param customerId The id of an adwords customer (client).
     *
     * @return The status and a list of CampaignMetrics objects.
     * @throws RuntimeException If fails to request the data.
     */
    public List<CampaignMetrics> getCampaignMetrics(String customerId, String startDate, String endDate, boolean active) throws RuntimeException {
        return googleAdsGateway.getCampaignMetrics(customerId, startDate, endDate, active);
    }

    /**
     * Test the connection with the adwords client.
     *
     * @return The status and a list of accessible customer accounts.
     * @throws RuntimeException If fails to connect.
     * */
    public List<String> testConnection() throws RuntimeException {
        return googleAdsGateway.testConnection();
    }

    /**
     * Get general information of manager account.
     *
     * @param managerAccountId The id of an adwords customer (client).
     *
     * @return A ManagerAccountInfo type object.
     * @throws RuntimeException If fails to request the data.
     */
    public ManagerAccountInfo getManagerAccount(String managerAccountId) {
        return googleAdsGateway.getManagerAccount(managerAccountId);
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
    public List<AccountMetrics> getAccountMetrics(String customerId, String startDate, String endDate) {
        return googleAdsGateway.getAccountMetrics(customerId, startDate, endDate);
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
    public List<CampaignPerDay> getTotalPerDay(String customerId, String startDate, String endDate) {
        List<CampaignPerDay> campaignPerDays = googleAdsGateway.getTotalPerDay(customerId, startDate, endDate);
        // Creating a list with all dates from the given period.
        List<LocalDate> allDates = new ArrayList<>();
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("The start date is after the end date");
        }
        while (!start.isAfter(end)) {
            allDates.add(start);
            start = start.plusDays(1);
        }
        // Creating a map to group metrics of a single date.
        Map<LocalDate, List<CampaignPerDay>> metricsMap = new HashMap<>();
        for (CampaignPerDay m: campaignPerDays) {
            LocalDate date = LocalDate.parse(m.getDate());
            if (!metricsMap.containsKey(date)) {
                metricsMap.put(date, new ArrayList<>());
            } else {
                metricsMap.get(date).add(m);
            }
        }
        // Creating a list to have the complete results.
        List<CampaignPerDay> completeResults = new ArrayList<>();
        for (LocalDate date: allDates) {
            List<CampaignPerDay> dailyMetrics = metricsMap.get(date);
            if (dailyMetrics == null || dailyMetrics.isEmpty()) {
                completeResults.add(new CampaignPerDay(date.toString(), 0L, 0L, 0d, 0d, 0, date.getDayOfWeek().name()));
            } else {
                completeResults.addAll(dailyMetrics);
            }
        }
        return completeResults;
    }

    /**
     * Use of getting all keyword metrics from an account.
     *
     * @param customerId The id of an adwords customer (client).
     * @param startDate The start date of the analysis period.
     * @param endDate The end date of the analysis period.
     * @return A list of KeywordMetrics object.
     */
    public List<CampaignKeywordMetrics> getKeywordMetrics(String customerId, String startDate, String endDate, boolean active) {
        return googleAdsGateway.getKeywordMetrics(customerId, startDate, endDate, active);
    }

    /**
     * This method allows to get all campaigns, ad groups, titles, descriptions, and its metrics.
     *
     * @param customerId The id of an adwords customer (client).
     * @param startDate The start date of the analysis period.
     * @param endDate The end date of the analysis period.
     * @return A list of AdTitleAndDescriptionInfo object.
     */
    public List<CampaignTitleAndDescription> getAdTitleAndDescriptions(String customerId, String startDate, String endDate) {
        return googleAdsGateway.getAdTitleAndDescriptions(customerId, startDate, endDate);
    }
}
