package org.pluralsight;

import javax.sql.DataSource;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        try {
            DataSource dataSource = DataSourceFactory.getDataSource();
            Connection connection = dataSource.getConnection();
            System.out.println("CONNECTED TO MYSQL SUCCESSFULLY!");
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
