package client;

/**
 * SocketClient class
 * A singleton class that helps the Andriod application
 * connect to the socket server.
 * @author Martin Sieving
 * Clarkson University CS , October 2023
 */

public class SocketClient {
    private static SocketClient instance;
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Gson gson;

    private SocketClient() {
        try
        {
            socket = new Socket("temporary", 5000);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            gson = new GsonBuilder().serializeNulls().create();
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage())
        }
    }

    public static SocketClient getInstance() {
        if (instance == null) {
            instance = new SocketClient();
        }
        return instance;
    }

    void close()
    {
        try
        {
            input.close();
        }
        catch(IOException ioe)
        {
            System.err.println("Problem encountered when closing input stream");
        }
        try
        {
            output.close();
        }
        catch(IOException ioe)
        {
            System.err.println("Problem encountered when closing output stream");
        }
        try
        {
            socket.close();
        }
        catch(IOException ioe)
        {
            System.err.println("Problem encountered when closing socket");
        }
    }

    public <T> T sendRequest(Request request, Class<T> type) {
        String requestMessage = gson.toJson(request, Request.class);
        String responseMessage = "";
        AppExecutors.getInstance().networkIO().execute(new Runnable()
        {
            public void run()
            {
                try
                {
                    output.writeUTF(requestMessage);
                    responseMessage = input.readUTF();
                }
                catch(Exception e)
                {
                    System.err.println(e.getMessage());
                }
            }
        });
        return gson.fromJson(responseMessage, type);
    }
}