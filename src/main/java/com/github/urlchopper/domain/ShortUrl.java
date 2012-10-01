package com.github.urlchopper.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@Entity
public class ShortUrl {

    private String shortUrl;

    private String originalUrl;

    private Long activeUntil;

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

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;

	public Long getId() {
        return this.id;
    }

	public void setId(Long id) {
        this.id = id;
    }

	public Integer getVersion() {
        return this.version;
    }

	public void setVersion(Integer version) {
        this.version = version;
    }
}
