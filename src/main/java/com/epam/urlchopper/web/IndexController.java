package com.epam.urlchopper.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.epam.urlchopper.service.GeneratorService;

/**
 * Controller to handle short url requests.
 * @author Marton_Sereg
 *
 */
@Controller
public class IndexController {

    private static final String TOP_SECRET_URL = "http://www.youtube.com/watch?v=SLmmfYd8dos";
    private static final String TOP_SECRET = "pinapinapunci";
    private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private GeneratorService generatorService;

    /**
     * Controller for index page.
     * @return tiles name.
     */
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    /**
     * Method to handle url generation requests.
     * @param url Original Url to be replaced
     * @param model MVC model
     * @return tiles name
     */
    @RequestMapping("/generateUrl")
    public String generate(@RequestParam String url, RedirectAttributes model) {

        LOG.debug("start");

        String nUrl = url;
        if (url.equals(TOP_SECRET)) {
            nUrl = TOP_SECRET_URL;
        }

        String shortUrl = generatorService.generate(nUrl);

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
            String url = generatorService.findOriginalUrl(shortUrl);
            //retUrl = "redirect:" + url;
            model.addFlashAttribute("url", url);
            retUrl = "redirect:/waitpage";
        } catch (Exception e) {
            model.addFlashAttribute("errorMsg", "This URL is not valid!");
            retUrl = "redirect:/";
        }

        return retUrl;
    }

    /**
     * When send redirect for the original URL, this controller show a wait page.
     * @return view page
     */
    @RequestMapping("/waitpage")
    public String waitpage() {
        return "waitpage";
    }

    //    @ModelAttribute("lastUrls")
    //    public List<ShortUrlDTO> lastUrls(){
    //        List<ShortUrlDTO> l = generatorService.getLastShortUrlHistory(10);
    //        return generatorService.getLastShortUrlHistory(10);
    //    }
}
