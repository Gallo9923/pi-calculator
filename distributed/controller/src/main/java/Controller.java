import Pi.MessengerPrx;
import Pi.PiControllerPrx;
import Pi.RepositoryPrx;
import Pi.TaskReportPrx;
import com.zeroc.Ice.Communicator;

public class Controller {
    public static void main(String[] args)
    {
        int status = 0;
        java.util.List<String> extraArgs = new java.util.ArrayList<String>();

        try(Communicator communicator = com.zeroc.Ice.Util.initialize(args, "config.controller", extraArgs))
        {

            Runtime.getRuntime().addShutdownHook(new Thread(() -> communicator.destroy()));

            if(!extraArgs.isEmpty())
            {
                System.err.println("too many arguments");
                status = 1;
            }
            else
            {

                MessengerPrx messengerPrx = MessengerPrx.checkedCast(communicator.stringToProxy("messenger:tcp -h hgrid2 -p 8015").ice_twoway().ice_secure(false));

                RepositoryPrx repositoryPrx = getRepositoryPrx(communicator);

                com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapter("PiController");
                com.zeroc.Ice.Properties properties = communicator.getProperties();
                com.zeroc.Ice.Identity id = com.zeroc.Ice.Util.stringToIdentity(properties.getProperty("Identity"));
                adapter.add(new PiControllerI(messengerPrx, repositoryPrx, communicator), id);
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
//                System.out.println("Retrieved topic TASK");
//            }
//            catch(com.zeroc.IceStorm.NoSuchTopic ex1)
//            {
//                try
//                {
//                    topic = topicManager.create("Tasks");
//                    System.out.println("Created topic TASK");
//                }
//                catch(com.zeroc.IceStorm.TopicExists ex2)
//                {
//                    System.out.println("Topic already created TASK");
//                    // Another client created the topic.
//                }
//            }
//        }
//
//        com.zeroc.Ice.ObjectPrx pub = topic.getPublisher().ice_twoway();
//        TaskReportPrx publisher = TaskReportPrx.uncheckedCast(pub);
//        return publisher;
//    }

    private static RepositoryPrx getRepositoryPrx(com.zeroc.Ice.Communicator communicator){
        RepositoryPrx repositoryPrx = null;
        try {
            repositoryPrx = RepositoryPrx.checkedCast(communicator.stringToProxy("Repository"));
        }
        catch(com.zeroc.Ice.NotRegisteredException ex)
        {
            com.zeroc.IceGrid.QueryPrx query = com.zeroc.IceGrid.QueryPrx.checkedCast(communicator.stringToProxy("PiIceGrid/Query"));
            repositoryPrx = RepositoryPrx.checkedCast(query.findObjectByType("::Pi::Repository"));
        }
        if(repositoryPrx == null)
        {
            System.err.println("couldn't find a `::Pi::PiRepository' object");
        }
        return repositoryPrx;
    }

}
