package com.epam.urlchopper.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/** Domain class, represents a short url.
 * @author Marton_Sereg
 *
 */
@Entity
public class ShortUrl {

    private static final int ORIGINAL_URL_MAX_LENGTH = 1000;

    @Id
    private String shortUrlPostfix;

    @Column(length = ORIGINAL_URL_MAX_LENGTH)
    private String originalUrl;

    private Long activeUntil;

    @Version
    @Column(name = "version")
    private Integer version;

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
    public ShortUrl(String shortUrl, String originalUrl, Long activeUntil) {
        this.shortUrlPostfix = shortUrl;
        this.originalUrl = originalUrl;
        this.activeUntil = activeUntil;
    }

    public String getShortUrlPostfix() {
        return shortUrlPostfix;
    }

    public void setShortUrlPostfix(String shortUrlPostfix) {
        this.shortUrlPostfix = shortUrlPostfix;
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

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * ReflectionToString based toString method.
     * @return String representation of ShortUrl
     */
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
