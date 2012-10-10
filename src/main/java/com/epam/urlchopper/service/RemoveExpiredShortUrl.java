package com.epam.urlchopper.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.epam.urlchopper.domain.ShortUrl;

/**
 * Remove expired short URL from database.
 */
@Component
public class RemoveExpiredShortUrl {

    private Logger logger = LoggerFactory.getLogger(RemoveExpiredShortUrl.class);

    @Autowired
    private UrlService urlService;

    @Scheduled(cron = "${expired.remove.cron}")
    public void remove() {

        List<ShortUrl> urls = urlService.getAllExpiredShortUrls();

        logger.info("Remove schedule starting..." + urls);

        for (ShortUrl shortUrl : urls) {
            try {
                urlService.removeShortUrl(shortUrl);
                logger.info("removed from database, because the limit was expired: " + shortUrl.getShortUrlPostfixId());
            } catch (Exception e) {
                logger.info(e.getMessage());
            }

        }

        logger.info("Remove schedule finished...");
    }
}
