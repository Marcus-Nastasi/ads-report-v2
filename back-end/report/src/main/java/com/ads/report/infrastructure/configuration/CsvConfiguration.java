package com.ads.report.infrastructure.configuration;

import com.ads.report.application.gateway.JsonToCsvGateway;
import com.ads.report.application.usecases.JsonToCsvUseCase;
import com.ads.report.infrastructure.gateway.JsonToCsv;
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
