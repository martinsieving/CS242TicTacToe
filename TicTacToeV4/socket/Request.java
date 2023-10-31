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
     */
    public enum RequestType
    {
        /**
         * Sent when a user wants to log into the game
         */
        LOGIN,
        /**
         * Sent when a user wants to register for the game for the first time
         */
        REGISTER,
        /**
         * Sent periodically once the user is logged in to check pairing
         */
        UPDATE_PAIRING,
        /**
         * Sent to the server by a player when a player selects an opponent
         */
        SEND_INVITATION,
        /**
         * Sent to the server by a player when a player accepts another players invitation
         */
        ACCEPT_INVITATION,
        /**
         * Sent to the server by a player when a player declines another players invitation
         */
        DECLINE_INVITATION,
        /**
         * Client sends this to a server whenever they recieve a message as acknowledgement
         */
        ACKNOWLEDGE_RESPONSE,
        /**
         * Sent periodically to the server during gameplay to request a game move be sent
         */
        REQUEST_MOVE,
        /**
         * Sent to the server during gameplay to send a move
         */
        SEND_MOVE,
        /**
         * Sent to the server whenever a user wants to abort a game
         */
        ABORT_GAME,
        /**
         * Sent when a user recieves a final move indicating the game is over
         */
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
     * sets both type and data class variables to null
     */
    public Request()
    {
        this.type = null;
        this.data = null;
    }

    /**
     * Constructor
     * @param type sets type class variable, represented as a RequestType enum
     * @param data sets data class variable, represented as a String
     */
    public Request(RequestType type, String data)
    {
        this.type = type;
        this.data = data;
    }
    
    /** 
     * @param type class variable, represented as a RequestType enum
     */
    public void setType(RequestType type)
    {
        this.type = type;
    }
    
    /** 
     * @param data class variable, represented as a String
     */
    public void setData(String data)
    {
        this.data = data;
    }

    /** 
     * @return type class variable, represented as a RequestType enum
     */
    public RequestType getType()
    {
        return type;
    }
    
    /** 
     * @return data class variable, represented as a String
     */
    public String getData()
    {
        return data;
    }
}
