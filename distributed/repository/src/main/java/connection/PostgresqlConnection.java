package connection;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Properties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresqlConnection implements DatabaseConnection{

    private static String connectionString = null;
    private static String user = null;
    private static String password = null;

    @Override
    public Connection getConnection() {
        return  connection;
    }

    private static PostgresqlConnection instance = null;
    private Connection connection;

    private PostgresqlConnection(){
        connect();
    }

    public static void configure(String connectionString, String user, String password){
        PostgresqlConnection.connectionString = connectionString;
        PostgresqlConnection.user = user;
        PostgresqlConnection.password = password;
    }

    public static PostgresqlConnection getInstance(){
        if (PostgresqlConnection.instance == null){
            PostgresqlConnection.instance = new PostgresqlConnection();
        }
        return PostgresqlConnection.instance;
    }


    public void connect(){
        try {
            this.connection = DriverManager.getConnection(connectionString, user, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
