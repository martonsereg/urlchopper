package com.epam.urlchopper.service;

import java.util.List;

import com.epam.urlchopper.domain.OriginalUrl;
import com.epam.urlchopper.domain.ShortUrl;

/**
 * .
 */
public interface UrlService {

    /**
     * Generate a unique short URL for an exist URL.
     * @param originalUrl original URL
     * @return unique shortUrl
     */
    String generate(String originalUrl, Long userId);

    /**
     * .
     * @param url short URL
     * @return original url to redirect
     */
    OriginalUrl findOriginalUrlByShortUrl(String url);

    List<OriginalUrl> getAllOriginalUrls();

    List<ShortUrl> getAllExpiredShortUrls();

    void removeShortUrl(ShortUrl shortUrl);

}
