package com.vd.university.data.source;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class ConnectionDataSource {

    private static ConnectionDataSource connectionSource = null;

    public final ConnectionSource source;

    private ConnectionDataSource() {
        try {
            source = new JdbcConnectionSource(HotelDatabase.DATABASE_PATH);
        } catch (SQLException e) {
            throw new IllegalArgumentException("Database not found");
        }
    }

    public <T> void createTableIfNotExists(Class<T> tableClass) {
        try {
            TableUtils.createTableIfNotExists(source, tableClass);
        } catch (SQLException e) {
            throw new IllegalArgumentException("Table was not found");
        }
    }

    public <T> Dao<T, Integer> createDao(Class<T> tableClass) {
        try {
            return DaoManager.createDao(source, tableClass);
        } catch (SQLException e) {
            throw new IllegalArgumentException("Table was not found");
        }
    }

    public static ConnectionDataSource getInstance() {
        if (connectionSource == null) connectionSource = new ConnectionDataSource();
        return connectionSource;
    }
}