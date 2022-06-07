import Enums.TaskState;
import Pi.*;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import model.PiResult;
import table.JobTable;
import table.TaskTable;
import threads.Checker;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class RepositoryI implements Pi.Repository{

    private Communicator communicator;

    private JobTable jobTable;
    private TaskTable taskTable;

    private static final int PENDING_TASK_SEMAPHORE_PERMITS = 1;
    private Semaphore pendingTaskSemaphore;
    private static final int COUNTER_POINT_SEMAPHORE_PERMITS = 1;
    private Semaphore counterPointsSemaphore;

    private TaskReportPrx taskReportPrx;

    private Random r;

    public RepositoryI(Communicator communicator, TaskReportPrx taskReportPrx){
        this.communicator = communicator;
        this.jobTable = new JobTable(communicator.getProperties().getProperty("JobTableName"));
        this.taskTable = new TaskTable(communicator.getProperties().getProperty("TaskTableName"));

        this.pendingTaskSemaphore = new Semaphore(RepositoryI.PENDING_TASK_SEMAPHORE_PERMITS);
        this.counterPointsSemaphore = new Semaphore(RepositoryI.COUNTER_POINT_SEMAPHORE_PERMITS);

        this.taskReportPrx = taskReportPrx;
    }

    @Override
    public Job createsJob(Job job, Current current) {
        this.r = new Random(job.seed);
        String id = jobTable.create(job.nPower + "", job.seed + "", job.epsilonPower + "", job.startDate, job.finishDate, job.taskCounter, job.pointsInside, job.clientProxy, job.repNumbers + "", job.batchSize + "", job.pi);
        job.id = id;
        return job;
    }

    @Override
    public Task getTask(String jobId, Current current) {
        Task task = null;
        try {
            this.counterPointsSemaphore.acquire();
            task = getTask(jobId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            this.counterPointsSemaphore.release();
            return task;
        }
    }

    private Task getTask(String jobId){
        System.out.println("getTask");

        ArrayList<Task> tasks = (ArrayList<Task>) taskTable.getPendingTasks(jobId);

        Task task = null;
        if (tasks.size() >= 0){
            task = tasks.get(0);
            taskTable.setTaskState(task.id, TaskState.IN_PROGRESS.toString());

        }else{
            Job job = jobTable.getJobById(jobId);

            BigInteger taskCounter = new BigInteger(job.taskCounter);
            BigInteger n = new BigInteger("10").pow(job.nPower);
            BigInteger batchSize = new BigInteger(job.batchSize + "");
            // TODO: Start Transaction
            if ( taskCounter.compareTo(n.divide(batchSize)) == -1){

                taskCounter = new BigInteger(job.taskCounter).add(BigInteger.ONE);
                job.taskCounter = taskCounter.toString();
                jobTable.updateTaskCounter(job.id, job.taskCounter);

                LocalDateTime createDate = LocalDateTime.now();
                String taskId = taskTable.create(jobId, job.seed + "", job.batchSize + "", createDate.toString(), TaskState.IN_PROGRESS + "", taskCounter.toString(), "0", job.epsilonPower + "");

                task = new Task(taskId, job.id, job.seed, job.batchSize, createDate.toString(), TaskState.IN_PROGRESS.toString(), taskCounter.toString(), 0, job.epsilonPower);

            }else {
                // Calculate PI
                BigDecimal pi = PiResult.getResult(job.pointsInside ,n.toString());
                jobTable.updatePiResult(job.id, pi.toString());

                LocalDateTime startTime = LocalDateTime.parse(job.startDate);
                LocalDateTime finishTime = LocalDateTime.now();

                long milliseconds = ChronoUnit.MILLIS.between(startTime, finishTime);
                long seconds = ChronoUnit.SECONDS.between(startTime, finishTime);
                long minutes = ChronoUnit.MINUTES.between(startTime, finishTime);

                Time time = new Time(milliseconds + "", seconds + "", minutes + "" );

                Result result = new Result(pi.toString(), job.repNumbers, time);

                Pi.ClientPrx clientProxy = Pi.ClientPrx.checkedCast(communicator.stringToProxy(job.clientProxy)).ice_twoway().ice_secure(false);
                clientProxy.setResult(result);
            }
            // TODO: END Transaction
        }

        if (task != null){
            // TODO: SET TASK TIMEOUT  !!!!!VERY IMPORTANT!!!!
            long taskMillisTimeout = Long.parseLong(communicator.getProperties().getProperty("taskMillisTimeout"));
            new Thread(new Checker(taskTable, taskMillisTimeout, task.id, taskReportPrx)).start();

        }

        return task;
    }

    @Override
    public void setTaskResult(TaskResult taskResult, Current current) {
        System.out.println("setTaskResult");

        // Step 1: Acquire del semaphore

        // Step 2: Consulta de la BD del task
        // Step 3: Modificar el result y el estado del task a TaskState.DONE


        // Step 4: Consulta BD del Job asociado al task
        // Step 5: Modificar el contador del Job

        // Step 6: invocar

    }


}
