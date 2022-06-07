package table;

import Pi.Job;
import connection.PostgresqlConnection;

import java.sql.*;

public class JobTable {

    private String tableName;

    public JobTable(String tableName){
        this.tableName = tableName;
    }

    public String create(String nPower, String seed, String epsilonPower, String startDate, String finishDate, String taskCounter, String pointsInside, String clientProxy, String repNumbers, String batchSize){
        String response = "";
        Connection con =  PostgresqlConnection.getInstance().getConnection();

        try {
            String sqlInsert = "INSERT INTO " + this.tableName +" (N_POWER, SEED, EPSILON_POWER, START_DATE, FINISH_DATE, TASK_COUNTER, POINTS_INSIDE, CLIENT_PROXY, REP_NUMBERS, BATCH_SIZE) VALUES (\n" +
                    "'" + nPower + "',\n" +
                    "'" + seed + "',\n" +
                    "'" + epsilonPower + "',\n" +
                    "'" + startDate + "',\n" +
                    "'" + finishDate + "',\n" +
                    "'" + taskCounter + "',\n" +
                    "'" + pointsInside + "',\n" +
                    "'" + clientProxy + "',\n" +
                    "'" + repNumbers + "',\n" +
                    "'" + batchSize + "')";


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

    public Job getJobById(String id){
        Connection con =  PostgresqlConnection.getInstance().getConnection();
        Job job = null;
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM " + this.tableName + " WHERE ID = " + id);

            if(rs.next()){

                String jobID = rs.getString("ID");
                short nPower = Short.parseShort(rs.getString("N_POWER"));
                long seed = Long.parseLong(rs.getString("N_POWER"));
                double repNumbers = Double.parseDouble(rs.getString("REP_NUMBERS"));
                short epsilonPower = Short.parseShort(rs.getString("EPSILON_POWER"));
                String startDate = rs.getString("START_DATE");
                String finishDate = rs.getString("FINISH_DATE");
                String taskCounter = rs.getString("TASK_COUNTER");
                String pointsInside = rs.getString("POINTS_INSIDE");
                String clientProxy = rs.getString("CLIENT_PROXY");
                int batchSize = Integer.parseInt(rs.getString("CLIENT_PROXY"));

                job = new Job(jobID, nPower, seed, repNumbers, epsilonPower, startDate, finishDate, taskCounter, pointsInside, clientProxy, batchSize);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return job;

    }
}
