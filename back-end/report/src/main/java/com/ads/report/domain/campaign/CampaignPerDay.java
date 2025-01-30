package com.ads.report.domain.campaign;

import java.io.Serial;
import java.io.Serializable;

/**
 * The domain of the object that represents the account metrics, granulated by days, based on a period.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public class CampaignPerDay implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String date;
    private long impressions;
    private long clicks;
    private double conversions;
    private double cost;
    private int hour;
    private String dayOfWeek;

    public CampaignPerDay(String date, long impressions, long clicks, double conversions, double cost, int hour, String dayOfWeek) {
        this.date = date;
        this.impressions = impressions;
        this.clicks = clicks;
        this.conversions = conversions;
        this.cost = cost;
        this.hour = hour;
        this.dayOfWeek = dayOfWeek;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getImpressions() {
        return impressions;
    }

    public void setImpressions(long impressions) {
        this.impressions = impressions;
    }

    public long getClicks() {
        return clicks;
    }

    public void setClicks(long clicks) {
        this.clicks = clicks;
    }

    public double getConversions() {
        return conversions;
    }

    public void setConversions(double conversions) {
        this.conversions = conversions;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
