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

    
    /** 
     * @param move
     */
    public void setMove(int move)
    {
        this.move = move;
    }

    
    /** 
     * @param active
     */
    public void setActive(boolean active)
    {
        this.active = active;
    }

    
    /** 
     * @return int
     */
    public int getMove()
    {
        return move;
    }

    
    /** 
     * @return boolean
     */
    public boolean getActive()
    {
        return active;
    }
}
