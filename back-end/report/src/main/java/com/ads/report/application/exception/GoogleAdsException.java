package com.ads.report.application.exception;

import java.io.Serial;

public class GoogleAdsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public GoogleAdsException(String message) {
        super(message);
    }
}
