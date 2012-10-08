package com.epam.urlchopper.service.simple;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.urlchopper.domain.ShortUrl;
import com.epam.urlchopper.domain.ShortUrlDTO;
import com.epam.urlchopper.domain.Creator;
import com.epam.urlchopper.repository.UrlRepository;
import com.epam.urlchopper.repository.UserRepository;
import com.epam.urlchopper.service.HistoryService;

/**
 * Simple implementation for HistoryService.
 */
@Service
public class SimpleHistoryService implements HistoryService {

    private static final int PAGINATION_IN_EVERY_CHARACTER = 100;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UrlRepository urlRepository;

    @Override
    public List<ShortUrlDTO> getUserUrls(Long userId) {
        Creator user = userRepository.findUser(userId);
        List<ShortUrlDTO> retUrls = new ArrayList<ShortUrlDTO>();

        if (user != null) {
            for (ShortUrl shorturl : user.getShortUrls()) {
                ShortUrlDTO dto = new ShortUrlDTO();
                dto.setOriginalUrl(insertBreak(shorturl.getOriginalUrl().getUrlId()));
                dto.setShortUrl(shorturl.getShortUrlPostfixId());
                retUrls.add(dto);
            }
        }
        return retUrls;
    }

    @Override
    public List<ShortUrl> getAllUrls() {
        return urlRepository.findAllShortUrls();
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
            dto.setOriginalUrl(url.getOriginalUrl().getUrlId());
            dto.setShortUrl(url.getShortUrlPostfixId());
        }

        return ret;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setUrlRepository(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    private String insertBreak(String string) {
        String ret = "";
        int i = 1;
        for (Character c : string.toCharArray()) {
            ret += c;
            if (i % PAGINATION_IN_EVERY_CHARACTER == 0) {
                ret += "<br>";
            }
            i++;
        }

        return ret;
    }

}
