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

import com.epam.urlchopper.domain.Creator;
import com.epam.urlchopper.domain.OriginalUrl;
import com.epam.urlchopper.domain.ShortUrl;
import com.epam.urlchopper.repository.UrlRepository;
import com.epam.urlchopper.repository.UserRepository;
import com.epam.urlchopper.service.UrlService;

/**
 * .
 */
@Service
public class SimpleUrlService implements UrlService {

    private static final String CONFIG_PROPERTIES = "config.properties";

    private static final long DEFAULT_LIFESPAN = 86400000L;

    private static final int DEFAULT_SHORTURL_LENGTH = 6;

    private static final int DIGITS_ASCII_END = 57;

    private static final int DIGITS_ASCII_START = 48;

    private static final int LOWER_CASE_ASCII_END = 122;

    private static final int LOWER_CASE_ASCII_START = 97;

    private static final int MULTIPLY = 1000;

    private Logger logger = LoggerFactory.getLogger(SimpleUrlService.class);

    private Integer generateUrlLength = DEFAULT_SHORTURL_LENGTH;

    private Long shortUrlLifeSpan = DEFAULT_LIFESPAN;

    private Properties properties;

    private List<Character> characters = new ArrayList<Character>();

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * The characters are initialized.
     */
    @PostConstruct
    private void createCharacterList() {
        for (int i = LOWER_CASE_ASCII_START; i <= LOWER_CASE_ASCII_END; i++) {
            characters.add((char) i);
        }
        for (int i = DIGITS_ASCII_START; i <= DIGITS_ASCII_END; i++) {
            characters.add((char) i);
        }
    }

    @PostConstruct
    private void loadProperties() {
        try {
            properties = PropertiesLoaderUtils.loadAllProperties(CONFIG_PROPERTIES);
            generateUrlLength = Integer.valueOf(properties.getProperty("shorturls.generateUrlLength"));
            shortUrlLifeSpan = Long.valueOf(properties.getProperty("shorturls.testlifespan"));
        } catch (NumberFormatException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public String generate(String requestedOriginalUrl, Long userId) {
        OriginalUrl originalUrl = findOriginalUrl(requestedOriginalUrl);
        ShortUrl shortUrl = null;

        if (shortUrlExistsForOriginal(originalUrl)) {
            lengthenLifeSpan(originalUrl);
            mergeOriginalUrl(originalUrl);
            shortUrl = originalUrl.getShortUrl();
        } else if (originalUrlIsExistWithoutShortUrl(originalUrl)) {
            mergeOriginalUrl(originalUrl);
            shortUrl = createShortUrl(originalUrl);
        } else {
            createOriginalUrl(requestedOriginalUrl);
            shortUrl = createShortUrl(requestedOriginalUrl);
        }
        if (userId != null) {
            Creator user = userRepository.findUser(userId);
            updateUser(shortUrl, user);
        }
        return shortUrl.getShortUrlPostfixId();
    }

    private void lengthenLifeSpan(OriginalUrl originalUrl) {
        urlRepository.lengthenLifespan(originalUrl.getShortUrl(), calculateLifeSpanEnd());
    }

    private void updateUser(ShortUrl shortUrl, Creator user) {
        user.addShortUrl(shortUrl);
        userRepository.update(user);
    }

    private void mergeOriginalUrl(OriginalUrl originalUrl) {
        originalUrl.increaseReferenceCount();
        urlRepository.mergeOriginalUrl(originalUrl);
    }

    private String initEmptyString() {
        return "";
    }

    private boolean shortUrlExistsForOriginal(OriginalUrl originalUrl) {
        return originalUrl != null && originalUrl.getShortUrl() != null;
    }

    private boolean originalUrlIsExistWithoutShortUrl(OriginalUrl existUrl) {
        return existUrl != null && existUrl.getShortUrl() == null;
    }

    private void createOriginalUrl(String originalUrl) {
        urlRepository.createOriginalUrl(new OriginalUrl(convertToValidUrl(originalUrl), 1));
    }

    private ShortUrl createShortUrl(String originalUrl) {
        OriginalUrl tmp = new OriginalUrl(convertToValidUrl(originalUrl), 1);
        return createShortUrl(tmp);
    }

    private ShortUrl createShortUrl(OriginalUrl originalUrl) {
        ShortUrl url = new ShortUrl(generateUniqueShortUrlPostfix(), originalUrl, calculateLifeSpanEnd());
        originalUrl.setShortUrl(url);
        urlRepository.createShortUrl(url);
        return url;
    }

    private long calculateLifeSpanEnd() {
        return new Date().getTime() + shortUrlLifeSpan;
    }

    private String generateUniqueShortUrlPostfix() {
        String ret = initEmptyString();
        ret = generateShortUrl();
        while (urlAlreadyExists(ret)) {
            ret = generateShortUrl();
        }
        return ret;
    }

    private String convertToValidUrl(String originalUrl) {
        String ret = originalUrl;
        if (!originalUrlIsValid(originalUrl)) {
            ret = "http://" + originalUrl;
        }
        return ret;
    }

    private boolean originalUrlIsValid(String originalUrl) {
        boolean ret = false;
        for (ValidUrl validUrl : ValidUrl.values()) {
            if (originalUrl.startsWith(validUrl.getValue())) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    private String generateShortUrl() {
        String ret = initEmptyString();

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

    private OriginalUrl findOriginalUrl(String url) {
        return urlRepository.findOriginalUrl(convertToValidUrl(url));
    }

    @Override
    public OriginalUrl findOriginalUrlByShortUrl(String url) {
        OriginalUrl origUrl = urlRepository.findShortUrl(url).getOriginalUrl();
        return origUrl;
    }

    @Override
    public List<OriginalUrl> getAllOriginalUrls() {
        //todo: transform to DTO and insert line breaks
        return urlRepository.findAllOriginalUrls();
    }

    @Override
    public List<ShortUrl> getAllExpiredShortUrls() {
        List<ShortUrl> list = urlRepository.findAllShortUrls();
        List<ShortUrl> ret = new ArrayList<ShortUrl>();

        Date now = new Date();
        for (ShortUrl shortUrl : list) {
            if (shortUrl.getActiveUntil() < now.getTime()) {
                ret.add(shortUrl);
                logger.info("add");
            }
        }

        return ret;
    }

    @Override
    public void removeShortUrl(ShortUrl shortUrl) {
        List<Creator> creators = userRepository.findAllUser();

        for (Creator creator : creators) {
            creator.removeShortUrl(shortUrl);
            userRepository.update(creator);
        }

        urlRepository.remove(shortUrl);
    }
}
