module Pi {

    class PiRequest 
    {
        short nPower;
        long seed;
        short epsilonPower;
    } 

    class Time
    {
        string milliseconds;
        string seconds;
        string minutes;
    }

    class Result
    {
        string pi;
        double repNumbers;
        Time totalTime;
    }

    interface Client
    {
        void setResult(Result result);
    }

    class Task
    {
        string jobId;
        int batchPoints;
        long seed;
    }

    class TaskResult
    {
        string jobId;
        int pointsInside;
    }

    interface PiController
    {
        void calculatePi(PiRequest request, Client* clientProxy);
        Task getTask();
        void notifyResult(TaskResult taskResult);
    }

    class Job
    {
        string id;
        short nPower;
        long seed;
        double repNumbers;
        short epsilonPower;
        string startDate;
        string finishDate;
        string taskCounter;
        string pointsInside;
        string clientProxy;
    }

    class JobResult
    {
        string jobId;
        string finishDate;
        double repNumbers;
        string pi;
    }

    interface Repository
    {
        Job createsJob(Job job);
        bool verifyPendingTasks();
        void addIntermediateResult(TaskResult taskResult);
        Job getJob(string jobId);
        void setJobResult(JobResult job);
    }

    interface TaskReport
    {
        void notifyTaskAvailable();
    }

    interface Worker
    {
    }

}
