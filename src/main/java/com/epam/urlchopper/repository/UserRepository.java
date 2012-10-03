package com.epam.urlchopper.repository;

import com.epam.urlchopper.domain.User;

/**
 * .
 */
public interface UserRepository {

    User create(User user);

    User findUser(Long userId);

    void update(User user);

}
