package com.epam.urlchopper.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for histories.
 * @author Gergely_Topolyai
 *
 */
@Controller
public class HistoryController {

    /**
     * Handle myUrls request.
     * @return view page
     */
    @RequestMapping("/myUrls")
    public String myUrls(HttpServletRequest request, HttpServletResponse response) {
        return "myUrls";
    }

    /**
     * Handle allUrls request.
     * @return view page
     */
    @RequestMapping("/allUrls")
    public String allUrls(HttpServletRequest request, HttpServletResponse response) {
        return "allUrls";
    }
}
