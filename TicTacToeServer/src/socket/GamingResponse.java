package socket;

/**
 * GamingResponse class extends Response
 * Models the response to a request of type REQUEST_MOVE
 * @author Alexander Odom
 * Clarkson University CS 242, October 2023
 */

public class GamingResponse extends Response
{
    /**
     * move is an integer representation of the last move made by an opponent
     * value 0-8 represents the cell from top-bottom, left-right
     */
    private int move;

    /**
     * active indicates if an opponent is still active in the game
     */
    private boolean active;

    /**
     * default Constructor
     * calls default super constructor
     * sets move to 0 and active to false
     */
    GamingResponse()
    {
        super();
        this.move = 0;
        this.active = false;
    }

    /**
     * Constructor
     * @param status sent to super constructor
     * @param message sent to super constructor
     * @param move sets the move class variable, represented as an int
     * @param active sets the active class variable, represented as a boolean 
     */
    GamingResponse(ResponseStatus status, String message, int move, boolean active)
    {
        super(status, message);
        this.move = move;
        this.active = active;
    }

    /** 
     * @param move class variable, represented as an int
     */
    public void setMove(int move)
    {
        this.move = move;
    }
    
    /** 
     * @param active class variable, represented as a boolean
     */
    public void setActive(boolean active)
    {
        this.active = active;
    }
    
    /** 
     * @return move class variable, represented as an int
     */
    public int getMove()
    {
        return move;
    }
    
    /** 
     * @return active class variable, represented as a boolean
     */
    public boolean getActive()
    {
        return active;
    }
}
