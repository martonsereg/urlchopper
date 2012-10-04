package com.epam.urlchopper.service;

import java.util.List;

import com.epam.urlchopper.domain.OriginalUrl;

/**
 * .
 * @author Marton_Sereg
 *
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
    OriginalUrl findOriginalUrl(String url);

    /**
     * .
     * @param url short URL
     * @return original url to redirect
     */
    OriginalUrl findOriginalUrlByShortUrl(String url);

    List<OriginalUrl> getAllOriginalUrls();

}
