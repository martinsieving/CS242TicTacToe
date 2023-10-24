package test;
import socket.Request;
import socket.Request.RequestType;

/**
 * RequestTest class
 * Tests getters, setters and constructors for Request class
 * @author Alexander Odom
 * Clarkson University CS 242, October 2023
 */

public class RequestTest
{
    /**
     * default Constructor
     */
    public RequestTest()
    {

    }
    
    /** 
     * @return boolean
     * true if all getters, setters, and constructors for the Request class work as intended
     * false otherwise
     */
    public boolean test()
    {
        RequestType typeTest = RequestType.LOGIN;
        String dataTest = "test data";

        Request request1 = new Request();

        request1.setType(typeTest);
        request1.setData(dataTest);

        Request request2 = new Request(typeTest, dataTest);

        boolean everythingWorks = true;

        if(request1.getType() != typeTest)
        {
            System.out.println("Problem with Request class type");
            everythingWorks = false;
        }

        if(request2.getType() != typeTest)
        {
            System.out.println("Problem with Request class type");
            everythingWorks = false;
        }

        if(!request1.getData().equals(dataTest))
        {
            System.out.println("Problem with Request class data");
            everythingWorks = false;
        }

        if(!request2.getData().equals(dataTest))
        {
            System.out.println("Problem with Request class data");
            everythingWorks = false;
        }

        return everythingWorks;
    }
}
