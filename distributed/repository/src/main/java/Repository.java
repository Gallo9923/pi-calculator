import Pi.MessengerPrx;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Properties;
import connection.PostgresqlConnection;

public class Repository {

    public static void main(String[] args)
    {
        int status = 0;
        java.util.List<String> extraArgs = new java.util.ArrayList<String>();

        try(Communicator communicator = com.zeroc.Ice.Util.initialize(args, "config.repository", extraArgs))
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

                MessengerPrx messengerPrx = MessengerPrx.checkedCast(communicator.stringToProxy("messenger:tcp -h hgrid2 -p 8015").ice_twoway().ice_secure(false));

                com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapter("PiRepository");
                com.zeroc.Ice.Properties properties = communicator.getProperties();
                com.zeroc.Ice.Identity id = com.zeroc.Ice.Util.stringToIdentity(properties.getProperty("Identity"));
                adapter.add(new RepositoryI(communicator, messengerPrx), id);
                adapter.activate();

                communicator.waitForShutdown();
            }
        }

        System.exit(status);
    }

//    private static TaskReportPrx getPublisher(Communicator communicator){
//        com.zeroc.Ice.ObjectPrx obj = communicator.stringToProxy("PiIceStorm/TopicManager:tcp -h hgrid2 -p 8091");
//        com.zeroc.IceStorm.TopicManagerPrx topicManager = com.zeroc.IceStorm.TopicManagerPrx.checkedCast(obj);
//        com.zeroc.IceStorm.TopicPrx topic = null;
//        while(topic == null)
//        {
//            try
//            {
//                topic = topicManager.retrieve("Tasks");
//            }
//            catch(com.zeroc.IceStorm.NoSuchTopic ex1)
//            {
//                try
//                {
//                    topic = topicManager.create("Tasks");
//                }
//                catch(com.zeroc.IceStorm.TopicExists ex2)
//                {
//                    // Another client created the topic.
//                }
//            }
//        }
//
//        com.zeroc.Ice.ObjectPrx pub = topic.getPublisher().ice_twoway();
//        TaskReportPrx publisher = TaskReportPrx.uncheckedCast(pub);
//        return publisher;
//    }
}