package com.ads.report.infrastructure.gateway;

import com.ads.report.application.gateway.GoogleSheetsGateway;
import com.ads.report.domain.account.AccountMetrics;
import com.ads.report.domain.campaign.CampaignKeywordMetrics;
import com.ads.report.domain.campaign.CampaignMetrics;
import com.ads.report.domain.campaign.CampaignTitleAndDescription;
import com.ads.report.domain.campaign.CampaignPerDay;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ClearValuesRequest;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of google sheets interface.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public class GoogleSheetsRepoGateway implements GoogleSheetsGateway {

    @Autowired
    private Sheets sheetsClient;

    /**
     * This method clenas the spreadsheet.
     *
     * @param spreadsheetId The google sheets id.
     * @param tab The sheets tab to clear.
     * @throws IOException throws IOException if fails.
     */
    @Override
    public void clearSheetTab(String spreadsheetId, String tab) throws IOException {
        String range = tab + "!A:Z";
        sheetsClient.spreadsheets().values().clear(spreadsheetId, range, new ClearValuesRequest()).execute();
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
    @Override
    public void accountMetricsToSheets(String spreadsheetId, String tab, List<AccountMetrics> accountMetrics) throws IOException {
        clearSheetTab(spreadsheetId, tab);
        List<List<Object>> sheetData = new ArrayList<>();
        // added sheets headers.
        sheetData.add(List.of("customerId", "descriptiveName", "impressions", "clicks", "cost", "conversions", "averageCpa", "ctr",	"averageCpc"));
        // iterates in all account metrics objects, and add as a row on sheetData list.
        for (AccountMetrics obj : accountMetrics) {
            List<Object> row = List.of(
                obj.getCustomerId(),
                obj.getDescriptiveName(),
                obj.getImpressions(),
                obj.getClicks(),
                obj.getCost(),
                obj.getConversions(),
                obj.getAverageCpa(),
                obj.getCtr(),
                obj.getAverageCpc()
            );
            sheetData.add(row);
        }
        ValueRange body = new ValueRange().setValues(sheetData);
        tab = tab + "!A:Z"; // sets tab and interval.
        sheetsClient.spreadsheets().values()
            .update(spreadsheetId, tab, body)
            .setValueInputOption("RAW")
            .execute();
    }

    /**
     * This method allows the user to send campaign metrics directly from google ads to google sheets.
     *
     * <p>
     * Here the user can pass a adwords customer id, a google sheets id and tab, to send the data
     * directly without needing to download a csv.
     * <p/>
     *
     * @param spreadsheetId The id of an adwords customer (client).
     * @param tab The sheets tab to write.
     * @param campaignMetrics A list of CampaignMetrics object.
     * @throws IOException if fails to send data.
     */
    @Override
    public void campaignMetricsToSheets(String spreadsheetId, String tab, List<CampaignMetrics> campaignMetrics) throws IOException {
        clearSheetTab(spreadsheetId, tab);
        List<List<Object>> sheetData = new ArrayList<>();
        // added sheets headers.
        sheetData.add(List.of("date", "dayOfWeek", "campaignId", "campaignName", "adGroupName", "status", "impressions", "clicks", "cost", "conversions", "averageCpa", "ctr", "averageCpc"));
        // iterates in all campaign metrics objects, and add as a row on sheetData list.
        for (CampaignMetrics obj : campaignMetrics) {
            List<Object> row = List.of(
                obj.getDate(),
                obj.getDayOfWeek(),
                obj.getCampaignId(),
                obj.getCampaignName(),
                obj.getAdGroupName(),
                obj.getStatus(),
                obj.getImpressions(),
                obj.getClicks(),
                obj.getCost(),
                obj.getConversions(),
                obj.getAverageCpa(),
                obj.getCtr(),
                obj.getAverageCpc()
            );
            sheetData.add(row);
        }
        ValueRange body = new ValueRange().setValues(sheetData);
        tab = tab + "!A:Z"; // sets tab and interval.
        sheetsClient.spreadsheets().values()
            .update(spreadsheetId, tab, body)
            .setValueInputOption("RAW")
            .execute();
    }

    /**
     * This method allows the user to send client account metrics, separated per days, directly from google ads to google sheets.
     *
     * <p>
     * Here the user can pass a adwords customer id, a start date, end date, a spreadsheet id and tab, to send metrics
     * per day directly without needing to download a csv.
     * <p/>
     *
     * @param spreadsheetId The google sheets id.
     * @param tab The sheets tab to write.
     * @param campaignPerDays the list of TotalPerDay objects.
     * @throws IOException throws IOException if fails.
     */
    @Override
    public void totalPerDayToSheets(String spreadsheetId, String tab, List<CampaignPerDay> campaignPerDays) throws IOException {
        clearSheetTab(spreadsheetId, tab);
        List<List<Object>> sheetData = new ArrayList<>();
        sheetData.add(List.of("date", "impressions", "clicks", "conversions", "cost", "hour", "dayOfWeek"));
        for (CampaignPerDay obj : campaignPerDays) {
            List<Object> row = List.of(
                obj.getDate(),
                obj.getImpressions(),
                obj.getClicks(),
                obj.getConversions(),
                obj.getCost(),
                obj.getHour(),
                obj.getDayOfWeek()
            );
            sheetData.add(row);
        }
        ValueRange body = new ValueRange().setValues(sheetData);
        tab = tab + "!A:Z"; // sets tab and interval.
        sheetsClient.spreadsheets().values()
            .update(spreadsheetId, tab, body)
            .setValueInputOption("RAW")
            .execute();
    }

    /**
     * This method implements the keyword metrics sending to sheets.
     *
     * @param spreadsheetId The google sheets id.
     * @param tab The sheets tab to write.
     * @param campaignKeywordMetrics the list of TotalPerDay objects.
     * @throws IOException throws IOException if fails.
     */
    @Override
    public void sendKeywordMetrics(String spreadsheetId, String tab, List<CampaignKeywordMetrics> campaignKeywordMetrics) throws IOException {
        clearSheetTab(spreadsheetId, tab);
        List<List<Object>> sheetData = new ArrayList<>();
        sheetData.add(List
            .of("date", "campaignName", "adGroupName", "keywordText", "matchType", "impressions", "clicks", "cost", "averageCpc", "conversions", "conversionRate", "dayOfWeek"));
        for (CampaignKeywordMetrics obj : campaignKeywordMetrics) {
            List<Object> row = List.of(
                obj.getDate(),
                obj.getCampaignName(),
                obj.getAdGroupName(),
                obj.getKeywordText(),
                obj.getMatchType(),
                obj.getImpressions(),
                obj.getClicks(),
                obj.getCost(),
                obj.getAverageCpc(),
                obj.getConversions(),
                obj.getConversionRate(),
                obj.getDayOfWeek()
            );
            sheetData.add(row);
        }
        ValueRange body = new ValueRange().setValues(sheetData);
        tab = tab + "!A:Z"; // sets tab and interval.
        sheetsClient.spreadsheets().values()
            .update(spreadsheetId, tab, body)
            .setValueInputOption("RAW")
            .execute();
    }

    /**
     * This method allows the user to send titles and descriptions to sheets.
     *
     * @param spreadsheetId The google sheets id.
     * @param tab The sheets tab to write.
     * @param campaignTitleAndDescriptions the list of AdTitleAndDescriptionInfo objects.
     * @throws IOException throws IOException if fails.
     */
    @Override
    public void sendAdTitleAndDescription(String spreadsheetId, String tab, List<CampaignTitleAndDescription> campaignTitleAndDescriptions) throws IOException {
        clearSheetTab(spreadsheetId, tab);
        List<List<Object>> sheetData = new ArrayList<>();
        sheetData.add(List.of("date", "campaignName", "adGroupName", "responsiveHeadlines", "responsiveDescriptions", "clicks", "impressions", "conversions"));
        for (CampaignTitleAndDescription obj : campaignTitleAndDescriptions) {
            List<Object> row = List.of(
                obj.getDate(),
                obj.getCampaignName(),
                obj.getAdName(),
                obj.getResponsiveHeadlines() != null ? String.join(", ", obj.getResponsiveHeadlines()) : "",
                obj.getResponsiveDescriptions() != null ? String.join(", ", obj.getResponsiveDescriptions()) : "",
                obj.getClicks(),
                obj.getImpressions(),
                obj.getConversions()
            );
            sheetData.add(row);
        }
        ValueRange body = new ValueRange().setValues(sheetData);
        tab = tab + "!A:Z"; // sets tab and interval.
        sheetsClient.spreadsheets().values()
            .update(spreadsheetId, tab, body)
            .setValueInputOption("RAW")
            .execute();
    }
}
