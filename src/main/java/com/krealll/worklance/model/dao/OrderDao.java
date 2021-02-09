package com.krealll.worklance.model.dao;

import com.krealll.worklance.exception.DaoException;
import com.krealll.worklance.model.entity.Order;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * The interface Order dao.
 */
public interface OrderDao extends BaseDao<Order> {

    /**
     * Find by budget list.
     *
     * @param lower the lower
     * @param upper the upper
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Order> findByBudget(Double lower, Double upper) throws DaoException;

    /**
     * Find by key list.
     *
     * @param keyWord the key word
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Order> findByKey(String keyWord) throws DaoException;

    /**
     * Find by customer list.
     *
     * @param customerLogin the customer login
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Order> findByCustomer(String customerLogin) throws DaoException;

    /**
     * Find by name list.
     *
     * @param name the name
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Order> findByName(String name) throws DaoException;

    /**
     * Find by user id list.
     *
     * @param userId the user id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Order> findByUserId(Long userId) throws DaoException;

    /**
     * Update boolean.
     *
     * @param parameters the parameters
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean update(Map<String,Object> parameters) throws DaoException;

    /**
     * Close.
     *
     * @param connection      the connection
     * @param firstStatement  the first statement
     * @param secondStatement the second statement
     * @param logger          the logger
     */
    default void close(Connection connection, PreparedStatement firstStatement,
                       PreparedStatement secondStatement, Logger logger){
        try {
            if(firstStatement!=null){
                firstStatement.close();
            }
            if(secondStatement!=null){
                secondStatement.close();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during closing statement", e);
        }
        try {
            if(connection!=null){
                connection.setAutoCommit(true);
                connection.close();
            }
        }catch (SQLException e){
            logger.log(Level.ERROR, "Error during closing connection", e);
        }
    }
}
