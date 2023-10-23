package server;

/**
 * SocketServer class
 * This class is responsible for creating the socket server and accepts all client connections
 * @author Alexander Odom
 * Clarkson University CS 242, October 2023
 */

public class SocketServer
{
    private int PORT;

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
        this.PORT = 5000;
    }

    /**
     * Constructor
     * @param PORT sets the PORT to the entered int value
     */
    public SocketServer(int PORT)
    {
        this.PORT = PORT;
    }

    public static void setup()
    {

    }

    public static void startAcceptingRequest()
    {

    }
    
    /** 
     * @return PORT class variable, represented as an int
     */
    public int getPort()
    {
        return PORT;
    }
}
