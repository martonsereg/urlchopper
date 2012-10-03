package com.epam.urlchopper.service.simple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

import com.epam.urlchopper.domain.OriginalUrl;
import com.epam.urlchopper.domain.ShortUrl;
import com.epam.urlchopper.domain.ShortUrlDTO;
import com.epam.urlchopper.repository.UrlRepository;
import com.epam.urlchopper.service.GeneratorService;

/**
 * .
 *
 */
@Service
public class SimpleGeneratorService implements GeneratorService {

    private static final int DIGITS_ASCII_END = 57;

    private static final int DIGITS_ASCII_START = 48;

    private static final int LOWER_CASE_ASCII_END = 122;

    private static final int LOWER_CASE_ASCII_START = 97;

    private static final int MULTIPLY = 1000;

    private Logger logger = LoggerFactory.getLogger(SimpleGeneratorService.class);

    private Integer generateUrlLength;

    private Long shortUrlLifeSpan;

    private Properties properties;

    private List<Character> characters = new ArrayList<Character>();

    @Autowired
    private UrlRepository urlRepository;

    /**
     * The characters are initialized.
     */
    @PostConstruct
    public void createCharacterList() {
        for (int i = LOWER_CASE_ASCII_START; i <= LOWER_CASE_ASCII_END; i++) {
            characters.add((char) i);
        }
        for (int i = DIGITS_ASCII_START; i <= DIGITS_ASCII_END; i++) {
            characters.add((char) i);
        }

        try {

            properties = PropertiesLoaderUtils.loadAllProperties("config.properties");

            generateUrlLength = Integer.valueOf(properties.getProperty("shorturls.generateUrlLength"));
            shortUrlLifeSpan = Long.valueOf(properties.getProperty("shorturls.lifespan"));
        } catch (NumberFormatException e) {
            logger.info(e.getMessage());
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

    @Override
    public String generate(String originalUrl) {
        String generatedShortUrlPostfix = "";
        logger.info("start");
        OriginalUrl existUrl = urlRepository.findOriginalUrl(convertToValidUrl(originalUrl));
        if (originalUrlIsExistWithShortUrl(existUrl)) {
            existUrl.increaseReferenceCount();
            generatedShortUrlPostfix = existUrl.getShortUrl().getShortUrlPostfix();
            urlRepository.activateShortUrl(existUrl.getShortUrl(), calculateLifeSpanEnd());
            logger.info(":" + existUrl.getReferenceCount());
            urlRepository.mergeOriginalUrl(existUrl);
        } else if (originalUrlIsExistWithoutShortUrl(existUrl)) {
            logger.info("exist without");
            existUrl.increaseReferenceCount();
            generatedShortUrlPostfix = generateUniqueShortUrlPostfix();
            urlRepository.mergeOriginalUrl(existUrl);
            createShortUrl(existUrl, generatedShortUrlPostfix);
        } else {
            generatedShortUrlPostfix = generateUniqueShortUrlPostfix();
            createOriginalUrl(originalUrl);
            createShortUrl(originalUrl, generatedShortUrlPostfix);

        }
        logger.info("finsih: " + generatedShortUrlPostfix);
        return generatedShortUrlPostfix;
    }

    private boolean originalUrlIsExistWithShortUrl(OriginalUrl existUrl) {
        if (existUrl == null) {
            logger.info("pina");
        }

        return existUrl != null && existUrl.getShortUrl() != null;
    }

    private boolean originalUrlIsExistWithoutShortUrl(OriginalUrl existUrl) {
        return existUrl != null && existUrl.getShortUrl() == null;
    }

    private void createOriginalUrl(String originalUrl) {
        OriginalUrl tmp = new OriginalUrl(convertToValidUrl(originalUrl), 1);
        urlRepository.createOriginalUrl(tmp);
    }

    private void createShortUrl(String originalUrl, String generatedShortUrl) {
        OriginalUrl tmp = new OriginalUrl(convertToValidUrl(originalUrl), 1);
        createShortUrl(tmp, generatedShortUrl);
    }

    private void createShortUrl(OriginalUrl originalUrl, String generatedShortUrl) {
        ShortUrl url = new ShortUrl(generatedShortUrl, originalUrl, calculateLifeSpanEnd());
        urlRepository.createShortUrl(url);
    }

    private long calculateLifeSpanEnd() {
        return new Date().getTime() + shortUrlLifeSpan;
    }

    private String generateUniqueShortUrlPostfix() {
        String ret = "";
        ret = generateShortUrl();
        while (urlAlreadyExists(ret)) {
            ret = generateShortUrl();
        }
        return ret;
    }

    private String convertToValidUrl(String originalUrl) {
        String ret = originalUrl;
        if (!originalUrl.startsWith("http://")) {
            ret = "http://" + originalUrl;
        }
        return ret;
    }

    private String generateShortUrl() {
        String ret = "";

        for (int i = 0; i < generateUrlLength; i++) {
            double rnd = Math.random();
            int index = (int) ((rnd * MULTIPLY) % characters.size());
            ret += characters.get(index);
        }
        return ret;
    }

    private boolean urlAlreadyExists(String url) {

        Boolean ret = true;

        if (urlRepository.findShortUrl(url) == null) {
            ret = false;
        }

        return ret;
    }

    @Override
    public String findOriginalUrl(String url) {
        return urlRepository.findOriginalUrl(url).getUrl();
    }

    @Override
    public List<ShortUrlDTO> getLastShortUrlHistory(Integer size) {
        List<ShortUrlDTO> ret = new ArrayList<ShortUrlDTO>();
        List<ShortUrl> list = urlRepository.findAllShortUrls();

        Integer historySize = size;
        if (list.size() < size) {
            historySize = list.size();
        }

        for (int i = list.size(); i > list.size() - historySize; i--) {
            ShortUrlDTO dto = new ShortUrlDTO();
            ShortUrl url = list.get(i);
            dto.setOriginalUrl(url.getOriginalUrl().getUrl());
            dto.setShortUrl(url.getShortUrlPostfix());
        }

        return ret;
    }

}
