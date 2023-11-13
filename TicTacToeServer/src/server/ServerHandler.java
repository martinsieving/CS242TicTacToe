package server;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

import javax.sound.sampled.AudioFileFormat.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import model.Event;
import socket.GamingResponse;
import socket.Request;
import socket.Response;
import socket.Response.ResponseStatus;

/**
 * ServerHandler class is a subclass of the Thread class
 * It is responsible for helping the SocketServer class handle individual user communication
 * @author Alexander Odom
 * Clarkson University CS 242, October 2023
 */

public class ServerHandler extends Thread
{
    private final Logger logger;

    private int currentEventId;
    private final Socket socket;
    private String currentUsername;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final Gson gson;

    /**
     * Constructor
     * @param socket sets the socket class variable
     * @param currentUsername sets the currentUsername class variable
     */
    public ServerHandler(Socket socket) throws IOException
    {
        logger = Logger.getLogger(SocketServer.class.getName());
        this.socket = socket;
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
        this.gson = new GsonBuilder().serializeNulls().create();
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
                String requestMessage = input.readUTF();
                logger.log(Level.INFO, "Request recieved: " + requestMessage);
                Request request = gson.fromJson(requestMessage, Request.class);

                Response response = handleRequest(request);
                String responseMessage = gson.toJson(response);
                logger.log(Level.INFO, "Sending response: " + responseMessage);
                output.writeUTF(responseMessage);
                output.flush();
            }
            catch(EOFException eofe)
            {
                logger.log(Level.INFO, "Client Disconnected: " + currentUsername + " - " + socket.getRemoteSocketAddress());
                break;
            }
            catch(IOException ioe)
            {
                logger.log(Level.SEVERE, "Client Connection Failed");
            }
            catch(JsonSyntaxException e)
            {
                logger.log(Level.SEVERE, "Serialization Error");
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
                return handleSendMove(gson.fromJson(request.getData(), Integer.class));
            case REQUEST_MOVE:
                return handleRequestMove();
            default:
                return new Response(ResponseStatus.FAILURE, "Invalid Requenst: " + request.getData());
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
        try
        {
            Event event = DatabaseHelper.getInstance().getEvent(currentEventId);
            if(move < 0 || move > 8)
            {
			    return new Response(Response.ResponseStatus.FAILURE, "Invalid Move");
		    }
		    if(event.getTurn() == null || !event.getTurn().equals(currentUsername))
            {
			    event.setMove(move);
			    event.setTurn(currentUsername);
			    return new Response(Response.ResponseStatus.SUCCESS, "Move Added");
		    }
            else
            {
			    return new Response(Response.ResponseStatus.FAILURE, "Not your turn to move");
		    }
            DatabaseHelper.getInstance().updateEvent(event);
        }
        catch(SQLException e)
        {
            logger.log(Level.SEVERE, "No event with this Event Id exists");
            return new Response(Response.ResponseStatus.FAILURE, "Game not found");
        }
    }

    /**
     * handles request of type REQUEST_MOVE
     * @param request
     * @return
     */
    private GamingResponse handleRequestMove()
    {
        try
        {
            Event event = DatabaseHelper.getInstance().getEvent(currentEventId);
            GamingResponse response = new GamingResponse();
            response.setStatus(ResponseStatus.SUCCESS);
            if(event.getMove() != -1 && !event.getTurn().equals(currentUsername))
            {
                response.setMove(event.getMove());
                event.setMove(-1);
                event.setTurn(null);
            }
            else
            {
                response.setMove(-1);
            }
            DatabaseHelper.getInstance().updateEvent(event);
            return response;
        }
        catch(SQLException e)
        {
            logger.log(Level.SEVERE, "No event with this Event Id exists");
            return new GamingResponse(ResponseStatus.FAILURE, currentUsername, -1, true);
        }
    }

}
