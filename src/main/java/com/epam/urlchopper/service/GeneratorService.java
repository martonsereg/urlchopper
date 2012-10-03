package com.epam.urlchopper.service;

import java.util.List;

import com.epam.urlchopper.domain.ShortUrlDTO;

/**
 * .
 * @author Marton_Sereg
 *
 */
public interface GeneratorService {

    /**
     * Generate a unique short URL for an exist URL.
     * @param originalUrl original URL
     * @return unique shortUrl
     */
    String generate(String originalUrl);

    /**
     * Get history.
     * @param size size of history
     * @return short URL's history
     */
    List<ShortUrlDTO> getLastShortUrlHistory(Integer size);

    /**
     * .
     * @param shortUrl short URL
     * @return original url to redirect
     */
    String findOriginalUrl(String url);
}
