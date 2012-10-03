package com.epam.urlchopper.service.simple;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.urlchopper.domain.ShortUrl;
import com.epam.urlchopper.domain.ShortUrlDTO;
import com.epam.urlchopper.domain.User;
import com.epam.urlchopper.repository.UrlRepository;
import com.epam.urlchopper.repository.UserRepository;
import com.epam.urlchopper.service.HistoryService;

/**
 * Simple implementation for HistoryService.
 * @author Gergely_Topolyai
 */
@Service
public class SimpleHistoryService implements HistoryService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UrlRepository urlRepository;

    @Override
    public List<ShortUrlDTO> getUserUrls(Long userId) {
        User user = userRepository.findUser(userId);
        List<ShortUrlDTO> retUrls = new ArrayList<ShortUrlDTO>();

        for (ShortUrl shorturl : user.getShortUrls()) {
            ShortUrlDTO dto = new ShortUrlDTO();
            dto.setOriginalUrl(shorturl.getOriginalUrl().getUrl());
            dto.setShortUrl(shorturl.getShortUrlPostfix());
            retUrls.add(dto);
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
            dto.setOriginalUrl(url.getOriginalUrl().getUrl());
            dto.setShortUrl(url.getShortUrlPostfix());
        }

        return ret;
    }

}
