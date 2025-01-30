package com.ads.report.domain.campaign;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * The domain of the titles and descriptions api call.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public class CampaignTitleAndDescription implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String date;
    private String campaignName;
    private String adName;
    private List<String> responsiveHeadlines;
    private List<String> responsiveDescriptions;
    private Long clicks;
    private Long impressions;
    private double conversions;

    public CampaignTitleAndDescription(String date, String campaignName, String adName, List<String> responsiveHeadlines, List<String> responsiveDescriptions, Long clicks, Long impressions, double conversions) {
        this.date = date;
        this.campaignName = campaignName;
        this.adName = adName;
        this.responsiveHeadlines = responsiveHeadlines;
        this.responsiveDescriptions = responsiveDescriptions;
        this.clicks = clicks;
        this.impressions = impressions;
        this.conversions = conversions;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public List<String> getResponsiveHeadlines() {
        return responsiveHeadlines;
    }

    public void setResponsiveHeadlines(List<String> responsiveHeadlines) {
        this.responsiveHeadlines = responsiveHeadlines;
    }

    public List<String> getResponsiveDescriptions() {
        return responsiveDescriptions;
    }

    public void setResponsiveDescriptions(List<String> responsiveDescriptions) {
        this.responsiveDescriptions = responsiveDescriptions;
    }

    public Long getClicks() {
        return clicks;
    }

    public void setClicks(Long clicks) {
        this.clicks = clicks;
    }

    public Long getImpressions() {
        return impressions;
    }

    public void setImpressions(Long impressions) {
        this.impressions = impressions;
    }

    public double getConversions() {
        return conversions;
    }

    public void setConversions(double conversions) {
        this.conversions = conversions;
    }
}
