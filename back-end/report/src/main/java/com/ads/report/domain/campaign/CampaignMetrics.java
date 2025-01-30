package com.ads.report.domain.campaign;

import java.io.Serial;
import java.io.Serializable;

/**
 * The domain of the object that returns from the campaign api call.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public class CampaignMetrics implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String date;
    private String dayOfWeek;
    private Long campaignId;
    private String campaignName;
    private String adGroupName;
    private String status;
    private Long impressions;
    private Long clicks;
    private Double cost;
    private Double conversions;
    private Double ctr;
    private Double averageCpc;
    private Double averageCpa;

    public CampaignMetrics(String date, String dayOfWeek, Long campaignId, String campaignName, String adGroupName, String status, Long impressions, Long clicks, Double cost, Double conversions, Double ctr, Double averageCpc, Double averageCpa) {
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.campaignId = campaignId;
        this.campaignName = campaignName;
        this.adGroupName = adGroupName;
        this.status = status;
        this.impressions = impressions;
        this.clicks = clicks;
        this.cost = cost;
        this.conversions = conversions;
        this.ctr = ctr;
        this.averageCpc = averageCpc;
        this.averageCpa = averageCpa;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getAdGroupName() {
        return adGroupName;
    }

    public void setAdGroupName(String adGroupName) {
        this.adGroupName = adGroupName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getImpressions() {
        return impressions;
    }

    public void setImpressions(Long impressions) {
        this.impressions = impressions;
    }

    public Long getClicks() {
        return clicks;
    }

    public void setClicks(Long clicks) {
        this.clicks = clicks;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getConversions() {
        return conversions;
    }

    public void setConversions(Double conversions) {
        this.conversions = conversions;
    }

    public Double getCtr() {
        return ctr;
    }

    public void setCtr(Double ctr) {
        this.ctr = ctr;
    }

    public Double getAverageCpc() {
        return averageCpc;
    }

    public void setAverageCpc(Double averageCpc) {
        this.averageCpc = averageCpc;
    }

    public Double getAverageCpa() {
        return averageCpa;
    }

    public void setAverageCpa(Double averageCpa) {
        this.averageCpa = averageCpa;
    }
}
