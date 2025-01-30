package com.ads.report.infrastructure.configuration;

import com.ads.report.application.gateway.GoogleAdsGateway;
import com.ads.report.application.gateway.GoogleSheetsGateway;
import com.ads.report.application.usecases.GoogleAdsUseCase;
import com.ads.report.application.usecases.UpdateAllReportsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateAllReportsConfiguration {

    @Bean
    public UpdateAllReportsUseCase updateAllReportsUseCase(GoogleAdsGateway googleAdsGateway, GoogleSheetsGateway googleSheetsGateway, GoogleAdsUseCase googleAdsUseCase) {
        return new UpdateAllReportsUseCase(googleAdsGateway, googleSheetsGateway, googleAdsUseCase);
    }
}
