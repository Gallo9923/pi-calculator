package table;

import Pi.Job;
import connection.PostgresqlConnection;

import java.sql.*;

public class JobTable {

    private String tableName;

    public JobTable(String tableName){
        this.tableName = tableName;
    }

    public String create(String nPower, String seed, String epsilonPower, String startDate, String finishDate, String taskCounter, String pointsInside, String clientProxy, String repNumbers, String batchSize, String pi){
        String response = "";
        Connection con =  PostgresqlConnection.getInstance().getConnection();

        try {
            String sqlInsert = "INSERT INTO " + this.tableName +" (N_POWER, SEED, EPSILON_POWER, START_DATE, FINISH_DATE, TASK_COUNTER, POINTS_INSIDE, CLIENT_PROXY, REP_NUMBERS, BATCH_SIZE, PI) VALUES (\n" +
                    "'" + nPower + "',\n" +
                    "'" + seed + "',\n" +
                    "'" + epsilonPower + "',\n" +
                    "'" + startDate + "',\n" +
                    "'" + finishDate + "',\n" +
                    "'" + taskCounter + "',\n" +
                    "'" + pointsInside + "',\n" +
                    "'" + clientProxy + "',\n" +
                    "'" + repNumbers + "',\n" +
                    "'" + batchSize + "',\n" +
                    "'" + pi + "')";


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
            statement.executeQuery("UPDATE " + this.tableName + " SET POINTS_INSIDE = " + pointsInside + " WHERE ID = " + id);
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

    public String updatePiResult(String id, String pi){
        String response = this.tableName + " - INSERT SUCCESSFUL";
        Connection con =  PostgresqlConnection.getInstance().getConnection();
        try {
            Statement statement = con.createStatement();
            statement.executeQuery("UPDATE " + this.tableName +" SET PI = " + pi + " WHERE ID = " + id);
        } catch (SQLException throwables) {
            response = this.tableName + " " + throwables.getMessage();
        }

        return response;
    }

    public Job getJobById(String id){
        System.out.println("getJobById " + id);
        Connection con =  PostgresqlConnection.getInstance().getConnection();
        Job job = null;
        try {
            Statement statement = con.createStatement();
            System.out.println("Job Pre Query");
            ResultSet rs = statement.executeQuery("SELECT * FROM " + this.tableName + " WHERE ID = " + "'" + id +"'");

            if(rs.next()){
                System.out.println("Found Record");
                String jobID = rs.getString("ID");
                System.out.println("ID " + jobID);
                short nPower = Short.parseShort(rs.getString("N_POWER"));
                System.out.println("N_POWER " + nPower);
                long seed = Long.parseLong(rs.getString("SEED"));
                System.out.println("SEED " + seed);
                double repNumbers = Double.parseDouble(rs.getString("REP_NUMBERS"));
                System.out.println("REP_NUMBERS " + repNumbers);
                short epsilonPower = Short.parseShort(rs.getString("EPSILON_POWER"));
                System.out.println("EPSILON_POWER " + epsilonPower);
                String startDate = rs.getString("START_DATE");
                System.out.println("START_DATE " + startDate);
                String finishDate = rs.getString("FINISH_DATE");
                System.out.println("FINISH_DATE " + finishDate);
                String taskCounter = rs.getString("TASK_COUNTER");
                System.out.println("TASK_COUNTER " + taskCounter);
                String pointsInside = rs.getString("POINTS_INSIDE");
                System.out.println("POINTS_INSIDE " + pointsInside);
                String clientProxy = rs.getString("CLIENT_PROXY");
                System.out.println("CLIENT_PROXY " + clientProxy);
                int batchSize = Integer.parseInt(rs.getString("BATCH_SIZE"));
                System.out.println("BATCH_SIZE " + batchSize);
                String pi = rs.getString("PI");
                System.out.println("PI " + pi);

                job = new Job(jobID, nPower, seed, repNumbers, epsilonPower, startDate, finishDate, taskCounter, pointsInside, clientProxy, batchSize, pi);
                System.out.println("Job Found Record " + job.id);
            }

        } catch (SQLException throwables) {
            System.out.println("JOB DB ERROR");
            throwables.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return job;

    }
}
