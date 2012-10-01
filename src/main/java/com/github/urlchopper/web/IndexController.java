package com.github.urlchopper.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IndexController {

    
    @RequestMapping("/")
    public String index(){        
        return "index";
    }
    
    @RequestMapping("/generateUrl")
    public String generate(RedirectAttributes model){
        
        String generatedUrl = "generatedUrl";
        model.addFlashAttribute("generatedUrl", generatedUrl);
        return "redirect:/";
    }
}
