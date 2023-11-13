package server;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * SocketServer class
 * This class is responsible for creating the socket server and accepts all client connections
 * @author Alexander Odom
 * Clarkson University CS 242, October 2023
 */

public class SocketServer
{
    /**
     * Responsible for logging important information
     */
    private final  Logger logger;
    /**
     * PORT used for socket connection
     */
    private final int PORT;
    /**
     * ServerSocket that clients connect to
     */
    private ServerSocket serverSocket;

    /**
     * main method that instantiates this class
     */
    public static void main(String[] args)
    {
        try
        {
            SocketServer socketServer = new SocketServer();
            socketServer.setup();
            socketServer.startAcceptingRequest();
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Constructor that sets the PORT to a default value of 5000
     */
    public SocketServer() throws Exception
    {
        this(5000);
    }

    /**
     * Constructor
     * @param PORT sets the PORT to the entered int value
     */
    public SocketServer(int PORT) throws Exception
    {
        if(PORT < 0)
        {
            throw new Exception("Invalid PORT");
        }
        this.PORT = PORT;
        logger = Logger.getLogger(SocketServer.class.getName());
    }

    /**
     * setup initializes the serverSocket class variable
     */
    public void setup()
    {
        try
        {
            serverSocket = new ServerSocket(PORT);
            logger.log(Level.INFO, "Server Initialization Succeeded"
					+ "\nServer Host Name: " + InetAddress.getLocalHost().getHostName()
					+ "\nServer IP: " + InetAddress.getLocalHost().getHostAddress()
					+ "\nServer Port Number: " + PORT);
        }
        catch (UnknownHostException e)
        {
			logger.log(Level.SEVERE,"Unable to Resolve Host", e);
			System.exit(1);
		}
        catch (IOException e)
        {
			logger.log(Level.SEVERE,"Server Initialization Failed", e);
			System.exit(1);
		}
        catch (Exception e)
        {
			logger.log(Level.SEVERE,"Unknown Exception Occurred", e);
			System.exit(1);
		}
    }

    /**
     * setup the two client ServerHandler threads
     */
    public void startAcceptingRequest()
    {
        try
        {
            Socket socketPlayer1 = serverSocket.accept();
            logger.log(Level.INFO,"New Socket Client Connect with IP: " + socketPlayer1.getRemoteSocketAddress());
            ServerHandler serverHandlerPlayer1 = new ServerHandler(socketPlayer1, "player1");
            serverHandlerPlayer1.start();

            Socket socketPlayer2 = serverSocket.accept();
            logger.log(Level.INFO,"New Socket Client Connect with IP: " + socketPlayer2.getRemoteSocketAddress());
            ServerHandler serverHandlerPlayer2 = new ServerHandler(socketPlayer2, "player2");
            serverHandlerPlayer2.start();
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
