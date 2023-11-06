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
import java.io.EOFException;
import java.io.IOException;
import java.net.*;
import javax.net.ssl.*;
import javax.sound.sampled.AudioFileFormat.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.Event;
import socket.GamingResponse;
import socket.Request;
import socket.Response;
import socket.Response.ResponseStatus;

public class ServerHandler extends Thread
{
    private static Logger logger = Logger.getLogger(SocketServer.class.getName());

    private static Event event;
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
            event = new Event(0, null, null, null, null, -1);
        }
        catch(Exception e)
        {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Continuosly read requests and sends responses until the client disconnects or sends an invalid request
     */
    public void run()
    {
        while(true)
        {
            try
            {
                Request request = gson.fromJson(input.readUTF(), Request.class);
                Response response = handleRequest(request);
                output.writeUTF(gson.toJson(response, Response.class));
            }
            catch(EOFException eofe)
            {
                break;
            }
            catch(IOException ioe)
            {
                System.err.println("IOException occured");
            }
        }
        close();
    }

    /**
     * Closes io streams and the socket connection
     */
    public void close()
    {
        try
        {
            input.close();
            logger.log(Level.INFO, "ServerHandler input stream closed");
        }
        catch(IOException ioe)
        {
            System.err.println("Problem encountered when closing input stream");
        }
        try
        {
            output.close();
            logger.log(Level.INFO, "ServerHandler output stream closed");
        }
        catch(IOException ioe)
        {
            System.err.println("Problem encountered when closing output stream");
        }
        try
        {
            socket.close();
            logger.log(Level.INFO, "ServerHandler socket closed");
        }
        catch(IOException ioe)
        {
            System.err.println("Problem encountered when closing socket");
        }
    }

    /**
     * Handles message requests
     * @param request
     * @return
     */
    public Response handleRequest(Request request)
    {
        switch(request.getType())
        {
            case SEND_MOVE:
                int move = gson.fromJson(request.getData(), int.class);
                return handleSendMove(move);
            case REQUEST_MOVE:
                GamingResponse response = handleRequestMove();
                return response;
            default:
                return new Response(ResponseStatus.FAILURE, request.getData());
        }
    }

    /**
     * handles request of type SEND_MOVE
     * Updates the move and turn class variable's in event and returns a SUCCESS Response if the last turn was not taken by this user
     * otherwise returns FAILURE Response
     * @param int move
     */
    private Response handleSendMove(int move)
    {
        if(event.getTurn().equals(currentUsername))
        {
            logger.log(Level.WARNING, "It is not " + currentUsername + "'s turn");
            return new Response(ResponseStatus.FAILURE, "It is not " + currentUsername + "'s turn");
        }
        event.setMove(move);
        event.setTurn(currentUsername);
        logger.log(Level.INFO, currentUsername + " performed move " + Integer.toString(move));
        return new Response(ResponseStatus.SUCCESS, "move: " + Integer.toString(move) + ", turn: ");
    }

    /**
     * handles request of type REQUEST_MOVE
     * @param request
     * @return
     */
    private GamingResponse handleRequestMove()
    {
        logger.log(Level.INFO, currentUsername + " is requesting a move");
        return new GamingResponse(ResponseStatus.SUCCESS, currentUsername + " is requesting a move", event.getMove(), true); 
    }

}
