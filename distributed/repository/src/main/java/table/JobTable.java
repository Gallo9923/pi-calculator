package table;

import connection.PostgresqlConnection;

import java.sql.*;

public class JobTable {

    private String tableName;

    public JobTable(String tableName){
        this.tableName = tableName;
    }

    public String create(String nPower, String seed, String epsilonPower, String startDate, String finishDate, String taskCounter, String pointsInside, String clientProxy){
        String response = "";
        Connection con =  PostgresqlConnection.getInstance().getConnection();

        try {
            String sqlInsert = "INSERT INTO" + this.tableName +" (N_POWER, SEED, EPSILON_POWER, START_DATE, FINISH_DATE, TASK_COUNTER, POINTS_INSIDE, CLIENT_PROXY) VALUES (\n" +
                    "'" + nPower + "',\n" +
                    "'" + seed + "',\n" +
                    "'" + epsilonPower + "',\n" +
                    "'" + startDate + "',\n" +
                    "'" + finishDate + "',\n" +
                    "'" + taskCounter + "',\n" +
                    "'" + pointsInside + "',\n" +
                    "'" + clientProxy + "')";

            PreparedStatement statement = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);

            ResultSet result = statement.getGeneratedKeys();

            if(result.next() && result != null){
                System.out.println("Key: " + result.getInt(1));
                response = result.getInt(1) + "";
            } else {
                System.out.println(this.tableName + "No Key");
            }

//            Statement statement = con.createStatement();
//            ResultSet rs = statement.executeQuery("INSERT INTO" + this.tableName +" (N_POWER, SEED, EPSILON_POWER, START_DATE, FINISH_DATE, TASK_COUNTER, POINTS_INSIDE, CLIENT_PROXY) VALUES (\n" +
//                    "'" + nPower + "',\n" +
//                    "'" + seed + "',\n" +
//                    "'" + epsilonPower + "',\n" +
//                    "'" + startDate + "',\n" +
//                    "'" + finishDate + "',\n" +
//                    "'" + taskCounter + "',\n" +
//                    "'" + pointsInside + "',\n" +
//                    "'" + clientProxy + "')");

        } catch (SQLException throwables) {
            System.out.println(this.tableName + " " + throwables.getMessage());
        }

        return response;
    }

    public String updatePointsInside(String id, String pointsInside){
        String response = this.tableName + " - INSERT SUCCESSFUL";
        Connection con =  PostgresqlConnection.getInstance().getConnection();

        try {
            Statement statement = con.createStatement();
            statement.executeQuery("UPDATE " + this.tableName +" SET POINTS_INSIDE = " + pointsInside + " WHERE ID = " + id);
        } catch (SQLException throwables) {
            response = this.tableName + " " + throwables.getMessage();
        }

        return response;
    }

    public String updateTaskCounter(String id, String taskCounter){
        String response = this.tableName + " - INSERT SUCCESSFUL";
        Connection con =  PostgresqlConnection.getInstance().getConnection();

        try {
            Statement statement = con.createStatement();
            statement.executeQuery("UPDATE " + this.tableName +" SET TASK_COUNTER = " + taskCounter + " WHERE ID = " + id);
        } catch (SQLException throwables) {
            response = this.tableName + " " + throwables.getMessage();
        }

        return response;
    }
}
