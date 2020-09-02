package model;

import java.util.Objects;

public class Site {
    private Integer siteId;
    private Integer publisherId;
    private Integer siteIndex;
    private String siteUrl;

    public Site() {
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public Integer getSiteIndex() {
        return siteIndex;
    }

    public void setSiteIndex(Integer siteIndex) {
        this.siteIndex = siteIndex;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Site site = (Site) o;
        return Objects.equals(siteId, site.siteId) &&
                Objects.equals(publisherId, site.publisherId) &&
                Objects.equals(siteIndex, site.siteIndex) &&
                Objects.equals(siteUrl, site.siteUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(siteId, publisherId, siteIndex, siteUrl);
    }

    @Override
    public String toString() {
        return "Site{" +
                "siteId=" + siteId +
                ", publisherId=" + publisherId +
                ", siteIndex=" + siteIndex +
                ", siteUrl='" + siteUrl + '\'' +
                '}';
    }
}
