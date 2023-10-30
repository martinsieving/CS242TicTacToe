package server;

/**
 * ServerHandler class is a subclass of the Thread class
 * It is responsible for helping the SocketServer class handle individual user communication
 * @author Alexander Odom
 * Clarkson University CS 242, October 2023
 */

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;
import javax.net.ssl.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServerHandler extends Thread
{
    private static Logger logger = Logger.getLogger(SocketServer.class.getName());

    private Socket socket;
    private String currentUsername;
    private DataInputStream input;
    private DataOutputStream output;
    private Gson gson;

    /**
     * Constructor
     * @param socket sets the socket class variable
     * @param currentUsername sets the currentUsername class variable
     */
    ServerHandler(Socket socket, String currentUsername)
    {
        this.socket = socket;
        this.currentUsername = currentUsername;
        try
        {
            this.input = new DataInputStream(socket.getInputStream());
            this.output = new DataOutputStream(socket.getOutputStream());
            this.gson = new GsonBuilder().serializeNulls().create();
        }
        catch(Exception e)
        {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    public void run()
    {

    }

    public void close()
    {
        
    }
}
