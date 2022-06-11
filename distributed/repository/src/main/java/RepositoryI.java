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

    private MessengerPrx messengerPrx;

    private Random r;

    public RepositoryI(Communicator communicator, MessengerPrx messengerPrx){
        this.communicator = communicator;
        this.jobTable = new JobTable(communicator.getProperties().getProperty("JobTableName"));
        this.taskTable = new TaskTable(communicator.getProperties().getProperty("TaskTableName"));

        this.pendingTaskSemaphore = new Semaphore(RepositoryI.PENDING_TASK_SEMAPHORE_PERMITS);
        this.counterPointsSemaphore = new Semaphore(RepositoryI.COUNTER_POINT_SEMAPHORE_PERMITS);

        this.messengerPrx = messengerPrx;
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
        if (tasks.size() > 0){
            System.out.println("Pending tasks " + tasks.size());
            task = tasks.get(0);
            taskTable.setTaskState(task.id, TaskState.IN_PROGRESS.toString());

        }else{
            System.out.println("No Pending tasks ");
            Job job = jobTable.getJobById(jobId);
            System.out.println("Job from DB " + job.id);
            BigInteger taskCounter = new BigInteger(job.taskCounter);  // 0
            BigInteger n = new BigInteger("10").pow(job.nPower);  // 10^5
            BigInteger batchSize = new BigInteger(job.batchSize + "");// 10^5

            boolean taskToBeDone = taskCounter.compareTo(n.divide(batchSize)) == -1;

            System.out.println("Task To be Done " + taskToBeDone);
            // TODO: Start Transaction
            if ( taskToBeDone){
                System.out.println("Tasks to be done");
                taskCounter = new BigInteger(job.taskCounter).add(BigInteger.ONE);
                job.taskCounter = taskCounter.toString();
                System.out.println("Updating Job's Task Counter " + job.taskCounter);
                jobTable.updateTaskCounter(job.id, job.taskCounter);

                System.out.println("Creating task in DB");
                LocalDateTime createDate = LocalDateTime.now();
                String taskId = taskTable.create(jobId, job.seed + "", job.batchSize + "", createDate.toString(), TaskState.IN_PROGRESS + "", taskCounter.toString(), "0", job.epsilonPower + "");

                task = new Task(taskId, job.id, job.seed, job.batchSize, createDate.toString(), TaskState.IN_PROGRESS.toString(), taskCounter.toString(), 0, job.epsilonPower);

                System.out.println("Starting Checker");
                long taskMillisTimeout = Long.parseLong(communicator.getProperties().getProperty("taskMillisTimeout"));
                new Thread(new Checker(taskTable, taskMillisTimeout, task.id, messengerPrx)).start();

            }else {
                System.out.println("No Tasks to be done - Calulating PI");
                // Calculate PI
                BigDecimal pi = PiResult.getResult(job.pointsInside ,n.toString());

                jobTable.updatePiResult(job.id, pi.toString());

                LocalDateTime startTime = LocalDateTime.parse(job.startDate);
                LocalDateTime finishTime = LocalDateTime.now();

                long milliseconds = ChronoUnit.MILLIS.between(startTime, finishTime);
                long seconds = ChronoUnit.SECONDS.between(startTime, finishTime);
                long minutes = ChronoUnit.MINUTES.between(startTime, finishTime);

                Time time = new Time(milliseconds + "", seconds + "", minutes + "" );

                System.out.println("Updating Job's Pi Result " + pi.toString() + " / milli: " + milliseconds + " / seconds: " + seconds + " / minutes: " + minutes);

                Result result = new Result(pi.toString(), job.repNumbers, time);

                Pi.ClientPrx clientProxy = Pi.ClientPrx.checkedCast(communicator.stringToProxy(job.clientProxy)).ice_twoway().ice_secure(false);
                System.out.println("Client Proxy: " + clientProxy.toString());
                clientProxy = ClientPrx.checkedCast(communicator.stringToProxy("client:tcp -h hgrid2 -p 9088"));
                clientProxy.setResult(result);
                System.out.println("PI: " + pi.toString());

            }
            // TODO: END Transaction
        }

        return task;
    }

    @Override
    public void setTaskResult(TaskResult taskResult, Current current) {
        System.out.println("setTaskResult");

        Task task = null;
        Job job = null;

        try {
            // Step 1: Acquire del semaphore
            this.counterPointsSemaphore.acquire();

            // Step 2: Consulta BD del Task
            task = taskTable.getTaskById(taskResult.taskId);
            System.out.println(task == null);

            // Step 3: Modificar el result y el estado del task a TaskState.DONE
            taskTable.setTaskResult(taskResult.taskId, TaskState.DONE.toString(), String.valueOf(taskResult.pointsInside));

            // Step 4: Consulta BD del Job asociado al Task
            job = jobTable.getJobById(task.jobId);

            // Step 5: Modificar el contador de puntos dentro del círculo del Job
            int pointsInside = Integer.parseInt(job.pointsInside) + taskResult.pointsInside;
            jobTable.updatePointsInside(job.id, String.valueOf(pointsInside));

            this.messengerPrx.publish(job.id);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            this.counterPointsSemaphore.release();;
        }
    }

}
