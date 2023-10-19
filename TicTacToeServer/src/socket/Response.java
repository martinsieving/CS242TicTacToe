package socket;

public class Response
{
    enum ResponseStatus
    {
        SUCCESS,
        FAILURE
    }

    private ResponseStatus status;
    private String message;

    Response()
    {
        status = new ResponseStatus();
    }

    Response(ResponseStatus status, String message)
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
