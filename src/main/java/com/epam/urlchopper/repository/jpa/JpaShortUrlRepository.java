package com.epam.urlchopper.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.epam.urlchopper.domain.ShortUrl;
import com.epam.urlchopper.repository.ShortUrlRepository;

/** Jpa based implementation of ShortUrlRepository.
 * @see com.epam.urlchopper.repository.ShortUrlRepository
 * @author Marton_Sereg
 *
 */
@Repository
public class JpaShortUrlRepository implements ShortUrlRepository {

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
    public void create(ShortUrl shortUrl) {
        entityManager.persist(shortUrl);
    }

    @Override
    @Transactional
    public void remove(ShortUrl shortUrl) {
        if (entityManager.contains(shortUrl)) {
            entityManager.remove(shortUrl);
        } else {
            ShortUrl attached = findShortUrl(shortUrl.getShortUrlPostfix());
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
    public List<ShortUrl> findShortUrlsByOriginalUrlEquals(String originalUrl) {
        if (originalUrl == null || originalUrl.length() == 0) {
            throw new IllegalArgumentException("The originalUrl argument is required");
        }
        TypedQuery<ShortUrl> q = entityManager.createQuery("SELECT o FROM ShortUrl AS o WHERE o.originalUrl = :originalUrl", ShortUrl.class);
        q.setParameter("originalUrl", originalUrl);
        return q.getResultList();
    }

    @Override
    public List<ShortUrl> findShortUrlsByOriginalUrlLike(String originalUrl) {
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
        TypedQuery<ShortUrl> q = entityManager.createQuery("SELECT o FROM ShortUrl AS o WHERE LOWER(o.originalUrl) LIKE LOWER(:originalUrl)", ShortUrl.class);
        q.setParameter("originalUrl", safeOriginalUrl);
        return q.getResultList();
    }
}
