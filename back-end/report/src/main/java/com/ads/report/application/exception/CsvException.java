package com.ads.report.application.exception;

import java.io.Serial;

/**
 *
 * The CSV Exception.
 *
 * @author Marcus Nastasi
 * @version 1.0.2
 * @since 2025
 * */
public class CsvException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CsvException(String message) {
        super(message);
    }
}
