package com.krealll.worklance.model.service;

import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.entity.Notification;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Notification service.
 */
public interface NotificationService {
    /**
     * Find by user id list.
     *
     * @param userId the user id
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Notification> findByUserId(Long userId) throws ServiceException;

    /**
     * Find by id optional.
     *
     * @param notificationId the notification id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Notification> findById(Long notificationId) throws ServiceException;

    /**
     * Accept notification boolean.
     *
     * @param notification the notification
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean acceptNotification(Notification notification) throws ServiceException;

    /**
     * Delete boolean.
     *
     * @param notificationId the notification id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean delete(Long notificationId) throws ServiceException;

    /**
     * Add notifications boolean.
     *
     * @param membersParameters the members parameters
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean addNotifications(Map<String, String> membersParameters) throws ServiceException;

    /**
     * Add notification boolean.
     *
     * @param membersParameters the members parameters
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean addNotification(Map<String, String> membersParameters) throws ServiceException;
}
