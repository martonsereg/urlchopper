package com.github.urlchopper.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.urlchopper.domain.ShortUrl;
import com.github.urlchopper.repository.ShortUrlRepository;

@Repository
public class JpaShortUrlRepository implements ShortUrlRepository   {
	
	@PersistenceContext
    transient EntityManager entityManager;
	
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
        if (id == null) return null;
        return entityManager.find(ShortUrl.class, id);
    }

	/* (non-Javadoc)
	 * @see com.github.urlchopper.repository.jpa.ShortUrlRepository#findShortUrlEntries(int, int)
	 */
	@Override
	public List<ShortUrl> findShortUrlEntries(int firstResult, int maxResults) {
        return entityManager.createQuery("SELECT o FROM ShortUrl o", ShortUrl.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	/* (non-Javadoc)
	 * @see com.github.urlchopper.repository.jpa.ShortUrlRepository#persist(com.github.urlchopper.domain.ShortUrl)
	 */
	@Override
	@Transactional
    public void persist(ShortUrl shortUrl) {
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
	 * @see com.github.urlchopper.repository.jpa.ShortUrlRepository#flush()
	 */
	@Override
	@Transactional
    public void flush() {
        entityManager.flush();
    }

	/* (non-Javadoc)
	 * @see com.github.urlchopper.repository.jpa.ShortUrlRepository#clear()
	 */
	@Override
	@Transactional
    public void clear() {
        entityManager.clear();
    }

	/* (non-Javadoc)
	 * @see com.github.urlchopper.repository.jpa.ShortUrlRepository#merge(com.github.urlchopper.domain.ShortUrl)
	 */
	@Override
	@Transactional
    public ShortUrl merge(ShortUrl shortUrl) {
        ShortUrl merged = entityManager.merge(shortUrl);
        entityManager.flush();
        return merged;
    }
	
	/* (non-Javadoc)
	 * @see com.github.urlchopper.repository.jpa.ShortUrlRepository#findShortUrlsByOriginalUrlEquals(java.lang.String)
	 */
	@Override
	public TypedQuery<ShortUrl> findShortUrlsByOriginalUrlEquals(String originalUrl) {
        if (originalUrl == null || originalUrl.length() == 0) throw new IllegalArgumentException("The originalUrl argument is required");
        TypedQuery<ShortUrl> q = entityManager.createQuery("SELECT o FROM ShortUrl AS o WHERE o.originalUrl = :originalUrl", ShortUrl.class);
        q.setParameter("originalUrl", originalUrl);
        return q;
    }

	/* (non-Javadoc)
	 * @see com.github.urlchopper.repository.jpa.ShortUrlRepository#findShortUrlsByOriginalUrlLike(java.lang.String)
	 */
	@Override
	public TypedQuery<ShortUrl> findShortUrlsByOriginalUrlLike(String originalUrl) {
        if (originalUrl == null || originalUrl.length() == 0) throw new IllegalArgumentException("The originalUrl argument is required");
        originalUrl = originalUrl.replace('*', '%');
        if (originalUrl.charAt(0) != '%') {
            originalUrl = "%" + originalUrl;
        }
        if (originalUrl.charAt(originalUrl.length() - 1) != '%') {
            originalUrl = originalUrl + "%";
        }
        TypedQuery<ShortUrl> q = entityManager.createQuery("SELECT o FROM ShortUrl AS o WHERE LOWER(o.originalUrl) LIKE LOWER(:originalUrl)", ShortUrl.class);
        q.setParameter("originalUrl", originalUrl);
        return q;
    }

	/* (non-Javadoc)
	 * @see com.github.urlchopper.repository.jpa.ShortUrlRepository#findShortUrlsByShortUrlEquals(java.lang.String)
	 */
	@Override
	public TypedQuery<ShortUrl> findShortUrlsByShortUrlEquals(String shortUrl) {
        if (shortUrl == null || shortUrl.length() == 0) throw new IllegalArgumentException("The shortUrl argument is required");
        TypedQuery<ShortUrl> q = entityManager.createQuery("SELECT o FROM ShortUrl AS o WHERE o.shortUrl = :shortUrl", ShortUrl.class);
        q.setParameter("shortUrl", shortUrl);
        return q;
    }

	/* (non-Javadoc)
	 * @see com.github.urlchopper.repository.jpa.ShortUrlRepository#findShortUrlsByShortUrlLike(java.lang.String)
	 */
	@Override
	public TypedQuery<ShortUrl> findShortUrlsByShortUrlLike(String shortUrl) {
        if (shortUrl == null || shortUrl.length() == 0) throw new IllegalArgumentException("The shortUrl argument is required");
        TypedQuery<ShortUrl> q = entityManager.createQuery("SELECT o FROM ShortUrl AS o WHERE LOWER(o.shortUrl) LIKE LOWER(:shortUrl)", ShortUrl.class);
        q.setParameter("shortUrl", shortUrl);
        return q;
    }
	
}
