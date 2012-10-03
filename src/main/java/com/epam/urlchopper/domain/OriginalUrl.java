package com.epam.urlchopper.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Original url is in a separate entity from Short url to collect statistics and because of performance.
 */
@Entity
public class OriginalUrl {

    private static final int ORIGINAL_URL_MAX_LENGTH = 767;

    @Id
    @Column(length = ORIGINAL_URL_MAX_LENGTH)
    private String url;

    private Integer referenceCount;

    @OneToOne(mappedBy = "originalUrl")
    private ShortUrl shortUrl;

    /**
     * Needed because of JPA.
     */
    public OriginalUrl() {
    }

    public OriginalUrl(String originalUrl, Integer references) {
        this.url = originalUrl;
        this.referenceCount = references;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String originalUrl) {
        this.url = originalUrl;
    }

    public Integer getReferenceCount() {
        return referenceCount;
    }

    public void setReferenceCount(Integer referenceCount) {
        this.referenceCount = referenceCount;
    }

}
