package com.github.urlchopper.service;

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
}
