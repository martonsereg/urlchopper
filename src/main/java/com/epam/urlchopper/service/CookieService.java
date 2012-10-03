package com.epam.urlchopper.service;

import javax.servlet.http.Cookie;

/**
 *  .
 */
public interface CookieService {

    Long getUserId(Cookie[] cookies);
}
