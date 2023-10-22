package socket;

public class Response
{
    public enum ResponseStatus
    {
        SUCCESS,
        FAILURE
    }

    private ResponseStatus status;
    private String message;

    public Response()
    {
        this.status = null;
        this.message = new String();
    }

    public Response(ResponseStatus status, String message)
    {
        this.status = status;
        this.message = message;
    }

    
    /** 
     * @param status
     */
    public void setStatus(ResponseStatus status)
    {
        this.status = status;
    }

    
    /** 
     * @param message
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

    
    /** 
     * @return ResponseStatus
     */
    public ResponseStatus getStatus()
    {
        return status;
    }

    
    /** 
     * @return String
     */
    public String getMessage()
    {
        return message;
    }
}
