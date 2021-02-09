package com.krealll.worklance.model.dao.impl;

import com.krealll.worklance.exception.DaoException;
import com.krealll.worklance.model.builder.UserBuilder;
import com.krealll.worklance.model.dao.ColumnName;
import com.krealll.worklance.model.dao.UserDao;
import com.krealll.worklance.model.entity.type.Specialization;
import com.krealll.worklance.model.entity.User;
import com.krealll.worklance.model.entity.type.UserRole;
import com.krealll.worklance.model.pool.ConnectionPool;
import com.krealll.worklance.util.MapKeys;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.sql.*;
import java.util.*;

public class UserDaoImpl implements UserDao {

    private final static Logger logger = LogManager.getLogger(UserDaoImpl.class);

    private static final String CREATE_USER = "INSERT INTO users (password, login, email, " +
            "role, specialization) " +
            "VALUES (?,?,?,?,?)";
    private static final String FIND_USER_BY_LOGIN =
            "SELECT user_id, password, login, email,role, specialization,description, show_email, fk_team_id " +
                    "FROM users WHERE login=?";
    private static final String FIND_PASSWORD_BY_LOGIN = "SELECT password FROM users WHERE login=?";
    private static final String FIND_ALL_USERS = "SELECT user_id, login, email, role, specialization, " +
            "description, show_email, fk_team_id FROM users ";
    private static final String FIND_USER_BY_ID = "SELECT user_id, password, login,email,role,specialization, " +
            "description, show_email, fk_team_id FROM users WHERE user_id=?";
    private static final String UPDATE_USER = "UPDATE users SET login=?, email=?, specialization=?, description=?," +
            " show_email=? WHERE user_id=?";
    private static final String FIND_BY_TEAM_ID= "SELECT user_id, login, email, role, specialization, " +
            "description, show_email, fk_team_id FROM users WHERE fk_team_id=? AND role=?";
    private static final String FIND_MANAGER_BY_ID= "SELECT user_id, login, email, role, specialization, " +
            "description, show_email, fk_team_id FROM users WHERE fk_team_id=? AND role=?";
    private static final String UPDATE_MEMBERSHIP = "UPDATE users SET fk_team_id=NULL WHERE user_id=? AND fk_team_id=?";
    private static final String LEAVE_TEAM = "UPDATE users SET fk_team_id=NULL WHERE user_id=?";
    private static final String FIND_BY_KEY_WORD = "SELECT user_id, login, email, role, specialization, " +
            "description, show_email, fk_team_id FROM users WHERE description LIKE ?";
    private static final String FIND_BY_SPECIALIZATION= "SELECT user_id, login, email, role, specialization, " +
            "description, show_email, fk_team_id FROM users WHERE specialization=?";
    private final static String DELETE_USER = "DELETE FROM users WHERE user_id=?";
    private final static String UPDATE_EMAIL_VISION = "UPDATE users SET show_email=? WHERE user_id=?";

    private static UserDao instance = new UserDaoImpl();

    private UserDaoImpl(){}

    public static UserDao getInstance(){
        return instance;
    }

    @Override
    public boolean add(Map<String, Object> parameters)throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_USER)) {
            statement.setString(1, parameters.get(MapKeys.PASSWORD).toString());
            statement.setString(2, parameters.get(MapKeys.LOGIN).toString());
            statement.setString(3, parameters.get(MapKeys.EMAIL).toString());
            statement.setString(4, UserRole.USER.toString());
            statement.setString(5, parameters.get(MapKeys.SPECIALIZATION).toString().toLowerCase());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during adding user", e);
            throw new DaoException( e);
        }
    }

    @Override
    public List<User> findAll() throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS)) {
            try (ResultSet resultSet = statement.executeQuery();){
                List<User> foundUsers = createUserList(resultSet);
                return foundUsers;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during searching for users", e);
            throw new DaoException( e);
        }
    }


    @Override
    public Optional<User> findById(Long userId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID)) {
            statement.setLong(1, userId);
            try (ResultSet userSet = statement.executeQuery()){
                return createUserOptional(userSet);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during searching for user", e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getSpecialization().toString().toLowerCase());
            statement.setString(4, user.getDescription());
            statement.setBoolean(5, user.getShowEmail());
            statement.setInt(6, user.getId().intValue());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during updating user", e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long userId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {
            statement.setInt(1,userId.intValue());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during deleting user", e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<User> findBySpecialization(String specialization) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_SPECIALIZATION)) {
            statement.setString(1,specialization);
            try (ResultSet resultSet = statement.executeQuery();){
                List<User> foundUsers = createUserList(resultSet);
                return foundUsers;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during searching users", e);
            throw new DaoException( e);
        }
    }

    @Override
    public List<User> findByKey(String keyWord) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_KEY_WORD)) {
            statement.setString(1, "%"+ keyWord + "%");
            try (ResultSet resultSet = statement.executeQuery();){
                List<User> foundUsers = createUserList(resultSet);
                return foundUsers;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during searching users", e);
            throw new DaoException( e);
        }
    }

    @Override
    public Optional<User> findManager(Long teamId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_MANAGER_BY_ID)) {
            statement.setInt(1, teamId.intValue());
            statement.setString(2, UserRole.MANAGER.getName().toUpperCase());
            try (ResultSet resultSet = statement.executeQuery()){
                return createUserOptional(resultSet);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during searching for manager", e);
            throw new DaoException( e);
        }
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_LOGIN)) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()){
                return createUserOptional(resultSet);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during searching for user", e);
            throw new DaoException( e);
        }
    }

    @Override
    public Optional<String> findPasswordByLogin(String login) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_PASSWORD_BY_LOGIN)) {
            statement.setString(1, login);
            try(ResultSet resultSet = statement.executeQuery();) {
                if (resultSet.next()) {
                    return Optional.of(resultSet.getString(ColumnName.PASSWORD));
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during password searching", e);
            throw new DaoException( e);
        }
    }


    @Override
    public List<User> findByTeamId(Long teamId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_TEAM_ID)) {
            statement.setInt(1,teamId.intValue());
            statement.setString(2, UserRole.USER.getName());
            try (ResultSet resultSet = statement.executeQuery();){
                return createUserList(resultSet);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during searching for users", e);
            throw new DaoException( e);
        }
    }

    @Override
    public boolean updateMembership(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_MEMBERSHIP)) {
            statement.setInt(1, user.getId().intValue());
            statement.setInt(2, user.getTeam().intValue());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during updating membership" , e);
            throw new DaoException(e);
        }
    }



    @Override
    public boolean leaveTeam(Long userId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(LEAVE_TEAM)) {
            statement.setInt(1, userId.intValue());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during leaving team", e);
            throw new DaoException(e);
        }
    }

    private Optional<User> createUserOptional(ResultSet resultSet) throws SQLException {
        Optional<User> user = Optional.empty();
        if (resultSet.next()) {
            UserBuilder userBuilder = new UserBuilder();
            userBuilder.setUserId(Long.parseLong (resultSet.getString(ColumnName.USER_ID)));
            userBuilder.setEmail((resultSet.getString(ColumnName.EMAIL)));
            userBuilder.setSpecialization(Specialization.valueOf (resultSet.getString(ColumnName.SPECIALIZATION).toUpperCase()));
            userBuilder.setLogin((resultSet.getString(ColumnName.LOGIN)));
            userBuilder.setRole(UserRole.valueOf(resultSet.getString(ColumnName.ROLE).toUpperCase()));
            userBuilder.setDescription(resultSet.getString(ColumnName.DESCRIPTION));
            userBuilder.setShow(resultSet.getBoolean(ColumnName.SHOW_EMAIL));
            userBuilder.setTeam(resultSet.getLong(ColumnName.FK_TEAM_ID));
            user = Optional.of(userBuilder.build());
        }
        return user;
    }


    private List<User> createUserList(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            UserBuilder userBuilder = new UserBuilder();
            userBuilder.setUserId(Long.parseLong (resultSet.getString(ColumnName.USER_ID)));
            userBuilder.setEmail((resultSet.getString(ColumnName.EMAIL)));
            userBuilder.setLogin((resultSet.getString(ColumnName.LOGIN)));
            userBuilder.setSpecialization(Specialization.valueOf (resultSet.getString(ColumnName.SPECIALIZATION).toUpperCase()));
            userBuilder.setRole(UserRole.valueOf(resultSet.getString(ColumnName.ROLE).toUpperCase()));
            userBuilder.setDescription(resultSet.getString(ColumnName.DESCRIPTION));
            userBuilder.setShow(resultSet.getBoolean(ColumnName.SHOW_EMAIL));
            userBuilder.setTeam((long)resultSet.getInt(ColumnName.FK_TEAM_ID));
            users.add(userBuilder.build());
        }
        return users;
    }

    @Override
    public boolean updateEmailVision(User user) throws DaoException {
        try(Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE_EMAIL_VISION)){
            statement.setBoolean(1,user.getShowEmail());
            statement.setInt(2, user.getId().intValue());
            return statement.executeUpdate()>0;
        } catch (SQLException e){
            logger.log(Level.ERROR, "Error during updating email vision",e);
            throw new DaoException("Error during updating email vision",e);
        }
    }
}
