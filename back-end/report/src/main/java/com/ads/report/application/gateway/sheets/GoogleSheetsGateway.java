package com.ads.report.application.gateway.sheets;

import com.ads.report.application.exception.GoogleSheetsException;
import com.ads.report.domain.account.AccountMetrics;
import com.ads.report.domain.campaign.CampaignKeywordMetrics;
import com.ads.report.domain.campaign.CampaignMetrics;
import com.ads.report.domain.campaign.CampaignTitleAndDescription;
import com.ads.report.domain.campaign.CampaignPerDay;

import java.util.List;

/**
 *
 * The Google Sheets interface.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public interface GoogleSheetsGateway {

    /**
     *
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
     * @throws GoogleSheetsException Throws exception if fails.
     */
    void accountMetricsToSheets(String spreadsheetId, String tab, List<AccountMetrics> accountMetrics) throws GoogleSheetsException;

    /**
     *
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
     * @throws GoogleSheetsException Throws if fails.
     */
    void campaignMetricsToSheets(String spreadsheetId, String tab, List<CampaignMetrics> campaignMetrics) throws GoogleSheetsException;

    /**
     *
     * <p>This method allows the user to send client account metrics, separated per days,
     * directly from google ads to google sheets.<p/>
     *
     * @param spreadsheetId The google sheets id.
     * @param tab The sheets tab to write.
     * @param campaignPerDays the list of TotalPerDay objects.
     * @throws GoogleSheetsException throws GoogleSheetsException if fails.
     */
    void totalPerDayToSheets(String spreadsheetId, String tab, List<CampaignPerDay> campaignPerDays) throws GoogleSheetsException;

    /**
     *
     * <p>This method allows the user to send keyword metrics to sheets.<p/>
     *
     * @param spreadsheetId The google sheets id.
     * @param tab The sheets tab to write.
     * @param campaignKeywordMetrics the list of KeywordMetrics objects.
     * @throws GoogleSheetsException throws IOException if fails.
     */
    void sendKeywordMetrics(String spreadsheetId, String tab, List<CampaignKeywordMetrics> campaignKeywordMetrics) throws GoogleSheetsException;

    /**
     *
     * <p>This method allows the user to send titles and descriptions to sheets.<p/>
     *
     * @param spreadsheetId The google sheets id.
     * @param tab The sheets tab to write.
     * @param campaignTitleAndDescriptions the list of AdTitleAndDescriptionInfo objects.
     * @throws GoogleSheetsException throws IOException if fails.
     */
    void sendAdTitleAndDescription(String spreadsheetId, String tab, List<CampaignTitleAndDescription> campaignTitleAndDescriptions) throws GoogleSheetsException;

    /**
     *
     * <p>This method clenas the spreadsheet.<p/>
     *
     * @param spreadsheetId The google sheets id.
     * @param tab The sheets tab to clear.
     * @throws GoogleSheetsException throws IOException if fails.
     */
    void clearSheetTab(String spreadsheetId, String tab) throws GoogleSheetsException;
}
