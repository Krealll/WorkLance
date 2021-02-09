package com.krealll.worklance.model.service;

import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface User service.
 */
public interface UserService {

    /**
     * Register user boolean.
     *
     * @param parameters the parameters
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean registerUser(Map<String,String> parameters) throws ServiceException;

    /**
     * Check login and password boolean.
     *
     * @param login    the login
     * @param password the password
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean checkLoginAndPassword(String login, String password) throws ServiceException;

    /**
     * Find all list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<User> findAll() throws ServiceException;

    /**
     * Find by specialization list.
     *
     * @param specialization the specialization
     * @return the list
     * @throws ServiceException the service exception
     */
    List<User> findBySpecialization(String specialization) throws ServiceException;

    /**
     * Find by key list.
     *
     * @param keyWord the key word
     * @return the list
     * @throws ServiceException the service exception
     */
    List<User> findByKey(String keyWord) throws ServiceException;

    /**
     * Find by team id list.
     *
     * @param teamId the team id
     * @return the list
     * @throws ServiceException the service exception
     */
    List<User> findByTeamId(Long teamId) throws ServiceException;

    /**
     * Find by login optional.
     *
     * @param login the login
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> findByLogin(String login) throws ServiceException;

    /**
     * Find manager optional.
     *
     * @param teamId the team id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> findManager(Long teamId) throws ServiceException;

    /**
     * Find by id optional.
     *
     * @param userId the user id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> findById(Long userId) throws ServiceException;

    /**
     * Delete member boolean.
     *
     * @param user the user
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean deleteMember(User user) throws ServiceException;

    /**
     * Update boolean.
     *
     * @param params the params
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean update(Map<String,String> params) throws ServiceException;

    /**
     * Leave team boolean.
     *
     * @param userId the user id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean leaveTeam(Long userId) throws ServiceException;

}
