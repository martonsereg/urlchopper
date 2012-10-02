package com.github.urlchopper.service.simple;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
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

    private static final Integer GENERATED_URL_LENGTH = 6;

    private static final Long SHORT_URL_LIFESPAN = 86400000L;

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
    }

    @Override
    public String generate(String originalUrl) {

        String ret = "";

        ret = generateSingleUrl();

        while (isExistUrl(ret)) {
            ret = generateSingleUrl();
        }

        ShortUrl url = new ShortUrl();
        url.setActiveUntil(new Date().getTime() + SHORT_URL_LIFESPAN);
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(ret);

        shortUrlRepository.create(url);

        return ret;
    }

    private String generateSingleUrl() {
        String ret = "";

        for (int i = 0; i < GENERATED_URL_LENGTH; i++) {
            double rnd = Math.random();
            int index = (int) ((rnd * MULTIPLY) % characters.size());
            ret += characters.get(index);
        }
        return ret;
    }

    private boolean isExistUrl(String url) {

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
