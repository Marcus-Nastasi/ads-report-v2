package com.ads.report.infrastructure.configuration.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
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
@SecurityScheme(type = SecuritySchemeType.OAUTH2, scheme = "OAuth 2.0 flux")
public class SwaggerConfiguration {}
