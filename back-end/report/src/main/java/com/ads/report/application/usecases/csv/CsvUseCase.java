package com.ads.report.application.usecases.csv;

import com.ads.report.application.exception.CsvException;
import com.ads.report.application.gateway.csv.CsvGateway;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

/**
 *
 * The interface of the google ads api calls.
 *
 * <p>
 * This represents the interface that communicates the application with the infra layer,
 * making possible to make api calls.
 * <p/>
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public class CsvUseCase {

    private final CsvGateway csvGateway;

    public CsvUseCase(CsvGateway csvGateway) {
        this.csvGateway = csvGateway;
    }

    /**
     *
     * <p>Convert a json object to csv type file.<p/>
     *
     * @param records The json.
     * @param response The response of the http call.
     *
     * @return The response with the converted csv written.
     * @throws CsvException if fails to parse from Json to CSV.
     */
    public HttpServletResponse fromJson(List<Map<String, Object>> records, HttpServletResponse response) throws CsvException {
        return csvGateway.fromJson(records, response);
    }
}
