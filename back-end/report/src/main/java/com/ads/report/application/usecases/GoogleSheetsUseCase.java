package com.ads.report.application.usecases;

import com.ads.report.application.gateway.GoogleSheetsGateway;
import com.ads.report.domain.account.AccountMetrics;
import com.ads.report.domain.campaign.CampaignKeywordMetrics;
import com.ads.report.domain.campaign.CampaignMetrics;
import com.ads.report.domain.campaign.CampaignTitleAndDescription;
import com.ads.report.domain.campaign.CampaignPerDay;

import java.io.IOException;
import java.util.List;

/**
 * The use cases of google sheets.
 *
 * <p>
 * This represents all the google sheets use cases.
 * <p/>
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public class GoogleSheetsUseCase {

    private final GoogleSheetsGateway googleSheetsGateway;

    /**
     * Constructor of google sheets use case class.
     *
     * @param googleSheetsGateway the interface contract of google sheets available methods.
     */
    public GoogleSheetsUseCase(GoogleSheetsGateway googleSheetsGateway) {
        this.googleSheetsGateway = googleSheetsGateway;
    }

    /**
     * This method allows the user to send account metrics directly from google ads to google sheets.
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
    public void accountMetricsToSheets(String spreadsheetId, String tab, List<AccountMetrics> accountMetrics) throws IOException {
        googleSheetsGateway.accountMetricsToSheets(spreadsheetId, tab, accountMetrics);
    }

    /**
     * This method allows the user to send campaign metrics directly from google ads to google sheets.
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
    public void campaignMetricsToSheets(String spreadsheetId, String tab, List<CampaignMetrics> campaignMetrics) throws IOException {
        googleSheetsGateway.campaignMetricsToSheets(spreadsheetId, tab, campaignMetrics);
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
     * @param spreadsheetId The google sheets id.
     * @param tab The sheets tab to write.
     * @param campaignPerDays the list of TotalPerDay objects.
     * @throws IOException throws IOException if fails.
     */
    public void totalPerDaysToSheet(String spreadsheetId, String tab, List<CampaignPerDay> campaignPerDays) throws IOException {
        googleSheetsGateway.totalPerDayToSheets(spreadsheetId, tab, campaignPerDays);
    }

    /**
     * This method allows the user to send keyword metrics to sheets.
     *
     * @param spreadsheetId The google sheets id.
     * @param tab The sheets tab to write.
     * @param campaignKeywordMetrics the list of TotalPerDay objects.
     * @throws IOException throws IOException if fails.
     */
    public void sendKeywordMetrics(String spreadsheetId, String tab, List<CampaignKeywordMetrics> campaignKeywordMetrics) throws IOException {
//        final double[] conversions = { 0.0 };
//        final double[] clicks = { 0.0 };
//        keywordMetrics.forEach(k -> k.setConversionRate(k.getConversions() / k.getClicks() * 100));
        googleSheetsGateway.sendKeywordMetrics(spreadsheetId, tab, campaignKeywordMetrics);
    }

    /**
     * This method allows the user to send titles and descriptions to sheets.
     *
     * @param spreadsheetId The google sheets id.
     * @param tab The sheets tab to write.
     * @param campaignTitleAndDescriptions the list of AdTitleAndDescriptionInfo objects.
     * @throws IOException throws IOException if fails.
     */
    public void sendAdTitleAndDescription(String spreadsheetId, String tab, List<CampaignTitleAndDescription> campaignTitleAndDescriptions) throws IOException {
        googleSheetsGateway.sendAdTitleAndDescription(spreadsheetId, tab, campaignTitleAndDescriptions);
    }
}
