package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlConnection {
    public static Connection getConnection(String dbUrl, String dbUsername, String dbPassword) {
        Connection con = null;
        try {

            // create the connection
            con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
         }

        catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
