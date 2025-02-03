package com.ads.report.infrastructure.configuration.csv;

import com.ads.report.application.gateway.csv.JsonToCsvGateway;
import com.ads.report.application.usecases.csv.JsonToCsvUseCase;
import com.ads.report.infrastructure.gateway.csv.JsonToCsv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The csv context configuration class.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
@Configuration
public class CsvConfiguration {

    @Bean
    public JsonToCsv jsonToCsvConverter() {
        return new JsonToCsv();
    }

    @Bean
    public JsonToCsvGateway jsonToCsvGateway() {
        return new JsonToCsv();
    }

    @Bean
    public JsonToCsvUseCase jsonToCsvUseCase(JsonToCsvGateway jsonToCsvGateway) {
        return new JsonToCsvUseCase(jsonToCsvGateway);
    }
}
