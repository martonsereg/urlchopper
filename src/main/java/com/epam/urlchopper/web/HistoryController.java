package com.epam.urlchopper.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epam.urlchopper.domain.ShortUrlDTO;
import com.epam.urlchopper.service.CookieService;
import com.epam.urlchopper.service.HistoryService;

/**
 * Controller for histories.
 * @author Gergely_Topolyai
 *
 */
@Controller
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private CookieService cookieService;

    /**
     * Handle myUrls request.
     * @param request request
     * @return view page
     */
    @RequestMapping("/myUrls")
    public String myUrls(HttpServletRequest request, HttpServletResponse response, Model model) {
        Long userId = cookieService.getUserId(request.getCookies());

        if (userId != null) {
            List<ShortUrlDTO> shortUrls = historyService.getUserUrls(userId);
            model.addAttribute("shortUrls", shortUrls);
        }

        return "myUrls";
    }

    /**
     * Handle allUrls request.
     * @return view page
     */
    @RequestMapping("/allUrls")
    public String allUrls(HttpServletRequest request, HttpServletResponse response, Long userId) {
        return "allUrls";
    }
}
