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
        System.out.println("getTask");
        return null;
    }

    @Override
    public void notifyResult(TaskResult taskResult, Current current) {
        System.out.println("notifyResult");
    }
}
