package com.epam.urlchopper.web;

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

    private static final String TOP_SECRET = "pinapinapunci";
    
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

        if (url.equals(TOP_SECRET)){
            url = "http://www.youtube.com/watch?v=SLmmfYd8dos";
        }
        
        String shortUrl = generatorService.generate(url);

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

        System.out.println(shortUrl);
        String retUrl = "";
        try {
            String url = generatorService.findActiveOriginalUrl(shortUrl);
            retUrl = "redirect:" + url;
            //retUrl = "redirect:/waitpage";
        } catch (Exception e) {
            model.addFlashAttribute("errorMsg", "This URL is not valid!");
            retUrl = "redirect:/";
        }

        return retUrl;
    }
    
    @RequestMapping("/waitpage")
    public String waitpage() {
        return "waitpage";
    }
    
//    @ModelAttribute("lastUrls")
//    public List<ShortUrlDTO> lastUrls(){
//        List<ShortUrlDTO> l = generatorService.getLastShortUrlHistory(10);
//        System.out.println(l.size());
//        return generatorService.getLastShortUrlHistory(10);
//    }
}
