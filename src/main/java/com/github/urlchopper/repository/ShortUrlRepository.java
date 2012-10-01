package com.github.urlchopper.repository;

import java.util.List;

import com.github.urlchopper.domain.ShortUrl;

/**
 * Repository interface, to manage the ShortUrl domain object.
 * @author Marton_Sereg
 *
 */
public interface ShortUrlRepository {

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
     * @param id id of ShortUrl.
     * @return ShortUrl
     */
    ShortUrl findShortUrl(Long id);


    /**
     * Creates a new ShortUrl.
     * @param shortUrl ShortUrl to be created.
     */
    void create(ShortUrl shortUrl);

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
     * Finds ShortUrls by OriginalUrl.
     * @param originalUrl originalUrl
     * @return found ShortUrls
     */
    List<ShortUrl> findShortUrlsByOriginalUrlEquals(String originalUrl);

    /**
     * Finds ShortUrls by OriginalUrl.
     * @param originalUrl originalUrl
     * @return found ShortUrls
     */
    List<ShortUrl> findShortUrlsByOriginalUrlLike(String originalUrl);

    /**
     * Finds ShortUrls by ShortUrl string.
     * @param shortUrl shortUrl
     * @return found ShortUrls
     */
    List<ShortUrl> findShortUrlsByShortUrlEquals(String shortUrl);

    /**
     * Finds ShortUrls by ShortUrl string.
     * @param shortUrl shortUrl
     * @return found ShortUrls
     */
    List<ShortUrl> findShortUrlsByShortUrlLike(String shortUrl);

}
