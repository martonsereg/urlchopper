package com.github.urlchopper.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.urlchopper.domain.ShortUrl;
import com.github.urlchopper.repository.ShortUrlRepository;

@Controller
public class IndexController {

    @Autowired
    private ShortUrlRepository urlRepository;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/generateUrl")
    public String generate(@RequestParam String url, RedirectAttributes model) {

        String generatedUrl = generate();

        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setOriginalUrl(url);
        shortUrl.setShortUrl(generatedUrl);

        urlRepository.persist(shortUrl);

        model.addFlashAttribute("shortUrl", shortUrl);
        return "redirect:/";
    }

    @RequestMapping("/{shortUrl}")
    public String redirect(@PathVariable String shortUrl, RedirectAttributes model) {

        String retUrl = "";
        try {
            ShortUrl url = urlRepository.findShortUrlsByShortUrlLike(shortUrl).getSingleResult();
            retUrl = "redirect:" + url.getOriginalUrl(); 
        } catch (Exception e) {
            model.addFlashAttribute("errorMsg", "This URL is not valid!");
            retUrl = "redirect:/index";
        }

        return retUrl;
    }
    
    private static int id;

    private String generate() {
        return "genUrl" + id++;
    }
}
