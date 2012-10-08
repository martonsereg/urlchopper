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
    private String urlId;

    private Integer referenceCount;

    @OneToOne(mappedBy = "originalUrl")
    private ShortUrl shortUrl;

    /**
     * Needed because of JPA.
     */
    public OriginalUrl() {
    }

    public OriginalUrl(String originalUrl, Integer references) {
        this.urlId = originalUrl;
        this.referenceCount = references;
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    public Integer getReferenceCount() {
        return referenceCount;
    }

    public void setReferenceCount(Integer referenceCount) {
        this.referenceCount = referenceCount;
    }

    public ShortUrl getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(ShortUrl shortUrl) {
        this.shortUrl = shortUrl;
    }

    /**
     * Increase reference count.
     */
    public void increaseReferenceCount() {
        referenceCount++;

    }

}
