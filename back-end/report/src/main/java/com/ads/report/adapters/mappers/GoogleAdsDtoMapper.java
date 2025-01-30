package com.ads.report.adapters.mappers;

import com.ads.report.adapters.output.TestResponseDto;

import java.util.List;

/**
 * The class that contains the adwords dto mappers.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public class GoogleAdsDtoMapper {

    /**
     *
     * @param googleAdsRows A list of strings that contains the rows returned by adwords
     * @return Return the dto 'TestResponseDto'
     */
    public TestResponseDto mapToResponse(List<String> googleAdsRows) {
        return new TestResponseDto(googleAdsRows.getFirst(), googleAdsRows.subList(1, googleAdsRows.size()));
    }
}
