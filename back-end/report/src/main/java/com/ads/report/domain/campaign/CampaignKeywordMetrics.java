package com.ads.report.domain.campaign;

import java.io.Serial;
import java.io.Serializable;

/**
 * The domain of the keywords api call.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public class CampaignKeywordMetrics implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String date;
    private String dayOfWeek;
    private String campaignName;
    private String adGroupName;
    private String keywordText;
    private String matchType;
    private Long impressions;
    private Long clicks;
    private double cost;
    private double averageCpc;
    private double conversions;
    private double conversionRate;

    public CampaignKeywordMetrics(String date, String dayOfWeek, String campaignName, String adGroupName, String keywordText, String matchType, Long impressions, Long clicks, double cost, double averageCpc, double conversions, double conversionRate) {
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.campaignName = campaignName;
        this.adGroupName = adGroupName;
        this.keywordText = keywordText;
        this.matchType = matchType;
        this.impressions = impressions;
        this.clicks = clicks;
        this.cost = cost;
        this.averageCpc = averageCpc;
        this.conversions = conversions;
        this.conversionRate = conversionRate;
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

    public String getKeywordText() {
        return keywordText;
    }

    public void setKeywordText(String keywordText) {
        this.keywordText = keywordText;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getAverageCpc() {
        return averageCpc;
    }

    public void setAverageCpc(double averageCpc) {
        this.averageCpc = averageCpc;
    }

    public double getConversions() {
        return conversions;
    }

    public void setConversions(double conversions) {
        this.conversions = conversions;
    }

    public double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(double conversionRate) {
        this.conversionRate = conversionRate;
    }
}
