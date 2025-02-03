package com.ads.report.application.gateway.ads;

import com.ads.report.application.exception.GoogleAdsException;
import com.ads.report.domain.account.AccountMetrics;
import com.ads.report.domain.campaign.CampaignKeywordMetrics;
import com.ads.report.domain.campaign.CampaignMetrics;
import com.ads.report.domain.campaign.CampaignTitleAndDescription;
import com.ads.report.domain.campaign.CampaignPerDay;
import com.ads.report.domain.manager.ManagerAccountInfo;

import java.util.List;

/**
 * The interface of the google ads api calls.
 *
 * <p>
 * This represents the interface that communicates the application with the infra layer,
 * making possible to make api calls.
 * <p/>
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public interface GoogleAdsGateway {

    /**
     * Test the connection with the adwords client.
     *
     * @return The status and a list of accessible customer accounts.
     * @throws RuntimeException If fails to connect.
     * */
    List<String> testConnection() throws GoogleAdsException;

    /**
     * Get campaigns and it's metrics.
     *
     * @param customerId The id of an adwords customer (client).
     *
     * @return The status and a list of CampaignMetrics objects.
     * @throws GoogleAdsException If fails to request the data.
     */
    List<CampaignMetrics> getCampaignMetrics(
            String customerId,
            String startDate,
            String endDate,
            boolean active) throws GoogleAdsException;

    /**
     * Get general information of manager account.
     *
     * @param managerAccountId The id of an adwords customer (client).
     *
     * @return A ManagerAccountInfo type object.
     * @throws GoogleAdsException If fails to request the data.
     */
    ManagerAccountInfo getManagerAccount(String managerAccountId) throws GoogleAdsException;

    /**
     * Get general information of manager account.
     *
     * @param customerId The id of an adwords customer (client).
     * @param startDate The start date of the analysis period.
     * @param endDate The end date of the analysis period.
     *
     * @return A ManagerAccountInfo type object.
     * @throws GoogleAdsException If fails to request the data.
     */
    List<AccountMetrics> getAccountMetrics(String customerId, String startDate, String endDate) throws GoogleAdsException;

    /**
     * This method allows the user to send client account metrics, separated per days,
     * directly from google ads to google sheets.
     *
     * @param customerId The id of an adwords customer (client).
     * @param startDate The start date of the analysis period.
     * @param endDate The end date of the analysis period.
     * @return Returns a list of TotalPerDay object.
     * @throws GoogleAdsException if fails.
     */
    List<CampaignPerDay> getTotalPerDay(String customerId, String startDate, String endDate) throws GoogleAdsException;

    /**
     * This method allows to get all keyword metrics from an account.
     *
     * @param customerId The id of an adwords customer (client).
     * @param startDate The start date of the analysis period.
     * @param endDate The end date of the analysis period.
     * @param active Select if the keyword have had any impressions or cost.
     * @return A list of KeywordMetrics object.
     * @throws GoogleAdsException if fails to request.
     */
    List<CampaignKeywordMetrics> getKeywordMetrics(
            String customerId,
            String startDate,
            String endDate,
            boolean active) throws GoogleAdsException;

    /**
     * This method allows to get all campaigns, ad groups, titles, descriptions, and its metrics.
     *
     * @param customerId The id of an adwords customer (client).
     * @param startDate The start date of the analysis period.
     * @param endDate The end date of the analysis period.
     * @return A list of AdTitleAndDescriptionInfo object.
     * @throws GoogleAdsException if fails to request.
     */
    List<CampaignTitleAndDescription> getAdTitleAndDescriptions(
            String customerId,
            String startDate,
            String endDate) throws GoogleAdsException;
}
