package com.ads.report.application.exception;

import java.io.Serial;

/**
 *
 * <p>The Google Sheets Exception.<p/>
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
