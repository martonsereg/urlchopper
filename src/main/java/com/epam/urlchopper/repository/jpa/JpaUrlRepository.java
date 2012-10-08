package com.epam.urlchopper.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.epam.urlchopper.domain.OriginalUrl;
import com.epam.urlchopper.domain.ShortUrl;
import com.epam.urlchopper.repository.UrlRepository;

/** Jpa based implementation of ShortUrlRepository.
 * @see com.epam.urlchopper.repository.UrlRepository
 */
@Repository
public class JpaUrlRepository implements UrlRepository {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long countShortUrls() {
        return entityManager.createQuery("SELECT COUNT(o) FROM ShortUrl o", Long.class).getSingleResult();
    }

    @Override
    public List<ShortUrl> findAllShortUrls() {
        return entityManager.createQuery("SELECT o FROM ShortUrl o", ShortUrl.class).getResultList();
    }

    @Override
    public ShortUrl findShortUrl(String shortUrlPostfix) {
        ShortUrl ret = null;
        if (shortUrlPostfix != null) {
            ret = entityManager.find(ShortUrl.class, shortUrlPostfix);
        }
        return ret;
    }

    @Override
    @Transactional
    public void createShortUrl(ShortUrl url) {
        entityManager.persist(url);
    }

    @Override
    @Transactional
    public void createOriginalUrl(OriginalUrl url) {
        entityManager.persist(url);
    }

    @Override
    @Transactional
    public void remove(ShortUrl shortUrl) {
        if (entityManager.contains(shortUrl)) {
            entityManager.remove(shortUrl);
        } else {
            ShortUrl attached = findShortUrl(shortUrl.getShortUrlPostfixId());
            this.entityManager.remove(attached);
        }
    }

    @Override
    @Transactional
    public ShortUrl update(ShortUrl shortUrl) {
        ShortUrl merged = entityManager.merge(shortUrl);
        entityManager.flush();
        return merged;
    }

    @Override
    public OriginalUrl findOriginalUrl(String url) {
        return entityManager.find(OriginalUrl.class, url);
    }

    @Override
    @Transactional
    public void mergeOriginalUrl(OriginalUrl url) {
        entityManager.merge(url);

    }

    @Override
    @Transactional
    public void lengthenLifespan(ShortUrl shortUrl, Long lifespanEnd) {
        shortUrl.setActiveUntil(lifespanEnd);
        entityManager.merge(shortUrl);

    }

    @Override
    public List<OriginalUrl> findAllOriginalUrls() {
        return entityManager.createQuery("SELECT o FROM OriginalUrl o", OriginalUrl.class).getResultList();
    }
}
