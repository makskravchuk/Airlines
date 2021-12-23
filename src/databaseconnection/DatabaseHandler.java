package databaseconnection;
import java.sql.*;

public class DatabaseHandler extends Configuration {
   private Connection dbConnection;
    public Connection getDbConnection() throws ClassNotFoundException, SQLException{
        String connectionString =  "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser,dbPass);
        return dbConnection;
    }
    public void closeDbConnection() throws SQLException{
        dbConnection.close();
    }
}
