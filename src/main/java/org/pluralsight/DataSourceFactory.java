package org.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

public class DataSourceFactory {

    private static final BasicDataSource dataSource = new BasicDataSource();

    static {
        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername("root");
        dataSource.setPassword("yearup24");
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxOpenPreparedStatements(100);
    }

    public static BasicDataSource getDataSource() {
        return dataSource;
    }
}
