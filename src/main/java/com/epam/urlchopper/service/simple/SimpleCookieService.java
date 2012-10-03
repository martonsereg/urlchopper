package com.epam.urlchopper.service.simple;

import javax.servlet.http.Cookie;

import org.springframework.stereotype.Service;

import com.epam.urlchopper.service.CookieService;

/**
 *  .
 */
@Service
public class SimpleCookieService implements CookieService {

    private static final String COOKIES_NAME = "urlchopper_userid";

    @Override
    public Long getUserId(Cookie[] cookies) {
        Long ret = null;
        if (cookies != null) {

            int i = 0;
            while (i < cookies.length && !cookies[i].getName().equals(COOKIES_NAME)) {
                i++;
            }

            if (i <= cookies.length) {
                ret = Long.valueOf(cookies[i].getValue());
            }
        }

        return ret;
    }

}
