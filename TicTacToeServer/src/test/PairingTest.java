package test;

import java.sql.SQLException;

import model.User;
import server.DatabaseHelper;
import server.SocketServer;
import socket.Request;
import socket.Request.RequestType;
import socket.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PairingTest {
    public PairingTest()
    {
        
    }

    public void test()
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
