package com.ads.report.adapters.input;

import com.ads.report.domain.reports.UpdateAllReports;

import java.util.List;

/**
 *
 * The DTO to update various reports.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public record UpdateAllReportsRequestDto(List<UpdateAllReports> data) {}
