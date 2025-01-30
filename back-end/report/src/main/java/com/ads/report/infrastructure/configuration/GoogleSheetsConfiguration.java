package com.ads.report.infrastructure.configuration;

import com.ads.report.application.gateway.GoogleSheetsGateway;
import com.ads.report.application.usecases.GoogleSheetsUseCase;
import com.ads.report.infrastructure.gateway.GoogleSheetsRepoGateway;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * The implementation of google sheets interface.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
@Configuration
public class GoogleSheetsConfiguration {

    /**
     * Google sheets client bean.
     *
     * @return A sheets client build.
     * @throws IOException Throws exception if fails to create or find path.
     */
    @Bean
    public Sheets googleSheetsService() throws IOException {
        InputStream resource = getClass().getClassLoader().getResourceAsStream("credentials.json");
        if (resource == null) throw new FileNotFoundException("Credentials not found in 'resources/credentials.json'");
        GoogleCredentials credentials = GoogleCredentials
            .fromStream(resource)
            .createScoped(List.of("https://www.googleapis.com/auth/spreadsheets"));
        return new Sheets.Builder(
            new NetHttpTransport(),
            new GsonFactory(),
            new HttpCredentialsAdapter(credentials)
        ).setApplicationName("Ads Report").build();
    }

    @Bean
    public GoogleSheetsRepoGateway googleSheetsRepoGateway() {
        return new GoogleSheetsRepoGateway();
    }

    @Bean
    public GoogleSheetsUseCase googleSheetsUseCase(GoogleSheetsGateway googleSheetsGateway) {
        return new GoogleSheetsUseCase(googleSheetsGateway);
    }
}
