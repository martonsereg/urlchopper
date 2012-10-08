package com.epam.urlchopper.repository;

import java.util.List;

import com.epam.urlchopper.domain.OriginalUrl;
import com.epam.urlchopper.domain.ShortUrl;

/**
 * Repository interface, to manage the ShortUrl domain object.
 */
public interface UrlRepository {

    /**
     * Counts existing ShortUrls.
     * @return count
     */
    long countShortUrls();

    /**
     * Finds all of the existing ShortUrls.
     * @return list of ShortUrls.
     */
    List<ShortUrl> findAllShortUrls();

    /**
     * Finds ShortUrl by id.
     * @param shortUrlPostfix id of ShortUrl.
     * @return ShortUrl
     */
    ShortUrl findShortUrl(String shortUrlPostfix);

    /**
     * Creates a new ShortUrl.
     * @param url ShortUrl to be created.
     */
    void createShortUrl(ShortUrl url);

    /**
     * Creates a new ShortUrl.
     * @param url OriginalUrl to be created.
     */
    void createOriginalUrl(OriginalUrl url);

    /**
     * merge a new ShortUrl with exist URL.
     * @param url OriginalUrl to be merged.
     */
    void mergeOriginalUrl(OriginalUrl url);

    /**
     * Removes an existing ShortUrl.
     * @param shortUrl ShortUrl to be removed.
     */
    void remove(ShortUrl shortUrl);

    /**
     * Updates an existing ShortUrl.
     * @param shortUrl shortUrl to be updated
     * @return updated ShortUrl.
     */
    ShortUrl update(ShortUrl shortUrl);

    /**
     * Finds OriginalUrl by id.
     * @param url originalUrl
     * @return found ShortUrls
     */
    OriginalUrl findOriginalUrl(String url);

    /**
     * Refresh the lifespan.
     * @param shortUrl originalUrl
     */
    void lengthenLifespan(ShortUrl shortUrl, Long lifespanEnd);

    /**
     * Get all OriginalUrls.
     * @return all Origianl URLs
     */
    List<OriginalUrl> findAllOriginalUrls();

}
