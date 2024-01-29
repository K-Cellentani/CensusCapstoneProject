package com.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class CreateConsTable {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/census";
        String username = "postgres";
        String password = "FILL IN PASSWORD";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            createCensusTable(connection, "yr19_table", "census_table");
            

            addFilteredData(connection, "yr19_table");
            addFilteredData(connection, "yr20_table");
            addFilteredData(connection, "yr21_table");

            System.out.println("Data loaded successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        private static void createCensusTable(Connection connection, String tableName, String newTable) {
            String sql = "CREATE TABLE " + newTable + " AS SELECT State, Year, Number_of_Establishments, Employment, Annual_Payroll_in_thousands FROM " + tableName + " WHERE 1 = 0;";
                
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private static void addFilteredData(Connection connection, String tableName) {
        
            String sql = "INSERT INTO census_table"+ 
                    " (State, Year, Number_of_Establishments, Employment, Annual_Payroll_in_thousands)" +
                    " SELECT State, Year, SUM(Number_of_Establishments) AS Number_of_Establishments, SUM(Employment) AS Employment, SUM(Annual_Payroll_in_thousands) AS Annual_Payroll_in_thousands" +
                    " FROM " + tableName + " WHERE NAICS_Description = 'Total for all sectors'" + 
                    " GROUP BY State, Year;";
        
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
}
