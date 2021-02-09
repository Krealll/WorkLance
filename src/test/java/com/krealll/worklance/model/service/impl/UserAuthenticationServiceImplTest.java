package com.krealll.worklance.model.service.impl;

import com.krealll.worklance.exception.DaoException;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.dao.AuthenticationDao;
import com.krealll.worklance.model.dao.impl.AuthenticationDaoImpl;
import com.krealll.worklance.model.entity.UserAuthenticationToken;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;


@PrepareForTest(UserAuthenticationServiceImpl.class)
public class UserAuthenticationServiceImplTest {

    private UserAuthenticationServiceImpl userAuthenticationService;
    private AuthenticationDao authenticationDao;
    private UserAuthenticationToken firstToken;
    private Optional<UserAuthenticationToken> userAuthenticationTokenOptional;

    @BeforeMethod
    public void setUp() {
        authenticationDao = mock(AuthenticationDao.class);
        userAuthenticationService = new UserAuthenticationServiceImpl();
        Whitebox.setInternalState(AuthenticationDaoImpl.class,"instance",authenticationDao);
        firstToken = new UserAuthenticationToken(1L,"selector", "validator", 2L);
        userAuthenticationTokenOptional = Optional.of(firstToken);
    }

    @AfterClass
    public void tearDown() {
        userAuthenticationService = null;
        authenticationDao = null;
        firstToken = null;
        userAuthenticationTokenOptional = Optional.empty();
    }

    @Test
    public void testAddTokenTrue() {
        try {
            when(authenticationDao.addToken(any(UserAuthenticationToken.class))).thenReturn(true);
            boolean result = userAuthenticationService.addToken(1L,"validator");
            assertTrue(result);
        } catch (DaoException|ServiceException e) {
            fail(e.getMessage());
        }

    }

    @Test(expectedExceptions = ServiceException.class)
    public void testAddTokenException() throws ServiceException, DaoException {
        when(authenticationDao.addToken(any(UserAuthenticationToken.class))).thenThrow(DaoException.class);
        userAuthenticationService.addToken(1L,"validator");
    }

    @Test
    public void testFindBySelectorTrue() {
        try {
            when(authenticationDao.findBySelector(anyString())).thenReturn(userAuthenticationTokenOptional);
            Optional<UserAuthenticationToken> foundToken = userAuthenticationService.findBySelector("selector");
            assertEquals(foundToken,userAuthenticationTokenOptional);
        } catch (DaoException|ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindBySelectorException() throws ServiceException, DaoException {
        when(authenticationDao.findBySelector(anyString())).thenThrow(DaoException.class);
        userAuthenticationService.findBySelector("selector");
    }

    @Test
    public void testFindByUserIdTrue() {
        try {
            when(authenticationDao.findByUserId(anyLong())).thenReturn(userAuthenticationTokenOptional);
            Optional<UserAuthenticationToken> foundToken = userAuthenticationService.findByUserId(1L);
            assertEquals(foundToken,userAuthenticationTokenOptional);
        } catch (DaoException|ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindByUserIdException() throws ServiceException, DaoException {
        when(authenticationDao.findByUserId(anyLong())).thenThrow(DaoException.class);
        userAuthenticationService.findByUserId(1L);
    }

    @Test
    public void testDeleteTrue() {
        try {
            UserAuthenticationToken token = new UserAuthenticationToken(1L,"selector", "validator",2L);
            when(authenticationDao.deleteTokenBySelector(anyString())).thenReturn(true);
            userAuthenticationService.delete(token);
        } catch (ServiceException|DaoException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testDeleteException() throws ServiceException, DaoException {
        UserAuthenticationToken token = new UserAuthenticationToken(1L,"selector", "validator",2L);
        when(authenticationDao.deleteTokenBySelector(anyString())).thenThrow(DaoException.class);
        userAuthenticationService.delete(token);
    }


    @Test
    public void testUpdateTrue() {
        try {
            UserAuthenticationToken token = new UserAuthenticationToken(1L,"selector", "validator",2L);
            when(authenticationDao.update(any(UserAuthenticationToken.class))).thenReturn(true);
            userAuthenticationService.delete(token);
        } catch (ServiceException|DaoException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testUpdateException() throws ServiceException, DaoException {
        UserAuthenticationToken token = new UserAuthenticationToken(1L,"selector", "validator",2L);
        when(authenticationDao.update(any(UserAuthenticationToken.class))).thenThrow(DaoException.class);
        userAuthenticationService.update(token);
    }
}