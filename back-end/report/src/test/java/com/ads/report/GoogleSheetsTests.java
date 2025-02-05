package com.ads.report;

import com.ads.report.application.exception.GoogleSheetsException;
import com.ads.report.application.gateway.sheets.GoogleSheetsGateway;
import com.ads.report.application.usecases.sheets.GoogleSheetsUseCase;
import com.ads.report.domain.account.AccountMetrics;
import com.ads.report.domain.campaign.CampaignKeywordMetrics;
import com.ads.report.domain.campaign.CampaignMetrics;
import com.ads.report.domain.campaign.CampaignPerDay;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * The Google Ads use cases tests.
 *
 * @author Marcus Nastasi
 * @version 1.0.2
 * @since 2025
 * */
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

    // Campaign metrics mock objects.
    CampaignMetrics campaignMetric1 = new CampaignMetrics("2025-01-01", "MONDAY", 1323242L, "campaignName1", "adGroupName1", "ENABLED", 23782378L, 19872L, 342d, 4323d, 0.4, 0.8, 0.56);
    CampaignMetrics campaignMetric2 = new CampaignMetrics("2025-01-06", "WEDNESDAY", 1323792L, "campaignName2", "adGroupName2", "ENABLED", 78979L, 782L, 200d, 3423d, 0.4, 0.8, 0.56);
    List<CampaignMetrics> campaignMetrics1 = new ArrayList<>(List.of(campaignMetric1));
    List<CampaignMetrics> campaignMetrics2 = new ArrayList<>(List.of(campaignMetric2));

    // Campaign per day mock object.
    CampaignPerDay campaignPerDay1 = new CampaignPerDay("2025-01-01", 2312312L, 42342L, 423d, 12323d, 4, "MONDAY");
    CampaignPerDay campaignPerDay2 = new CampaignPerDay("2025-01-05", 439284L, 8942L, 231d, 54343d, 8, "MONDAY");
    List<CampaignPerDay> campaignPerDays1 = new ArrayList<>(List.of(campaignPerDay1));
    List<CampaignPerDay> campaignPerDays2 = new ArrayList<>(List.of(campaignPerDay2));

    // Keyword metrics mock object.
    CampaignKeywordMetrics campaignKeywordMetric1 = new CampaignKeywordMetrics("2025-01-01", "MONDAY", "campaignName 1", "act", "adGroupName 1", "text", "matchType", 43213L, 42342L, 0, 0, 23, 0);
    CampaignKeywordMetrics campaignKeywordMetric2 = new CampaignKeywordMetrics("2025-01-8", "MONDAY", "campaignName 2", "act", "adGroupName 2", "text", "matchType", 43213L, 42342L, 0, 0, 23, 0);
    List<CampaignKeywordMetrics> campaignKeywordMetrics1 = new ArrayList<>(List.of(campaignKeywordMetric1));
    List<CampaignKeywordMetrics> campaignKeywordMetrics2 = new ArrayList<>(List.of(campaignKeywordMetric2));

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

    /**
     *
     * Testing the method sendCampaignMetricsToSpreadsheet.
     *
     */
    @Test
    void sendCampaignMetricsToSpreadsheet() {
        // Mocking results and throws.
        doNothing().when(googleSheetsGateway).campaignMetricsToSheets("123", "dma", campaignMetrics1);
        doThrow(GoogleSheetsException.class).when(googleSheetsGateway).campaignMetricsToSheets("234", "am", campaignMetrics2);

        // Asserting behavior.
        assertDoesNotThrow(() -> {
            googleSheetsUseCase.sendCampaignMetricsToSpreadsheet("123", "dma", campaignMetrics1);
        });
        assertThrows(GoogleSheetsException.class, () -> {
            googleSheetsUseCase.sendCampaignMetricsToSpreadsheet("234", "am", campaignMetrics2);
        });

        // Verifying calls.
        verify(googleSheetsGateway, times(2)).campaignMetricsToSheets(anyString(), anyString(), anyList());
    }

    /**
     *
     * Testing the method sendTotalPerDaysToSpreadsheet.
     *
     */
    @Test
    void sendTotalPerDaysToSpreadsheet() {
        // Mocking results and throws.
        doNothing().when(googleSheetsGateway).totalPerDayToSheets("123", "dma", campaignPerDays1);
        doThrow(GoogleSheetsException.class).when(googleSheetsGateway).totalPerDayToSheets("234", "am", campaignPerDays2);

        // Asserting behavior.
        assertDoesNotThrow(() -> {
            googleSheetsUseCase.sendTotalPerDaysToSpreadsheet("123", "dma", campaignPerDays1);
        });
        assertThrows(GoogleSheetsException.class, () -> {
            googleSheetsUseCase.sendTotalPerDaysToSpreadsheet("234", "am", campaignPerDays2);
        });

        // Verifying calls.
        verify(googleSheetsGateway, times(2)).totalPerDayToSheets(anyString(), anyString(), anyList());
    }

    /**
     *
     * Testing the method sendKeywordMetricsToSpreadsheet.
     *
     */
    @Test
    void sendKeywordMetricsToSpreadsheet() {
        // Mocking results and throws.
        doNothing().when(googleSheetsGateway).sendKeywordMetrics("123", "dma", campaignKeywordMetrics1);
        doThrow(GoogleSheetsException.class).when(googleSheetsGateway).sendKeywordMetrics("234", "am", campaignKeywordMetrics2);

        // Asserting behavior.
        assertDoesNotThrow(() -> {
            googleSheetsUseCase.sendKeywordMetricsToSpreadsheet("123", "dma", campaignKeywordMetrics1);
        });
        assertThrows(GoogleSheetsException.class, () -> {
            googleSheetsUseCase.sendKeywordMetricsToSpreadsheet("234", "am", campaignKeywordMetrics2);
        });

        // Verifying calls.
        verify(googleSheetsGateway, times(2)).sendKeywordMetrics(anyString(), anyString(), anyList());
    }
}
