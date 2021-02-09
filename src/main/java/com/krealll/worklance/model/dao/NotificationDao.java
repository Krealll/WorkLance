package com.krealll.worklance.model.dao;

import com.krealll.worklance.exception.DaoException;
import com.krealll.worklance.model.entity.Notification;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Notification dao.
 */
public interface NotificationDao {
    /**
     * Find by user id list.
     *
     * @param userId the user id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Notification> findByUserId(Long userId) throws DaoException;

    /**
     * Find by id optional.
     *
     * @param notificationId the notification id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Notification> findById(Long notificationId) throws DaoException;

    /**
     * Delete boolean.
     *
     * @param notificationId the notification id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean delete(Long notificationId) throws DaoException;

    /**
     * Accept notification boolean.
     *
     * @param notification the notification
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean acceptNotification(Notification notification) throws DaoException;

    /**
     * Add boolean.
     *
     * @param parameters the parameters
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean add(Map<String, Object> parameters) throws DaoException;
}
