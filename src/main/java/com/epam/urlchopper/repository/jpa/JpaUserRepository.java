package com.epam.urlchopper.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.epam.urlchopper.domain.Creator;
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
    public Creator create(Creator user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public Creator findUser(Long userId) {
        return entityManager.find(Creator.class, userId);
    }

    @Override
    @Transactional
    public void update(Creator user) {
        entityManager.merge(user);
    }

    @Override
    public List<Creator> findAllUser() {
        return entityManager.createQuery("SELECT c FROM Creator c", Creator.class).getResultList();
    }

}
