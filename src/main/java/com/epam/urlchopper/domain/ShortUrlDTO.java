package com.epam.urlchopper.domain;

/**
 * Data Transfer Object. Transfer the short URL and Original URL only.
 * @author Gergely_Topolyai
 *
 */
public class ShortUrlDTO {

    private String originalUrl;
    private String shortUrl;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

}
