package com.epam.urlchopper.repository.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.epam.urlchopper.domain.ShortUrlAlt;
import com.epam.urlchopper.repository.ShortUrlAltRepository;

@Repository
public class JpaShortUrlAltRepository implements ShortUrlAltRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public long countShortUrlAlts() {
        return entityManager.createQuery("SELECT COUNT(o) FROM ShortUrlAlt o", Long.class).getSingleResult();
    }

    @Override
    public List<ShortUrlAlt> findAllShortUrlAlts() {
        return entityManager.createQuery("SELECT o FROM ShortUrlAlt o", ShortUrlAlt.class).getResultList();
    }

    @Override
    public ShortUrlAlt findShortUrlAlt(String url) {
        ShortUrlAlt ret = null;
        if (url != null) {
            ret = entityManager.find(ShortUrlAlt.class, url);
        }
        return ret;
    }

    @Override
    @Transactional
    public void remove(ShortUrlAlt shortUrl) {
        if (entityManager.contains(shortUrl)) {
            entityManager.remove(shortUrl);
        } else {
            ShortUrlAlt attached = findShortUrlAlt(shortUrl.getShortUrl());
            this.entityManager.remove(attached);
        }
    }

    @Override
    @Transactional
    public ShortUrlAlt update(ShortUrlAlt shortUrl) {
        ShortUrlAlt merged = entityManager.merge(shortUrl);
        entityManager.flush();
        return merged;
    }

    @Override
    public List<ShortUrlAlt> findShortUrlAltsByOriginalUrlEquals(String originalUrl) {
        if (originalUrl == null || originalUrl.length() == 0) {
            throw new IllegalArgumentException("The originalUrl argument is required");
        }
        TypedQuery<ShortUrlAlt> q = entityManager.createQuery("SELECT o FROM ShortUrlAlt AS o WHERE o.originalUrl = :originalUrl", ShortUrlAlt.class);
        q.setParameter("originalUrl", originalUrl);
        return q.getResultList();
    }

    @Override
    public List<ShortUrlAlt> findShortUrlAltsByOriginalUrlLike(String originalUrl) {
        if (originalUrl == null || originalUrl.length() == 0) {
            throw new IllegalArgumentException("The originalUrl argument is required");
        }
        String safeOriginalUrl = originalUrl.replace('*', '%');
        if (safeOriginalUrl.charAt(0) != '%') {
            safeOriginalUrl = "%" + safeOriginalUrl;
        }
        if (safeOriginalUrl.charAt(safeOriginalUrl.length() - 1) != '%') {
            safeOriginalUrl = safeOriginalUrl + "%";
        }
        TypedQuery<ShortUrlAlt> q = entityManager.createQuery("SELECT o FROM ShortUrlAlt AS o WHERE LOWER(o.originalUrl) LIKE LOWER(:originalUrl)", ShortUrlAlt.class);
        q.setParameter("originalUrl", safeOriginalUrl);
        return q.getResultList();
    }

    @Override
    public ShortUrlAlt findShortUrlAltByShortUrlAltEquals(String shortUrl) {
        if (shortUrl == null || shortUrl.length() == 0) {
            throw new IllegalArgumentException("The shortUrl argument is required");
        }
        TypedQuery<ShortUrlAlt> q = entityManager.createQuery("SELECT o FROM ShortUrlAlt AS o WHERE o.shortUrl = :shortUrl and o.activeUntil >= :dateNow", ShortUrlAlt.class);
        q.setParameter("shortUrl", shortUrl);
        q.setParameter("dateNow", new Date().getTime());
        return q.getSingleResult();
    }

    @Override
    public ShortUrlAlt findShortUrlAltByShortUrlAltLike(String shortUrl) {
        if (shortUrl == null || shortUrl.length() == 0) {
            throw new IllegalArgumentException("The shortUrl argument is required");
        }
        TypedQuery<ShortUrlAlt> q = entityManager.createQuery("SELECT o FROM ShortUrlAlt AS o WHERE LOWER(o.shortUrl) LIKE LOWER(:shortUrl) and o.activeUntil >= :dateNow",
                ShortUrlAlt.class);
        q.setParameter("shortUrl", shortUrl);
        q.setParameter("dateNow", new Date().getTime());
        return q.getSingleResult();
    }

    @Override
    @Transactional
    public void create(ShortUrlAlt shortUrl) {
        entityManager.persist(shortUrl);
    }

}
