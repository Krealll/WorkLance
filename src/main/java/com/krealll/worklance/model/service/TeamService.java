package com.krealll.worklance.model.service;

import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.entity.Team;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Team service.
 */
public interface TeamService {
    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Team> findByName(String name) throws ServiceException;

    /**
     * Find by id optional.
     *
     * @param teamId the team id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Team> findById(Long teamId) throws ServiceException;

    /**
     * Find by manager id optional.
     *
     * @param managerId the manager id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Team> findByManagerId(Long managerId) throws ServiceException;

    /**
     * Find by manager list.
     *
     * @param manager the manager
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Team> findByManager(String manager) throws ServiceException;

    /**
     * Find by key list.
     *
     * @param keyWord the key word
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Team> findByKey(String keyWord) throws ServiceException;

    /**
     * Find all list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Team> findAll() throws ServiceException;

    /**
     * Delete boolean.
     *
     * @param teamId the team id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean delete(Long teamId) throws ServiceException;

    /**
     * Update boolean.
     *
     * @param params the params
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean update(Map<String, String> params) throws ServiceException;

    /**
     * Create boolean.
     *
     * @param params the params
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean create(Map<String, String> params) throws ServiceException;

}
