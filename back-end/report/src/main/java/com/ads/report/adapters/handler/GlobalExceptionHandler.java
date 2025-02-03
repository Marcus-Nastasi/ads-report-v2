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
 * The exception handler class.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Object> handleForbiddenException(ForbiddenException exception, WebRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(GoogleAdsException.class)
    public ResponseEntity<Object> handleAdsException(GoogleAdsException exception, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(GoogleSheetsException.class)
    public ResponseEntity<Object> handleSheetsException(GoogleSheetsException exception, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(CsvException.class)
    public ResponseEntity<Object> handleCsvException(CsvException exception, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleUnknownRuntime(RuntimeException exception, WebRequest request) {
        return ResponseEntity.internalServerError().body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnknownException(Exception exception, WebRequest request) {
        return ResponseEntity.internalServerError().body(Map.of("error", exception.getMessage()));
    }
}
