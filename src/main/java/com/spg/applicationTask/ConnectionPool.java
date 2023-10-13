package com.spg.applicationTask;


import com.spg.applicationTask.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static com.spg.applicationTask.extension.PropertiesUtil.get;

public enum ConnectionPool {

    INSTANCE;

    private BlockingQueue<Connection> pool;

    @Value("db.url")
    private String url;

    private static final int DEFAULT_POOL_SIZE = 5;

    ConnectionPool() {
        try {
            Class.forName(get("db.driver"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        pool = new ArrayBlockingQueue<>(DEFAULT_POOL_SIZE);

        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {

                Connection connection = createConnection(url, get("db.username"), get("db.password"));
                pool.add(connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized Connection getConnection() {
        while (pool.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return pool.poll();
    }

    public synchronized void releaseConnection(Connection connection) {
        if (connection != null) {
            pool.add(connection);
            notify();
        }
    }

    private Connection createConnection(String url, String user,
                                        String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
