package com.ads.report.application.usecases.sheets;

import com.ads.report.application.exception.GoogleAdsException;
import com.ads.report.application.gateway.sheets.GoogleSheetsGateway;
import com.ads.report.domain.account.AccountMetrics;
import com.ads.report.domain.campaign.CampaignKeywordMetrics;
import com.ads.report.domain.campaign.CampaignMetrics;
import com.ads.report.domain.campaign.CampaignTitleAndDescription;
import com.ads.report.domain.campaign.CampaignPerDay;

import java.util.List;

/**
 *
 * The use cases of Google Sheets.
 *
 * <p>This represents all the Google Sheets use cases.<p/>
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public class GoogleSheetsUseCase {

    private final GoogleSheetsGateway googleSheetsGateway;

    /**
     *
     * Constructor of Google Sheets use case class.
     *
     * @param googleSheetsGateway the interface contract of Google Sheets available methods.
     */
    public GoogleSheetsUseCase(GoogleSheetsGateway googleSheetsGateway) {
        this.googleSheetsGateway = googleSheetsGateway;
    }

    /**
     *
     * This method allows the user to send account metrics directly from Google Ads to Google Sheets.
     *
     * <p>Here the user can pass an adwords customer id, a start date, end date,
     * a Google Sheets id and tab, to send the data directly without needing
     * to download a csv.<p/>
     *
     * @param spreadsheetId The spreadsheet id.
     * @param tab The sheets tab to write.
     * @param accountMetrics A list of AccountMetrics object.
     *
     * @throws GoogleAdsException Throws exception if fails.
     */
    public void sendAccountMetricsToSpreadsheet(
            String spreadsheetId,
            String tab,
            List<AccountMetrics> accountMetrics) throws GoogleAdsException {
        googleSheetsGateway.accountMetricsToSheets(spreadsheetId, tab, accountMetrics);
    }

    /**
     *
     * This method allows the user to send campaign metrics directly from Google Ads to Google Sheets.
     *
     * <p>Here the user can pass an adwords customer id, a start date, end date,
     * a Google Sheets id and tab, to send the data directly without needing
     * to download a csv.<p/>
     *
     * @param spreadsheetId The spreadsheet id.
     * @param tab The sheets tab to write.
     * @param campaignMetrics A list of AccountMetrics object.
     *
     * @throws GoogleAdsException Throws exception if fails.
     */
    public void sendCampaignMetricsToSpreadsheet(
            String spreadsheetId,
            String tab,
            List<CampaignMetrics> campaignMetrics) throws GoogleAdsException {
        googleSheetsGateway.campaignMetricsToSheets(spreadsheetId, tab, campaignMetrics);
    }

    /**
     *
     * This method allows the user to send client account metrics, separated per days,
     * directly from Google Ads to Google Sheets.
     *
     * <p>Here the user can pass an adwords customer id, a start date, end date,
     * a spreadsheet id and tab, to send metrics per day directly without needing
     * to download a csv.<p/>
     *
     * @param spreadsheetId The spreadsheet id.
     * @param tab The sheets tab to write.
     * @param campaignPerDays the list of TotalPerDay objects.
     *
     * @throws GoogleAdsException throws IOException if fails.
     */
    public void sendTotalPerDaysToSpreadsheet(
            String spreadsheetId,
            String tab,
            List<CampaignPerDay> campaignPerDays) throws GoogleAdsException {
        googleSheetsGateway.totalPerDayToSheets(spreadsheetId, tab, campaignPerDays);
    }

    /**
     *
     * This method allows the user to send keyword metrics to a spreadsheets.
     *
     * @param spreadsheetId The spreadsheet id.
     * @param tab The sheets tab to write.
     * @param campaignKeywordMetrics the list of TotalPerDay objects.
     *
     * @throws GoogleAdsException throws IOException if fails.
     */
    public void sendKeywordMetricsToSpreadsheet(
            String spreadsheetId,
            String tab,
            List<CampaignKeywordMetrics> campaignKeywordMetrics) throws GoogleAdsException {
//        final double[] conversions = { 0.0 };
//        final double[] clicks = { 0.0 };
//        keywordMetrics.forEach(k -> k.setConversionRate(k.getConversions() / k.getClicks() * 100));
        googleSheetsGateway.sendKeywordMetrics(spreadsheetId, tab, campaignKeywordMetrics);
    }

    /**
     *
     * This method allows the user to send titles and descriptions to sheets.
     *
     * @param spreadsheetId The spreadsheet id.
     * @param tab The sheets tab to write.
     * @param campaignTitleAndDescriptions the list of AdTitleAndDescriptionInfo objects.
     *
     * @throws GoogleAdsException throws IOException if fails.
     */
    public void sendAdTitleAndDescription(
            String spreadsheetId,
            String tab,
            List<CampaignTitleAndDescription> campaignTitleAndDescriptions) throws GoogleAdsException {
        googleSheetsGateway.sendAdTitleAndDescription(spreadsheetId, tab, campaignTitleAndDescriptions);
    }
}
