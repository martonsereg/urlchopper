package com.epam.urlchopper.service.simple;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.epam.urlchopper.domain.ShortUrl;
import com.epam.urlchopper.domain.Creator;
import com.epam.urlchopper.repository.UrlRepository;
import com.epam.urlchopper.repository.UserRepository;

/**
 * Test class for SimpleHistoryService.
 * @author Gergely_Topolyai
 *
 */
public class SimpleHistoryServiceTest {

    private SimpleHistoryService underTest;
    private UrlRepository urlRepository;
    private UserRepository userRepository;

    @Before
    public void setUp() {
        underTest = new SimpleHistoryService();
        userRepository = EasyMock.createMock(UserRepository.class);
        urlRepository = EasyMock.createMock(UrlRepository.class);
        underTest.setUserRepository(userRepository);
        underTest.setUrlRepository(urlRepository);
    }

    @Test
    public void testGetUsersUrlsShouldEmptyListWhenNoShortUrls() {
        //given
        Creator user = new Creator();
        user.setShortUrls(new ArrayList<ShortUrl>());

        EasyMock.expect(userRepository.findUser(EasyMock.anyLong())).andReturn(user);

        //when
        EasyMock.replay(userRepository);
        int expected = 0;
        int actual = underTest.getUserUrls(EasyMock.anyLong()).size();
        EasyMock.verify(userRepository);
        //then
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void testGetUsersUrlsShouldEmptyListWhenUserNull() {
        //given

        EasyMock.expect(userRepository.findUser(0L)).andReturn(null);

        //when
        EasyMock.replay(userRepository);
        int expected = 0;
        int actual = underTest.getUserUrls(EasyMock.anyLong()).size();
        EasyMock.verify(userRepository);

        //then
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void testGetAllUrls() {
        //given
        List<ShortUrl> urls = new ArrayList<ShortUrl>();
        //EasyMock.anyObject(ShortUrl.class)
        urls.add(new ShortUrl());
        urls.add(new ShortUrl());
        EasyMock.expect(urlRepository.findAllShortUrls()).andReturn(urls);

        //when
        EasyMock.replay(urlRepository);
        int expected = 2;
        int actual = underTest.getAllUrls().size();
        EasyMock.replay(urlRepository);

        //then
        Assert.assertEquals(expected, actual);

    }
}
