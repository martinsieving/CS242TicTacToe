package test;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.User;
import server.DatabaseHelper;
import server.SocketServer;
import socket.*;
import socket.Request.RequestType;

/**
 * This class helps in creating socket client for testing purpose
 */
public class SocketClientHelper {

    /**
     * Used for printing server logs of different levels
     */
    private final Logger LOGGER;

    /**
     * Used for object serialization
     */
    private final Gson gson;

    /**
     * Socket connection with the server
     */
    private Socket socket;

    /**
     * Stream used to read (receive) server response to our request
     */
    private DataInputStream inputStream;

    /**
     * Stream used to write (send) our request to the server
     */
    private DataOutputStream outputStream;


    /**
     * A private constructor that instantiate the class and set attributes
     * Can be accessed only the within the class (for singleton design pattern)
     */
    public SocketClientHelper() {
        String HOSTNAME = "Your IP Address";
        int PORT = 5000;

        LOGGER = Logger.getLogger(SocketServer.class.getName());
        gson = new GsonBuilder().serializeNulls().create();

        try {
            socket = new Socket(InetAddress.getByName(HOSTNAME), PORT);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     *
     * @param request The request to be sent to the server
     * @param responseClass The class of response we expect from the server
     * @return Object of the responseClass received from the server
     * @param <T> {@link Response} class or one of its subclasses i.e., {@link GamingResponse} and {@link PairingResponse}
     */
    public <T> T sendRequest(Request request, Class<T> responseClass) {
        try {
            // Send Request
            String serializedRequest = gson.toJson(request);
            outputStream.writeUTF(serializedRequest);
            outputStream.flush();

            // Get Response
            String serializedResponse = inputStream.readUTF();
            return gson.fromJson(serializedResponse, responseClass);
        } catch (IOException e) {
            close();
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }

    /**
     * Closes the socket connection with the server and all IO Streams
     * Destruct the singleton instance
     */
    public void close() {
        try {
            if(socket != null) socket.close();
            if(inputStream != null) inputStream.close();
            if(outputStream != null) outputStream.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void pairingTest()
    {
        Thread mainThread = new Thread(() ->
        {
            try
            {
                DatabaseHelper.getInstance().truncateTables();;
            }
            catch(SQLException e)
            {
                throw new RuntimeException(e);
            }
            SocketServer.main(null);
        });
        mainThread.start();
        try
        {
            Thread.sleep(1000);
            Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

            User user1 = new User("player1", "admin", "Player 1", false);
            User user2 = new User("player2", "admin", "Player 2", false);
            User user3 = new User("player3", "admin", "Player 3", false);
            User user4 = new User("player4", "admin", "Player 4", false);
            
            SocketClientHelper sch1 = new SocketClientHelper();
            SocketClientHelper sch2 = new SocketClientHelper();
            SocketClientHelper sch3 = new SocketClientHelper();
            SocketClientHelper sch4 = new SocketClientHelper();

            Request request1 = new Request(RequestType.LOGIN, gson.toJson(user1));
            Request request2 = new Request(RequestType.LOGIN, gson.toJson(user2));
            Request request3 = new Request(RequestType.LOGIN, gson.toJson(user3));
            Request request4 = new Request(RequestType.LOGIN, gson.toJson(user4));

            Response response1 = sch1.sendRequest(request1, Response.class);
            Response response2 = sch2.sendRequest(request2, Response.class);
            Response response3 = sch3.sendRequest(request3, Response.class);
            Response response4 = sch4.sendRequest(request4, Response.class);

            System.out.println(gson.toJson(response1));
            System.out.println(gson.toJson(response2));
            System.out.println(gson.toJson(response3));
            System.out.println(gson.toJson(response4));
        }
        catch(InterruptedException e)
        {
            System.err.println("Problem waking thread from sleep");
        }
    }

}