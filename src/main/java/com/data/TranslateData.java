package com.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;

public class TranslateData {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/census";
        String username = "postgres";
        String password = "FILL IN PASSWORD";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            changeDataType(connection, "yr19_table");
            changeDataType(connection, "yr20_table");
            changeDataType(connection, "yr21_table");

            addYearColumn(connection, "yr19_table");
            addYearColumn(connection, "yr20_table");
            addYearColumn(connection, "yr21_table");

            System.out.println("Data loaded successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void changeDataType(Connection connection, String tableName) {
        String sql = "ALTER TABLE " + tableName +
                " ALTER COLUMN Number_of_Establishments TYPE integer USING REPLACE(Number_of_Establishments, ',', '')::integer," +
                " ALTER COLUMN Employment TYPE integer USING REPLACE(Employment, ',', '')::integer," +
                " ALTER COLUMN Q1_Payroll_in_thousands TYPE integer USING REPLACE(Q1_Payroll_in_thousands, ',', '')::integer," +
                " ALTER COLUMN Annual_Payroll_in_thousands TYPE integer USING REPLACE(Annual_Payroll_in_thousands, ',', '')::integer;";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        private static void addYearColumn(Connection connection, String tableName) {
            Dictionary<String, String> years = new Hashtable<>();

            years.put("yr19_table", "2019");
            years.put("yr20_table", "2020");
            years.put("yr21_table", "2021");


            String sql = "ALTER TABLE " + tableName +
                    " ADD COLUMN Year INTEGER;" +
                    "UPDATE " + tableName + " SET Year = " + years.get(tableName) + ";";
        
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
    
    
}
