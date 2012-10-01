package com.github.urlchopper.repository.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.urlchopper.domain.ShortUrl;
import com.github.urlchopper.repository.ShortUrlRepository;

/** Jpa based implementation of ShortUrlRepository.
 * @see com.github.urlchopper.repository.ShortUrlRepository
 * @author Marton_Sereg
 *
 */
@Repository
public class JpaShortUrlRepository implements ShortUrlRepository {

    private static final Long SHORT_URL_LIFESPAN = 86400000L;

    @PersistenceContext
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see com.github.urlchopper.repository.jpa.ShortUrlRepository#countShortUrls()
     */
    @Override
    public long countShortUrls() {
        return entityManager.createQuery("SELECT COUNT(o) FROM ShortUrl o", Long.class).getSingleResult();
    }

    /* (non-Javadoc)
     * @see com.github.urlchopper.repository.jpa.ShortUrlRepository#findAllShortUrls()
     */
    @Override
    public List<ShortUrl> findAllShortUrls() {
        return entityManager.createQuery("SELECT o FROM ShortUrl o", ShortUrl.class).getResultList();
    }

    /* (non-Javadoc)
     * @see com.github.urlchopper.repository.jpa.ShortUrlRepository#findShortUrl(java.lang.Long)
     */
    @Override
    public ShortUrl findShortUrl(Long id) {
        ShortUrl ret = null;
        if (id != null) {
            ret = entityManager.find(ShortUrl.class, id);
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see com.github.urlchopper.repository.jpa.ShortUrlRepository#create(com.github.urlchopper.domain.ShortUrl)
     */
    @Override
    @Transactional
    public void create(ShortUrl shortUrl) {
        entityManager.persist(shortUrl);
    }

    /* (non-Javadoc)
     * @see com.github.urlchopper.repository.jpa.ShortUrlRepository#remove(com.github.urlchopper.domain.ShortUrl)
     */
    @Override
    @Transactional
    public void remove(ShortUrl shortUrl) {
        if (entityManager.contains(shortUrl)) {
            entityManager.remove(shortUrl);
        } else {
            ShortUrl attached = findShortUrl(shortUrl.getId());
            this.entityManager.remove(attached);
        }
    }

    /* (non-Javadoc)
     * @see com.github.urlchopper.repository.jpa.ShortUrlRepository#update(com.github.urlchopper.domain.ShortUrl)
     */
    @Override
    @Transactional
    public ShortUrl update(ShortUrl shortUrl) {
        ShortUrl merged = entityManager.merge(shortUrl);
        entityManager.flush();
        return merged;
    }

    /* (non-Javadoc)
     * @see com.github.urlchopper.repository.jpa.ShortUrlRepository#findShortUrlsByOriginalUrlEquals(java.lang.String)
     */
    @Override
    public List<ShortUrl> findShortUrlsByOriginalUrlEquals(String originalUrl) {
        if (originalUrl == null || originalUrl.length() == 0) {
            throw new IllegalArgumentException("The originalUrl argument is required");
        }
        TypedQuery<ShortUrl> q = entityManager.createQuery("SELECT o FROM ShortUrl AS o WHERE o.originalUrl = :originalUrl", ShortUrl.class);
        q.setParameter("originalUrl", originalUrl);
        return q.getResultList();
    }

    /* (non-Javadoc)
     * @see com.github.urlchopper.repository.jpa.ShortUrlRepository#findShortUrlsByOriginalUrlLike(java.lang.String)
     */
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
        TypedQuery<ShortUrl> q = entityManager.createQuery("SELECT o FROM ShortUrl AS o WHERE LOWER(o.originalUrl) LIKE LOWER(:originalUrl)",
                ShortUrl.class);
        q.setParameter("originalUrl", safeOriginalUrl);
        return q.getResultList();
    }

    /* (non-Javadoc)
     * @see com.github.urlchopper.repository.jpa.ShortUrlRepository#findShortUrlsByShortUrlEquals(java.lang.String)
     */
    @Override
    public ShortUrl findShortUrlByShortUrlEquals(String shortUrl) {
        if (shortUrl == null || shortUrl.length() == 0) {
            throw new IllegalArgumentException("The shortUrl argument is required");
        }
        TypedQuery<ShortUrl> q = entityManager.createQuery("SELECT o FROM ShortUrl AS o WHERE o.shortUrl = :shortUrl and o.activeUntil >= :dateNow", ShortUrl.class);
        q.setParameter("shortUrl", shortUrl);
        q.setParameter("dateNow", new Date().getTime());
        return q.getSingleResult();
    }

    /* (non-Javadoc)
     * @see com.github.urlchopper.repository.jpa.ShortUrlRepository#findShortUrlsByShortUrlLike(java.lang.String)
     */
    @Override
    public ShortUrl findShortUrlByShortUrlLike(String shortUrl) {
        if (shortUrl == null || shortUrl.length() == 0) {
            throw new IllegalArgumentException("The shortUrl argument is required");
        }
        TypedQuery<ShortUrl> q = entityManager.createQuery("SELECT o FROM ShortUrl AS o WHERE LOWER(o.shortUrl) LIKE LOWER(:shortUrl) and o.activeUntil >= :dateNow",
                ShortUrl.class);
        q.setParameter("shortUrl", shortUrl);
        q.setParameter("dateNow", new Date().getTime());
        return q.getSingleResult();
    }
}
