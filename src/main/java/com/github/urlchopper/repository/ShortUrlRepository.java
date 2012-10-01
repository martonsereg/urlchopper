package com.github.urlchopper.repository;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import com.github.urlchopper.domain.ShortUrl;

public interface ShortUrlRepository {

	public abstract long countShortUrls();

	public abstract List<ShortUrl> findAllShortUrls();

	public abstract ShortUrl findShortUrl(Long id);

	public abstract List<ShortUrl> findShortUrlEntries(int firstResult,
			int maxResults);

	@Transactional
	public abstract void persist(ShortUrl shortUrl);

	@Transactional
	public abstract void remove(ShortUrl shortUrl);

	@Transactional
	public abstract void flush();

	@Transactional
	public abstract void clear();

	@Transactional
	public abstract ShortUrl merge(ShortUrl shortUrl);

	public abstract TypedQuery<ShortUrl> findShortUrlsByOriginalUrlEquals(
			String originalUrl);

	public abstract TypedQuery<ShortUrl> findShortUrlsByOriginalUrlLike(
			String originalUrl);

	public abstract TypedQuery<ShortUrl> findShortUrlsByShortUrlEquals(
			String shortUrl);

	public abstract TypedQuery<ShortUrl> findShortUrlsByShortUrlLike(
			String shortUrl);

}