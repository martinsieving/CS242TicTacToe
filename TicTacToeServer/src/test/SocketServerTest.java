package test;
import server.SocketServer;

/**
 * SocketServerTest class
 * Tests the getters and constructors for the SocketServer class
 * @author Alexander Odom
 * Clarkson University CS 242, October 2023
 */

public class SocketServerTest
{
    /**
     * default Constructor
     */
    public SocketServerTest()
    {

    }

    /** 
     * @return boolean
     * true if all getters and constructors for the SocketServer class work as intended
     * false otherwise
     */
    public boolean test()
    {
        int portTest = 6500;
        int defaultPort = 5000;

        try{
            SocketServer socketServer1 = new SocketServer();
            SocketServer socketServer2 = new SocketServer(portTest);

            boolean everythingWorks = true;

            if(socketServer1.getPort() != defaultPort)
            {
                System.out.println("Problem with SockerServer class PORT");
                everythingWorks = false;
            }

            if(socketServer2.getPort() != portTest)
            {
                System.out.println("Problem with SockerServer class PORT");
                everythingWorks = false;
            }

            return everythingWorks;
        }
        catch(Exception e)
        {
            return false;
        }
    }
}
