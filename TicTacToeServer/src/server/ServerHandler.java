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
import model.User;
import model.Event.EventStatus;
import socket.GamingResponse;
import socket.PairingResponse;
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
    /**
     * logs important information
     */
    private final Logger logger;

    /**
     * stores eventId of event in DatabaseHelper when playing
     */
    private int currentEventId;

    /**
     * stores the socket connection
     */
    private final Socket socket;

    /**
     * Stores users username
     */
    private String currentUsername;

    /**
     * input stream for socket connection
     */
    private final DataInputStream input;

    /**
     * output stream for socket connection
     */
    private final DataOutputStream output;

    /**
     * handles serialization for io
     */
    private final Gson gson;

    /**
     * Constructor
     * @param socket sets the socket class variable
     * @throws IOException when retreiving io streams from socket
     */
    public ServerHandler(Socket socket) throws IOException
    {
        logger = Logger.getLogger(SocketServer.class.getName());
        this.socket = socket;
        currentUsername = null;
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
     * Closes io streams and the socket connection and sets the user to offline
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
        try
        {
            if(currentUsername != null)
            {
                User user = DatabaseHelper.getInstance().getUser(currentUsername);
                user.setOnline(false);
                DatabaseHelper.getInstance().updateUser(user);
                DatabaseHelper.getInstance().abortAllUserEvents(currentUsername);
            }
        }
        catch(SQLException e)
        {
            logger.log(Level.SEVERE, "User does not exist");
        }

    }

    /**
     * Handles message requests and returns the appropriate response
     * @param request proper Request base on the given Request
     * @return Response indicating the success of the action prompted by the submitted Request
     */
    public Response handleRequest(Request request)
    {
        switch(request.getType())
        {
            case SEND_MOVE:
                return handleSendMove(gson.fromJson(request.getData(), Integer.class));
            case REQUEST_MOVE:
                return handleRequestMove();
            case REGISTER:
                return handleRegister(gson.fromJson(request.getData(), User.class));
            case LOGIN:
                return handleLogin(gson.fromJson(request.getData(), User.class));
            case UPDATE_PAIRING:
                return handleUpdatePairing();
            case SEND_INVITATION:
                return handleSendInvitation(gson.fromJson(request.getData(), String.class));
            case ACCEPT_INVITATION:
                return handleAcceptInvitation(gson.fromJson(request.getData(), Integer.class));
            case DECLINE_INVITATION:
                return handleDeclineInvitation(gson.fromJson(request.getData(), Integer.class));
            case ACKNOWLEDGE_RESPONSE:
                return handleAcknowledgeResponse(gson.fromJson(request.getData(), Integer.class));
            case COMPLETE_GAME:
                return handleCompleteGame();
            case ABORT_GAME:
                return handleAbortGame();
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
                DatabaseHelper.getInstance().updateEvent(event);
			    return new Response(Response.ResponseStatus.SUCCESS, "Move Added");
		    }
            else
            {
			    return new Response(Response.ResponseStatus.FAILURE, "Not your turn to move");
		    }
        }
        catch(SQLException e)
        {
            logger.log(Level.SEVERE, "No event with this Event Id exists");
            return new Response(Response.ResponseStatus.FAILURE, "Game not found");
        }
    }

    /**
     * handles request of type REQUEST_MOVE
     * @return Response with status indicating the success of requesting a move
     */
    private GamingResponse handleRequestMove()
    {
        try
        {
            Event event = DatabaseHelper.getInstance().getEvent(currentEventId);
            GamingResponse response = new GamingResponse();
            if(event.getStatus() == EventStatus.ABORTED)
            {
                response.setActive(false);
                response.setMessage("Opponent Abort");

            }
            else if(event.getStatus() == EventStatus.COMPLETED)
            {
                response.setActive(false);
                response.setMessage("Opponent Deny Play Again");
            }
            else if(event.getMove() != -1 && !event.getTurn().equals(currentUsername))
            {
                response.setActive(true);
                response.setMove(event.getMove());
                event.setMove(-1);
                event.setTurn(null);
            }
            else
            {
                response.setActive(true);
                response.setMove(-1);
            }
            DatabaseHelper.getInstance().updateEvent(event);
            response.setStatus(ResponseStatus.SUCCESS);
            return response;
        }
        catch(SQLException e)
        {
            logger.log(Level.SEVERE, "No event with this Event Id exists");
            return new GamingResponse(ResponseStatus.FAILURE, currentUsername, -1, false);
        }
    }

    /**
     * handles requests of type REGISTER
     * @param user the user to be registered
     * @return Response with status indicating the success of registering the submitted user
     */
    private Response handleRegister(User user)
    {
        try
        {
            if(DatabaseHelper.getInstance().isUsernameExists(user.getUsername()))
            {
                return new Response(ResponseStatus.FAILURE, "User already exists");
            }
            DatabaseHelper.getInstance().createUser(user);
            return new Response(ResponseStatus.SUCCESS, "User has been added");
        }
        catch(SQLException e)
        {
            logger.log(Level.SEVERE, "Problem occured when registering user");
            return new Response(ResponseStatus.FAILURE, "Problem occured when registering user");
        }
    }

    /**
     * handles requests of type LOGIN
     * @param user the user information for login
     * @return  Response with status indicating a successful login
     */
    private Response handleLogin(User user)
    {
        try
        {
            User validUser = DatabaseHelper.getInstance().getUser(user.getUsername());
            if(validUser == null)
            {
                return new Response(ResponseStatus.FAILURE, "User does not exist");
            }
            if(!validUser.getPassword().equals(user.getPassword()))
            {
                return new Response(ResponseStatus.FAILURE, "Incorrect password");
            }
            currentUsername = user.getUsername();
            user.setOnline(true);
            DatabaseHelper.getInstance().updateUser(user);
            return new Response(ResponseStatus.SUCCESS, "Successful login");
        }
        catch(SQLException e)
        {
            logger.log(Level.SEVERE, "Problem occured during login");
            return new Response(ResponseStatus.FAILURE, "Problem occured during login");
        }
    }

    /**
     * handles requests of type UPDATE_PAIRING
     * @return Response with status indicating a successful pairing request
     */
    private PairingResponse handleUpdatePairing()
    {
        if(currentUsername == null)
        {
            return new PairingResponse(ResponseStatus.FAILURE, "User is not logged in", null, null, null);
        }
        try
        {
            User user = DatabaseHelper.getInstance().getUser(currentUsername);
            PairingResponse response = new PairingResponse();
            response.setAvailableUsers(DatabaseHelper.getInstance().getAvailableUsers(currentUsername));
            response.setInvitation(DatabaseHelper.getInstance().getUserInvitation(currentUsername));
            response.setInvitationResponse(DatabaseHelper.getInstance().getUserInvitationResponse(currentUsername));
            response.setMessage("Successfully requested pairing update");
            response.setStatus(ResponseStatus.SUCCESS);
            return response;
        }
        catch(SQLException e)
        {
            logger.log(Level.SEVERE, "Current user does not exist");
            return new PairingResponse(ResponseStatus.FAILURE, "User does not exist", null, null, null);
        }
    }

    /**
     * handles requests of type SEND_INVITATION
     * @param opponent username of the opponent to send an invitation to
     * @return Response with status indicating if the invitation was successfully sent
     */
    private Response handleSendInvitation(String opponent)
    {
        if(currentUsername == null)
        {
            return new PairingResponse(ResponseStatus.FAILURE, "User is not logged in", null, null, null);
        }
        try
        {
            if(!DatabaseHelper.getInstance().isUserAvailable(opponent))
            {
                return new Response(ResponseStatus.FAILURE, "User is not available");
            }
            Event event = new Event();
            event.setSender(currentUsername);
            event.setOpponent(opponent);
            event.setStatus(EventStatus.PENDING);
            event.setMove(-1);
            DatabaseHelper.getInstance().createEvent(event);
            return new Response(ResponseStatus.SUCCESS, "Created event");
        }
        catch(SQLException e)
        {
            logger.log(Level.SEVERE, "User does not exist");
            return new Response(ResponseStatus.FAILURE, "User does not exist");
        }
    }

    /**
     * handles requests of type ACCEPT_INVITATION
     * @param eventId eventId of the Event in the DatabaseHelper for the game being played
     * @return Response with status indicating if the invitation was successfully accepted
     */
    private Response handleAcceptInvitation(int eventId)
    {
        try
        {
            Event event = DatabaseHelper.getInstance().getEvent(eventId);
            if(event == null)
            {
                return new Response(ResponseStatus.FAILURE, "Request does not exist");
            }
            if(event.getStatus() != EventStatus.PENDING)
            {
                return new Response(ResponseStatus.FAILURE, "Request is not PENDING");
            }
            if(!event.getOpponent().equals(currentUsername))
            {
                return new Response(ResponseStatus.FAILURE, "Request is not for this user");
            }
            event.setStatus(EventStatus.ACCEPTED);
            DatabaseHelper.getInstance().abortAllUserEvents(currentUsername);
            DatabaseHelper.getInstance().updateEvent(event);
            currentEventId = eventId;
            return new Response(ResponseStatus.SUCCESS, "Accepted request");
        }
        catch(SQLException e)
        {
            logger.log(Level.SEVERE, "Event does not exist");
            return new Response(ResponseStatus.FAILURE, "Event does not exist");
        }
    }

    /**
     * handles requests of type DECLINE_INVITATION
     * @param eventId eventId of the Event in the DatabaseHelper for the game being played
     * @return Response with status indicating if the invitation was successfully declined
     */
    private Response handleDeclineInvitation(int eventId)
    {
        try
        {
            Event event = DatabaseHelper.getInstance().getEvent(eventId);
            if(event == null)
            {
                return new Response(ResponseStatus.FAILURE, "Request does not exist");
            }
            if(event.getStatus() != EventStatus.PENDING)
            {
                return new Response(ResponseStatus.FAILURE, "Request is not PENDING");
            }
            if(!event.getOpponent().equals(currentUsername))
            {
                return new Response(ResponseStatus.FAILURE, "Request is not for this user");
            }
            event.setStatus(EventStatus.DECLINED);
            DatabaseHelper.getInstance().updateEvent(event);
            return new Response(ResponseStatus.SUCCESS, "Declined request");
        }
        catch(SQLException e)
        {
            logger.log(Level.SEVERE, "Event does not exist");
            return new Response(ResponseStatus.FAILURE, "Event does not exist");
        }
    }

    /**
     * handles requests of type ACKNOWLEDGE_RESPONSE
     * @param eventId eventId of the Event in the DatabaseHelper for the game being played
     * @return Response with status indicating if a successful acknowledgement occured
     */
    private Response handleAcknowledgeResponse(int eventId)
    {
        try
        {
            Event event = DatabaseHelper.getInstance().getEvent(eventId);
            if(event == null)
            {
                return new Response(ResponseStatus.FAILURE, "Event does not exist");
            }
            if(!event.getSender().equals(currentUsername))
            {
                return new Response(ResponseStatus.FAILURE, "Not this users message");
            }
            switch(event.getStatus())
            {
                case DECLINED:
                    event.setStatus(EventStatus.ABORTED);
                    DatabaseHelper.getInstance().updateEvent(event);
                    return new Response(ResponseStatus.SUCCESS, "Acknowledged response");
                case ACCEPTED:
                    event.setStatus(EventStatus.PLAYING);
                    currentEventId = event.getEventId();
                    DatabaseHelper.getInstance().abortAllUserEvents(currentUsername);
                    DatabaseHelper.getInstance().updateEvent(event);
                    return new Response(ResponseStatus.SUCCESS, "Acknowledged response");
                default:
                    return new Response(ResponseStatus.FAILURE, "Do not need to acknowledge");
            }
        }
        catch(SQLException e)
        {
            logger.log(Level.SEVERE, "Event does not exist");
            return new Response(ResponseStatus.FAILURE, "Event does not exist");
        }
    }

    /**
     * handles requests of type COMPLETE_GAME
     * @return Response with status indicating if the game was successfully completed
     */
    private Response handleCompleteGame()
    {
        try
        {
            Event event = DatabaseHelper.getInstance().getEvent(currentEventId);
            if(event == null)
            {
                return new Response(ResponseStatus.FAILURE, "Event does not exist");
            }
            if(event.getStatus() != EventStatus.PLAYING)
            {
                return new Response(ResponseStatus.FAILURE, "Game is not being played");
            }
            event.setStatus(EventStatus.COMPLETED);
            currentEventId = -1;
            DatabaseHelper.getInstance().updateEvent(event);
            return new Response(ResponseStatus.SUCCESS, "Completed game");
        }
        catch(SQLException e)
        {
            logger.log(Level.SEVERE, "Event does not exist");
            return new Response(ResponseStatus.FAILURE, "Event does not exist");
        }
    }

    /**
     * handles requests of type ABORT_GAME
     * @return Response with status indicating if the game was successfully aborted
     */
    private Response handleAbortGame()
    {
        try
        {
            Event event = DatabaseHelper.getInstance().getEvent(currentEventId);
            if(event == null)
            {
                return new Response(ResponseStatus.FAILURE, "Event does not exist");
            }
            if(event.getStatus() != EventStatus.PLAYING)
            {
                return new Response(ResponseStatus.FAILURE, "Game is not being played");
            }
            event.setStatus(EventStatus.ABORTED);
            currentEventId = -1;
            DatabaseHelper.getInstance().updateEvent(event);
            return new Response(ResponseStatus.SUCCESS, "Aborted game");
        }
        catch(SQLException e)
        {
            logger.log(Level.SEVERE, "Event does not exist");
            return new Response(ResponseStatus.FAILURE, "Event does not exist");
        }
    }
}