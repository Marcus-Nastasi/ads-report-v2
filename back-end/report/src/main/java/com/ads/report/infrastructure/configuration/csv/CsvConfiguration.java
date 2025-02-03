package com.ads.report.infrastructure.configuration.csv;

import com.ads.report.application.gateway.csv.CsvGateway;
import com.ads.report.application.usecases.csv.CsvUseCase;
import com.ads.report.infrastructure.gateway.csv.CsvRepoGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * The csv context configuration class.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
@Configuration
public class CsvConfiguration {

    @Bean
    public CsvGateway jsonToCsvGateway() {
        return new CsvRepoGateway();
    }

    @Bean
    public CsvUseCase csvUseCase(CsvGateway csvGateway) {
        return new CsvUseCase(csvGateway);
    }
}
