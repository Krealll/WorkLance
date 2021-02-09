package com.krealll.worklance.model.service;

import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.entity.Order;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Order service.
 */
public interface OrderService {
    /**
     * Create boolean.
     *
     * @param parameters the parameters
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean create(Map<String, String> parameters) throws ServiceException;

    /**
     * Find all list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Order> findAll() throws ServiceException;

    /**
     * Find by budget list.
     *
     * @param lower the lower
     * @param upper the upper
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Order> findByBudget(String lower, String upper) throws ServiceException;

    /**
     * Find by key list.
     *
     * @param keyWord the key word
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Order> findByKey(String keyWord) throws ServiceException;

    /**
     * Find by customer list.
     *
     * @param customer the customer
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Order> findByCustomer(String customer) throws ServiceException;

    /**
     * Find by name list.
     *
     * @param name the name
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Order> findByName(String name) throws ServiceException;

    /**
     * Find by user id list.
     *
     * @param userId the user id
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Order> findByUserId(Long userId) throws ServiceException;

    /**
     * Delete boolean.
     *
     * @param orderId the order id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean delete(Long orderId) throws ServiceException;

    /**
     * Find by id optional.
     *
     * @param orderId the order id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Order> findById(Long orderId) throws ServiceException;

    /**
     * Update boolean.
     *
     * @param params the params
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean update(Map<String, String> params) throws ServiceException;
}
