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



    class Task
    {
        string id;
        string jobId;
        long seed;
        int batchSize;
        string createDate;
        string state;
        string batchNumber;
        int result;
        short epsilonPower;
    }

    class TaskResult
    {
        string taskId;
        int pointsInside;
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

    interface Client
    {
        void setResult(Result result);
    }

    interface PiController
    {
        void calculatePi(PiRequest request, Client* clientProxy);
        Task getTask();
        void setTaskResult(TaskResult taskResult);
        void notifyPiResult(Job job);
    }

    interface Repository
    {
        Job createsJob(Job job);
        Task getTask();
        void setTaskResult(TaskResult taskResult);
    }

    interface TaskReport
    {
        void notifyTaskAvailable();
    }

}
