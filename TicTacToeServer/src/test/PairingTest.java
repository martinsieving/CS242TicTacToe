package test;

import java.sql.SQLException;

import model.User;
import server.DatabaseHelper;
import server.SocketServer;
import socket.PairingResponse;
import socket.Request;
import socket.Request.RequestType;
import socket.Response.ResponseStatus;
import socket.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * PairingTest class
 * tests functions of the ServerHandler class's pairing cabalities
 * @author Alexander Odom
 * Clarkson University CS 242, November 2023
 */
public class PairingTest {
    /**
     * Default constructor
     */
    public PairingTest()
    {
        
    }

    /**
     * Tests all message requests for the ServerHandler class
     * @return true if all tests pass, false otherwise
     */
    public boolean test()
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

            if(sch1.sendRequest(new Request(RequestType.LOGIN, gson.toJson(user1)), Response.class).getStatus() != ResponseStatus.FAILURE)
            {
                System.out.println("Problem when logging in with non-registered user");
                return false;
            }

            if(sch1.sendRequest(new Request(RequestType.REGISTER, gson.toJson(user1)), Response.class).getStatus() != ResponseStatus.SUCCESS)
            {
                System.out.println("Problem when registering a non-registered user");
                return false;
            }

            user1.setPassword("not admin");
            if(sch1.sendRequest(new Request(RequestType.LOGIN, gson.toJson(user1)), Response.class).getStatus() != ResponseStatus.FAILURE)
            {
                System.out.println("Problem when logging in with incorrect password");
                return false;
            }
            user1.setPassword("admin");

            if(sch1.sendRequest(new Request(RequestType.LOGIN, gson.toJson(user1)), Response.class).getStatus() != ResponseStatus.SUCCESS)
            {
                System.out.println("Problem when logging in with proper login credentials for registered user");
                return false;
            }

            sch1.sendRequest(new Request(RequestType.REGISTER, gson.toJson(user2)), Response.class);
            sch1.sendRequest(new Request(RequestType.REGISTER, gson.toJson(user3)), Response.class);
            sch1.sendRequest(new Request(RequestType.REGISTER, gson.toJson(user4)), Response.class);

            if(!sch1.sendRequest(new Request(RequestType.UPDATE_PAIRING, gson.toJson(user1)), PairingResponse.class).getAvailableUsers().isEmpty())
            {
                System.out.println("Problem when requesting update pairing with no users");
                return false;
            }

            if(sch2.sendRequest(new Request(RequestType.UPDATE_PAIRING, gson.toJson(user2)), PairingResponse.class).getStatus() != ResponseStatus.FAILURE)
            {
                System.out.println("Problem when requesting update pairing without logging in");
                return false;
            }

            sch2.sendRequest(new Request(RequestType.LOGIN, gson.toJson(user2)), Response.class);

            if(sch2.sendRequest(new Request(RequestType.UPDATE_PAIRING, gson.toJson(user2)), PairingResponse.class).getAvailableUsers().size() != 1)
            {
                System.out.println("Problem when requesting update pairing with 1 additional user");
                return false;
            }

            sch3.sendRequest(new Request(RequestType.LOGIN, gson.toJson(user3)), Response.class);
            sch4.sendRequest(new Request(RequestType.LOGIN, gson.toJson(user4)), Response.class);

            if(sch2.sendRequest(new Request(RequestType.UPDATE_PAIRING, gson.toJson(user2)), PairingResponse.class).getAvailableUsers().size() != 3)
            {
                System.out.println("Problem when requesting update pairing with 3 additional users");
                return false;
            }
            
            sch4.close();
            Thread.sleep(10);

            if(sch2.sendRequest(new Request(RequestType.UPDATE_PAIRING, gson.toJson(user2)), PairingResponse.class).getAvailableUsers().size() != 2)
            {
                System.out.println("Problem when requesting update pairing with 2 additional users");
                return false;
            }
            
            sch4 = new SocketClientHelper();
            sch4.sendRequest(new Request(RequestType.LOGIN, gson.toJson(user4)), Response.class);

            if(sch2.sendRequest(new Request(RequestType.UPDATE_PAIRING, gson.toJson(user2)), PairingResponse.class).getAvailableUsers().size() != 3)
            {
                System.out.println("Problem when requesting update pairing with 3 additional users after Player 4 logged out and back in");
                return false;
            }

            if(sch1.sendRequest(new Request(RequestType.SEND_INVITATION, gson.toJson(user2.getUsername())), Response.class).getStatus() != ResponseStatus.SUCCESS)
            {
                System.out.println("Problem sending invitation");
                return false;
            }

            if(sch2.sendRequest(new Request(RequestType.UPDATE_PAIRING, gson.toJson(user2)), PairingResponse.class).getInvitation() == null)
            {
                System.out.println("Problem recieving invitation");
                return false;
            }

            if(sch2.sendRequest(new Request(RequestType.DECLINE_INVITATION, gson.toJson(sch2.sendRequest(new Request(RequestType.UPDATE_PAIRING, gson.toJson(user2)), PairingResponse.class).getInvitation().getEventId())), Response.class).getStatus() != ResponseStatus.SUCCESS)
            {
                System.out.println("Problem rejecting invitation");
                return false;
            }

            if(sch1.sendRequest(new Request(RequestType.UPDATE_PAIRING, gson.toJson(user1)), PairingResponse.class).getInvitationResponse() == null)
            {
                System.out.println("Problem recieving rejected invitation");
                return false;
            }

            if(sch1.sendRequest(new Request(RequestType.ACKNOWLEDGE_RESPONSE, gson.toJson(sch1.sendRequest(new Request(RequestType.UPDATE_PAIRING, gson.toJson(user1)), PairingResponse.class).getInvitationResponse().getEventId())), Response.class).getStatus() != ResponseStatus.SUCCESS)
            {
                System.out.println("Problem acknowledging rejected invitation");
                return false;
            }

            if(sch1.sendRequest(new Request(RequestType.SEND_INVITATION, gson.toJson(user3.getUsername())), Response.class).getStatus() != ResponseStatus.SUCCESS)
            {
                System.out.println("Problem sending invitation");
                return false;
            }

            if(sch3.sendRequest(new Request(RequestType.ACCEPT_INVITATION, gson.toJson(sch3.sendRequest(new Request(RequestType.UPDATE_PAIRING, gson.toJson(user3)), PairingResponse.class).getInvitation().getEventId())), Response.class).getStatus() != ResponseStatus.SUCCESS)
            {
                System.out.println("Problem accepting invitation");
                return false;
            }

            if(sch1.sendRequest(new Request(RequestType.UPDATE_PAIRING, gson.toJson(user1)), PairingResponse.class).getInvitationResponse() == null)
            {
                System.out.println("Problem recieving accepted invitation");
                return false;
            }

            if(sch1.sendRequest(new Request(RequestType.ACKNOWLEDGE_RESPONSE, gson.toJson(sch1.sendRequest(new Request(RequestType.UPDATE_PAIRING, gson.toJson(user1)), PairingResponse.class).getInvitationResponse().getEventId())), Response.class).getStatus() != ResponseStatus.SUCCESS)
            {
                System.out.println("Problem acknowledging accepted invitation");
                return false;
            }

            if(sch2.sendRequest(new Request(RequestType.UPDATE_PAIRING, gson.toJson(user2)), PairingResponse.class).getAvailableUsers().size() != 1)
            {
                System.out.println("Problem when requesting update pairing while 2 users are in a game");
                return false;
            }

            if(sch1.sendRequest(new Request(RequestType.ABORT_GAME, null), Response.class).getStatus() != ResponseStatus.SUCCESS)
            {
                System.out.println("Problem aborting game");
                return false;
            }

            if(sch2.sendRequest(new Request(RequestType.UPDATE_PAIRING, gson.toJson(user2)), PairingResponse.class).getAvailableUsers().size() != 3)
            {
                System.out.println("Problem when requesting update pairing after 2 users ended a game");
                return false;
            }

            return true;
        }
        catch(InterruptedException e)
        {
            System.err.println("Problem waking thread from sleep");
        }
        catch(JsonSyntaxException e)
        {
            System.err.println("Problem parsing json data");
        }
        return false;
    }
}
