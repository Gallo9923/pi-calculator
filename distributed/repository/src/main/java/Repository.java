import Pi.TaskReportPrx;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Properties;
import connection.PostgresqlConnection;

public class Repository {

    public static void main(String[] args)
    {
        int status = 0;
        java.util.List<String> extraArgs = new java.util.ArrayList<String>();

        try(Communicator communicator = com.zeroc.Ice.Util.initialize(args, extraArgs))
        {

            Runtime.getRuntime().addShutdownHook(new Thread(() -> communicator.destroy()));

            if(!extraArgs.isEmpty())
            {
                System.err.println("too many arguments");
                status = 1;
            }
            else
            {
                // Configure DB Connection String
                Properties p = communicator.getProperties();
                String connectionString = p.getProperty("ConexionBD");
                String user = p.getProperty("BDuser");
                String password = p.getProperty("BDpassword");
                PostgresqlConnection.configure(connectionString, user, password);

                TaskReportPrx taskReportPrx = getPublisher(communicator);

                com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapter("Repository");
                com.zeroc.Ice.Properties properties = communicator.getProperties();
                com.zeroc.Ice.Identity id = com.zeroc.Ice.Util.stringToIdentity(properties.getProperty("Identity"));
                adapter.add(new RepositoryI(communicator, taskReportPrx), id);
                adapter.activate();

                communicator.waitForShutdown();
            }
        }

        System.exit(status);
    }

    private static TaskReportPrx getPublisher(Communicator communicator){
        com.zeroc.Ice.ObjectPrx obj = communicator.stringToProxy("IceStorm/TopicManager:tcp -p 9999");
        com.zeroc.IceStorm.TopicManagerPrx topicManager = com.zeroc.IceStorm.TopicManagerPrx.checkedCast(obj);
        com.zeroc.IceStorm.TopicPrx topic = null;
        while(topic == null)
        {
            try
            {
                topic = topicManager.retrieve("Tasks");
            }
            catch(com.zeroc.IceStorm.NoSuchTopic ex1)
            {
                try
                {
                    topic = topicManager.create("Tasks");
                }
                catch(com.zeroc.IceStorm.TopicExists ex2)
                {
                    // Another client created the topic.
                }
            }
        }

        com.zeroc.Ice.ObjectPrx pub = topic.getPublisher().ice_oneway();
        TaskReportPrx publisher = TaskReportPrx.uncheckedCast(pub);
        return publisher;
    }
}