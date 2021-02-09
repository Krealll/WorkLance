package com.krealll.worklance.model.dao.impl;

import com.krealll.worklance.exception.DaoException;
import com.krealll.worklance.model.builder.OrderBuilder;
import com.krealll.worklance.model.dao.ColumnName;
import com.krealll.worklance.model.dao.OrderDao;
import com.krealll.worklance.model.entity.Order;
import com.krealll.worklance.model.entity.type.OrderStatus;
import com.krealll.worklance.model.pool.ConnectionPool;
import com.krealll.worklance.util.MapKeys;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrderDaoImpl implements OrderDao {

    private static Logger logger = LogManager.getLogger(OrderDaoImpl.class);

    private static OrderDao instance = new OrderDaoImpl();

    private OrderDaoImpl(){}

    public static OrderDao getInstance(){
        return instance;
    }


    private static final String CREATE_ORDER = "INSERT INTO orders (name ,description , budget, date_of_creation, status, users_user_id)" +
            "VALUES (?,?,?,?,?,?)";
    private static final String FIND_ALL_ORDERS = "SELECT id_order, name, description, budget, date_of_creation, " +
            "status, users_user_id FROM orders ";
    private static final String FIND_BY_ID = "SELECT id_order, name, description, budget, date_of_creation, status," +
            " users_user_id FROM orders WHERE id_order=?";
    private static final String FIND_BY_USER_ID = "SELECT id_order, name, description, budget, date_of_creation, status," +
            " users_user_id FROM orders WHERE users_user_id=?";
    private static final String UPDATE_USER_EMAIL_VISIBILITY = "UPDATE users SET show_email=? WHERE user_id=?";

    private static final String UPDATE_ORDER = "UPDATE orders SET name=?, description=?, budget=?, status=? " +
            "WHERE id_order=?";

    private static final String DELETE_ORDER = "DELETE FROM orders WHERE id_order=?";
    private static final String FIND_BY_KEY = "SELECT id_order, name, description, budget, date_of_creation, " +
            "status, users_user_id FROM orders WHERE description LIKE ?";
    private static final String FIND_BY_NAME = "SELECT id_order, name, description, budget, date_of_creation, " +
            "status, users_user_id FROM orders WHERE name=?";
    private static final String FIND_BY_CUSTOMER = "SELECT id_order, name, orders.description, budget, date_of_creation, " +
            "status, users_user_id FROM orders LEFT JOIN users ON orders.users_user_id=users.user_id WHERE users.login=?";
    private static final String FIND_BY_BUDGET = "SELECT id_order, name, description, budget, date_of_creation, " +
            "status, users_user_id FROM orders WHERE budget>? AND budget<?";

    @Override
    public boolean add(Map<String, Object> parameters) throws DaoException {
        boolean result;
        Connection connection = null;
        PreparedStatement orderStatement = null;
        PreparedStatement userStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            orderStatement = connection.prepareStatement(CREATE_ORDER);
            orderStatement.setString(1,(String)parameters.get(MapKeys.NAME));
            orderStatement.setString(2,(String)parameters.get(MapKeys.DESCRIPTION));
            orderStatement.setDouble(3, Double.parseDouble((String)parameters.get(MapKeys.BUDGET)));
            orderStatement.setDate(4, Date.valueOf(LocalDate.now()));
            orderStatement.setString(5,OrderStatus.OPENED.getName());
            orderStatement.setInt(6, Integer.parseInt((String)parameters.get(MapKeys.USERS_USER_ID)));
            orderStatement.executeUpdate();
            boolean showEmail = false;
            if(parameters.get(MapKeys.SHOW_EMAIL)!=null){
                showEmail=true;
            }
            userStatement = connection.prepareStatement(UPDATE_USER_EMAIL_VISIBILITY);
            userStatement.setBoolean(1, showEmail);
            userStatement.setInt(2, Integer.parseInt((String)parameters.get(MapKeys.USERS_USER_ID)));
            userStatement.executeUpdate();

            connection.commit();
            result = true;
        } catch (SQLException e){
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.log(Level.ERROR, "Error during rollback", ex);
            }
            throw new  DaoException("Error during order creation",e);
        } finally {
            close(connection,orderStatement,userStatement,logger);
        }
        return result;
    }

    @Override
    public boolean delete(Long orderId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ORDER)) {
            statement.setInt(1,orderId.intValue());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during deleting order", e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Order order) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER)) {
            statement.setString(1,order.getName());
            statement.setString(2,order.getDescription());
            statement.setDouble(3,order.getBudget());
            statement.setString(4,order.getStatus().getName());
            statement.setInt(5, order.getId().intValue());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during updating order", e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Map<String,Object> parameters) throws DaoException{
        boolean result;
        Connection connection = null;
        PreparedStatement orderStatement = null;
        PreparedStatement userStatement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            orderStatement = connection.prepareStatement(UPDATE_ORDER);
            orderStatement.setString(1,(String)parameters.get(MapKeys.NAME));
            orderStatement.setString(2,(String)parameters.get(MapKeys.DESCRIPTION));
            orderStatement.setDouble(3, Double.parseDouble((String)parameters.get(MapKeys.BUDGET)));
            orderStatement.setString(4,parameters.get(MapKeys.STATUS).toString());
            orderStatement.setInt(5, Integer.parseInt((String) parameters.get(MapKeys.ORDER_ID)));
            orderStatement.executeUpdate();
            boolean showEmail = false;
            if(parameters.get(MapKeys.SHOW_EMAIL)!=null){
                showEmail=true;
            }
            userStatement = connection.prepareStatement(UPDATE_USER_EMAIL_VISIBILITY);
            userStatement.setBoolean(1, showEmail);
            userStatement.setInt(2, Integer.parseInt((String)parameters.get(MapKeys.CUSTOMER)));
            userStatement.executeUpdate();

            connection.commit();
            result = true;
        } catch (SQLException e){
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.log(Level.ERROR, "Error during rollback", ex);
            }
            throw new  DaoException("Error during order creation",e);
        } finally {
            close(connection,orderStatement,userStatement,logger);
        }
        return result;
    }


    @Override
    public Optional<Order> findById(Long orderId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(1,orderId.intValue());
            try (ResultSet resultSet = statement.executeQuery()){
                Optional<Order> foundOrder = Optional.empty();
                if(resultSet.next()){
                    OrderBuilder orderBuilder = new OrderBuilder();
                    orderBuilder.setName(resultSet.getString(ColumnName.NAME));
                    orderBuilder.setDate(LocalDate.parse(resultSet.getString(ColumnName.DATE)));
                    orderBuilder.setBudget(resultSet.getDouble(ColumnName.BUDGET));
                    orderBuilder.setStatus(OrderStatus.valueOf(resultSet.getString(ColumnName.STATUS)
                            .replace(" ","_").toUpperCase()));
                    orderBuilder.setCustomer(Long.valueOf(resultSet.getInt(ColumnName.USERS_USER_ID)));
                    orderBuilder.setDescription(resultSet.getString(ColumnName.DESCRIPTION));
                    orderBuilder.setId(Long.valueOf(resultSet.getInt(ColumnName.ORDER_ID)));
                    foundOrder = Optional.of(orderBuilder.build());
                }
                return foundOrder;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during searching for order", e);
            throw new DaoException( e);
        }
    }


    @Override
    public List<Order> findAll() throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_ORDERS)) {
            try (ResultSet resultSet = statement.executeQuery()){
                List<Order> foundUsers = createOrderList(resultSet);
                return foundUsers;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during searching for orders", e);
            throw new DaoException( e);
        }
    }

    @Override
    public List<Order> findByBudget(Double lower, Double upper) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_BUDGET)) {
            statement.setDouble(1, lower);
            statement.setDouble(2, upper);
            try (ResultSet resultSet = statement.executeQuery()){
                List<Order> foundUsers = createOrderList(resultSet);
                return foundUsers;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during searching for orders", e);
            throw new DaoException( e);
        }
    }

    @Override
    public List<Order> findByKey(String keyWord) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_KEY)) {
            statement.setString(1, "%" + keyWord + "%");
            try (ResultSet resultSet = statement.executeQuery()){
                List<Order> foundUsers = createOrderList(resultSet);
                return foundUsers;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during searching for orders", e);
            throw new DaoException( e);
        }
    }

    @Override
    public List<Order> findByCustomer(String customerLogin) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_CUSTOMER)) {
            statement.setString(1, customerLogin);
            try (ResultSet resultSet = statement.executeQuery()){
                List<Order> foundUsers = createOrderList(resultSet);
                return foundUsers;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during searching for orders", e);
            throw new DaoException( e);
        }
    }

    @Override
    public List<Order> findByName(String name) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()){
                List<Order> foundUsers = createOrderList(resultSet);
                return foundUsers;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during searching for orders", e);
            throw new DaoException( e);
        }
    }

    @Override
    public List<Order> findByUserId(Long userId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_USER_ID)) {
            statement.setInt(1,userId.intValue());
            try (ResultSet resultSet = statement.executeQuery()){
                List<Order> orders = new ArrayList<>();
                while (resultSet.next()){
                    OrderBuilder orderBuilder = new OrderBuilder();
                    orderBuilder.setBudget(resultSet.getDouble(ColumnName.BUDGET));
                    orderBuilder.setName(resultSet.getString(ColumnName.NAME));
                    orderBuilder.setDate(LocalDate.parse(resultSet.getString(ColumnName.DATE)));

                    orderBuilder.setStatus((OrderStatus.valueOf(resultSet.getString(ColumnName.STATUS).replace(" ","_").toUpperCase())));


                    orderBuilder.setDescription(resultSet.getString(ColumnName.DESCRIPTION));
                    orderBuilder.setCustomer(Long.valueOf(resultSet.getInt(ColumnName.USERS_USER_ID)));
                    orderBuilder.setId(Long.valueOf(resultSet.getInt(ColumnName.ORDER_ID)));
                    orders.add(orderBuilder.build());
                }
                return orders;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error during searching for orders", e);
            throw new DaoException( e);
        }
    }

    private List<Order> createOrderList(ResultSet resultSet) throws SQLException {
        List<Order> orders = new ArrayList<>();
        while (resultSet.next()) {
            OrderBuilder orderBuilder = new OrderBuilder();
            orderBuilder.setName(resultSet.getString(ColumnName.NAME));
            orderBuilder.setBudget(resultSet.getDouble(ColumnName.BUDGET));
            orderBuilder.setDate(LocalDate.parse(resultSet.getString(ColumnName.DATE)));
            orderBuilder.setStatus(OrderStatus.valueOf(resultSet.getString(ColumnName.STATUS)
                    .replace(" ","_").toUpperCase()));
            orderBuilder.setDescription(resultSet.getString(ColumnName.DESCRIPTION));
            orderBuilder.setCustomer(Long.valueOf(resultSet.getInt(ColumnName.USERS_USER_ID)));
            orderBuilder.setId(Long.valueOf(resultSet.getInt(ColumnName.ORDER_ID)));
            orders.add(orderBuilder.build());
        }
        return orders;
    }

}
