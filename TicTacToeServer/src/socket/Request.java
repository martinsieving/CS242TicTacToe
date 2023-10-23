package socket;

/**
 * Request class
 * The Request class models all the requests a client will send to the server
 * The client must create a request class to communicate with the server and vice versa
 * @author Alexander Odom
 * Clarkson University CS 242, October 2023
 */

public class Request
{
    /**
     * RequestType enum
     * LOGIN - sent when a user wants to log into the game
     * REGISTER - sent when a user wants to register for the game for the first time
     * UPDATE_PAIRING - request is sent periodically once the user is logged in to check pairing
     * SEND_INVITATION - sent to the server by a player when a player selects an opponent
     * ACCEPT_INVITATION - sent to the server by a player when a player accepts another players invitation 
     * DECLINE_INVITATION - sent to the server by a player when a player declines another players invitation
     * ACKNOWLEDGE_RESPONSE - client sends this to a server whenever they recieve a message as acknowledgement
     * REQUEST_MOVE - sent periodically to the server during gameplay to request a game move be sent
     * SEND_MOVE - sent to the server during gameplay to send a move
     * ABORT_GAME - sent to the server whenever a user wants to abort a game
     * COMPLETE_GAME - sent when a user recieves a final move indicating the game is over
     */
    public enum RequestType
    {
        LOGIN,
        REGISTER,
        UPDATE_PAIRING,
        SEND_INVITATION,
        ACCEPT_INVITATION,
        DECLINE_INVITATION,
        ACKNOWLEDGE_RESPONSE,
        REQUEST_MOVE,
        SEND_MOVE,
        ABORT_GAME,
        COMPLETE_GAME
    }

    /**
     * type contains the type of message
     */
    private RequestType type;

    /**
     * data contains serialized data represented as a String
     */
    private String data;

    /**
     * default Constructor
     * sets both type and data data to null
     */
    public Request()
    {
        this.type = null;
        this.data = null;
    }

    /**
     * Constructor
     * @param type sets type data, represented as a RequestType enum
     * @param data sets data data, represented as a String
     */
    public Request(RequestType type, String data)
    {
        this.type = type;
        this.data = data;
    }
    
    /** 
     * @param type data, represented as a RequestType enum
     */
    public void setType(RequestType type)
    {
        this.type = type;
    }
    
    /** 
     * @param data data, represented as a String
     */
    public void setData(String data)
    {
        this.data = data;
    }

    /** 
     * @return type data, represented as a RequestType enum
     */
    public RequestType getType()
    {
        return type;
    }
    
    /** 
     * @return data, represented as a String
     */
    public String getData()
    {
        return data;
    }
}
