import Pi.*;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.Properties;

import java.time.LocalDateTime;

public class PiControllerI implements PiController {

    private TaskReportPrx taskReportPrx;
    private RepositoryPrx repositoryPrx;
    private Communicator communicator;

    public PiControllerI(TaskReportPrx taskReportPrx, RepositoryPrx repositoryPrx, Communicator communicator){
        this.taskReportPrx = taskReportPrx;
        this.repositoryPrx = repositoryPrx;
        this.communicator = communicator;
    }

    @Override
    public void calculatePi(PiRequest request, ClientPrx clientProxy, Current current) {
        System.out.println("calculatePi");

        Properties p = communicator.getProperties();

        int jobBatchSize;

        if (request.nPower > 6) {
            jobBatchSize = Integer.parseInt(p.getProperty("BATCH_SIZE1"));
        } else {
            jobBatchSize = Integer.parseInt(p.getProperty("BATCH_SIZE2"));
        }

        Job job = new Job("", request.nPower, request.seed, 0.0, request.epsilonPower, LocalDateTime.now().toString(), "", "0", "0", clientProxy.toString(), jobBatchSize, "0");

        // Guardar BD
        Job currentJob = repositoryPrx.createsJob(job);

        // Notificar
        taskReportPrx.notifyTaskAvailable(currentJob.id);
    }

    @Override
    public Task getTask(String jobId, Current current) {

        System.out.println("getTask");
        return repositoryPrx.getTask(jobId);
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
