package com.ads.report.application.usecases.ads;

import com.ads.report.application.exception.GoogleSheetsException;
import com.ads.report.application.gateway.ads.GoogleAdsGateway;
import com.ads.report.application.gateway.sheets.GoogleSheetsGateway;
import com.ads.report.domain.reports.UpdateAllReports;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * The use case of updating various reports.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public class UpdateAllReportsUseCase {

    private final GoogleAdsGateway googleAdsGateway;

    private final GoogleSheetsGateway googleSheetsGateway;

    private final Executor contextAwareExecutor;

    /**
     * The constructor uses the Google Ads and Google Sheets gateway interfaces to make the calls.
     *
     * @param googleAdsGateway the Google Ads gateway
     * @param googleSheetsGateway the Google Sheets gateway
     */
    public UpdateAllReportsUseCase(GoogleAdsGateway googleAdsGateway,
                                   GoogleSheetsGateway googleSheetsGateway,
                                   Executor contextAwareExecutor) {
        this.googleAdsGateway = googleAdsGateway;
        this.googleSheetsGateway = googleSheetsGateway;
        this.contextAwareExecutor = contextAwareExecutor;
    }

    /**
     * This method allows the user to send data to sheets, from various accounts.
     *
     * <p>By passing a {@link List} of the customer id, start date, end date, spreadsheet id, client and active flag,
     * you can update the sheets tables with ease.<p/>
     *
     * <p>This method uses the {@link CompletableFuture} to run async each request to each client,
     * enabling the execution to be asynchronous, and reducing the response time.<p/>
     *
     * @param updateAllReports the list of {@link UpdateAllReports} domain object.
     */
    public void updateReports(List<UpdateAllReports> updateAllReports) {
        // Iterating through the objects.
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        try {
            for (UpdateAllReports r: updateAllReports) {
                futures.add(CompletableFuture.runAsync(() ->
                    googleSheetsGateway.campaignMetricsToSheets(
                        r.getSpreadsheetId(),
                        r.getClient() + "-campanhas",
                        googleAdsGateway.getCampaignMetrics(
                            r.getCustomerId(), r.getStartDate(), r.getEndDate(), r.getActive())
                    ), contextAwareExecutor
                ));
                futures.add(CompletableFuture.runAsync(() ->
                    googleSheetsGateway.sendAdTitleAndDescription(
                        r.getSpreadsheetId(),
                        r.getClient() + "-anuncios",
                        googleAdsGateway.getAdTitleAndDescriptions(
                            r.getCustomerId(), r.getStartDate(), r.getEndDate())
                    ), contextAwareExecutor
                ));
                futures.add(CompletableFuture.runAsync(() ->
                    googleSheetsGateway.sendKeywordMetrics(
                        r.getSpreadsheetId(),
                        r.getClient() + "-keywords",
                        googleAdsGateway.getKeywordMetrics(
                            r.getCustomerId(), r.getStartDate(), r.getEndDate(), r.getActive())
                    ), contextAwareExecutor
                ));
                futures.add(CompletableFuture.runAsync(() ->
                    googleSheetsGateway.totalPerDayToSheets(
                        r.getSpreadsheetId(),
                        r.getClient() + "-grafico",
                        googleAdsGateway.getTotalPerDay(
                            r.getCustomerId(), r.getStartDate(), r.getEndDate())
                    ), contextAwareExecutor
                ));
            }
            // Here all of 4×N tasks are simultaneous
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        } catch (Exception e) {
            throw new GoogleSheetsException(e.getMessage());
        }
    }
}
