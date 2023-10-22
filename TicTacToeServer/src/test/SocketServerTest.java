package test;
import server.SocketServer;

public class SocketServerTest
{
    public SocketServerTest()
    {

    }

    
    /** 
     * @return boolean
     */
    public boolean test()
    {
        int portTest = 6500;
        int defaultPort = 5000;

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
}
