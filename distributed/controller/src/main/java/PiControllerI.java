import Pi.*;
import com.zeroc.Ice.Current;

public class PiControllerI implements PiController {

    @Override
    public void calculatePi(PiRequest request, ClientPrx clientProxy, Current current) {
        System.out.println("calculatePi");
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
