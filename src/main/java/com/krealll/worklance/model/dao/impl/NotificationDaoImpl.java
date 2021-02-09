package com.krealll.worklance.model.dao.impl;

import com.krealll.worklance.exception.DaoException;
import com.krealll.worklance.model.builder.NotificationBuilder;
import com.krealll.worklance.model.dao.ColumnName;
import com.krealll.worklance.model.dao.NotificationDao;
import com.krealll.worklance.model.entity.Notification;
import com.krealll.worklance.model.entity.type.UserRole;
import com.krealll.worklance.model.pool.ConnectionPool;
import com.krealll.worklance.util.MapKeys;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class NotificationDaoImpl implements NotificationDao {

    private final static Logger logger = LogManager.getLogger(NotificationDaoImpl.class);

    private final static String CREATE_NOTIFICATION = "INSERT INTO notifications (development_teams_id_team, users_user_id) VALUES (?,?)";
    private final static String DELETE_NOTIFICATION = "DELETE FROM notifications WHERE id_notification=?";
    private static final String FIND_BY_USER_ID = "SELECT id_notification, development_teams_id_team, users_user_id, " +
            "team_name, user_id, login, role" +
            " FROM notifications AS n " +
            "RIGHT JOIN development_teams AS d  ON n.development_teams_id_team=d.id_team " +
            "RIGHT JOIN users as u ON d.id_team=u.fk_team_id " +
            "WHERE n.users_user_id=? AND role=?";
    private final static String UPDATE_USER_TEAM = "UPDATE users SET fk_team_id=? WHERE user_id=?";
    private final static String KILL_FOREIGN_KEY_CHECKS = "SET FOREIGN_KEY_CHECKS=0";
    private final static String SET_FOREIGN_KEY_CHECKS = "SET FOREIGN_KEY_CHECKS=1";
    private final static String FIND_BY_ID = "SELECT id_notification, development_teams_id_team, users_user_id " +
            "FROM notifications WHERE id_notification=?";

    private static NotificationDao instance = new NotificationDaoImpl();

    private NotificationDaoImpl(){}

    public static NotificationDao getInstance(){
        return instance;
    }

    @Override
    public boolean add(Map<String, Object> parameters) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_NOTIFICATION)) {
            statement.setInt(1, Integer.parseInt((String) parameters.get(MapKeys.TEAM_ID)));
            statement.setInt(2, Integer.parseInt((String) parameters.get(MapKeys.USER_ID)));
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during adding notification", e);
            throw new DaoException( e);
        }
    }

    @Override
    public List<Notification>  findByUserId(Long userId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_USER_ID)) {
            statement.setInt(1,userId.intValue());
            statement.setString(2,UserRole.MANAGER.getName().toUpperCase());

            try (ResultSet resultSet = statement.executeQuery()){
                List<Notification> notifications = new ArrayList<>();
                while (resultSet.next()){
                    NotificationBuilder builder = new NotificationBuilder();
                    builder.setNotificationId((long) resultSet.getInt(ColumnName.NOTIFICATION_ID));
                    builder.setTeamId( (long)resultSet.getInt(ColumnName.DEVELOPMENT_TEAMS_ID_TEAM));
                    builder.setUserId((long)resultSet.getInt(ColumnName.USERS_USER_ID));
                    builder.setTeamName(resultSet.getString(ColumnName.TEAM_NAME));
                    builder.setManagerId((long)resultSet.getInt(ColumnName.USER_ID));
                    builder.setManagerLogin(resultSet.getString(ColumnName.LOGIN));
                    notifications.add(builder.build());
                }
                return notifications;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during searching for notifications", e);
            throw new DaoException( e);
        }
    }

    @Override
    public Optional<Notification> findById(Long notificationId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(1,notificationId.intValue());

            try (ResultSet resultSet = statement.executeQuery()){
                Optional<Notification> notification = Optional.empty();
                if (resultSet.next()){
                    NotificationBuilder builder = new NotificationBuilder();
                    builder.setNotificationId((long) resultSet.getInt(ColumnName.NOTIFICATION_ID));
                    builder.setTeamId( (long)resultSet.getInt(ColumnName.DEVELOPMENT_TEAMS_ID_TEAM));
                    builder.setUserId((long)resultSet.getInt(ColumnName.USERS_USER_ID));
                    notification = Optional.of(builder.build());
                }
                return notification;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during searching for notification", e);
            throw new DaoException( e);
        }
    }

    @Override
    public boolean delete(Long notificationId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_NOTIFICATION)) {
            statement.setInt(1,notificationId.intValue());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during notification deleting", e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean acceptNotification(Notification notification) throws DaoException {
        boolean result;
        Connection connection = null;
        PreparedStatement userStatement = null;
        PreparedStatement notificationStatement = null;
        Statement setKeyStatement = null;
        Statement killKeyStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            killKeyStatement = connection.createStatement();
            setKeyStatement = connection.createStatement();
            killKeyStatement.execute(KILL_FOREIGN_KEY_CHECKS);
            connection.setAutoCommit(false);

            userStatement = connection.prepareStatement(UPDATE_USER_TEAM);
            userStatement.setInt(1, notification.getTeamId().intValue());
            userStatement.setInt(2, notification.getUserId().intValue());
            userStatement.executeUpdate();

            notificationStatement = connection.prepareStatement(DELETE_NOTIFICATION);
            notificationStatement.setInt(1,notification.getNotificationId().intValue());
            notificationStatement.executeUpdate();

            setKeyStatement.execute(SET_FOREIGN_KEY_CHECKS);

            connection.commit();
            result = true;

        } catch (SQLException e){
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.log(Level.ERROR, "Error during rollback", ex);
            }
            throw new  DaoException("Error during accepting notification",e);
        } finally {
            try {
                if(killKeyStatement!=null){
                    killKeyStatement.close();
                }
                if(setKeyStatement!=null){
                    setKeyStatement.close();
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Error during closing foreign key statement", e);
            }
            try {
                if(userStatement!=null){
                    userStatement.close();
                }
                if(notificationStatement!=null){
                    notificationStatement.close();
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
        return result;
    }
}
