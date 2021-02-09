package com.krealll.worklance.model.dao.impl;

import com.krealll.worklance.exception.DaoException;
import com.krealll.worklance.model.dao.AuthenticationDao;
import com.krealll.worklance.model.dao.ColumnName;
import com.krealll.worklance.model.entity.UserAuthenticationToken;
import com.krealll.worklance.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AuthenticationDaoImpl implements AuthenticationDao {

    private final static Logger logger  = LogManager.getLogger(AuthenticationDaoImpl.class);

    private static final String FIND_TOKEN_BY_SELECTOR = "SELECT  id_user_auth, validator, users_user_id FROM user_auth" +
            " WHERE selector=?";
    private static final String ADD_TOKEN = "INSERT INTO user_auth(selector,validator,users_user_id) VALUES(?,?,?)";
    private static final String DELETE_TOKEN_BY_SELECTOR = "DELETE FROM user_auth WHERE selector=?";
    private static final String UPDATE_TOKEN = "UPDATE user_auth SET selector=?, validator=? WHERE id_user_auth=?";
    private static final String FIND_TOKEN_BY_USER = "SELECT id_user_auth,selector, validator, users_user_id " +
            "FROM user_auth WHERE  users_user_id=?";

    private static AuthenticationDao instance = new AuthenticationDaoImpl();

    private AuthenticationDaoImpl(){}

    public static AuthenticationDao getInstance(){
        return AuthenticationDaoImpl.instance;
    }

    @Override
    public Optional<UserAuthenticationToken> findBySelector(String requiredSelector) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_TOKEN_BY_SELECTOR)) {
            statement.setString(1,requiredSelector);
            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    String idToken = resultSet.getString(ColumnName.ID_USER_AUTH);
                    Long userId = resultSet.getLong(ColumnName.USERS_USER_ID);
                    String validator  = resultSet.getString(ColumnName.VALIDATOR);
                    String selector = requiredSelector;
                    UserAuthenticationToken userAuthenticationToken =
                            new UserAuthenticationToken(Long.parseLong(idToken),selector, validator, userId );
                    Optional<UserAuthenticationToken> foundToken = Optional.of(userAuthenticationToken);
                    return foundToken;

                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during searching for token", e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<UserAuthenticationToken> findByUserId(Long userId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_TOKEN_BY_USER)) {
            statement.setLong(1,userId);
            try (ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    String idToken = resultSet.getString(ColumnName.ID_USER_AUTH);
                    String selector = resultSet.getString(ColumnName.SELECTOR);
                    String validator  = resultSet.getString(ColumnName.VALIDATOR);
                    Long idUser  = resultSet.getLong(ColumnName.USERS_USER_ID);
                    UserAuthenticationToken userAuthenticationToken =
                            new UserAuthenticationToken(Long.parseLong(idToken),selector, validator, idUser);
                    Optional<UserAuthenticationToken> foundToken = Optional.of(userAuthenticationToken);
                    return foundToken;

                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during searching for token", e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean addToken(UserAuthenticationToken token) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_TOKEN)) {
            statement.setString(1, token.getSelector());
            statement.setString(2, token.getValidator());
            statement.setInt(3, token.getUserId().intValue());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during adding token", e);
            throw new DaoException( e);
        }
    }

    @Override
    public boolean deleteTokenBySelector(String selector) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_TOKEN_BY_SELECTOR)) {
            statement.setString(1, selector);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during deleting token", e);
            throw new DaoException(e);
        }
    }


    @Override
    public boolean update(UserAuthenticationToken token) throws DaoException{
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_TOKEN)) {
            statement.setString(1, token.getSelector());
            statement.setString(2, token.getValidator());
            statement.setInt(3, Integer.parseInt(token.getTokenId().toString()));

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during updating token", e);
            throw new DaoException(e);
        }
    }



}
