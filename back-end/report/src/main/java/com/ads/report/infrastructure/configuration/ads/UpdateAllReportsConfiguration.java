package com.ads.report.infrastructure.configuration.ads;

import com.ads.report.application.gateway.ads.GoogleAdsGateway;
import com.ads.report.application.gateway.sheets.GoogleSheetsGateway;
import com.ads.report.application.usecases.ads.GoogleAdsUseCase;
import com.ads.report.application.usecases.ads.UpdateAllReportsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;

/**
 * The configuration of the use case of updating various reports at same time.
 *
 * <p>Here we create the adwords beans..<p/>
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
@Configuration
public class UpdateAllReportsConfiguration {

    @Bean
    public UpdateAllReportsUseCase updateAllReportsUseCase(GoogleSheetsGateway googleSheetsGateway,
                                                           GoogleAdsUseCase googleAdsUseCase,
                                                           Executor contextAwareExecutor) {
        return new UpdateAllReportsUseCase(googleSheetsGateway, googleAdsUseCase, contextAwareExecutor);
    }
}
