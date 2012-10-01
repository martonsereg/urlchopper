package com.github.urlchopper.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.urlchopper.domain.ShortUrl;
import com.github.urlchopper.repository.ShortUrlRepository;

/**
 * Controller to handle short url requests.
 * @author Marton_Sereg
 *
 */
@Controller
public class IndexController {

    private static int id;

    @Autowired
    private ShortUrlRepository urlRepository;

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

        String generatedUrl = generate();

        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setOriginalUrl(url);
        shortUrl.setShortUrl(generatedUrl);

        urlRepository.create(shortUrl);

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
            //todo
            ShortUrl url = urlRepository.findShortUrlsByShortUrlLike(shortUrl).get(0);
            retUrl = "redirect:" + url.getOriginalUrl();
        } catch (Exception e) {
            model.addFlashAttribute("errorMsg", "This URL is not valid!");
            retUrl = "redirect:/index";
        }

        return retUrl;
    }

    private String generate() {
        return "genUrl" + id++;
    }
}
