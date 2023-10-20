package test;

public class RequestTest
{
    RequestTest()
    {

    }

    public boolean test()
    {
        int typeTest = 3;
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
