package org.pluralsight;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        DataSource dataSource = DataSourceFactory.getDataSource(args);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("========== NORTHWIND MENU ==========");
            System.out.println("1) Display all products");
            System.out.println("2) Display all customers");
            System.out.println("3) Display categories and products in a category");
            System.out.println("0) Exit");
            System.out.print("Select an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> displayProducts(dataSource);
                case 2 -> displayCustomers(dataSource);
                case 3 -> displayCategoriesAndProducts(dataSource, scanner);
                case 0 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private static void displayProducts(DataSource dataSource) {

        String sql = "SELECT ProductID, ProductName, UnitPrice FROM products";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n=== PRODUCTS ===");
            System.out.println("ID | Name | Price");
            System.out.println("-----------------------------------");

            while (rs.next()) {
                System.out.printf("%d | %s | $%.2f%n",
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("UnitPrice"));
            }

            System.out.println();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayCustomers(DataSource dataSource) {

        String sql = """
                SELECT ContactName, CompanyName, City, Country, Phone
                FROM customers
                ORDER BY Country
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n=== CUSTOMERS ===");
            System.out.println("Name | Company | City | Country | Phone");
            System.out.println("--------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%s | %s | %s | %s | %s%n",
                        rs.getString("ContactName"),
                        rs.getString("CompanyName"),
                        rs.getString("City"),
                        rs.getString("Country"),
                        rs.getString("Phone"));
            }

            System.out.println();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayCategoriesAndProducts(DataSource dataSource, Scanner scanner) {

        String sqlCategories = """
                SELECT CategoryID, CategoryName
                FROM categories
                ORDER BY CategoryID
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlCategories);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n=== CATEGORIES ===");
            System.out.println("ID | Name");
            System.out.println("----------------------");

            while (rs.next()) {
                int id = rs.getInt("CategoryID");
                String name = rs.getString("CategoryName");
                System.out.printf("%d | %s%n", id, name);
            }

            System.out.println();

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        System.out.print("Enter a category id to view products: ");
        int categoryId = scanner.nextInt();
        scanner.nextLine();

        String sqlProducts = """
                SELECT ProductID, ProductName, UnitPrice, UnitsInStock
                FROM products
                WHERE CategoryID = ?
                ORDER BY ProductID
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlProducts)) {

            ps.setInt(1, categoryId);

            try (ResultSet rs = ps.executeQuery()) {

                System.out.println("\n=== PRODUCTS IN CATEGORY " + categoryId + " ===");
                System.out.println("ID | Name | Price | Units In Stock");
                System.out.println("----------------------------------------------");

                boolean foundAny = false;

                while (rs.next()) {
                    foundAny = true;
                    System.out.printf("%d | %s | $%.2f | %d%n",
                            rs.getInt("ProductID"),
                            rs.getString("ProductName"),
                            rs.getDouble("UnitPrice"),
                            rs.getInt("UnitsInStock"));
                }

                if (!foundAny) {
                    System.out.println("No products found for this category.");
                }

                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
