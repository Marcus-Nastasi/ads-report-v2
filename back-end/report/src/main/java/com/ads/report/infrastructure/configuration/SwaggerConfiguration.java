package com.ads.report.infrastructure.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

/**
 * The swagger ui configuration.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
@Configuration
@OpenAPIDefinition(info = @Info(title = "Ads Report", description = "The ads report generator", version = "1.0.1"))
public class SwaggerConfiguration {}
