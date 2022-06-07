import Enums.TaskState;
import Pi.Job;
import Pi.Task;
import Pi.TaskResult;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import table.JobTable;
import table.TaskTable;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class RepositoryI implements Pi.Repository{

    private Communicator communicator;

    private String jobTableName;
    private String taskTableName;

    private static final int PENDING_TASK_SEMAPHORE_PERMITS = 1;
    private Semaphore pendingTaskSemaphore;
    private static final int COUNTER_POINT_SEMAPHORE_PERMITS = 1;
    private Semaphore counterPointsSemaphore;


    public RepositoryI(Communicator communicator){
        this.communicator = communicator;
        this.jobTableName = communicator.getProperties().getProperty("JobTableName");
        this.taskTableName = communicator.getProperties().getProperty("TaskTableName");

        this.pendingTaskSemaphore = new Semaphore(RepositoryI.PENDING_TASK_SEMAPHORE_PERMITS);
        this.counterPointsSemaphore = new Semaphore(RepositoryI.COUNTER_POINT_SEMAPHORE_PERMITS);
    }

    @Override
    public Job createsJob(Job job, Current current) {

        JobTable jobTable = new JobTable(this.jobTableName);
        String id = jobTable.create(job.nPower + "", job.seed + "", job.epsilonPower + "", job.startDate, job.finishDate, job.taskCounter, job.pointsInside, job.clientProxy);
        job.id = id;
        return job;
    }

    @Override
    public Task getTask(String jobId, Current current) {
        System.out.println("getTask");

        // Step 1: Query Task Where Task.State == PENDING
        // TODO:
        TaskTable taskTable = new TaskTable(this.taskTableName);
        ArrayList<Task> tasks = (ArrayList<Task>) taskTable.getPendingTasks();

        Task task = null;
        if (tasks.size() >= 0){
            task = tasks.get(0);
            taskTable.setTaskState(task.id, TaskState.IN_PROGRESS.toString());

        }else{

        }

        // TODO: SET TASK TIMEOUT


        // Step 2: If no pending task. Create Task.
        // Transaction
            // Aumentar el taskCounter del job
            // Crear Task

        // Step 4: Return Task

        return null;
    }

    @Override
    public void setTaskResult(TaskResult taskResult, Current current) {
        System.out.println("setTaskResult");
    }


}
