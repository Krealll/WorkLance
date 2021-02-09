package com.krealll.worklance.model.dao;

import com.krealll.worklance.exception.DaoException;
import com.krealll.worklance.model.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * The interface User dao.
 */
public interface UserDao extends BaseDao<User>{

    /**
     * Find by specialization list.
     *
     * @param specialization the specialization
     * @return the list
     * @throws DaoException the dao exception
     */
    List<User> findBySpecialization(String specialization) throws DaoException;

    /**
     * Find by key list.
     *
     * @param keyWord the key word
     * @return the list
     * @throws DaoException the dao exception
     */
    List<User> findByKey(String keyWord) throws DaoException;

    /**
     * Find by team id list.
     *
     * @param teamId the team id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<User> findByTeamId(Long teamId) throws DaoException;

    /**
     * Find manager optional.
     *
     * @param teamId the team id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findManager(Long teamId) throws DaoException;

    /**
     * Find user by login optional.
     *
     * @param login the login
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findUserByLogin(String login) throws DaoException;

    /**
     * Find password by login optional.
     *
     * @param login the login
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<String> findPasswordByLogin(String login) throws DaoException;

    /**
     * Update membership boolean.
     *
     * @param user the user
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateMembership(User user) throws DaoException;

    /**
     * Leave team boolean.
     *
     * @param userId the user id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean leaveTeam(Long userId) throws DaoException;

    /**
     * Update email vision boolean.
     *
     * @param user the user
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateEmailVision(User user) throws DaoException;

}
