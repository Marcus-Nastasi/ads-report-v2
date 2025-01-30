package com.ads.report.domain.manager;

import java.io.Serial;
import java.io.Serializable;

/**
 * The domain of the object that returns from the manager
 * account generic information api call.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public class ManagerAccountInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String currency;
    private String timeZone;
    private boolean testAccount;
    private String status;
    private boolean manager;
    private boolean autoTaggingEnabled;
    private String trackingUrlTemplate;
    private String finalUrlSuffix;
    private Long conversionTrackingId;
    private String conversionTrackingStatus;

    public ManagerAccountInfo(String id, String name, String currency, String timeZone, boolean testAccount, String status, boolean manager, boolean autoTaggingEnabled, String trackingUrlTemplate, String finalUrlSuffix, Long conversionTrackingId, String conversionTrackingStatus) {
        this.id = id;
        this.name = name;
        this.currency = currency;
        this.timeZone = timeZone;
        this.testAccount = testAccount;
        this.status = status;
        this.manager = manager;
        this.autoTaggingEnabled = autoTaggingEnabled;
        this.trackingUrlTemplate = trackingUrlTemplate;
        this.finalUrlSuffix = finalUrlSuffix;
        this.conversionTrackingId = conversionTrackingId;
        this.conversionTrackingStatus = conversionTrackingStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public boolean isTestAccount() {
        return testAccount;
    }

    public void setTestAccount(boolean testAccount) {
        this.testAccount = testAccount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public boolean isAutoTaggingEnabled() {
        return autoTaggingEnabled;
    }

    public void setAutoTaggingEnabled(boolean autoTaggingEnabled) {
        this.autoTaggingEnabled = autoTaggingEnabled;
    }

    public String getTrackingUrlTemplate() {
        return trackingUrlTemplate;
    }

    public void setTrackingUrlTemplate(String trackingUrlTemplate) {
        this.trackingUrlTemplate = trackingUrlTemplate;
    }

    public String getFinalUrlSuffix() {
        return finalUrlSuffix;
    }

    public void setFinalUrlSuffix(String finalUrlSuffix) {
        this.finalUrlSuffix = finalUrlSuffix;
    }

    public Long getConversionTrackingId() {
        return conversionTrackingId;
    }

    public void setConversionTrackingId(Long conversionTrackingId) {
        this.conversionTrackingId = conversionTrackingId;
    }

    public String getConversionTrackingStatus() {
        return conversionTrackingStatus;
    }

    public void setConversionTrackingStatus(String conversionTrackingStatus) {
        this.conversionTrackingStatus = conversionTrackingStatus;
    }
}
