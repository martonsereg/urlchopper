package com.epam.urlchopper.repository;

import java.util.List;

import com.epam.urlchopper.domain.ShortUrlAlt;

public interface ShortUrlAltRepository {

    /**
     * Counts existing ShortUrlAlts.
     * @return count
     */
    long countShortUrlAlts();

    /**
     * Finds all of the existing ShortUrlAlts.
     * @return list of ShortUrlAlts.
     */
    List<ShortUrlAlt> findAllShortUrlAlts();

    /**
     * Finds ShortUrlAlt by id.
     * @param id id of ShortUrlAlt.
     * @return ShortUrlAlt
     */
    ShortUrlAlt findShortUrlAlt(String id);


    /**
     * Creates a new ShortUrlAlt.
     * @param shortUrl ShortUrlAlt to be created.
     */
    void create(ShortUrlAlt shortUrl);

    /**
     * Removes an existing ShortUrlAlt.
     * @param shortUrl ShortUrlAlt to be removed.
     */
    void remove(ShortUrlAlt shortUrl);


    /**
     * Updates an existing ShortUrlAlt.
     * @param shortUrl shortUrl to be updated
     * @return updated ShortUrlAlt.
     */
    ShortUrlAlt update(ShortUrlAlt shortUrl);

    /**
     * Finds ShortUrlAlts by OriginalUrl.
     * @param originalUrl originalUrl
     * @return found ShortUrlAlts
     */
    List<ShortUrlAlt> findShortUrlAltsByOriginalUrlEquals(String originalUrl);

    /**
     * Finds ShortUrlAlts by OriginalUrl.
     * @param originalUrl originalUrl
     * @return found ShortUrlAlts
     */
    List<ShortUrlAlt> findShortUrlAltsByOriginalUrlLike(String originalUrl);

    /**
     * Finds an active, unique ShortUrlAlt by its string representation.
     * @param shortUrl shortUrl
     * @return found ShortUrlAlts
     */
    ShortUrlAlt findShortUrlAltByShortUrlAltEquals(String shortUrl);

    /**
     * Finds ShortUrlAlts by ShortUrlAlt string.
     * @param shortUrl shortUrl
     * @return found ShortUrlAlts
     */
    ShortUrlAlt findShortUrlAltByShortUrlAltLike(String shortUrl);
}
