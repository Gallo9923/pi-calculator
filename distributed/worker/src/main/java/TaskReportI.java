import Pi.PiControllerPrx;
import Pi.Task;
import com.zeroc.Ice.Current;
import model.Solver;

public class TaskReportI implements Pi.TaskReport {

    private PiControllerPrx piControllerPrx;
    private State state;

    public TaskReportI(PiControllerPrx piControllerPrx){
        this.state = State.IDLE;
        this.piControllerPrx = piControllerPrx;
    }

    @Override
    public void notifyTaskAvailable(Current current) {

        if (this.state == State.WORKING){
            return;
        }

        Task task = piControllerPrx.getTask();


        Solver solver = new Solver(task.epsilonPower, task.seed, task.batchPoints);

    }
}
