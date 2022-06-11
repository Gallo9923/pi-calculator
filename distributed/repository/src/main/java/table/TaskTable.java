package table;

import Enums.TaskState;
import connection.PostgresqlConnection;

import Pi.Task;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<Task> getPendingTasks(String taskJobId){
        Connection con =  PostgresqlConnection.getInstance().getConnection();

        List<Task> pendingTasks = new ArrayList<>();

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT " + " * FROM " + this.tableName + " WHERE STATE = '" + TaskState.PENDING.toString() + "' AND JOB_ID = " + taskJobId);

            if(rs.next()){
                String taskId = rs.getString("ID");
                String jobId = rs.getString("JOB_ID");
                long seed = Long.parseLong(rs.getString("SEED"));
                int batchSize = Integer.parseInt(rs.getString("BATCH_SIZE"));
                String createDate = rs.getString("CREATE_DATE");
                String state = rs.getString("STATE");
                String batchNumber = rs.getString("BATCH_NUMBER");
                int result = Integer.parseInt(rs.getString("RESULT"));
                short nPower = Short.parseShort(rs.getString("N_POWER"));

                Task task = new Task(taskId, jobId, seed, batchSize, createDate, state, batchNumber, result, nPower);

                pendingTasks.add(task);
            }
        } catch (SQLException throwables) {
            throwables.getMessage();
        }

        return pendingTasks;
    }

    public Task getTaskById(String id) {
        Connection con =  PostgresqlConnection.getInstance().getConnection();

        Task task = null;

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM " + this.tableName + " WHERE ID = " + id);

            if(rs.next()){
                System.out.println("Task record found " + id);
                String taskId = rs.getString("ID");
                String jobId = rs.getString("JOB_ID");
                long seed = Long.parseLong(rs.getString("SEED"));
                int batchSize = Integer.parseInt(rs.getString("BATCH_SIZE"));
                String createDate = rs.getString("CREATE_DATE");
                String state = rs.getString("STATE");
                String batchNumber = rs.getString("BATCH_NUMBER");
                int result = Integer.parseInt(rs.getString("RESULT"));
                short nPower = Short.parseShort(rs.getString("N_POWER"));

                task = new Task(taskId, jobId, seed, batchSize, createDate, state, batchNumber, result, nPower);
            }
        } catch (SQLException throwables) {
            throwables.getMessage();
        }

        return task;
    }

    public String setTaskState(String id, String state) {
        String response = "";
        Connection con =  PostgresqlConnection.getInstance().getConnection();

        try {
            String sqlUpdate = "UPDATE " + this.tableName + " SET STATE = ? WHERE ID = ?";

            PreparedStatement ps = con.prepareStatement(sqlUpdate);

            ps.setString(1, state);
            ps.setString(2, id);

            int status = ps.executeUpdate();

            if (status == 1) {
                response = this.tableName + " - UPDATE SUCCESSFUL";
                System.out.println("Update task result successful");
            }

            //Statement statement = con.createStatement();
            //statement.executeQuery("UPDATE " + this.tableName + " SET STATE = " + state + " WHERE ID = " + id);

        } catch (SQLException throwables) {
            System.out.println(this.tableName + " " + throwables.getMessage());
        }

        return response;
    }

    public String setTaskResult(String id, String state, String result) {
        String response = "";
        Connection con =  PostgresqlConnection.getInstance().getConnection();

        try {
            String sqlUpdate = "UPDATE " + this.tableName + " SET STATE = ?, RESULT = ? WHERE ID = ?";

            PreparedStatement ps = con.prepareStatement(sqlUpdate);

            ps.setString(1, state);
            ps.setString(2, result);
            ps.setString(3, id);

            int status = ps.executeUpdate();

            if (status == 1) {
                response = this.tableName + " - UPDATE SUCCESSFUL";
                System.out.println("Update task result successful");
            }

            //Statement statement = con.createStatement();
            //statement.executeQuery("UPDATE " + this.tableName + " SET STATE = " + state + ", RESULT = " + result + " WHERE ID = " + id);

        } catch (SQLException throwables) {
            System.out.println(this.tableName + " " + throwables.getMessage());
        }

        return response;
    }
}
