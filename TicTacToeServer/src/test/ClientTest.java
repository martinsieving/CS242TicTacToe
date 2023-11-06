package test;

import server.ServerHandler;
import java.net.*;

public class ClientTest
{
    public static void main(String[] args)
    {
        try
        {
            int port = 6500;
            Socket socket1 = new Socket("localhost", port);
            Socket socket2 = new Socket("localhost", port);
            ServerHandler client1 = new ServerHandler(socket1, "client1");
            ServerHandler client2 = new ServerHandler(socket2, "client2");
            client1.start();
            client2.start();
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
    }    
}
