package com.krealll.worklance.model.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class CustomConnectionPool {

    private static final Logger logger = LogManager.getLogger(CustomConnectionPool.class);

    private static CustomConnectionPool instance = new CustomConnectionPool();

    private BlockingQueue<ProxyConnection> freeConnections;
    private Queue<ProxyConnection> busyConnections;

    private final static int DEFAULT_CONNECTION_POOL_SIZE = 32;

    private final static String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private final static String USER_PROP = "user";
    private final static String PASS_PROP = "password";
    private final static String USER = "root";
    private final static String PASSWORD = "readB__KSfolks7091";
    private final static String URL = "jdbc:mysql://localhost:3306/bookDb";
    private final static String SERVER_TIMEZONE_PROP="serverTimezone";
    private final static String SERVER_TIMEZONE="UTC";
    private final static String AUTO_RECONNECT_PROP="autoReconnect";
    private final static String AUTO_RECONNECT="true";

    public static CustomConnectionPool getInstance() {
        return instance;
    }

    private CustomConnectionPool() {
        try{
            Class.forName(DRIVER_NAME);
            freeConnections = new LinkedBlockingDeque<>(DEFAULT_CONNECTION_POOL_SIZE);
            busyConnections = new ArrayDeque<>();
            Properties prop = new Properties();
            prop.setProperty(USER_PROP, USER);
            prop.setProperty(PASS_PROP, PASSWORD);
            prop.setProperty(AUTO_RECONNECT_PROP,AUTO_RECONNECT);
           // prop.setProperty(SERVER_TIMEZONE_PROP,SERVER_TIMEZONE);
            for (int i = 0; i < DEFAULT_CONNECTION_POOL_SIZE; i++) {
                freeConnections.add(new ProxyConnection(DriverManager.getConnection(URL, prop)));
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.fatal("Error during connection pool creation",e);
            throw new RuntimeException("Error during connection pool creation");
        }
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            busyConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.error("Error while getting connection",e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if(connection instanceof ProxyConnection){
            busyConnections.remove(connection);
            freeConnections.offer((ProxyConnection) connection);
        } else {
            logger.error("Error: wrong connection to release");
        }
    }

    public void destroyPool() {
        try {
            for (int i = 0; i <DEFAULT_CONNECTION_POOL_SIZE ; i++) {
                freeConnections.take().reallyClose();
            }
            deregisterDrivers();
        } catch (InterruptedException | SQLException e) {
            logger.error("Error while destroying connection pool",e);
        }
    }

    private void deregisterDrivers() throws SQLException {
        while(DriverManager.getDrivers().hasMoreElements()){
                DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
        }
    }
}
