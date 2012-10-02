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
     * .
     * @param originalUrl original URL
     * @return unique shortUrl
     */
    String generate(String originalUrl);

    /**
     * .
     * @param shortUrl short URL
     * @return original url to redirect
     */
    String findActiveOriginalUrl(String shortUrl);
    
    /**
     * Get history.
     * @param size size of history
     * @return short URL's history
     */
    List<ShortUrlDTO> getLastShortUrlHistory(Integer size);
}
