import Enums.TaskState;
import Pi.Job;
import Pi.Task;
import Pi.TaskResult;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import table.JobTable;
import table.TaskTable;

import java.math.BigInteger;
import java.time.LocalDateTime;
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

    private Random r;

    public RepositoryI(Communicator communicator){
        this.communicator = communicator;
        this.jobTable = new JobTable(communicator.getProperties().getProperty("JobTableName"));
        this.taskTable = new TaskTable(communicator.getProperties().getProperty("TaskTableName"));

        this.pendingTaskSemaphore = new Semaphore(RepositoryI.PENDING_TASK_SEMAPHORE_PERMITS);
        this.counterPointsSemaphore = new Semaphore(RepositoryI.COUNTER_POINT_SEMAPHORE_PERMITS);
    }

    @Override
    public Job createsJob(Job job, Current current) {
        this.r = new Random(job.seed);
        String id = jobTable.create(job.nPower + "", job.seed + "", job.epsilonPower + "", job.startDate, job.finishDate, job.taskCounter, job.pointsInside, job.clientProxy, job.repNumbers + "", job.batchSize + "");
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
            // TODO: Start Transaction
            Job job = jobTable.getJobById(jobId);
            BigInteger batchNumber = new BigInteger(job.taskCounter).add(BigInteger.ONE);

            job.taskCounter = batchNumber.toString();
            jobTable.updateTaskCounter(job.id, job.taskCounter);

            LocalDateTime createDate = LocalDateTime.now();
            String taskId = taskTable.create(jobId, job.seed + "", job.batchSize + "", createDate.toString(), TaskState.IN_PROGRESS + "", batchNumber.toString(), "0", job.epsilonPower + "");

            task = new Task(taskId, job.id, job.seed, job.batchSize, createDate.toString(), TaskState.IN_PROGRESS.toString(), batchNumber.toString(), 0, job.epsilonPower);
            // TODO: END Transaction

        }

        // TODO: SET TASK TIMEOUT  !!!!!VERY IMPORTANT!!!!

        return task;
    }

    @Override
    public void setTaskResult(TaskResult taskResult, Current current) {
        System.out.println("setTaskResult");
    }


}
