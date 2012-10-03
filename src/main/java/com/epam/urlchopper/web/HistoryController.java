package com.epam.urlchopper.web;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.epam.urlchopper.domain.ShortUrlDTO;
import com.epam.urlchopper.service.HistoryService;

/**
 * Controller for histories.
 * @author Gergely_Topolyai
 *
 */
@Controller
public class HistoryController {

    private static final String COOKIES_NAME = "urlchopper_userid";
    @Autowired
    private HistoryService historyService;

    /**
     * Handle myUrls request.
     * @param request request
     * @return view page
     */
    @RequestMapping("/myUrls")
    public String myUrls(HttpServletRequest request, RedirectAttributes model) {
        Long userId = getUserId(request);

        List<ShortUrlDTO> shortUrls = historyService.getUserUrls(userId);
        model.addFlashAttribute("shortUrls", shortUrls);

        return "myUrls";
    }

    /**
     * Handle allUrls request.
     * @return view page
     */
    @RequestMapping("/allUrls")
    public String allUrls() {
        return "allUrls";
    }

    private Long getUserId(HttpServletRequest request) {
        Long ret = null;

        Cookie[] cookies = request.getCookies();

        int i = 0;
        while (i < cookies.length && !cookies[i].getName().equals(COOKIES_NAME)) {
            i++;
        }

        if (i >= cookies.length) {
            ret = Long.valueOf(cookies[i].getValue());
        }

        return ret;
    }
}
