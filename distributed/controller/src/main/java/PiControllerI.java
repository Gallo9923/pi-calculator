import Pi.*;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;

public class PiControllerI implements PiController {

    private TaskReportPrx taskReportPrx;

    public PiControllerI(TaskReportPrx taskReportPrx){
        this.taskReportPrx = taskReportPrx;
    }

    @Override
    public void calculatePi(PiRequest request, ClientPrx clientProxy, Current current) {
        System.out.println("calculatePi");

        // Guardar BD
        // TODO:

        // Notificar
        taskReportPrx.notifyTaskAvailable();
    }

    @Override
    public Task getTask(Current current) {

        // TODO: PASS JOB ID
        System.out.println("getTask");
        // TODO: retrieve task from DB
        return null;
    }

    @Override
    public void setTaskResult(TaskResult taskResult, Current current) {
        System.out.println("setTaskResult");
    }

    @Override
    public void notifyPiResult(Job job, Current current) {
        System.out.println("notifyPiResult");
    }

}
