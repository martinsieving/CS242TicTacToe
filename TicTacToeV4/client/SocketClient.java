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
    private String gson; // not sure about type

    private SocketClient() {

    }
    public static SocketClient getInstance() {
        return instance;
    }

    void close() {

    }

    responseClass sendRequest(request, responseClass) {

    }
}