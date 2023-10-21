package test;

public class EventTest
{
    EventTest()
    {
        
    }

    public boolean test()
    {
        int eventIdTest = 3;
        String senderTest = "test sender";
        String opponentTest = "test opponent";
        Event.EventStatus statusTest = Event.EventStatus.ABORTED;
        String turnTest = "test turn";
        int moveTest = 4;


        Event event1 = new Event();
        event1.setEventId(eventIdTest);
        event1.setSender(senderTest);
        event1.setOpponent(opponentTest);
        event1.setStatus(statusTest);
        event1.setTurn(turnTest);
        event1.setMove(moveTest);

        Event event2 = new Event(eventIdTest, senderTest, opponentTest, statusTest, turnTest, moveTest);

        boolean everythingWorks = true;

        if(event1.getEventId() != eventIdTest)
        {
            System.out.println("Problem with Event class eventId");
            everythingWorks = false;
        }

        if(event2.getEventId() != eventIdTest)
        {
            System.out.println("Problem with Event class eventId");
            everythingWorks = false;
        }

        if(!event1.getSender().equals(senderTest))
        {
            System.out.println("Problem with Event class sender");
            everythingWorks = false;
        }

        if(!event2.getSender().equals(senderTest))
        {
            System.out.println("Problem with Event class sender");
            everythingWorks = false;
        }

        if(!event1.getOpponent().equals(opponentTest))
        {
            System.out.println("Problem with Event class opponent");
            everythingWorks = false;
        }

        if(!event2.getOpponent().equals(opponentTest))
        {
            System.out.println("Problem with Event class opponent");
            everythingWorks = false;
        }

        if(!event1.getStatus().equals(statusTest))
        {
            System.out.println("Problem with Event class status");
            everythingWorks = false;
        }

        if(!event2.getStatus().equals(statusTest))
        {
            System.out.println("Problem with Event class status");
            everythingWorks = false;
        }

        if(!event1.getTurn().equals(turnTest))
        {
            System.out.println("Problem with Event class turn");
            everythingWorks = false;
        }

        if(!event2.getTurn().equals(turnTest))
        {
            System.out.println("Problem with Event class turn");
            everythingWorks = false;
        }

        if(!event1.getMove() == moveTest)
        {
            System.out.println("Problem with Event class move");
            everythingWorks = false;
        }

        if(!event2.getMove() == moveTest)
        {
            System.out.println("Problem with Event class move");
            everythingWorks = false;
        }

        if(!event1.equals(event2))
        {
            System.out.println("Problem with equals() for the Event class");
            everythingWorks = false;
        }

        return everythingWorks;
    }
}
