package com.epam.urlchopper.repository;

import com.epam.urlchopper.domain.Creator;

/**
 * .
 */
public interface UserRepository {

    Creator create(Creator user);

    Creator findUser(Long userId);

    void update(Creator user);

}
