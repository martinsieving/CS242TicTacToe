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

    public void setStatus(ResponseStatus status)
    {
        this.status = status;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public ResponseStatus getStatus()
    {
        return status;
    }

    public String getMessage()
    {
        return message;
    }
}
