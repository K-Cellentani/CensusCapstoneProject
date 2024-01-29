package com.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CSVToPostgres {

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/census";
        String username = "postgres";
        String password = "FILL IN PASSWORD";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            createTable(connection, "C:\\Users\\kmcel\\Documents\\census_capstone\\census-capstone\\src\\cbp19cd.csv", "yr19_table");
            createTable(connection, "C:\\Users\\kmcel\\Documents\\census_capstone\\census-capstone\\src\\cbp20cd.csv", "yr20_table");
            createTable(connection, "C:\\Users\\kmcel\\Documents\\census_capstone\\census-capstone\\src\\cbp21cd.csv", "yr21_table");

            loadCSVToTable(connection, "C:\\Users\\kmcel\\Documents\\census_capstone\\census-capstone\\src\\cbp19cd.csv", "yr19_table");
            loadCSVToTable(connection, "C:\\Users\\kmcel\\Documents\\census_capstone\\census-capstone\\src\\cbp20cd.csv", "yr20_table");
            loadCSVToTable(connection, "C:\\Users\\kmcel\\Documents\\census_capstone\\census-capstone\\src\\cbp21cd.csv", "yr21_table");

            System.out.println("Data loaded successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTable(Connection connection, String csvFilePath, String tableName) {

            
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName
        + "  (State_FIPS NUMERIC,"
        + "   State VARCHAR(50),"
        + "   Congressional_District TEXT,"
        + "   NAICS_Code VARCHAR(50),"
        + "   NAICS_Description TEXT,"
        + "   Number_of_Establishments TEXT,"
        + "   Employment TEXT,"
        + "   Employment_Noise_Flag VARCHAR(1),"
        + "   Q1_Payroll_in_thousands TEXT," 
        + "   Q1_Payroll_Noise_Flag VARCHAR(1),"
        + "   Annual_Payroll_in_thousands TEXT,"
        + "   Annual_Payroll_Noise_Flag VARCHAR(1))";
            
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        }
         catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadCSVToTable(Connection connection, String csvFilePath, String tableName) {
        String sql = "COPY " + tableName + " FROM '" + csvFilePath + "' DELIMITER ',' CSV HEADER QUOTE '\"'";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
