package server;

public class SocketServer
{
    private int PORT;

    public static void main()
    {
        this.setup();
        this.startAcceptingRequest();
    }

    SocketServer()
    {
        this.PORT = 5000;
    }

    SocketServer(int PORT)
    {
        this.PORT = PORT;
    }

    public void setup()
    {

    }

    public void startAcceptingRequest()
    {

    }

    public int getPort()
    {
        return PORT;
    }
}
