package com.ads.report.application.gateway;

import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

/**
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
public interface JsonToCsvGateway {

    /**
     * Convert a json object to csv type file.
     *
     * @param records The json.
     * @param response The response of the http call.
     * @return The response with the converted csv written.
     */
    HttpServletResponse convert(List<Map<String, Object>> records, HttpServletResponse response);
}
