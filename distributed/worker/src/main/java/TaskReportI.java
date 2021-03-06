import Pi.PiControllerPrx;
import Pi.TaskResult;
import com.zeroc.Ice.Current;
import model.*;
import thread.Task;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TaskReportI implements Pi.TaskReport {

    public static final int THREAD_NUMBER = 8;
    public static final int BATCH_SIZE = 1000;

    private PiControllerPrx piControllerPrx;
    private State state;
    private RepeatedCounter repCounter;

    public TaskReportI(PiControllerPrx piControllerPrx){
        System.out.println("Notify task available constructor");

        this.state = State.IDLE;
        this.piControllerPrx = piControllerPrx;
    }

    @Override
    public void notifyTaskAvailable(String jobId, Current current) {

        System.out.println("Notify task available " + jobId);

        if (this.state == State.WORKING){
            return;
        }
        
        Pi.Task task = piControllerPrx.getTask(jobId);

        if (task == null){
            return;
        }

        this.state = State.WORKING;

        Counter result = new PIResult();
        Point.setEpsilonPower(task.epsilonPower);
        Random r = new Random(task.seed);
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_NUMBER);

        int numberOfTasks = task.batchSize / BATCH_SIZE;
        for (int i = 0; i < numberOfTasks; i++) {
            Task t = new Task(r.nextInt(), BATCH_SIZE, null, result);
            pool.execute(t);
        }

        try {
            pool.shutdown();
            pool.awaitTermination(30, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            int pointsInside = result.getResult();
            TaskResult taskResult = new TaskResult(task.id, pointsInside);

            this.state = State.IDLE;

            new Thread(() -> piControllerPrx.setTaskResult(taskResult)).start();

            System.out.println("Task report " + taskResult.pointsInside + " - task id " + taskResult.taskId);
        }

        this.state = State.IDLE;

        // TODO: getTask()
    }
}
