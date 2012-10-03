package com.epam.urlchopper.aop.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.epam.urlchopper.domain.User;
import com.epam.urlchopper.repository.UserRepository;

/**
 * A new cookie is generated to remember the user the next time he visits the site.
 */
@Configurable
@Aspect
public class CookieGeneratorAspect {

    private Logger logger = LoggerFactory.getLogger(CookieGeneratorAspect.class);
    private HttpServletRequest request;
    private HttpServletResponse response;

    @Autowired
    private UserRepository userRepository;

    /**
     * Adds the new cookie if needed.
     * @param joinPoint
     * @return
     */
    @Before("controllerMethods()")
    public void addCookie(JoinPoint joinPoint) {

        try {
            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                if ("RequestFacade".equals(arg.getClass().getSimpleName())) {
                    request = (HttpServletRequest) arg;
                }
                if ("ResponseFacade".equals(arg.getClass().getSimpleName())) {
                    response = (HttpServletResponse) arg;
                }
                logger.info("next arg: " + arg.getClass().getName());
            }

            if (request != null && response != null) {
                Cookie[] cookies = request.getCookies();
                boolean cookieExists = false;
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ("urlchopper_userid".equals(cookie.getName())) {
                            cookieExists = true;
                            logger.info("cookie already exists in browser with userid: " + cookie.getValue());
                            //todo: lengthen cookie lifespan
                        }
                    }
                }
                if (!cookieExists) {
                    User user = createUser();
                    createCookie(user);
                }

            }
        } catch (Throwable e) {
            logger.error(e.getMessage());
        }
    }

    private User createUser() {
        User user = userRepository.create(new User());
        logger.info("new user added with id: " + user.getUserId());
        return user;
    }

    private void createCookie(User user) {
        Cookie cookie = new Cookie("urlchopper_userid", user.getUserIdAsString());
        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);
        logger.info("cookie added to response with userid: " + cookie.getValue());
    }

    @Pointcut("execution(* com.epam.urlchopper.web.*.*(..))")
    public void controllerMethods() {
    }

}
