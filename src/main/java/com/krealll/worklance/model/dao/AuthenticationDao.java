package com.krealll.worklance.model.dao;

import com.krealll.worklance.exception.DaoException;
import com.krealll.worklance.model.entity.UserAuthenticationToken;

import java.util.Optional;

/**
 * The interface Authentication dao.
 */
public interface AuthenticationDao {

    /**
     * Find by selector optional.
     *
     * @param requiredSelector the required selector
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<UserAuthenticationToken> findBySelector(String requiredSelector) throws DaoException;

    /**
     * Find by user id optional.
     *
     * @param userId the user id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<UserAuthenticationToken> findByUserId(Long userId) throws DaoException;

    /**
     * Add token boolean.
     *
     * @param token the token
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean addToken(UserAuthenticationToken token) throws DaoException;

    /**
     * Delete token by selector boolean.
     *
     * @param selector the selector
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean deleteTokenBySelector(String selector) throws DaoException;

    /**
     * Update boolean.
     *
     * @param token the token
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean update(UserAuthenticationToken token) throws DaoException;
}
