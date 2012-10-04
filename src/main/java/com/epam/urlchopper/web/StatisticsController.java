package com.epam.urlchopper.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epam.urlchopper.domain.OriginalUrl;
import com.epam.urlchopper.domain.StatisticUrlDTO;
import com.epam.urlchopper.service.UrlService;

/**
 * .
 */
@Controller
public class StatisticsController {

    @Autowired
    private UrlService urlService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * .
     * @return view page
     */
    @RequestMapping("/statistics")
    public String statistics() {
        return "statistics";
    }

    /**
     * Get StatisticUrlDTO list.
     * @return list
     */
    @ModelAttribute("urls")
    public List<StatisticUrlDTO> urls() {
        List<OriginalUrl> list = urlService.getAllOriginalUrls();
        List<StatisticUrlDTO> retList = new ArrayList<StatisticUrlDTO>();

        for (OriginalUrl originalUrl : list) {
            StatisticUrlDTO dto = new StatisticUrlDTO(originalUrl.getUrl(), originalUrl.getReferenceCount());
            retList.add(dto);
        }
        return retList;
    }
}
