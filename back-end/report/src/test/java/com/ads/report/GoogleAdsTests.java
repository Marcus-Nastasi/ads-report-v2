package com.ads.report;

import com.ads.report.application.usecases.GoogleAdsUseCase;
import com.ads.report.domain.account.AccountMetrics;
import com.ads.report.domain.campaign.CampaignKeywordMetrics;
import com.ads.report.domain.campaign.CampaignMetrics;
import com.ads.report.domain.campaign.CampaignTitleAndDescription;
import com.ads.report.domain.manager.ManagerAccountInfo;
import com.ads.report.infrastructure.gateway.GoogleAdsRepoGateway;
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
public class GoogleAdsTests {

    @Mock
    private GoogleAdsRepoGateway googleAdsRepoGateway;
    @InjectMocks
    private GoogleAdsUseCase googleAdsUseCase;

    // Campaign metrics objects and lists
    CampaignMetrics campaignMetrics1 = new CampaignMetrics(
        "2025-01-12",
        "MONDAY",
        4232301839L,
        "campaignName",
        "adGroupName",
        "ENABLED",
        10000L,
        200L,
        22.4,
        4d,
        0.4d,
        0.12d,
        13.456d
    );
    CampaignMetrics campaignMetrics2 = new CampaignMetrics(
        "2025-01-13",
        "TUESDAY",
        3094808323L,
        "campaignName2",
        "adGroupName2",
        "ENABLED",
        30000L,
        600L,
        12.4,
        4d,
        0.05d,
        0.01d,
        12.456d
    );
    List<CampaignMetrics> campaignMetrics = List.of(campaignMetrics1, campaignMetrics2);

    // Manager account information object.
    ManagerAccountInfo managerAccountInfo = new ManagerAccountInfo(
        "id",
        "name",
        "currency",
        "timeZone",
        true,
        "status",
        true,
        true,
        "trackingUrlTemplate",
        "finalUrlSuffix",
        321897L,
        "conversionTrackingStatus"
    );

    // Account metrics object.
    AccountMetrics accountMetrics1 = new AccountMetrics(2390828L, "", 0L, 0L, 0d, 0d, 0d, 0d, 0d);
    AccountMetrics accountMetrics2 = new AccountMetrics(43827423L, "", 0L, 0L, 0d, 0d, 0d, 0d, 0d);

    // Campaign metrics from keywords object.
    CampaignKeywordMetrics campaignKeywordMetrics1 = new CampaignKeywordMetrics("", "", "", "", "", "", 0L, 0L, 0d, 0d, 0d, 0d);
    CampaignKeywordMetrics campaignKeywordMetrics2 = new CampaignKeywordMetrics("", "", "", "", "", "", 12L, 120L, 330d, 30d, 30d, 40d);

    // Campaign titles and descriptions object.
    CampaignTitleAndDescription campaignTitleAndDescription = new CampaignTitleAndDescription("", "", "", List.of(), List.of(), 900L, 322L, 4);

    /**
     *
     * Testing get campaigns.
     *
     */
    @Test
    void getCampaignMetrics() {
        // Mocking interface's method 'getCampaignMetrics' to return campaignMetrics global variable.
        when(googleAdsRepoGateway.getCampaignMetrics(anyString(), anyString(), anyString(), anyBoolean())).thenReturn(campaignMetrics);

        // Tests if the call throws an exception.
        assertDoesNotThrow(() -> googleAdsUseCase.getCampaignMetrics("1234", "2025-01-01", "2025-01-31", true));
        // Tests if the call's response for campaign id of the first object equals to the campaignMetrics1's id.
        assertEquals(
            campaignMetrics1.getCampaignId(),
            googleAdsUseCase.getCampaignMetrics("1234", "2025-01-01", "2025-01-31", true).getFirst().getCampaignId()
        );
        // Tests if the call's response for day of week of the second object equals to the campaignMetrics2's day of week.
        assertEquals(
            campaignMetrics2.getDayOfWeek(),
            googleAdsUseCase.getCampaignMetrics("1234", "2025-01-01", "2025-01-31", true).get(1).getDayOfWeek()
        );
        // Tests if response's campaign id of the first object is null.
        assertNotNull(googleAdsUseCase.getCampaignMetrics("1234", "2025-01-01", "2025-01-31", true).getFirst().getCampaignId());

        // Verifies how many times 'getCampaignMetrics' was called.
        verify(googleAdsRepoGateway, times(4)).getCampaignMetrics(anyString(), anyString(), anyString(), anyBoolean());
    }

    /**
     *
     * Testing 'testConnection'.
     *
     */
    @Test
    void testConnection() {
        // Mocking interface's method 'testConnection' to return a list of strings.
        when(googleAdsRepoGateway.testConnection()).thenReturn(List.of("status", "ok"));

        // Tests if the call throws an exception.
        assertDoesNotThrow(() -> googleAdsUseCase.testConnection());
        // Tests if the call's second index response equals to the 'ok'.
        assertEquals("ok", googleAdsUseCase.testConnection().get(1));
        // Tests if the call's first index response equals to the 'status'.
        assertEquals("status", googleAdsUseCase.testConnection().get(0));
        // Tests if response's campaign id of the first object is null.
        assertNotNull(googleAdsUseCase.testConnection());

        // Verifies how many times 'getCampaignMetrics' was called.
        verify(googleAdsRepoGateway, times(4)).testConnection();
    }

    /**
     *
     * Testing 'getManagerAccount' method.
     *
     */
    @Test
    void getManagerAccount() {
        // Mocking interface's method 'getManagerAccount' to return a list of strings.
        when(googleAdsRepoGateway.getManagerAccount(anyString())).thenReturn(managerAccountInfo);

        // Tests if the call throws an exception.
        assertDoesNotThrow(() -> googleAdsUseCase.getManagerAccount("123"));
        // Tests if the call's response is manager account.
        assertTrue(googleAdsUseCase.getManagerAccount("123").isManager());
        // Tests if the call's response name equals to the name passed.
        assertEquals(managerAccountInfo.getName(), googleAdsUseCase.getManagerAccount("123").getName());
        // Tests if response is not null.
        assertNotNull(googleAdsUseCase.getManagerAccount("123"));

        // Verifies how many times 'getManagerAccount' was called.
        verify(googleAdsRepoGateway, times(4)).getManagerAccount(anyString());
    }

    /**
     *
     * Testing 'getAccountMetrics' method.
     *
     */
    @Test
    void getAccountMetrics() {
        // Mocking interface's method 'getAccountMetrics' to return a list of AccountMetrics.
        when(googleAdsRepoGateway.getAccountMetrics(anyString(), anyString(), anyString()))
            .thenReturn(List.of(accountMetrics1, accountMetrics2));

        // Tests if the call throws an exception.
        assertDoesNotThrow(() -> googleAdsUseCase.getAccountMetrics("123", "", ""));
        // Tests if the call's first index's response equals to the 'customerId'.
        assertEquals(accountMetrics1.getCustomerId(), googleAdsUseCase.getAccountMetrics("123", "", "").getFirst().getCustomerId());
        // Tests if response's account metrics of the first object is null.
        assertNotNull(googleAdsUseCase.getAccountMetrics("123", "", ""));

        // Verifies how many times 'getAccountMetrics' was called.
        verify(googleAdsRepoGateway, times(3)).getAccountMetrics(anyString(), anyString(), anyString());
    }

    /**
     *
     * Testing 'getTotalPerDay' method.
     *
     */
    @Test
    void getTotalPerDay() {
        // Mocking interface's method 'getTotalPerDay' to return a list of CampaignPerDay.
        when(googleAdsRepoGateway.getTotalPerDay(anyString(), anyString(), anyString())).thenReturn(new ArrayList<>());

        // Tests if the call throws an exception.
        assertDoesNotThrow(() -> googleAdsUseCase.getTotalPerDay("", "2025-01-01", "2025-01-31"));
        // Tests if the call's first index's response equals in date.
        assertEquals(
            "2025-01-01",
            googleAdsUseCase.getTotalPerDay("", "2025-01-01", "2025-01-31").getFirst().getDate()
        );
        // Tests if the call's last index's response equals in date.
        assertEquals(
            "2025-01-31",
            googleAdsUseCase.getTotalPerDay("", "2025-01-01", "2025-01-31").get(30).getDate()
        );
        // Tests if response is null.
        assertNotNull(googleAdsUseCase.getTotalPerDay("", "2025-01-01", "2025-01-31"));

        // Verifies how many times 'getTotalPerDay' was called.
        verify(googleAdsRepoGateway, times(4)).getTotalPerDay(anyString(), anyString(), anyString());
    }

    /**
     *
     * Testing 'getKeywordMetrics' method.
     *
     */
    @Test
    void getKeywordMetrics() {
        // Mocking interface's method 'getKeywordMetrics' to return a list of CampaignKeywordMetrics.
        when(googleAdsRepoGateway.getKeywordMetrics(anyString(), anyString(), anyString(), anyBoolean()))
            .thenReturn(List.of(campaignKeywordMetrics1, campaignKeywordMetrics2));

        // Tests if the call throws an exception.
        assertDoesNotThrow(() -> googleAdsUseCase.getKeywordMetrics("123", "", "", true));
        // Tests if the call's first index's response equals to the 'campaign name'.
        assertEquals(
            campaignKeywordMetrics2.getCampaignName(),
            googleAdsUseCase.getKeywordMetrics("123", "", "", true).get(1).getCampaignName()
        );
        // Tests if response's account metrics of the first object is null.
        assertNotNull(googleAdsUseCase.getKeywordMetrics("123", "", "", true));

        // Verifies how many times 'getKeywordMetrics' was called.
        verify(googleAdsRepoGateway, times(3)).getKeywordMetrics(anyString(), anyString(), anyString(), anyBoolean());
    }

    /**
     *
     * Testing 'getAdTitleAndDescriptions' method.
     *
     */
    @Test
    void getAdTitleAndDescriptions() {
        // Mocking interface's method 'getAdTitleAndDescriptions' to return a list of CampaignKeywordMetrics.
        when(googleAdsRepoGateway.getAdTitleAndDescriptions(anyString(), anyString(), anyString()))
            .thenReturn(List.of(campaignTitleAndDescription));

        // Tests if the call throws an exception.
        assertDoesNotThrow(() -> googleAdsUseCase.getAdTitleAndDescriptions("123", "", ""));
        // Tests if the call's first index's response equals to the 'campaign name'.
        assertEquals(
            campaignTitleAndDescription.getCampaignName(),
            googleAdsUseCase.getAdTitleAndDescriptions("123", "", "").getFirst().getCampaignName()
        );
        // Tests if response is null.
        assertNotNull(googleAdsUseCase.getAdTitleAndDescriptions("123", "", ""));

        // Verifies how many times 'getAdTitleAndDescriptions' was called.
        verify(googleAdsRepoGateway, times(3)).getAdTitleAndDescriptions(anyString(), anyString(), anyString());
    }
}
