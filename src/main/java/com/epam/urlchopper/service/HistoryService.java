package com.epam.urlchopper.service;

import java.util.List;

import com.epam.urlchopper.domain.ShortUrl;
import com.epam.urlchopper.domain.ShortUrlDTO;

/**
 * HistoryService.
 */
public interface HistoryService {

    /**
     * Get users histories.
     * @param userId userId
     * @return short URL's history
     */
    List<ShortUrlDTO> getUserUrls(Long userId);

    /**
     * Get history.
     * @param size size of history
     * @return short URL's history
     */
    List<ShortUrlDTO> getLastShortUrlHistory(Integer size);

    /**
     * Get all histories.
     * @return short URL's history
     */
    List<ShortUrl> getAllUrls();
}
