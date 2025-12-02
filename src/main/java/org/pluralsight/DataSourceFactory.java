package org.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class DataSourceFactory {

    private static BasicDataSource dataSource;

    public static DataSource getDataSource(String[] args) {

        if (args.length < 3) {
            throw new RuntimeException("You must pass 3 arguments: username password databasename");
        }

        String username = args[0];
        String password = args[1];
        String dbName   = args[2];

        if (dataSource == null) {

            dataSource = new BasicDataSource();
            dataSource.setUrl("jdbc:mysql://localhost:3306/" + dbName);
            dataSource.setUsername(username);
            dataSource.setPassword(password);

            dataSource.setMinIdle(3);
            dataSource.setMaxIdle(10);
            dataSource.setMaxTotal(20);
        }

        return dataSource;
    }
}
