package org.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class DataSourceFactory {

    public static DataSource getDataSource(String username, String password) {

        BasicDataSource ds = new BasicDataSource();

        ds.setUrl("jdbc:mysql://localhost:3306/northwind");
        ds.setUsername(username);
        ds.setPassword(password);

        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);

        return ds;
    }
}
