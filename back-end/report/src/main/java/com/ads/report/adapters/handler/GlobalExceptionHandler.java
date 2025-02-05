package com.ads.report.adapters.handler;

import com.ads.report.application.exception.CsvException;
import com.ads.report.application.exception.GoogleAdsException;
import com.ads.report.application.exception.GoogleSheetsException;
import com.ads.report.infrastructure.exception.ForbiddenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 *
 * The exception handler class.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     *
     * Handler to forbidden exception, with 403 code.
     *
     * @param exception An instance of type ForbiddenException.
     * @param request The request param.
     *
     * @return Return a response entity of object type.
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Object> handleForbiddenException(ForbiddenException exception, WebRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", exception.getMessage()));
    }

    /**
     *
     * Handler to ads exception, with 400 code.
     *
     * @param exception An instance of type GoogleAdsException.
     * @param request The request param.
     *
     * @return Return a response entity of object type.
     */
    @ExceptionHandler(GoogleAdsException.class)
    public ResponseEntity<Object> handleAdsException(GoogleAdsException exception, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", exception.getMessage()));
    }

    /**
     *
     * Handler to sheets exception, with 400 code.
     *
     * @param exception An instance of type GoogleSheetsException.
     * @param request The request param.
     *
     * @return Return a response entity of object type.
     */
    @ExceptionHandler(GoogleSheetsException.class)
    public ResponseEntity<Object> handleSheetsException(GoogleSheetsException exception, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", exception.getMessage()));
    }

    /**
     *
     * Handler to CSV exception, with 400 code.
     *
     * @param exception An instance of type CsvException.
     * @param request The request param.
     *
     * @return Return a response entity of object type.
     */
    @ExceptionHandler(CsvException.class)
    public ResponseEntity<Object> handleCsvException(CsvException exception, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", exception.getMessage()));
    }

    /**
     *
     * Handler to unknown runtime exceptions, with 500 code.
     *
     * @param exception An instance of type RuntimeException.
     * @param request The request param.
     *
     * @return Return a response entity of object type.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleUnknownRuntime(RuntimeException exception, WebRequest request) {
        return ResponseEntity.internalServerError().body(Map.of("error", exception.getMessage()));
    }

    /**
     *
     * Handler to unknown exceptions, with 500 code.
     *
     * @param exception An instance of type Exception.
     * @param request The request param.
     *
     * @return Return a response entity of object type.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnknownException(Exception exception, WebRequest request) {
        return ResponseEntity.internalServerError().body(Map.of("error", exception.getMessage()));
    }
}
