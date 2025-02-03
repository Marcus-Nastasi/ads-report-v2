package com.ads.report.infrastructure.gateway.csv;

import com.ads.report.application.exception.CsvException;
import com.ads.report.application.gateway.csv.CsvGateway;
import com.opencsv.CSVWriter;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

/**
 *
 * The class that contains the methods that convert json objects to csv files.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public class CsvRepoGateway implements CsvGateway {

    /**
     *
     * Method to convert from json to csv.
     *
     * <p>This method uses an algorithm and the CSVWriter library to convert from JSON to CSV type, and write into response.<p/>
     *
     * @param records  The json object converte to raw type.
     * @param response The response object.
     * @throws CsvException if fails to parse Json to CSV.
     */
    @Override
    public HttpServletResponse fromJson(List<Map<String, Object>> records, HttpServletResponse response) throws CsvException {
        try (CSVWriter writer = new CSVWriter(response.getWriter())) {
            if (!records.isEmpty()) {
                String[] headers = records.getFirst().keySet().toArray(new String[0]);
                writer.writeNext(headers);
            }
            for (Map<String, Object> record : records) {
                String[] data = record.values().stream().map(String::valueOf).toArray(String[]::new);
                writer.writeNext(data);
            }
            return response;
        } catch (Exception e) {
            throw new CsvException("Error parsing to CSV: " + e.getMessage());
        }
    }
}
