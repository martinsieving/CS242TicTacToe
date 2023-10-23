package socket;

/**
 * Response class
 * This class is responsible for modeling all the server's response to a client request
 * Both server and client use Response to communicate
 * It contatins useful information about request status and contains string messages with more information about SUCCESS and FAILURE
 * @author Alexander Odom
 * Clarkson University CS 242, October 2023
 */

public class Response
{
    /**
     * ResponseStatus enum
     */
    public enum ResponseStatus
    {
        SUCCESS,
        FAILURE
    }

    /**
     * status contains the status of the server response
     */
    private ResponseStatus status;

    /**
     * message contains a String message description of the client-server communication
     */
    private String message;

    /**
     * default Constructor
     * sets both status and message to null
     */
    public Response()
    {
        this.status = null;
        this.message = null;
    }

    /**
     * Constructor
     * @param status sets the status class variable, represented as a ResponseStatus enum
     * @param message set the message class variable, represented as a String
     */
    public Response(ResponseStatus status, String message)
    {
        this.status = status;
        this.message = message;
    }
    
    /** 
     * @param status class variable, represented as a ResponseStatus enum
     */
    public void setStatus(ResponseStatus status)
    {
        this.status = status;
    }
    
    /** 
     * @param message class variable, represented as a String
     */
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    /** 
     * @return status class variable, represented as a ResponseStatus enum
     */
    public ResponseStatus getStatus()
    {
        return status;
    }
    
    /** 
     * @return message class variable, represented as a String
     */
    public String getMessage()
    {
        return message;
    }
}