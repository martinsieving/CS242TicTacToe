package server;

public class SocketServer
{
    private int PORT;

    public void main()
    {
        this.setup();
        this.startAcceptingRequest();
    }

    public SocketServer()
    {
        this.PORT = 5000;
    }

    public SocketServer(int PORT)
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
