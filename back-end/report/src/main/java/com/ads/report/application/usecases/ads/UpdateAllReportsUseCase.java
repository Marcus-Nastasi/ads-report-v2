package com.ads.report.application.usecases.ads;

import com.ads.report.application.exception.GoogleSheetsException;
import com.ads.report.application.gateway.ads.GoogleAdsGateway;
import com.ads.report.application.gateway.sheets.GoogleSheetsGateway;
import com.ads.report.domain.reports.UpdateAllReports;

import java.util.List;

/**
 *
 * The use case of updating various reports.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public class UpdateAllReportsUseCase {

    private final GoogleAdsGateway googleAdsGateway;
    private final GoogleSheetsGateway googleSheetsGateway;
    private final GoogleAdsUseCase googleAdsUseCase;

    /**
     *
     * The constructor uses the Google Ads and Google Sheets gateway interfaces to make the calls.
     *
     * @param googleAdsGateway the Google Ads gateway
     * @param googleSheetsGateway the Google Sheets gateway
     */
    public UpdateAllReportsUseCase(GoogleAdsGateway googleAdsGateway, GoogleSheetsGateway googleSheetsGateway, GoogleAdsUseCase googleAdsUseCase) {
        this.googleAdsGateway = googleAdsGateway;
        this.googleSheetsGateway = googleSheetsGateway;
        this.googleAdsUseCase = googleAdsUseCase;
    }

    /**
     *
     * This method allows the user to send data to sheets, from various accounts.
     *
     * <p>By passing a list of the customer id, start date, end date, spreadsheet id, client and active flag,
     * you can update the sheets tables with ease.<p/>
     *
     * @param updateAllReports the list of UpdateAllReports domain object.
     */
    public void updateReports(List<UpdateAllReports> updateAllReports) {
        // Iterating through the objects.
        updateAllReports.forEach(r -> {
            try {
                // Getting and sending campaign metrics.
                googleSheetsGateway.campaignMetricsToSheets(
                    r.getSpreadsheetId(),
                    r.getClient() + "-campanhas",
                    googleAdsGateway.getCampaignMetrics(r.getCustomerId(), r.getStartDate(), r.getEndDate(), r.getActive())
                );
                // Getting and sending title and description metrics.
                googleSheetsGateway.sendAdTitleAndDescription(
                    r.getSpreadsheetId(),
                    r.getClient() + "-anuncios",
                    googleAdsGateway.getAdTitleAndDescriptions(r.getCustomerId(), r.getStartDate(), r.getEndDate())
                );
                // Getting and sending keyword metrics.
                googleSheetsGateway.sendKeywordMetrics(
                    r.getSpreadsheetId(),
                    r.getClient() + "-keywords",
                    googleAdsGateway.getKeywordMetrics(r.getCustomerId(), r.getStartDate(), r.getEndDate(), r.getActive())
                );
                // Getting and sending campaign's total per day metrics.
                googleSheetsGateway.totalPerDayToSheets(
                    r.getSpreadsheetId(),
                    r.getClient() + "-grafico",
                    googleAdsUseCase.getTotalPerDay(r.getCustomerId(), r.getStartDate(), r.getEndDate())
                );
            } catch (Exception e) {
                throw new GoogleSheetsException(e.getMessage());
            }
        });
    }
}
