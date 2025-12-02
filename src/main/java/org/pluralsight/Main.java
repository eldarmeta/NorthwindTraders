package org.pluralsight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
    public static void main(String[] args) {

        String sql = "SELECT ProductID, ProductName, UnitPrice FROM products";

        try (
                Connection connection = DataSourceFactory.getDataSource().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet results = statement.executeQuery()
        ) {
            System.out.println("PRODUCTS LIST:");
            System.out.println("--------------------------------");

            while (results.next()) {
                int id = results.getInt("ProductID");
                String name = results.getString("ProductName");
                double price = results.getDouble("UnitPrice");

                System.out.printf("%d | %s | $%.2f%n", id, name, price);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
