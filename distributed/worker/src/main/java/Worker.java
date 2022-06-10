import Pi.PiControllerPrx;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;

public class Worker{

    public static void main(String[] args) {
        int status = 0;
        java.util.List<String> extraArgs = new java.util.ArrayList<String>();

        try(Communicator communicator = com.zeroc.Ice.Util.initialize(args, "config.worker", extraArgs))
        {

            Runtime.getRuntime().addShutdownHook(new Thread(() -> communicator.destroy()));

            if(!extraArgs.isEmpty())
            {
                System.err.println("too many arguments");
                status = 1;
            }
            else
            {
                PiControllerPrx piControllerPrx = null;
                do {
                    piControllerPrx = getPiControllerPrx(communicator);
                }while(piControllerPrx == null);

                com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapter("PiWorker");
                com.zeroc.Ice.Properties properties = communicator.getProperties();
                com.zeroc.Ice.Identity id = com.zeroc.Ice.Util.stringToIdentity(properties.getProperty("Identity"));
                ObjectPrx subscriber = adapter.add(new TaskReportI(piControllerPrx), id);
                adapter.activate();

                subscribeToIceStorm(communicator, subscriber);

                communicator.waitForShutdown();
            }
        }

        System.exit(status);
    }

    private static PiControllerPrx getPiControllerPrx(Communicator communicator){
        PiControllerPrx piControllerPrx = null;
        try {
            piControllerPrx = PiControllerPrx.checkedCast(communicator.stringToProxy("PiController")).ice_locatorCacheTimeout(0);
        }
        catch(com.zeroc.Ice.NotRegisteredException ex)
        {
            com.zeroc.IceGrid.QueryPrx query = com.zeroc.IceGrid.QueryPrx.checkedCast(communicator.stringToProxy("PiIceGrid/Query"));
            piControllerPrx = PiControllerPrx.checkedCast(query.findObjectByType("::Pi::PiController"));
        }
        if(piControllerPrx == null)
        {
            System.err.println("couldn't find a `::Pi::PiController' object");
        }
        return piControllerPrx;
    }


    private static void subscribeToIceStorm(Communicator communicator, com.zeroc.Ice.ObjectPrx subscriber){
        // Step 1: Get the topic manager of ICE Storm

        System.out.println(communicator.propertyToProxy("TopicManager.Proxy"));

        com.zeroc.IceStorm.TopicManagerPrx manager = com.zeroc.IceStorm.TopicManagerPrx.checkedCast(communicator.propertyToProxy("TopicManager.Proxy"));

        if(manager == null)
        {
            System.err.println("invalid proxy");

        }

        // Step 2: Get the topic proxy
        String topicName = communicator.getProperties().getProperty("TopicName");
        com.zeroc.IceStorm.TopicPrx topic = null;
        try
        {
            topic = manager.retrieve(topicName);
        }
        catch(com.zeroc.IceStorm.NoSuchTopic e)
        {
            try
            {
                topic = manager.create(topicName);
            }
            catch(com.zeroc.IceStorm.TopicExists ex)
            {
                System.err.println("temporary failure, try again.");
            }
        }

        // Step 3: Create the adapter that will be used to subscribe to the topic
        // Do nothing, the adapter is being passed by parameters

        // Step 4: Create the Quality of Service (QOS)
        java.util.Map<String, String> qos = new java.util.HashMap<>();
        qos.put("reliability", "ordered");

        // Step 5: Subscribe to the topic with the previously created adapter
        try
        {
            topic.subscribeAndGetPublisher(qos, subscriber);
        }
        catch(com.zeroc.IceStorm.AlreadySubscribed e)
        {
            System.out.println("reactivating persistent subscriber");
        }
        catch(com.zeroc.IceStorm.InvalidSubscriber e)
        {
            e.printStackTrace();
        }
        catch(com.zeroc.IceStorm.BadQoS e)
        {
            e.printStackTrace();
        }

        // Step 6: Configure Shutdown hook to unsubcribe from IceStorm
        final com.zeroc.IceStorm.TopicPrx topicF = topic;
        final com.zeroc.Ice.ObjectPrx subscriberF = subscriber;
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
        {
            try
            {
                topicF.unsubscribe(subscriberF);
            }
            finally
            {
                communicator.destroy();
            }
        }));
    }

}