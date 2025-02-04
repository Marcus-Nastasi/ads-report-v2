package com.ads.report.adapters.resources.sheets;

import com.ads.report.application.usecases.ads.GoogleAdsUseCase;
import com.ads.report.application.usecases.sheets.GoogleSheetsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("v2/spreadsheets")
public class SheetsResource {

    @Autowired
    private GoogleAdsUseCase googleAdsUseCase;
    @Autowired
    private GoogleSheetsUseCase googleSheetsUseCase;

    /**
     *
     * This method allows the user to send campaign metrics directly from Google Ads to Google Sheets.
     *
     * <p>Here the user can pass an adwords customer id, a spreadsheet id and a tab, to send the data directly.<p/>
     *
     * @param customer_id The id of an adwords customer (client).
     * @param spreadsheet_id The Google Sheets id.
     * @param tab The spreadsheet's tab to write.
     * @return Returns a response entity 'ok' if successful.
     */
    @GetMapping("/campaign/{customer_id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Send all campaign metrics to sheets",
        description = "In this route you can send all metrics of a account separated by campaigns, in a certain period."
    )
    @ApiResponse(responseCode = "200", description = "Returning 200 and sending to sheets.")
    public ResponseEntity<String> campaignMetricsToSheets(
            @PathVariable("customer_id") String customer_id,
            @PathParam("start_date") String start_date,
            @PathParam("end_date") String end_date,
            @PathParam("spreadsheet_id") String spreadsheet_id,
            @PathParam("tab") String tab,
            @PathParam("active") boolean active) throws IOException {
        googleSheetsUseCase.campaignMetricsToSheets(spreadsheet_id, tab, googleAdsUseCase.getCampaignMetrics(customer_id, start_date, end_date, active));
        return ResponseEntity.ok("");
    }

    /**
     *
     * This method allows the user to send client account metrics directly from Google Ads to google sheets.
     *
     * <p>Here the user can pass an adwords customer id, a start date, end date,
     * a spreadsheet id and tab, to send the data directly.<p/>
     *
     * @param customer_id The id of an adwords customer (client).
     * @param start_date The start date of the analysis period.
     * @param end_date The end date of the analysis period.
     * @param spreadsheet_id The Google Sheets id.
     * @param tab The spreadsheets tab to write.
     * @return Returns a response entity 'ok' if successful.
     */
    @GetMapping("/account/{customer_id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Send all account metrics to sheets",
        description = "In this route you can send all metrics of a account, in a certain period."
    )
    @ApiResponse(responseCode = "200", description = "Returning 200 and sending to sheets.")
    public ResponseEntity<String> accountMetricsToSheet(
            @PathVariable("customer_id") String customer_id,
            @PathParam("start_date") String start_date,
            @PathParam("end_date") String end_date,
            @PathParam("spreadsheet_id") String spreadsheet_id,
            @PathParam("tab") String tab) throws IOException {
        googleSheetsUseCase.sendAccountMetricsToSpreadsheet(spreadsheet_id, tab, googleAdsUseCase.getAccountMetrics(customer_id, start_date, end_date));
        return ResponseEntity.ok("");
    }

    /**
     *
     * This method allows the user to send client account metrics, separated per days,
     * directly from Google Ads to google sheets.
     *
     * <p>
     * Here the user can pass an adwords customer id, a start date, end date,
     * a spreadsheet id and tab, to send metrics per day directly without needing
     * to download a csv.
     * <p/>
     *
     * @param customer_id The id of an adwords customer (client).
     * @param start_date The start date of the analysis period.
     * @param end_date The end date of the analysis period.
     * @param spreadsheet_id The google sheets id.
     * @param tab The sheets tab to write.
     * @return Returns a response entity ok if successful.
     */
    @GetMapping("/campaign/days/{customer_id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Send all campaign metrics to sheets, separated by days",
        description = "In this route you can send all metrics of a campaign, separated by campaigns and days, in a certain period."
    )
    @ApiResponse(responseCode = "200", description = "Returning 200 and sending to sheets.")
    public ResponseEntity<String> getTotalPerDay(
            @PathVariable("customer_id") String customer_id,
            @PathParam("start_date") String start_date,
            @PathParam("end_date") String end_date,
            @PathParam("spreadsheet_id") String spreadsheet_id,
            @PathParam("tab") String tab) throws IOException {
        googleSheetsUseCase.totalPerDaysToSheet(spreadsheet_id, tab, googleAdsUseCase.getTotalPerDay(customer_id, start_date, end_date));
        return ResponseEntity.ok("");
    }

    /**
     *
     * This method allows the user to send all the keyword metrics from an account, filtering by period.
     *
     * @param customerId The id of an adwords customer (client).
     * @param start_date The start date of the analysis period.
     * @param end_date The end date of the analysis period.
     * @return Returns a response entity ok if successful.
     */
    @GetMapping("/keywords/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Send all keyword metrics to sheets",
        description = "In this route you can send all keyword metrics in a certain period."
    )
    @ApiResponse(responseCode = "200", description = "Returning 200 and sending to sheets.")
    public ResponseEntity<String> sendKeywordMetrics(
            @PathVariable("customerId") String customerId,
            @PathParam("start_date") String start_date,
            @PathParam("end_date") String end_date,
            @PathParam("spreadsheet_id") String spreadsheet_id,
            @PathParam("tab") String tab,
            @PathParam("active") boolean active) throws IOException {
        googleSheetsUseCase.sendKeywordMetrics(spreadsheet_id, tab, googleAdsUseCase.getKeywordMetrics(customerId, start_date, end_date, active));
        return ResponseEntity.ok("");
    }

    /**
     *
     * This method allows the user to send all the title and description metrics from an account, filtering by period.
     *
     * @param customerId The id of an adwords customer (client).
     * @param start_date The start date of the analysis period.
     * @param end_date The end date of the analysis period.
     * @return Returns a response entity ok if successful.
     */
    @GetMapping("/headlines/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Send all title and descriptions, and its metrics to sheets",
        description = "In this route you can send all title, descriptions and its metrics, in a certain period."
    )
    @ApiResponse(responseCode = "200", description = "Returning 200 and sending to sheets.")
    public ResponseEntity<String> sendAdTitle(
            @PathVariable("customerId") String customerId,
            @PathParam("start_date") String start_date,
            @PathParam("end_date") String end_date,
            @PathParam("spreadsheet_id") String spreadsheet_id,
            @PathParam("tab") String tab) throws IOException {
        googleSheetsUseCase.sendAdTitleAndDescription(spreadsheet_id, tab, googleAdsUseCase.getAdTitleAndDescriptions(customerId, start_date, end_date));
        return ResponseEntity.ok("");
    }
}
