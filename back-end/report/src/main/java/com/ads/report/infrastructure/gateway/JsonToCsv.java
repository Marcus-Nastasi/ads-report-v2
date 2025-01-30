package com.ads.report.infrastructure.gateway;

import com.ads.report.application.gateway.JsonToCsvGateway;
import com.opencsv.CSVWriter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * The class that contains the methods that convert json objects to csv files.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public class JsonToCsv implements JsonToCsvGateway {

    /**
     * Method to convert json to csv.
     *
     * <p>
     * This method uses an algorithm and the CSVWriter library to convert the JSON to CSV type, and then
     * write into the response.
     * <p/>
     *
     * @param records  The json object converte to raw type.
     * @param response The response object.
     */
    @Override
    public HttpServletResponse convert(List<Map<String, Object>> records, HttpServletResponse response) {
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
