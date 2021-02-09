package com.krealll.worklance.model.dao;

import com.krealll.worklance.exception.DaoException;
import com.krealll.worklance.model.entity.Team;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * The interface Team dao.
 */
public interface TeamDao extends BaseDao<Team> {

    /**
     * Find by manager list.
     *
     * @param manager the manager
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Team> findByManager(String manager) throws DaoException;

    /**
     * Find by key list.
     *
     * @param keyWord the key word
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Team> findByKey(String keyWord) throws DaoException;

    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Team> findByName(String name) throws DaoException;

    /**
     * Find by manager id optional.
     *
     * @param managerId the manager id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Team> findByManagerId(Long managerId) throws DaoException;

    /**
     * Close all.
     *
     * @param first      the first
     * @param second     the second
     * @param third      the third
     * @param connection the connection
     * @param logger     the logger
     */
    default void closeAll(PreparedStatement first, PreparedStatement second, PreparedStatement third,
                          Connection connection, Logger logger){
        try {
            if(first!=null){
                first.close();
            }
            if(second!=null){
                second.close();
            }
            if(third!=null){
                third.close();
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
