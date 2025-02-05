package com.ads.report.adapters.output;

import java.io.Serializable;
import java.util.List;

/**
 *
 * The test response DTO record.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public record TestResponseDto(String status, List<String> accessible_accounts) implements Serializable {}
