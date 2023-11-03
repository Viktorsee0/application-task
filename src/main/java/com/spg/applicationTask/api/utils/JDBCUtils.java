package com.spg.applicationTask.api.utils;

import java.sql.Connection;
import java.sql.SQLException;

import static com.spg.applicationTask.engine.Constants.Messages.DB_CONNECTION_ERROR;
import static com.spg.applicationTask.engine.Constants.Messages.DB_ERROR;
import static com.spg.applicationTask.engine.Constants.Messages.DB_ROLLBACK_ERROR;


public class JDBCUtils {

    public static void rollback(final Connection connection, final SQLException e) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (final SQLException ex) {
                throw new RuntimeException(DB_ROLLBACK_ERROR, e);
            }
            throw new RuntimeException(DB_ERROR, e);
        }
    }

    public static void close(final Connection connection) {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (final SQLException e) {
                throw new RuntimeException(DB_CONNECTION_ERROR);
            }
        }
    }
}
