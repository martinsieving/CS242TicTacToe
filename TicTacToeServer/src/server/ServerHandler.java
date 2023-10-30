package server;

/**
 * ServerHandler class is a subclass of the Thread class
 * It is responsible for helping the SocketServer class handle individual user communication
 * @author Alexander Odom
 * Clarkson University CS 242, October 2023
 */

import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.*;
import javax.net.ssl.*;

public class ServerHandler extends Thread
{
    private Socket socket;
    private String currentUsername;

    /**
     * Constructor
     * @param socket sets the socket class variable
     * @param currentUsername sets the currentUsername class variable
     */
    ServerHandler(Socket socket, String currentUsername)
    {
        this.socket = socket;
        this.currentUsername = currentUsername;
    }

    public void run()
    {

    }

    public void close()
    {
        
    }
}
