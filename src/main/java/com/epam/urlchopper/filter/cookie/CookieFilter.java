package com.epam.urlchopper.filter.cookie;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet Filter implementation class CookieFilter.
 */
public class CookieFilter implements Filter {

    public static final String USER_COOKIE_NAME = "urlchopper_userid";

    private Logger logger = LoggerFactory.getLogger(CookieFilter.class);

    /**
     * Default constructor.
     */
    public CookieFilter() {
        // TODO Auto-generated constructor stub
    }

    /**
     * .
     * @see Filter#destroy()
     */
    public void destroy() {
        // TODO Auto-generated method stub
    }

    /**
     * .
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // TODO Auto-generated method stub
        // place your code here
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        Cookie[] cookies = httpRequest.getCookies();

        if (cookies != null) {
            int i = 0;
            while (i < cookies.length && !cookies[i].getName().equals(USER_COOKIE_NAME)) {
                i++;
            }
            if (i < cookies.length) {
                logger.info("cookie already exists in browser with userid: " + cookies[i].getValue());
                addUserIdToSession(httpRequest, cookies[i].getValue());
            }
        }

        // pass the request along the filter chain
        chain.doFilter(httpRequest, httpResponse);
    }

    private void addUserIdToSession(HttpServletRequest httpRequest, String userId) {
        if (httpRequest.getSession().getAttribute(USER_COOKIE_NAME) == null) {
            httpRequest.getSession().setAttribute(USER_COOKIE_NAME, userId);
        }
    }

    /**
     * .
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        // TODO Auto-generated method stub
    }
}
