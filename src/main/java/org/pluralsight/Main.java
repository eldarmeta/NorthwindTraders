package org.pluralsight;

import javax.sql.DataSource;
import java.sql.*;

public class Main {

    public static void main(String[] args) {

        // 1 — validate input
        if (args.length != 3) {
            System.out.println("Usage: java Main <username> <password> <pattern>");
            return;
        }

        String username = args[0];
        String password = args[1];
        String pattern  = args[2];

        String sql = "SELECT ProductID, ProductName " +
                "FROM products " +
                "WHERE ProductName LIKE ?";

        // 2 — connect
        DataSource dataSource = DataSourceFactory.getDataSource(username, password);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql))
        {
            // 3 — set pattern
            ps.setString(1, pattern);

            // 4 — run query
            try (ResultSet rs = ps.executeQuery()) {

                // 5 — iterate results
                while (rs.next()) {
                    int id = rs.getInt("ProductID");
                    String name = rs.getString("ProductName");
                    System.out.printf("%d - %s\n", id, name);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
