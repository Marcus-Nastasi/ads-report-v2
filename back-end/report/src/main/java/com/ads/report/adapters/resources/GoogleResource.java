package com.ads.report.adapters.resources;

import com.ads.report.adapters.input.UpdateAllReportsRequestDto;
import com.ads.report.adapters.mappers.GoogleAdsDtoMapper;
import com.ads.report.adapters.output.TestResponseDto;
import com.ads.report.application.usecases.GoogleAdsUseCase;
import com.ads.report.application.usecases.GoogleSheetsUseCase;
import com.ads.report.application.usecases.JsonToCsvUseCase;
import com.ads.report.application.usecases.UpdateAllReportsUseCase;
import com.ads.report.domain.manager.ManagerAccountInfo;
import com.ads.report.domain.account.AccountMetrics;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * The controller to the application.
 *
 * <p>
 * This represents the controller of the application, making possible to request api calls.
 * To test request, you can request from route /api/reports and look at the routs bellow.
 * <p/>
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
@RestController
@RequestMapping("v1/reports")
public class GoogleResource {

    @Autowired
    private GoogleAdsUseCase googleAdsUseCase;
    @Autowired
    private GoogleAdsDtoMapper googleAdsDtoMapper;
    @Autowired
    private GoogleSheetsUseCase googleSheetsUseCase;
    @Autowired
    private UpdateAllReportsUseCase updateAllReportsUseCase;
    @Autowired
    private Gson gson;
    @Autowired
    private JsonToCsvUseCase jsonToCsv;

    /**
     * Test the connection between the application and the google account.
     *
     * @return The TestResponseDto
     */
    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Test the connection", description = "In this route you can test your Google Account connection.")
    @ApiResponse(responseCode = "200", description = "Returning the accessible accounts.")
    public ResponseEntity<TestResponseDto> test() {
        return ResponseEntity.ok(googleAdsDtoMapper.mapToResponse(googleAdsUseCase.testConnection()));
    }

    /**
     * Recover the general data of the manager account specified.
     *
     * @param id The id of an adwords customer (client).
     * @return A object of type ManagerAccountInfo, that contains the general MCC info.
     */
    @GetMapping("/manager/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get manager (MCC) account", description = "In this route you can get general data from MCC.")
    @ApiResponse(responseCode = "200", description = "Returning general data from MCC.")
    public ResponseEntity<ManagerAccountInfo> getManagerAccount(@PathVariable("id") String id) {
        return ResponseEntity.ok(googleAdsUseCase.getManagerAccount(id));
    }

    /**
     * Endpoint to get All campaigns metrics.
     *
     * <p>
     * This method uses an algorithm and the CSVWriter library
     * to convert the JSON to CSV type.
     * <p/>
     *
     * @param customerId The id of an adwords customer (client).
     * @param response The response object.
     */
    @GetMapping("/csv/campaign/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Get all campaign metrics (CSV)",
        description = "In this route you can get all metrics separated by campaigns, in a certain period."
    )
    @ApiResponse(responseCode = "200", description = "Returning the CSV with campaign metrics.")
    public void getAllCampaignMetrics(
            @PathVariable String customerId,
            @PathParam("start_date") String start_date,
            @PathParam("end_date") String end_date,
            @PathParam("active") boolean active,
            HttpServletResponse response) {
        String json = gson.toJson(googleAdsUseCase.getCampaignMetrics(customerId, start_date, end_date, active));
        String fileName = "campaigns-"+customerId+".csv";
        List<Map<String, Object>> records = gson.fromJson(json, new TypeToken<List<Map<String, Object>>>() {}.getType());
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=" + '"' + fileName + '"');
        jsonToCsv.convert(records, response);
    }

    /**
     * Endpoint to get aggregated metrics from one client account.
     *
     * @param customerId The id of an adwords customer (client).
     * @param start_date The start date of the analysis period.
     * @param end_date The end date of the analysis period.
     */
    @GetMapping("/csv/account/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Get all account metrics (CSV)",
        description = "In this route you can get all metrics of an account, in a certain period."
    )
    @ApiResponse(responseCode = "200", description = "Returning the CSV with account metrics.")
    public void getAccountMetrics(
            @PathVariable("customerId") String customerId,
            @PathParam("start_date") String start_date,
            @PathParam("end_date") String end_date,
            @PathParam("type") String type,
            HttpServletResponse response) {
        List<AccountMetrics> accountMetrics = googleAdsUseCase.getAccountMetrics(customerId, start_date, end_date);
        String json = gson.toJson(accountMetrics);
        String fileName = "account-metrics-"+accountMetrics.getFirst().getDescriptiveName()+"-"+start_date+"-"+end_date+".csv";
        List<Map<String, Object>> records = gson.fromJson(json, new TypeToken<List<Map<String, Object>>>() {}.getType());
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=" + '"' + fileName + '"');
        jsonToCsv.convert(records, response);
    }

    /**
     * This method allows the user to send campaign metrics directly from google ads to google sheets.
     *
     * <p>
     * Here the user can pass a adwords customer id, a spreadsheet id and tab, to send the data
     * directly without needing to download a csv.
     * <p/>
     *
     * @param customer_id The id of an adwords customer (client).
     * @param spreadsheet_id The google sheets id.
     * @param tab The sheets tab to write.
     * @return Returns a response entity ok if successful.
     */
    @GetMapping("/sheets/campaign/{customer_id}")
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
     * This method allows the user to send client account metrics directly from google ads to google sheets.
     *
     * <p>
     * Here the user can pass a adwords customer id, a start date, end date,
     * a spreadsheet id and tab, to send the data directly without needing
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
    @GetMapping("/sheets/account/{customer_id}")
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
        googleSheetsUseCase.accountMetricsToSheets(spreadsheet_id, tab, googleAdsUseCase.getAccountMetrics(customer_id, start_date, end_date));
        return ResponseEntity.ok("");
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
     * @param customer_id The id of an adwords customer (client).
     * @param start_date The start date of the analysis period.
     * @param end_date The end date of the analysis period.
     * @param spreadsheet_id The google sheets id.
     * @param tab The sheets tab to write.
     * @return Returns a response entity ok if successful.
     */
    @GetMapping("/sheets/campaign/days/{customer_id}")
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
     * This method allows the user to send all the keyword metrics from an account, filtering by period.
     *
     * @param customerId The id of an adwords customer (client).
     * @param start_date The start date of the analysis period.
     * @param end_date The end date of the analysis period.
     * @return Returns a response entity ok if successful.
     */
    @GetMapping("/sheets/keywords/{customerId}")
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
     * This method allows the user to send all the title and description metrics from an account, filtering by period.
     *
     * @param customerId The id of an adwords customer (client).
     * @param start_date The start date of the analysis period.
     * @param end_date The end date of the analysis period.
     * @return Returns a response entity ok if successful.
     */
    @GetMapping("/sheets/headlines/{customerId}")
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

    /**
     *
     * This endpoint allows the user to send data to sheets, from various accounts.
     *
     * <p>
     * By passing the customer id, start date, end date, spreadsheet id, client and active flag,
     * you can update the sheets tables with ease.
     * <p/>
     *
     * @param allReportsRequestDto the list of UpdateAllReports domain object.
     * @return ok if the cll is successful
     * @throws IOException throws exception if fails
     */
    @PostMapping("/generate")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Generate various reports",
        description = "In this route you can send all client metrics with one request, " +
            "by passing the data from all the clients you want to on the request body."
    )
    @ApiResponse(responseCode = "200", description = "Returning 200 and sending to sheets.")
    public ResponseEntity<String> generateAllReports(
            @RequestBody @Valid UpdateAllReportsRequestDto allReportsRequestDto) throws IOException {
        updateAllReportsUseCase.updateReports(allReportsRequestDto.data());
        return ResponseEntity.ok("");
    }
}
