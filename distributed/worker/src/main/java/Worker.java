import Pi.PiControllerPrx;
import com.zeroc.Ice.Communicator;

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
                adapter.add(new TaskReportI(piControllerPrx), id);
                adapter.activate();


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
}