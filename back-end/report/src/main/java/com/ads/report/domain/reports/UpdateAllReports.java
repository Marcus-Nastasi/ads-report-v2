package com.ads.report.domain.reports;

/**
 * The domain of the object to update various reports.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public class UpdateAllReports {

    private String customerId;
    private String startDate;
    private String endDate;
    private String spreadsheetId;
    private String client;
    private boolean active;

    public UpdateAllReports(String customerId, String startDate, String endDate, String spreadsheetId, String client, boolean active) {
        this.customerId = customerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.spreadsheetId = spreadsheetId;
        this.client = client;
        this.active = active;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSpreadsheetId() {
        return spreadsheetId;
    }

    public void setSpreadsheetId(String spreadsheetId) {
        this.spreadsheetId = spreadsheetId;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
