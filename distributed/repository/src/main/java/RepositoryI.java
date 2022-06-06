import Pi.Job;
import Pi.JobResult;
import Pi.TaskResult;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import table.JobTable;

public class RepositoryI implements Pi.Repository{

    private Communicator communicator;

    private String jobTableName;

    public RepositoryI(Communicator communicator){
        this.communicator = communicator;
        this.jobTableName = communicator.getProperties().getProperty("JobTableName");
    }

    @Override
    public Job createsJob(Job job, Current current) {
        JobTable jobTable = new JobTable(this.jobTableName);
        jobTable.create(job.nPower + "", job.seed + "", job.epsilonPower + "", job.startDate, job.finishDate, job.taskCounter, job.pointsInside, job.clientProxy);

        return null;
    }

    @Override
    public boolean verifyPendingTasks(Current current) {
        return false;
    }

    @Override
    public void addIntermediateResult(TaskResult taskResult, Current current) {

    }

    @Override
    public Job getJob(String jobId, Current current) {
        return null;
    }

    @Override
    public void setJobResult(JobResult job, Current current) {

    }
}
