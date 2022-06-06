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
        this.state = State.IDLE;
        this.piControllerPrx = piControllerPrx;
    }

    @Override
    public void notifyTaskAvailable(Current current) {

        if (this.state == State.WORKING){
            return;
        }

        this.state = State.WORKING;

        Pi.Task task = piControllerPrx.getTask();

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
            TaskResult taskResult = new TaskResult(task.jobId, pointsInside);

            piControllerPrx.setTaskResult(taskResult);
        }

        this.state = State.IDLE;

        // TODO: getTask()
    }
}
