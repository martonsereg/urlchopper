package com.epam.urlchopper.service.dto;

/**
 * Data Transfer Object for OriginalUrl.
 */
public class StatisticUrlDTO {

    private String url;

    private Integer referenceCount;

    /**
     * Constructor for inject all arguments.
     * @param url original URL
     * @param referenceCount refenreces
     */
    public StatisticUrlDTO(String url, Integer referenceCount) {
        super();
        this.url = url;
        this.referenceCount = referenceCount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getReferenceCount() {
        return referenceCount;
    }

    public void setReferenceCount(Integer referenceCount) {
        this.referenceCount = referenceCount;
    }

}
