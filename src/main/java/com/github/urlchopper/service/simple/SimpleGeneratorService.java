package com.github.urlchopper.service.simple;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.urlchopper.repository.ShortUrlRepository;
import com.github.urlchopper.service.GeneratorService;

/**
 * .
 * @author Marton_Sereg
 *
 */
@Service
public class SimpleGeneratorService implements GeneratorService {

    private List<Character> characters = new ArrayList<Character>();

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    /**
     * todo: constants.
     */
    @PostConstruct
    public void createCharacterList() {
        for (int i = 65; i <= 90; i++) {
            characters.add((char) i);
        }
        for (int i = 97; i <= 122; i++) {
            characters.add((char) i);
        }
        for (int i = 48; i <= 57; i++) {
            characters.add((char) i);
        }
    }

    @Override
    public String generate(String originalUrl) {

        return null;
    }

    @Override
    public String findActiveShortUrl(String shortUrl) {
        
        return null;
    }

}
