package test;

import server.SocketServer;

public class ServerTest
{
    public static void main(String[] args)
    {
        int port = 6500;
        try
        {
            SocketServer server = new SocketServer(port);
            server.setup();
            server.startAcceptingRequest();
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
    }    
}
