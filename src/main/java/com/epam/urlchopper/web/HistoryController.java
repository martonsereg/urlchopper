package com.epam.urlchopper.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epam.urlchopper.domain.ShortUrlDTO;
import com.epam.urlchopper.filter.cookie.CookieFilter;
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

    private Logger logger = LoggerFactory.getLogger(HistoryController.class);

    /**
     * Handle myUrls request.
     * @param request request
     * @return view page
     */
    @RequestMapping("/myUrls")
    public String myUrls(HttpSession session, Model model) {
        try {
            //if (session.getAttribute(CookieFilter.USER_COOKIE_NAME) != null) {
            Long userId = Long.valueOf(session.getAttribute(CookieFilter.USER_COOKIE_NAME).toString());
            List<ShortUrlDTO> shortUrls = historyService.getUserUrls(userId);
            model.addAttribute("shortUrls", shortUrls);
            //}
        } catch (NumberFormatException e) {
            logger.error("Session attribute cannot be parsed to Long " + e.getMessage());
        } catch (Exception e) {
            logger.error("Session attribute cannot be found " + e.getMessage());
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
