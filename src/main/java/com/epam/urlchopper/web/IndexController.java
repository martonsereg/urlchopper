package com.epam.urlchopper.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.epam.urlchopper.domain.ShortUrlDTO;
import com.epam.urlchopper.filter.cookie.CookieFilter;
import com.epam.urlchopper.service.HistoryService;
import com.epam.urlchopper.service.UrlService;

/**
 * Controller to handle short url requests.
 */
@Controller
public class IndexController {

    private static final int PAGINATION_IN_EVERY_CHARACTER = 100;

    private Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private UrlService urlService;

    @Autowired
    private HistoryService historyService;

    /**
     * Controller for index page.
     * @return tiles name.
     */
    @RequestMapping("/")
    public String index(HttpSession session, Model model) {
        try {
            if (session.getAttribute(CookieFilter.USER_COOKIE_NAME) != null) {
                Long userId = Long.valueOf(session.getAttribute(CookieFilter.USER_COOKIE_NAME).toString());
                List<ShortUrlDTO> shortUrls = brokenUsersUrls(historyService.getUserUrls(userId));
                model.addAttribute("shortUrls", shortUrls);
            } else {
                logger.error("Session attribute cannot be found");
            }
        } catch (NumberFormatException e) {
            logger.error("Session attribute cannot be parsed to Long " + e.getMessage());
        }
        return "index";
    }

    /**
     * Method to handle url generation requests.
     * @param url Original Url to be replaced
     * @param model MVC model
     * @return tiles name
     */
    @RequestMapping("/generateUrl")
    public String generate(@RequestParam String url, HttpServletRequest request, RedirectAttributes model) {

        Long userId = null;
        try {
            if (request.getSession().getAttribute(CookieFilter.USER_COOKIE_NAME) != null) {
                userId = Long.valueOf(request.getSession().getAttribute(CookieFilter.USER_COOKIE_NAME).toString());
            }
        } catch (NumberFormatException e) {
            logger.error("Session attribute cannot be parsed to Long " + e.getMessage());
        }
        String shortUrl = urlService.generate(url, userId);

        model.addFlashAttribute("shortUrl", shortUrl);
        return "redirect:/";
    }

    /**
     * Method to handle redirects from short urls to original urls.
     * @param shortUrl Short url
     * @param model MVC model
     * @return returnUrl
     */
    @RequestMapping("/{shortUrl}")
    public String redirect(@PathVariable String shortUrl, RedirectAttributes model) {

        String retUrl = "";
        try {
            String url = urlService.findOriginalUrlByShortUrl(shortUrl).getUrlId();
            model.addFlashAttribute("url", url);
            retUrl = "redirect:/waitpage";
        } catch (Exception e) {
            model.addFlashAttribute("errorMsg", "This URL is not valid!");
            retUrl = "redirect:/";
        }

        return retUrl;
    }

    /**
     * Before redirected to the original URL, a wait page is shown for a few seconds.
     * @return view page
     */
    @RequestMapping("/waitpage")
    public String waitpage() {
        return "waitpage";
    }

    private List<ShortUrlDTO> brokenUsersUrls(List<ShortUrlDTO> list) {
        List<ShortUrlDTO> ret = new ArrayList<ShortUrlDTO>();

        for (ShortUrlDTO shortUrlDTO : list) {
            String newOriginalUrl = insertBreak(shortUrlDTO.getOriginalUrl());
            ShortUrlDTO dto = new ShortUrlDTO();
            dto.setOriginalUrl(newOriginalUrl);
            dto.setShortUrl(shortUrlDTO.getShortUrl());
            ret.add(dto);
        }

        return ret;
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
