package com.github.urlchopper.service.simple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

import com.github.urlchopper.domain.ShortUrl;
import com.github.urlchopper.repository.ShortUrlRepository;
import com.github.urlchopper.service.GeneratorService;

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

    private Integer generateUrlLength;

    private Long shortUrlLifeSpan;
    
    private Properties properties;

    private List<Character> characters = new ArrayList<Character>();

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    /**
     * todo: constants.
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
            e.printStackTrace();        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String generate(String originalUrl) {
        String generatedShortUrlPostfix = "";
        generatedShortUrlPostfix = generateUniqueShortUrlPostfix();
        createShortUrl(originalUrl, generatedShortUrlPostfix);
        return generatedShortUrlPostfix;
    }

    private void createShortUrl(String originalUrl, String generatedShortUrl) {
        ShortUrl url = new ShortUrl(generatedShortUrl, convertToValidUrl(originalUrl), new Date().getTime() + shortUrlLifeSpan);
        shortUrlRepository.create(url);
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
        try {
            shortUrlRepository.findShortUrlByShortUrlLike(url);
        } catch (Exception e) {
            ret = false;
        }

        return ret;
    }

    @Override
    public String findActiveOriginalUrl(String shortUrl) {
        ShortUrl url = shortUrlRepository.findShortUrlByShortUrlEquals(shortUrl);
        return url.getOriginalUrl();
    }
}
