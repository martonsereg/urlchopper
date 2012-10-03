package com.epam.urlchopper.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/** Domain class, represents a short url.
 * @author Marton_Sereg
 *
 */
@Entity
public class ShortUrlAlt {

    private static final int ORIGINAL_URL_MAX_LENGTH = 1000;

    @Id
    private String shortUrl;

    @Column(length = ORIGINAL_URL_MAX_LENGTH)
    private String originalUrl;

    private Long activeUntil;

    public ShortUrlAlt() {
    }

    /**
     * .
     * @param shortUrl
     * @param originalUrl
     * @param activeUntil
     */
    public ShortUrlAlt(String shortUrl, String originalUrl, Long activeUntil) {
        this.shortUrl = shortUrl;
        this.originalUrl = originalUrl;
        this.activeUntil = activeUntil;
    }

    public String getShortUrl() {
        return this.shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getOriginalUrl() {
        return this.originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public Long getActiveUntil() {
        return this.activeUntil;
    }

    public void setActiveUntil(Long activeUntil) {
        this.activeUntil = activeUntil;
    }

    /**
     * ReflectionToString based toString method.
     * @return String representation of ShortUrl
     */
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
