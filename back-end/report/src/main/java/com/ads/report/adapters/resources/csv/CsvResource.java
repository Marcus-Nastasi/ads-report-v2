package com.ads.report.adapters.resources.csv;

import com.ads.report.application.usecases.ads.GoogleAdsUseCase;
import com.ads.report.application.usecases.csv.CsvUseCase;
import com.ads.report.domain.account.AccountMetrics;
import com.ads.report.domain.campaign.CampaignKeywordMetrics;
import com.ads.report.domain.campaign.CampaignPerDay;
import com.ads.report.domain.campaign.CampaignTitleAndDescription;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v2/csv")
public class CsvResource {

    @Autowired
    private GoogleAdsUseCase googleAdsUseCase;
    @Autowired
    private Gson gson;
    @Autowired
    private CsvUseCase csvUseCase;

    /**
     *
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
    @GetMapping("/campaign/{customerId}")
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
        csvUseCase.fromJson(records, response);
    }

    /**
     *
     * Endpoint to get aggregated metrics from one client account.
     *
     * @param customerId The id of an adwords customer (client).
     * @param start_date The start date of the analysis period.
     * @param end_date The end date of the analysis period.
     */
    @GetMapping("/account/{customerId}")
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
            HttpServletResponse response) {
        List<AccountMetrics> accountMetrics = googleAdsUseCase.getAccountMetrics(customerId, start_date, end_date);
        String json = gson.toJson(accountMetrics);
        String fileName = "account-metrics-"+accountMetrics.getFirst().getDescriptiveName()+"-"+start_date+"-"+end_date+".csv";
        List<Map<String, Object>> records = gson.fromJson(json, new TypeToken<List<Map<String, Object>>>() {}.getType());
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=" + '"' + fileName + '"');
        csvUseCase.fromJson(records, response);
    }

    /**
     *
     * Endpoint to get keyword metrics from a client.
     *
     * @param customerId The id of an adwords customer (client).
     * @param start_date The start date of the analysis period.
     * @param end_date The end date of the analysis period.
     * @param active Flag to check if the campaign is enabled.
     */
    @GetMapping("/keywords/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Get all keyword metrics on CSV format",
        description = "In this route you can get all keyword metrics form a client, in a certain period."
    )
    @ApiResponse(responseCode = "200", description = "Returning the CSV with keyword metrics.")
    public void getCampaignKeywordMetrics(
            @PathVariable("customerId") String customerId,
            @PathParam("start_date") String start_date,
            @PathParam("end_date") String end_date,
            @PathParam("active") boolean active,
            HttpServletResponse response) {
        List<CampaignKeywordMetrics> campaignKeywordMetrics = googleAdsUseCase
            .getKeywordMetrics(customerId, start_date, end_date, active);
        String json = gson.toJson(campaignKeywordMetrics);
        String fileName = "keyword-metrics-"+campaignKeywordMetrics.getFirst().getAdvertisingChannelType()+"-"+start_date+"-"+end_date+".csv";
        List<Map<String, Object>> records = gson.fromJson(json, new TypeToken<List<Map<String, Object>>>() {}.getType());
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=" + '"' + fileName + '"');
        csvUseCase.fromJson(records, response);
    }

    /**
     *
     * Endpoint to get titles and descriptions from a client.
     *
     * @param customerId The id of an adwords customer (client).
     * @param start_date The start date of the analysis period.
     * @param end_date The end date of the analysis period.
     */
    @GetMapping("/headlines/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Get title and descriptions on CSV format",
        description = "In this route you can get all title and descriptions form a client, in a certain period."
    )
    @ApiResponse(responseCode = "200", description = "Returning the CSV with titles and descriptions.")
    public void getTitleAndDescriptionMetrics(
            @PathVariable("customerId") String customerId,
            @PathParam("start_date") String start_date,
            @PathParam("end_date") String end_date,
            HttpServletResponse response) {
        List<CampaignTitleAndDescription> campaignTitleAndDescriptions = googleAdsUseCase.getAdTitleAndDescriptions(customerId, start_date, end_date);
        String json = gson.toJson(campaignTitleAndDescriptions);
        String fileName = "headlines-"+campaignTitleAndDescriptions.getFirst().getResponsiveHeadlines()+"-"+start_date+"-"+end_date+".csv";
        List<Map<String, Object>> records = gson.fromJson(json, new TypeToken<List<Map<String, Object>>>() {}.getType());
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=" + '"' + fileName + '"');
        csvUseCase.fromJson(records, response);
    }

    /**
     *
     * Endpoint to get titles and descriptions from a client.
     *
     * @param customerId The id of an adwords customer (client).
     * @param start_date The start date of the analysis period.
     * @param end_date The end date of the analysis period.
     */
    @GetMapping("/campaign/days/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Get campaign per days metrics on CSV format",
        description = "In this route you can get campaign per days metrics form a client, in a certain period."
    )
    @ApiResponse(responseCode = "200", description = "Returning the CSV with campaign per days metrics.")
    public void getCampaignPerDaysMetrics(
            @PathVariable("customerId") String customerId,
            @PathParam("start_date") String start_date,
            @PathParam("end_date") String end_date,
            HttpServletResponse response) {
        List<CampaignPerDay> campaignPerDays = googleAdsUseCase.getTotalPerDay(customerId, start_date, end_date);
        String json = gson.toJson(campaignPerDays);
        String fileName = "campaign-per-days-"+customerId+"-"+start_date+"-"+end_date+".csv";
        List<Map<String, Object>> records = gson.fromJson(json, new TypeToken<List<Map<String, Object>>>() {}.getType());
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=" + '"' + fileName + '"');
        csvUseCase.fromJson(records, response);
    }
}
