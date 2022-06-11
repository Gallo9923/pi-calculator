package threads;

import Enums.TaskState;
import Pi.MessengerPrx;
import Pi.Task;
import Pi.TaskReportPrx;
import table.TaskTable;

import java.util.Random;

public class Checker implements Runnable {

    private TaskTable taskTable;
    private long millisTimeout;
    private String taskId;
    private MessengerPrx messengerPrx;

    public Checker(TaskTable taskTable, long millisTimeout, String taskId, MessengerPrx messengerPrx){
        this.taskTable = taskTable;
        this.millisTimeout = millisTimeout;
        this.taskId = taskId;
        this.messengerPrx = messengerPrx;
    }

    @Override
    public void run() {

        try {
            System.out.println("Started Checker");
            Thread.sleep(millisTimeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // TODO: Semaforo
            Task task = taskTable.getTaskById(this.taskId);
            System.out.println("Checking task state ended " + task.id);
            if (task.state.equals(TaskState.IN_PROGRESS.toString())){
                System.out.println("Task didnt ended " + task.id);
                taskTable.setTaskState(taskId, TaskState.PENDING.toString());
                messengerPrx.publish(task.jobId);
            }
        }
    }
}
