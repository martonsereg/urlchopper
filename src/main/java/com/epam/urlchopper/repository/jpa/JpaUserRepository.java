package com.epam.urlchopper.repository.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.epam.urlchopper.domain.User;
import com.epam.urlchopper.repository.UserRepository;

/**
 *  .
 */
@Repository
public class JpaUserRepository implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void create(User user) {
        entityManager.persist(user);
    }

    @Override
    public User findUser(Long userId) {
        return entityManager.find(User.class, userId);
    }

    @Override
    @Transactional
    public void update(User user) {
        entityManager.merge(user);
    }

}
