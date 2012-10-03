package com.epam.urlchopper.service;

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
    String generate(String originalUrl, Long userId);

    /**
     * .
     * @param url short URL
     * @return original url to redirect
     */
    String findOriginalUrl(String url);

}
