package com.krealll.worklance.model.pool;

import com.krealll.worklance.exception.CustomPropertyException;
import com.krealll.worklance.util.DataBaseProperty;
import com.krealll.worklance.util.PropertyReader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ConnectionPool {

    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);

    private final static int CONNECTION_POOL_SIZE = 8;

    private BlockingQueue<ProxyConnection> freeConnections;
    private BlockingQueue<ProxyConnection> busyConnections;

    public static ConnectionPool getInstance(){
        return PoolHolder.getInstance();
    }

    private ConnectionPool() {
        try{
            PropertyReader reader = new PropertyReader();
            Properties properties = reader.read(DataBaseProperty.DATA_BASE_PROPERTY_PATH);
            String url = properties.getProperty(DataBaseProperty.DATA_BASE_URL);
            String driver = properties.getProperty(DataBaseProperty.DATA_BASE_DRIVER);
            Class.forName(driver);
            freeConnections = new LinkedBlockingDeque<>(CONNECTION_POOL_SIZE);
            busyConnections = new LinkedBlockingDeque<>();
            for (int i = 0; i < CONNECTION_POOL_SIZE; i++) {
                freeConnections.add(new ProxyConnection(DriverManager.getConnection(url, properties)));
            }
        } catch (SQLException | ClassNotFoundException | CustomPropertyException e) {
            logger.log(Level.FATAL, "Error during connection pool creation", e);
            throw new RuntimeException("Error during connection pool creation",e);
        }
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            busyConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Error while getting connection", e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if(connection instanceof ProxyConnection){
            busyConnections.remove(connection);
            freeConnections.offer((ProxyConnection) connection);
        } else {
            logger.log(Level.ERROR, "Error: wrong connection to release");
        }
    }

    public void destroyPool() {
        try {
            for (int i = 0; i < CONNECTION_POOL_SIZE; i++) {
                freeConnections.take().reallyClose();
            }
            deregisterDrivers();
        } catch (InterruptedException | SQLException e) {
            logger.log(Level.ERROR, "Error while destroying connection pool");
            Thread.currentThread().interrupt();
        }
    }

    private void deregisterDrivers() throws SQLException {
        while(DriverManager.getDrivers().hasMoreElements()){
            DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
        }
    }

    private static class PoolHolder{
        public static final ConnectionPool HOLDER_INSTANCE = new ConnectionPool();

        public static ConnectionPool getInstance(){
            return PoolHolder.HOLDER_INSTANCE;
        }
    }

}
