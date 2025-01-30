package com.ads.report.application.gateway;

import com.ads.report.domain.account.AccountMetrics;
import com.ads.report.domain.campaign.CampaignKeywordMetrics;
import com.ads.report.domain.campaign.CampaignMetrics;
import com.ads.report.domain.campaign.CampaignTitleAndDescription;
import com.ads.report.domain.campaign.CampaignPerDay;

import java.io.IOException;
import java.util.List;

/**
 * The google sheets interface.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public interface GoogleSheetsGateway {

    /**
     * This method allows the user to send data directly from google ads to google sheets.
     *
     * <p>
     * Here the user can pass a adwords customer id, a start date, end date,
     * a google sheets id and tab, to send the data directly without needing
     * to download a csv.
     * <p/>
     *
     * @param spreadsheetId The google sheets id.
     * @param tab The sheets tab to write.
     * @param accountMetrics A list of AccountMetrics object.
     * @throws IOException Throws exception if fails.
     */
    void accountMetricsToSheets(String spreadsheetId, String tab, List<AccountMetrics> accountMetrics) throws IOException;

    /**
     * This method allows the user to send data directly from google ads to google sheets.
     *
     * <p>
     * Here the user can pass a adwords customer id, a start date, end date,
     * a google sheets id and tab, to send the data directly without needing
     * to download a csv.
     * <p/>
     *
     * @param spreadsheetId The google sheets id.
     * @param tab The sheets tab to write.
     * @param campaignMetrics A list of AccountMetrics object.
     */
    void campaignMetricsToSheets(String spreadsheetId, String tab, List<CampaignMetrics> campaignMetrics) throws IOException;

    /**
     * This method allows the user to send client account metrics, separated per days,
     * directly from google ads to google sheets.
     *
     * @param spreadsheetId The google sheets id.
     * @param tab The sheets tab to write.
     * @param campaignPerDays the list of TotalPerDay objects.
     * @throws IOException throws IOException if fails.
     */
    void totalPerDayToSheets(String spreadsheetId, String tab, List<CampaignPerDay> campaignPerDays) throws IOException;

    /**
     * This method allows the user to send keyword metrics to sheets.
     *
     * @param spreadsheetId The google sheets id.
     * @param tab The sheets tab to write.
     * @param campaignKeywordMetrics the list of KeywordMetrics objects.
     * @throws IOException throws IOException if fails.
     */
    void sendKeywordMetrics(String spreadsheetId, String tab, List<CampaignKeywordMetrics> campaignKeywordMetrics) throws IOException;

    /**
     * This method allows the user to send titles and descriptions to sheets.
     *
     * @param spreadsheetId The google sheets id.
     * @param tab The sheets tab to write.
     * @param campaignTitleAndDescriptions the list of AdTitleAndDescriptionInfo objects.
     * @throws IOException throws IOException if fails.
     */
    void sendAdTitleAndDescription(String spreadsheetId, String tab, List<CampaignTitleAndDescription> campaignTitleAndDescriptions) throws IOException;

    /**
     * This method clenas the spreadsheet.
     *
     * @param spreadsheetId The google sheets id.
     * @param tab The sheets tab to clear.
     * @throws IOException throws IOException if fails.
     */
    void clearSheetTab(String spreadsheetId, String tab) throws IOException;
}
