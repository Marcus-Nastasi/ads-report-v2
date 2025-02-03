package com.ads.report.infrastructure.configuration.ads;

import com.ads.report.application.gateway.ads.GoogleAdsGateway;
import com.ads.report.application.gateway.sheets.GoogleSheetsGateway;
import com.ads.report.application.usecases.ads.GoogleAdsUseCase;
import com.ads.report.application.usecases.ads.UpdateAllReportsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateAllReportsConfiguration {

    @Bean
    public UpdateAllReportsUseCase updateAllReportsUseCase(GoogleAdsGateway googleAdsGateway, GoogleSheetsGateway googleSheetsGateway, GoogleAdsUseCase googleAdsUseCase) {
        return new UpdateAllReportsUseCase(googleAdsGateway, googleSheetsGateway, googleAdsUseCase);
    }
}
