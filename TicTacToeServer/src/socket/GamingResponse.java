package socket;

public class GamingResponse extends Response
{
    private int move;
    private boolean active;

    GamingResponse()
    {
        this.move = new int();
        this.active = new boolean();
        super();
    }

    GamingResponse(int move, boolean active)
    {
        this.move = move;
        this.active = active;
        super();
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
