package socket;

public class GamingResponse extends Response
{
    private int move;
    private boolean active;

    GamingResponse()
    {
        super();
        this.move = 0;
        this.active = false;
    }

    GamingResponse(int move, boolean active)
    {
        super();
        this.move = move;
        this.active = active;
    }

    public void setMove(int move)
    {
        this.move = move;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public int getMove()
    {
        return move;
    }

    public boolean getActive()
    {
        return active;
    }
}
