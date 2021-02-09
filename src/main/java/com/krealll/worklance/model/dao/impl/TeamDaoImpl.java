package com.krealll.worklance.model.dao.impl;

import com.krealll.worklance.exception.DaoException;
import com.krealll.worklance.model.builder.TeamBuilder;
import com.krealll.worklance.model.dao.ColumnName;
import com.krealll.worklance.model.dao.TeamDao;
import com.krealll.worklance.model.entity.Team;
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

public class TeamDaoImpl implements TeamDao {

    private final static Logger logger = LogManager.getLogger(TeamDaoImpl.class);

    private static final String CREATE_TEAM = "INSERT INTO development_teams (team_name, description) VALUES (?,?)";
    private static final String UPDATE_USER_TEAM = "UPDATE users SET role=?,fk_team_id=? WHERE user_id=?";
    private static final String FIND_TEAM_BY_NAME = "SELECT id_team, team_name, description FROM development_teams " +
            "WHERE team_name=?";
    private static final String FIND_TEAM_BY_MANAGER = "SELECT id_team, team_name, description FROM development_teams "+
            "WHERE id_team IN (SELECT fk_team_id FROM users WHERE user_id=?)";
    private final static String FIND_TEAM_BY_ID = "SELECT id_team,team_name, description FROM development_teams WHERE id_team=?";
    private final static String DELETE_TEAM = "DELETE FROM development_teams WHERE id_team=?";
    private final static String DELETE_NOTIFICATIONS_BY_TEAM_ID = "DELETE FROM notifications WHERE development_teams_id_team=?";
    private final static String UPDATE_USER_TEAM_ID = "UPDATE users SET fk_team_id = NULL, role=? WHERE fk_team_id=?";
    private final static String UPDATE_TEAM = "UPDATE development_teams SET team_name=?, description=? WHERE id_team=?";
    private final static String KILL_FOREIGN_KEY_CHECKS = "SET FOREIGN_KEY_CHECKS=0";
    private final static String SET_FOREIGN_KEY_CHECKS = "SET FOREIGN_KEY_CHECKS=1";
    private final static String FIND_ALL = "SELECT id_team, team_name, description FROM development_teams";
    private final static String FIND_BY_KEY = "SELECT id_team, team_name, description FROM development_teams " +
            "WHERE description LIKE ?";
    private final static String FIND_BY_MANAGER = "SELECT id_team, team_name, development_teams.description FROM development_teams " +
            "LEFT JOIN users ON users.fk_team_id=development_teams.id_team WHERE users.login=?";


    private static TeamDao instance = new TeamDaoImpl();

    private TeamDaoImpl(){}

    public static TeamDao getInstance(){
        return instance;
    }


    @Override
    public boolean add(Map<String, Object> parameters) throws DaoException {
        boolean result;
        Connection connection = null;
        PreparedStatement teamStatement = null;
        PreparedStatement userStatement = null;
        PreparedStatement findIdStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            teamStatement = connection.prepareStatement(CREATE_TEAM);
            teamStatement.setString(1, parameters.get(MapKeys.NAME).toString());
            teamStatement.setString(2,  parameters.get(MapKeys.DESCRIPTION).toString());

            teamStatement.executeUpdate();

            findIdStatement = connection.prepareStatement(FIND_TEAM_BY_NAME);
            findIdStatement.setString(1,parameters.get(MapKeys.NAME).toString());
            try(ResultSet resultSet = findIdStatement.executeQuery()) {
                if (resultSet.next()){
                    userStatement = connection.prepareStatement(UPDATE_USER_TEAM);
                    userStatement.setString(1, UserRole.MANAGER.toString());
                    userStatement.setInt(2, resultSet.getInt(ColumnName.TEAM_ID));
                    userStatement.setInt(3, Integer.parseInt(parameters.get(MapKeys.USER_ID).toString()));
                    userStatement.executeUpdate();

                    connection.commit();
                    result = true;
                } else {
                    result = false;
                }
            }
        } catch (SQLException e){
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.log(Level.ERROR, "Error during rollback", ex);
            }
            throw new  DaoException("Error during order creation",e);
        } finally {
            closeAll(teamStatement,userStatement,findIdStatement,connection,logger);
        }
        return result;
    }

    @Override
    public boolean delete(Long teamId) throws DaoException {
        boolean result;
        Connection connection = null;
        PreparedStatement teamStatement = null;
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

            userStatement = connection.prepareStatement(UPDATE_USER_TEAM_ID);
            userStatement.setString(1, UserRole.USER.toString());
            userStatement.setInt(2, teamId.intValue());
            userStatement.executeUpdate();

            notificationStatement = connection.prepareStatement(DELETE_NOTIFICATIONS_BY_TEAM_ID);
            notificationStatement.setInt(1,teamId.intValue());
            notificationStatement.executeUpdate();

            teamStatement = connection.prepareStatement(DELETE_TEAM);
            teamStatement.setInt(1, teamId.intValue());
            teamStatement.executeUpdate();

            setKeyStatement.execute(SET_FOREIGN_KEY_CHECKS);

            connection.commit();
            result = true;

        } catch (SQLException e){
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.log(Level.ERROR, "Error during team deleting rollback", ex);
            }
            throw new  DaoException("Error during team deleting",e);
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
            closeAll(teamStatement,userStatement,notificationStatement,connection,logger);
        }
        return result;
    }

    @Override
    public boolean update(Team team) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_TEAM)) {
            statement.setString(1,team.getName());
            statement.setString(2,team.getDescription());
            statement.setInt(3,team.getTeamId().intValue());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during updating team", e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Team> findAll() throws DaoException {
        try(Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            try (ResultSet resultSet = statement.executeQuery()){
                return createTeamList(resultSet);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during searching for teams", e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Team> findById(Long teamId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_TEAM_BY_ID)) {
            statement.setInt(1, teamId.intValue());
            try (ResultSet resultSet = statement.executeQuery()){
                return createTeam(resultSet);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during searching for team", e);
            throw new DaoException( e);
        }
    }

    @Override
    public List<Team> findByManager(String manager) throws DaoException {
        try(Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement managerStatement = connection.prepareStatement(FIND_BY_MANAGER)) {
            managerStatement.setString(1, manager);
            try (ResultSet resultSet = managerStatement.executeQuery()){
                return createTeamList(resultSet);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during searching for teams", e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Team> findByKey(String keyWord) throws DaoException {
        try(Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_KEY)) {
            statement.setString(1, "%"+ keyWord+"%");
            try (ResultSet resultSet = statement.executeQuery()){
                return createTeamList(resultSet);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during searching for teams", e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Team> findByName(String name) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_TEAM_BY_NAME)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()){
               return createTeam(resultSet);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during searching for team", e);
            throw new DaoException( e);
        }
    }


    @Override
    public Optional<Team> findByManagerId(Long managerId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_TEAM_BY_MANAGER)) {
            statement.setInt(1, managerId.intValue());
            try (ResultSet resultSet = statement.executeQuery()){
                return createTeam(resultSet);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during searching for team", e);
            throw new DaoException( e);
        }
    }

    private Optional<Team> createTeam(ResultSet resultSet) throws SQLException {
        Optional<Team> teamOptional = Optional.empty();
        if(resultSet.next()){
            TeamBuilder teamBuilder = new TeamBuilder();
            teamBuilder.setTeamName(resultSet.getString(ColumnName.TEAM_NAME));
            teamBuilder.setTeamId((long) resultSet.getInt(ColumnName.TEAM_ID));
            teamBuilder.setDescription(resultSet.getString(ColumnName.DESCRIPTION));
            teamOptional = Optional.of(teamBuilder.build());
        }
        return teamOptional;
    }

    private List<Team> createTeamList(ResultSet resultSet) throws SQLException {
        List<Team> teams = new ArrayList<>();
        while (resultSet.next()) {
            TeamBuilder teamBuilder = new TeamBuilder();
            teamBuilder.setTeamId((long)resultSet.getInt(ColumnName.TEAM_ID));
            teamBuilder.setTeamName(resultSet.getString(ColumnName.TEAM_NAME));
            teamBuilder.setDescription(resultSet.getString(ColumnName.DESCRIPTION));
            teams.add(teamBuilder.build());
        }
        return teams;
    }


}
