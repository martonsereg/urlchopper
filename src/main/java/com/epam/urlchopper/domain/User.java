package com.epam.urlchopper.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * .
 */
@Entity
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue
    private Long userId;

    @ManyToMany
    private List<ShortUrl> shortUrls = new ArrayList<ShortUrl>();

    public Long getUserId() {
        return userId;
    }

    public String getUserIdAsString() {
        return userId.toString();
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<ShortUrl> getShortUrls() {
        return shortUrls;
    }

    public void setShortUrls(List<ShortUrl> shortUrls) {
        this.shortUrls = shortUrls;
    }

    public void addShortUrl(ShortUrl shortUrl) {
        shortUrls.add(shortUrl);
    }

}
