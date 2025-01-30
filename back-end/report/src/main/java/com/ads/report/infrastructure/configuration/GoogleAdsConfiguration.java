package com.ads.report.infrastructure.configuration;

import com.ads.report.adapters.mappers.GoogleAdsDtoMapper;
import com.ads.report.application.gateway.GoogleAdsGateway;
import com.ads.report.application.usecases.GoogleAdsUseCase;
import com.ads.report.infrastructure.gateway.GoogleAdsRepoGateway;
import com.google.ads.googleads.lib.GoogleAdsClient;
import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The google ads configuration class.
 *
 * <p>Here we create the adwords client's bean, based
 * on the ads.properties file.<p/>
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
@Configuration
public class GoogleAdsConfiguration {

    /**
     * Bean that generates the google ads client.
     *
     * @return Return the adwords client based on an ads.properties file.
     * @throws IOException If fails to create the client with builder.
     */
    @Bean
    public GoogleAdsClient googleAdsClient() throws IOException {
        InputStream resource = getClass().getClassLoader().getResourceAsStream("ads.properties");
        if (resource == null) throw new IllegalArgumentException("File 'ads.properties' not found on classpath");
        Properties properties = new Properties();
        properties.load(resource);
        return GoogleAdsClient.newBuilder().fromProperties(properties).build();
    }

    @Bean
    public GoogleAdsGateway googleAdsGateway() {
        return new GoogleAdsRepoGateway();
    }

    @Bean
    public GoogleAdsUseCase googleAdsUseCase(GoogleAdsGateway googleAdsGateway) {
        return new GoogleAdsUseCase(googleAdsGateway);
    }

    @Bean
    public GoogleAdsDtoMapper googleAdsDtoMapper() {
        return new GoogleAdsDtoMapper();
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }
}
