package com.github.urlchopper.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
public class ShortUrl {

    private String shortUrl;

    private String originalUrl;

    private Long activeUntil;

	public String getShortUrl() {
        return this.shortUrl;
    }

	public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

	public String getOriginalUrl() {
        return this.originalUrl;
    }

	public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

	public Long getActiveUntil() {
        return this.activeUntil;
    }

	public void setActiveUntil(Long activeUntil) {
        this.activeUntil = activeUntil;
    }

	public static TypedQuery<ShortUrl> findShortUrlsByOriginalUrlEquals(String originalUrl) {
        if (originalUrl == null || originalUrl.length() == 0) throw new IllegalArgumentException("The originalUrl argument is required");
        EntityManager em = ShortUrl.entityManager();
        TypedQuery<ShortUrl> q = em.createQuery("SELECT o FROM ShortUrl AS o WHERE o.originalUrl = :originalUrl", ShortUrl.class);
        q.setParameter("originalUrl", originalUrl);
        return q;
    }

	public static TypedQuery<ShortUrl> findShortUrlsByOriginalUrlLike(String originalUrl) {
        if (originalUrl == null || originalUrl.length() == 0) throw new IllegalArgumentException("The originalUrl argument is required");
        originalUrl = originalUrl.replace('*', '%');
        if (originalUrl.charAt(0) != '%') {
            originalUrl = "%" + originalUrl;
        }
        if (originalUrl.charAt(originalUrl.length() - 1) != '%') {
            originalUrl = originalUrl + "%";
        }
        EntityManager em = ShortUrl.entityManager();
        TypedQuery<ShortUrl> q = em.createQuery("SELECT o FROM ShortUrl AS o WHERE LOWER(o.originalUrl) LIKE LOWER(:originalUrl)", ShortUrl.class);
        q.setParameter("originalUrl", originalUrl);
        return q;
    }

	public static TypedQuery<ShortUrl> findShortUrlsByShortUrlEquals(String shortUrl) {
        if (shortUrl == null || shortUrl.length() == 0) throw new IllegalArgumentException("The shortUrl argument is required");
        EntityManager em = ShortUrl.entityManager();
        TypedQuery<ShortUrl> q = em.createQuery("SELECT o FROM ShortUrl AS o WHERE o.shortUrl = :shortUrl", ShortUrl.class);
        q.setParameter("shortUrl", shortUrl);
        return q;
    }

	public static TypedQuery<ShortUrl> findShortUrlsByShortUrlLike(String shortUrl) {
        if (shortUrl == null || shortUrl.length() == 0) throw new IllegalArgumentException("The shortUrl argument is required");
        EntityManager em = ShortUrl.entityManager();
        TypedQuery<ShortUrl> q = em.createQuery("SELECT o FROM ShortUrl AS o WHERE LOWER(o.shortUrl) LIKE LOWER(:shortUrl)", ShortUrl.class);
        q.setParameter("shortUrl", shortUrl);
        return q;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new ShortUrl().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countShortUrls() {
        return entityManager().createQuery("SELECT COUNT(o) FROM ShortUrl o", Long.class).getSingleResult();
    }

	public static List<ShortUrl> findAllShortUrls() {
        return entityManager().createQuery("SELECT o FROM ShortUrl o", ShortUrl.class).getResultList();
    }

	public static ShortUrl findShortUrl(Long id) {
        if (id == null) return null;
        return entityManager().find(ShortUrl.class, id);
    }

	public static List<ShortUrl> findShortUrlEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM ShortUrl o", ShortUrl.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            ShortUrl attached = ShortUrl.findShortUrl(this.id);
            this.entityManager.remove(attached);
        }
    }

	@Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

	@Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

	@Transactional
    public ShortUrl merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ShortUrl merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;

	public Long getId() {
        return this.id;
    }

	public void setId(Long id) {
        this.id = id;
    }

	public Integer getVersion() {
        return this.version;
    }

	public void setVersion(Integer version) {
        this.version = version;
    }
}
