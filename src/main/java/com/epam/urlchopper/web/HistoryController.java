package com.epam.urlchopper.web;

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
    public String myUrls() {
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
}
