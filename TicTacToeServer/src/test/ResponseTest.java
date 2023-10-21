package test;

public class ResponseTest
{
    ResponseTest()
    {

    }

    public boolean test()
    {
        Response.ResponseStatus statusTest = Response.ResponseStatus.SUCCESS;
        String messageTest = "test message";

        Response response1 = new Response();
        response1.setStatus(statusTest);
        response1.setMessage(messageTest);

        Response response2 = new Response(statusTest, messageTest);

        boolean everythingWorks = true;

        if(!response1.getStatus().equals(statusTest))
        {
            System.out.println("Problem with Response class status")
            everythingWorks = false;
        }

        if(!response2.getStatus().equals(statusTest))
        {
            System.out.println("Problem with Response class status")
            everythingWorks = false;
        }

        if(!response1.getMessage().equals(messageTest))
        {
            System.out.println("Problem with Response class message")
            everythingWorks = false;
        }

        if(!response2.getMessage().equals(messageTest))
        {
            System.out.println("Problem with Response class message")
            everythingWorks = false;
        }

        return everythingWorks;
    }
}
