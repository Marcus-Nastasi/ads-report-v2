package com.ads.report.application.exception;

import java.io.Serial;

public class CsvException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CsvException(String message) {
        super(message);
    }
}
