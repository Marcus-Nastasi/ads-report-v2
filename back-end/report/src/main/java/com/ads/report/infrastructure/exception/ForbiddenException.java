package com.ads.report.infrastructure.exception;

import java.io.Serial;

/**
 *
 * The Forbidden Exception.
 *
 * @author Marcus Nastasi
 * @version 1.0.2
 * @since 2025
 * */
public class ForbiddenException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ForbiddenException(String message) {
        super(message);
    }
}
