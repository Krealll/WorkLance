package com.krealll.worklance.model.service;

import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.entity.UserAuthenticationToken;

import java.util.Optional;

/**
 * The interface User authentication service.
 */
public interface UserAuthenticationService {
    /**
     * Add token boolean.
     *
     * @param userId       the user id
     * @param rawValidator the raw validator
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean addToken(Long userId, String rawValidator) throws ServiceException;

    /**
     * Find by selector optional.
     *
     * @param selector the selector
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<UserAuthenticationToken> findBySelector(String selector) throws ServiceException;

    /**
     * Find by user id optional.
     *
     * @param userId the user id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<UserAuthenticationToken> findByUserId(Long userId) throws ServiceException;

    /**
     * Delete boolean.
     *
     * @param token the token
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean delete(UserAuthenticationToken token) throws ServiceException;

    /**
     * Update boolean.
     *
     * @param token the token
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean update(UserAuthenticationToken token) throws ServiceException;
}
