package com.ads.report.domain.account;

import java.io.Serial;
import java.io.Serializable;

/**
 * The domain of the object that returns from the account metrics api call.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public class AccountMetrics implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private long customerId;
    private String descriptiveName;
    private long impressions;
    private long clicks;
    private double cost;
    private double conversions;
    private double ctr;
    private double averageCpc;
    private double averageCpa;

    public AccountMetrics(long customerId, String descriptiveName, long impressions, long clicks, double cost, double conversions, double ctr, double averageCpc, double averageCpa) {
        this.customerId = customerId;
        this.descriptiveName = descriptiveName;
        this.impressions = impressions;
        this.clicks = clicks;
        this.cost = cost;
        this.conversions = conversions;
        this.ctr = ctr;
        this.averageCpc = averageCpc;
        this.averageCpa = averageCpa;
    }

    public long getCustomerId() {
        return customerId;
    }

    public String getDescriptiveName() {
        return descriptiveName;
    }

    public long getImpressions() {
        return impressions;
    }

    public long getClicks() {
        return clicks;
    }

    public double getCost() {
        return cost;
    }

    public double getConversions() {
        return conversions;
    }

    public double getCtr() {
        return ctr;
    }

    public double getAverageCpc() {
        return averageCpc;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public void setDescriptiveName(String descriptiveName) {
        this.descriptiveName = descriptiveName;
    }

    public void setImpressions(long impressions) {
        this.impressions = impressions;
    }

    public void setClicks(long clicks) {
        this.clicks = clicks;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setConversions(double conversions) {
        this.conversions = conversions;
    }

    public void setCtr(double ctr) {
        this.ctr = ctr;
    }

    public void setAverageCpc(double averageCpc) {
        this.averageCpc = averageCpc;
    }

    public double getAverageCpa() {
        return averageCpa;
    }

    public void setAverageCpa(double averageCpa) {
        this.averageCpa = averageCpa;
    }
}
