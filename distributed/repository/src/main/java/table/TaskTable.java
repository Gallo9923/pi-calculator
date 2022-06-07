package table;

import connection.PostgresqlConnection;

import java.sql.*;

public class TaskTable {

    private String tableName;

    public TaskTable(String tableName){
        this.tableName = tableName;
    }

    public String create(String jodId, String seed, String batchSize, String createDate, String state, String batchNumber, String result, String epsilonPower){
        String response = "";
        Connection con =  PostgresqlConnection.getInstance().getConnection();

        try {
            String sqlInsert = "INSERT INTO " + this.tableName +" (JOB_ID, SEED, BATCH_SIZE, CREATE_DATE, STATE, BATCH_NUMBER, RESULT, EPSILON_POWER) VALUES (\n" +
                    "'" + jodId + "',\n" +
                    "'" + seed + "',\n" +
                    "'" + batchSize + "',\n" +
                    "'" + createDate + "',\n" +
                    "'" + state + "',\n" +
                    "'" + batchNumber + "',\n" +
                    "'" + result + "',\n" +
                    "'" + epsilonPower + "')";


            PreparedStatement ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            int id = -1;
            if (rs.next()) {
                response = rs.getInt(1) + "";
            }

        } catch (SQLException throwables) {
            System.out.println(this.tableName + " " + throwables.getMessage());
        }

        return response;
    }

    public String getPendingTasks(){
        //String response = this.tableName + " - INSERT SUCCESSFUL";
        Connection con =  PostgresqlConnection.getInstance().getConnection();

        try {
            Statement statement = con.createStatement();
            statement.executeQuery("SELECT " + " * FROM " + this.tableName + " WHERE STATE = 'IN_PROGRESS'");
        } catch (SQLException throwables) {
            response = this.tableName + " " + throwables.getMessage();
        }

        return response;
    }

    public String getTaskById(String id) {

        Connection con =  PostgresqlConnection.getInstance().getConnection();

        try {
            Statement statement = con.createStatement();
            statement.executeQuery("SELECT " + " * FROM " + this.tableName + " WHERE ID = '" + id + "'");
        } catch (SQLException throwables) {
            response = this.tableName + " " + throwables.getMessage();
        }

        return response;


    }

    public String setTaskState(String state) {

    }

    public String setTaskResult(String state, String result) {

    }
}
