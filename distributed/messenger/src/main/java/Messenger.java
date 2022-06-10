public class Messenger {
    // 10 ^ 17 = 20m
    public static void main(String[] args)
    {
        java.util.List<String> extraArgs = new java.util.ArrayList<String>();

        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args, "config.messenger",extraArgs))
        {
            if(!extraArgs.isEmpty())
            {
                System.err.println("too many arguments");
                for(String v:extraArgs){
                    System.out.println(v);
                }
            }

            com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapter("Messenger");
            // String hostname = communicator.getProperties().getProperty("Ice.Default.Host");
            com.zeroc.Ice.ObjectPrx prx = adapter.add(new MessengerI(), com.zeroc.Ice.Util.stringToIdentity("messenger"));
            adapter.activate();

//            Demo. callback =  Demo.SubjectPrx.uncheckedCast(
//                    prx);
//            if(callback == null)
//            {
//                throw new Error("Invalid Callback proxy");
//            }
//
//            SubjectI.proxy=callback;
//
//            System.out.println("Server running...");
            communicator.waitForShutdown();
        }
    }

}