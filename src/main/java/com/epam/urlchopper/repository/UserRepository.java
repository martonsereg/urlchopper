package com.epam.urlchopper.repository;

import com.epam.urlchopper.domain.User;

/**
 * .
 */
public interface UserRepository {

    void create(User user);

    User findUser(Long userId);

    void update(User user);

}
