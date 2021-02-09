package com.krealll.worklance.model.service.impl;

import com.krealll.worklance.exception.DaoException;
import com.krealll.worklance.exception.HashGeneratorException;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.dao.AuthenticationDao;
import com.krealll.worklance.model.dao.impl.AuthenticationDaoImpl;
import com.krealll.worklance.model.entity.UserAuthenticationToken;
import com.krealll.worklance.model.service.UserAuthenticationService;
import com.krealll.worklance.util.HashGenerator;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Optional;


public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    @Override
    public boolean addToken(Long userId, String rawValidator) throws ServiceException  {
        AuthenticationDao authDao = AuthenticationDaoImpl.getInstance();
        try {
            boolean result;
            UserAuthenticationToken token;
            String rawSelector = RandomStringUtils.randomAlphanumeric(HashGenerator.SELECTOR_LENGTH);
            String hashedValidator = HashGenerator.generate(rawValidator,HashGenerator.TOKEN_ENCRYPTION,HashGenerator.VALIDATOR_LENGTH);
            token = new UserAuthenticationToken(rawSelector,hashedValidator,userId);
            result = authDao.addToken(token);
            return result;
        } catch (DaoException|HashGeneratorException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<UserAuthenticationToken> findBySelector(String selector) throws ServiceException {
        AuthenticationDao authDao = AuthenticationDaoImpl.getInstance();
        try {
            Optional<UserAuthenticationToken> token = authDao.findBySelector(selector);
            return token;
        } catch (DaoException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<UserAuthenticationToken> findByUserId(Long userId) throws ServiceException {
        AuthenticationDao authDao = AuthenticationDaoImpl.getInstance();
        try {
            Optional<UserAuthenticationToken> token = authDao.findByUserId(userId);
            return token;
        } catch (DaoException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean delete(UserAuthenticationToken token) throws ServiceException {
        AuthenticationDao authDao = AuthenticationDaoImpl.getInstance();
        boolean result;
        try {
            result = authDao.deleteTokenBySelector(token.getSelector());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean update(UserAuthenticationToken token) throws ServiceException {
        AuthenticationDao authDao = AuthenticationDaoImpl.getInstance();
        boolean result;
        try {
            result = authDao.update(token);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

}
