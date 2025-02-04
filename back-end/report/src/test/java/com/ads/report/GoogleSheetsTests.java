package com.ads.report;

import com.ads.report.application.exception.GoogleSheetsException;
import com.ads.report.application.gateway.sheets.GoogleSheetsGateway;
import com.ads.report.application.usecases.sheets.GoogleSheetsUseCase;
import com.ads.report.domain.account.AccountMetrics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GoogleSheetsTests {

    @Mock
    private GoogleSheetsGateway googleSheetsGateway;
    @InjectMocks
    private GoogleSheetsUseCase googleSheetsUseCase;

    // Account metrics mock objects.
    AccountMetrics accountMetric1 = new AccountMetrics(0L, "name 1", 0L, 0L, 0, 0, 0, 0, 0);
    AccountMetrics accountMetric2 = new AccountMetrics(0L, "name 2", 0L, 0L, 0, 0, 0, 0, 0);
    AccountMetrics accountMetric3 = new AccountMetrics(0L, "name 3", 0L, 0L, 0, 0, 0, 0, 0);
    List<AccountMetrics> accountMetrics1 = new ArrayList<>(List.of(accountMetric1, accountMetric2));
    List<AccountMetrics> accountMetrics2 = new ArrayList<>(List.of(accountMetric3));

    /**
     *
     * Testing the method sendAccountMetricsToSpreadsheet.
     *
     */
    @Test
    void sendAccountMetricsToSpreadsheet() {
        // Mocking results and throws.
        doNothing().when(googleSheetsGateway).accountMetricsToSheets("123", "dma", accountMetrics1);
        doThrow(GoogleSheetsException.class).when(googleSheetsGateway).accountMetricsToSheets("234", "am", accountMetrics2);

        // Asserting behavior.
        assertDoesNotThrow(() -> {
            googleSheetsUseCase.sendAccountMetricsToSpreadsheet("123", "dma", accountMetrics1);
        });
        assertThrows(GoogleSheetsException.class, () -> {
            googleSheetsUseCase.sendAccountMetricsToSpreadsheet("234", "am", accountMetrics2);
        });

        // Verifying calls.
        verify(googleSheetsGateway, times(2)).accountMetricsToSheets(anyString(), anyString(), anyList());
    }
}
