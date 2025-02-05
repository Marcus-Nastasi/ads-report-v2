package com.ads.report.application.exception;

import java.io.Serial;

/**
 *
 * The Google Sheets Exception.
 *
 * @author Marcus Nastasi
 * @version 1.0.2
 * @since 2025
 * */
public class GoogleSheetsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public GoogleSheetsException(String message) {
        super(message);
    }
}
