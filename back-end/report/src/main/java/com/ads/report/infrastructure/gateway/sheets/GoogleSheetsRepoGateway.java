package com.ads.report.infrastructure.gateway.sheets;

import com.ads.report.application.exception.GoogleSheetsException;
import com.ads.report.application.gateway.sheets.GoogleSheetsGateway;
import com.ads.report.domain.account.AccountMetrics;
import com.ads.report.domain.campaign.CampaignKeywordMetrics;
import com.ads.report.domain.campaign.CampaignMetrics;
import com.ads.report.domain.campaign.CampaignTitleAndDescription;
import com.ads.report.domain.campaign.CampaignPerDay;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ClearValuesRequest;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * The implementation of Google Sheets interface.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public class GoogleSheetsRepoGateway implements GoogleSheetsGateway {

    @Autowired
    private Sheets sheetsClient;

    /**
     *
     * This method clenas the spreadsheet.
     *
     * @param spreadsheetId The spreadsheet id.
     * @param tab The sheets tab to clear.
     *
     * @throws GoogleSheetsException throws GoogleSheetsException if fails.
     */
    @Override
    public void clearSheetTab(String spreadsheetId, String tab) throws GoogleSheetsException {
        try {
            final String range = tab + "!A:Z";
            sheetsClient.spreadsheets().values().clear(spreadsheetId, range, new ClearValuesRequest()).execute();
        } catch (Exception e) {
            throw new GoogleSheetsException("Could not clear the tab: " + e.getMessage());
        }
    }

    /**
     *
     * This method allows the user to send account metrics directly from Google Ads to Google Sheets.
     *
     * <p>Here the user can pass an adwords customer id, a start date, end date,
     * a spreadsheet id and tab, to send the data directly without needing
     * to download a csv.<p/>
     *
     * @param spreadsheetId The spreadsheet id.
     * @param tab The sheets tab to write.
     * @param accountMetrics A list of AccountMetrics object.
     *
     * @throws GoogleSheetsException Throws exception if fails.
     */
    @Override
    public void accountMetricsToSheets(
            String spreadsheetId,
            String tab,
            List<AccountMetrics> accountMetrics) throws GoogleSheetsException {
        try {
            clearSheetTab(spreadsheetId, tab);
            final List<List<Object>> sheetData = new ArrayList<>();
            // added sheets headers.
            sheetData.add(List.of("customerId", "descriptiveName", "impressions", "clicks", "cost", "conversions", "averageCpa", "ctr",	"averageCpc"));
            // iterates in all account metrics objects, and add as a row on sheetData list.
            for (AccountMetrics obj : accountMetrics) {
                final List<Object> row = List.of(
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
            final ValueRange body = new ValueRange().setValues(sheetData);
            tab = tab + "!A:Z"; // sets tab and interval.
            sheetsClient.spreadsheets().values()
                .update(spreadsheetId, tab, body)
                .setValueInputOption("RAW")
                .execute();
        } catch (Exception e) {
            throw new GoogleSheetsException("Could not send account metrics: " + e.getMessage());
        }
    }

    /**
     *
     * This method allows the user to send campaign metrics directly from Google Ads to Google Sheets.
     *
     * <p>Here the user can pass an adwords customer id, a spreadsheet id and tab, to send the data
     * directly without needing to download a csv.<p/>
     *
     * @param spreadsheetId The id of an adwords customer (client).
     * @param tab The sheets tab to write.
     * @param campaignMetrics A list of CampaignMetrics object.
     *
     * @throws GoogleSheetsException if fails to send data.
     */
    @Override
    public void campaignMetricsToSheets(
            String spreadsheetId,
            String tab,
            List<CampaignMetrics> campaignMetrics) throws GoogleSheetsException {
        try {
            clearSheetTab(spreadsheetId, tab);
            final List<List<Object>> sheetData = new ArrayList<>();
            // added sheets headers.
            sheetData.add(List.of("date", "dayOfWeek", "campaignId", "campaignName", "adGroupName", "status", "impressions", "clicks", "cost", "conversions", "averageCpa", "ctr", "averageCpc"));
            // iterates in all campaign metrics objects, and add as a row on sheetData list.
            for (CampaignMetrics obj : campaignMetrics) {
                final List<Object> row = List.of(
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
            final ValueRange body = new ValueRange().setValues(sheetData);
            tab = tab + "!A:Z"; // sets tab and interval.
            sheetsClient.spreadsheets().values()
                .update(spreadsheetId, tab, body)
                .setValueInputOption("RAW")
                .execute();
        } catch (Exception e) {
            throw new GoogleSheetsException("Could not send campaign metrics: " + e.getMessage());
        }
    }

    /**
     *
     * This method allows the user to send client account metrics, separated per days, directly from Google Ads to Google Sheets.
     *
     * <p>
     * Here the user can pass an adwords customer id, a start date, end date, a spreadsheet id and tab, to send metrics
     * per day directly without needing to download a csv.
     * <p/>
     *
     * @param spreadsheetId The spreadsheet id.
     * @param tab The sheets tab to write.
     * @param campaignPerDays the list of TotalPerDay objects.
     *
     * @throws GoogleSheetsException throws IOException if fails.
     */
    @Override
    public void totalPerDayToSheets(
            String spreadsheetId,
            String tab,
            List<CampaignPerDay> campaignPerDays) throws GoogleSheetsException {
        try {
            clearSheetTab(spreadsheetId, tab);
            final List<List<Object>> sheetData = new ArrayList<>();
            sheetData.add(List.of("date", "impressions", "clicks", "conversions", "cost", "hour", "dayOfWeek"));
            for (CampaignPerDay obj : campaignPerDays) {
                final List<Object> row = List.of(
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
            final ValueRange body = new ValueRange().setValues(sheetData);
            tab = tab + "!A:Z"; // sets tab and interval.
            sheetsClient.spreadsheets().values()
                .update(spreadsheetId, tab, body)
                .setValueInputOption("RAW")
                .execute();
        } catch (Exception e) {
            throw new GoogleSheetsException("Could not send total data per days: " + e.getMessage());
        }
    }

    /**
     *
     * This method implements the keyword metrics sending to spreadsheet.
     *
     * @param spreadsheetId The spreadsheet id.
     * @param tab The sheets tab to write.
     * @param campaignKeywordMetrics the list of TotalPerDay objects.
     *
     * @throws GoogleSheetsException throws IOException if fails.
     */
    @Override
    public void sendKeywordMetrics(
            String spreadsheetId,
            String tab,
            List<CampaignKeywordMetrics> campaignKeywordMetrics) throws GoogleSheetsException {
        try {
            clearSheetTab(spreadsheetId, tab);
            final List<List<Object>> sheetData = new ArrayList<>();
            sheetData.add(List.of("date", "campaignName", "adGroupName", "keywordText", "matchType", "impressions", "clicks", "cost", "averageCpc", "conversions", "conversionRate", "dayOfWeek", "chanel"));
            for (CampaignKeywordMetrics obj : campaignKeywordMetrics) {
                final List<Object> row = List.of(
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
            final ValueRange body = new ValueRange().setValues(sheetData);
            tab = tab + "!A:Z"; // sets tab and interval.
            sheetsClient.spreadsheets().values()
                .update(spreadsheetId, tab, body)
                .setValueInputOption("RAW")
                .execute();
        } catch (Exception e) {
            throw new GoogleSheetsException("Could not send keyword metrics: " + e.getMessage());
        }
    }

    /**
     *
     * This method allows the user to send titles and descriptions to spreadsheet.
     *
     * @param spreadsheetId The spreadsheet id.
     * @param tab The sheets tab to write.
     * @param campaignTitleAndDescriptions the list of AdTitleAndDescriptionInfo objects.
     *
     * @throws GoogleSheetsException throws IOException if fails.
     */
    @Override
    public void sendAdTitleAndDescription(
            String spreadsheetId,
            String tab,
            List<CampaignTitleAndDescription> campaignTitleAndDescriptions) throws GoogleSheetsException {
        try {
            clearSheetTab(spreadsheetId, tab);
            final List<List<Object>> sheetData = new ArrayList<>();
            sheetData.add(List.of("date", "campaignName", "adGroupName", "responsiveHeadlines", "responsiveDescriptions", "clicks", "impressions", "conversions"));
            for (CampaignTitleAndDescription obj : campaignTitleAndDescriptions) {
                final List<Object> row = List.of(
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
            final ValueRange body = new ValueRange().setValues(sheetData);
            tab = tab + "!A:Z"; // sets tab and interval.
            sheetsClient.spreadsheets().values()
                .update(spreadsheetId, tab, body)
                .setValueInputOption("RAW")
                .execute();
        } catch (Exception e) {
            throw new GoogleSheetsException("Could not send title and descriptions to spreadsheet: " + e.getMessage());
        }
    }
}
