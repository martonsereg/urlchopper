package com.epam.urlchopper.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Domain class, represents a short url.
 */
@Entity
public class ShortUrl {

    @Id
    private String shortUrlPostfixId;

    private Long activeUntil;

    @OneToOne
    private OriginalUrl originalUrl;

    /**
     * Needed by JPA.
     */
    public ShortUrl() {
    }

    /**
     * .
     * @param shortUrl
     * @param originalUrl
     * @param activeUntil
     */
    public ShortUrl(String shortUrl, OriginalUrl originalUrl, Long activeUntil) {
        this.shortUrlPostfixId = shortUrl;
        this.originalUrl = originalUrl;
        this.activeUntil = activeUntil;
    }

    public String getShortUrlPostfixId() {
        return shortUrlPostfixId;
    }

    public void setShortUrlPostfixId(String shortUrlPostfix) {
        this.shortUrlPostfixId = shortUrlPostfix;
    }

    public Long getActiveUntil() {
        return this.activeUntil;
    }

    public void setActiveUntil(Long activeUntil) {
        this.activeUntil = activeUntil;
    }

    public OriginalUrl getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(OriginalUrl originalUrl) {
        this.originalUrl = originalUrl;
    }

    /**
     * ReflectionToString based toString method.
     * @return String representation of ShortUrl
     */
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
