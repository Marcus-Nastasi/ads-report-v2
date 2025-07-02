package com.ads.report.adapters.mappers;

import com.ads.report.adapters.output.TestResponseDto;

import java.util.List;

/**
 * The class that contains the adwords DTO mappers.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public class GoogleAdsDtoMapper {

    /**
     * Map from Google Ads Rows to {@link TestResponseDto}.
     *
     * @param googleAdsRows A list of {@link String} that contains the rows returned by adwords.
     *
     * @return Return the dto {@link TestResponseDto}
     */
    public TestResponseDto mapToResponse(List<String> googleAdsRows) {
        return new TestResponseDto(googleAdsRows.getFirst(), googleAdsRows.subList(1, googleAdsRows.size()));
    }
}
