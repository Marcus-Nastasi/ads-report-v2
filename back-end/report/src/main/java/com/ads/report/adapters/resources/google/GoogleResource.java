package com.ads.report.adapters.resources.google;

import com.ads.report.adapters.input.UpdateAllReportsRequestDto;
import com.ads.report.adapters.mappers.GoogleAdsDtoMapper;
import com.ads.report.adapters.output.TestResponseDto;
import com.ads.report.application.usecases.ads.GoogleAdsUseCase;
import com.ads.report.application.usecases.ads.UpdateAllReportsUseCase;
import com.ads.report.domain.manager.ManagerAccountInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * The controller to general Google resources.
 *
 * <p>This class represents the controller of general Google resources. To make api calls, you can request
 * from route /v2/reports and look at the routs bellow.<p/>
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
@RestController
@RequestMapping("v2/reports")
public class GoogleResource {

    @Autowired
    private GoogleAdsUseCase googleAdsUseCase;
    @Autowired
    private GoogleAdsDtoMapper googleAdsDtoMapper;
    @Autowired
    private UpdateAllReportsUseCase updateAllReportsUseCase;

    /**
     *
     * est the connection between the application and the Google Account.
     *
     * <p>This route returns an object that consists in the connection status,
     * and a list of user's accessible accounts.<p/>
     *
     * @return The {@link TestResponseDto} object.
     */
    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Test the connection", description = "In this route you can test your Google Account connection.")
    @ApiResponse(responseCode = "200", description = "Returning the accessible accounts.")
    public ResponseEntity<TestResponseDto> test() {
        return ResponseEntity.ok(googleAdsDtoMapper.mapToResponse(googleAdsUseCase.testConnection()));
    }

    /**
     *
     * Recover the general data of the manager account specified.
     *
     * <p>In this route you can recover generic information from a manager account (MCC).<p/>
     *
     * @param id The id of an adwords customer (client).
     *
     * @return An object of type {@link ManagerAccountInfo}, that contains the general MCC info.
     */
    @GetMapping("/manager/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get manager (MCC) account", description = "In this route you can get general data from MCC.")
    @ApiResponse(responseCode = "200", description = "Returning general data from MCC.")
    public ResponseEntity<ManagerAccountInfo> getManagerAccount(@PathVariable("id") String id) {
        return ResponseEntity.ok(googleAdsUseCase.getManagerAccount(id));
    }

    /**
     *
     * This endpoint allows the user to send a conjunct of data to sheets, from various accounts.
     *
     * <p>By passing the customer id, start date, end date, spreadsheet id, client and active flag,
     * you can update the sheets tables with ease.<p/>
     *
     * @param allReportsRequestDto the list of {@link com.ads.report.domain.reports.UpdateAllReports} domain object.
     *
     * @return ok if the cll is successful.
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
            @RequestBody @Valid UpdateAllReportsRequestDto allReportsRequestDto) {
        updateAllReportsUseCase.updateReports(allReportsRequestDto.data());
        return ResponseEntity.ok("");
    }
}
