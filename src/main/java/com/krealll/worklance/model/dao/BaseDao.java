package com.krealll.worklance.model.dao;

import com.krealll.worklance.exception.DaoException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Base dao.
 *
 * @param <T> the type parameter
 */
public interface BaseDao<T> {

    /**
     * Add boolean.
     *
     * @param parameters the parameters
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean add(Map<String,Object> parameters) throws DaoException;

    /**
     * Update boolean.
     *
     * @param t the t
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean update(T t) throws DaoException;

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean delete(Long id) throws DaoException;

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<T> findById(Long id) throws DaoException;

    /**
     * Find all list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<T> findAll() throws DaoException;
}
