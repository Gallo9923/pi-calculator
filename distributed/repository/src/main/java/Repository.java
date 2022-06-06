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
                PostgresqlConnection.configure(connectionString);

                com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapter("Repository");
                com.zeroc.Ice.Properties properties = communicator.getProperties();
                com.zeroc.Ice.Identity id = com.zeroc.Ice.Util.stringToIdentity(properties.getProperty("Identity"));
                adapter.add(new RepositoryI(communicator), id);
                adapter.activate();

                communicator.waitForShutdown();
            }
        }

        System.exit(status);
    }
}