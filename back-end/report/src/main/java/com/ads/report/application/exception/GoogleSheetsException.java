package com.ads.report.application.exception;

import java.io.Serial;

public class GoogleSheetsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public GoogleSheetsException(String message) {
        super(message);
    }
}
