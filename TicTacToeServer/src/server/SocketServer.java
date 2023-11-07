package server;

/**
 * SocketServer class
 * This class is responsible for creating the socket server and accepts all client connections
 * @author Alexander Odom
 * Clarkson University CS 242, October 2023
 */

import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.*;
//import javax.net.ssl.*;


public class SocketServer
{
    /**
     * Responsible for logging important information
     */
    private static Logger logger = Logger.getLogger(SocketServer.class.getName());
    /**
     * PORT used for socket connection
     */
    private static int PORT = 5000;
    /**
     * ServerSocket that clients connect to
     */
    private static ServerSocket serverSocket;

    /**
     * main method that instantiates this class
     */
    public static void main()
    {
        setup();
        startAcceptingRequest();
    }

    /**
     * Constructor that sets the PORT to a default value of 5000
     */
    public SocketServer()
    {
        
    }

    /**
     * Constructor
     * @param PORT sets the PORT to the entered int value
     */
    public SocketServer(int PORT_) throws Exception
    {
        if(PORT_ < 0)
        {
            throw new Exception("Invalid PORT");
        }
        PORT = PORT_;
    }

    /**
     * setup initializes the serverSocket class variable
     */
    public static void setup()
    {
        try
        {
            serverSocket = new ServerSocket(PORT);
        }
        catch(Exception e)
        {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * setup the two client ServerHandler threads
     */
    public static void startAcceptingRequest()
    {
        try
        {
            ServerHandler clientOne = new ServerHandler(serverSocket.accept(), "player1");
            ServerHandler clientTwo = new ServerHandler(serverSocket.accept(), "player2");
            clientOne.start();
            clientTwo.start();
        }
        catch(Exception e)
        {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
    
    /** 
     * @return PORT class variable, represented as an int
     */
    public int getPort()
    {
        return PORT;
    }
}
