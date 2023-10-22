package model;

public class Event {
    public enum EventStatus {
        PENDING, DECLINED, ACCEPTED, PLAYING, COMPLETED, ABORTED
    }
    private int eventId;
    private String sender;
    private String opponent;
    private EventStatus status;
    private String turn;
    private int move;

    public Event()
    {
        this.eventId = 0;
        this.sender = new String();
        this.opponent = new String();
        this.status = null;
        this.turn = new String();
        this.move = 0;
    }

    public Event(int eventId, String sender, String opponent, EventStatus status, String turn, int move)
    {
        this.eventId = eventId;
        this.sender = sender;
        this.opponent = opponent;
        this.status = status;
        this.turn = turn;
        this.move = move;
    }


    /** 
     * @return int
     */
    public int getEventId() {
        return eventId;
    }


    /** 
     * @return EventStatus
     */
    public EventStatus getStatus() {
        return status;
    }


    /** 
     * @return int
     */
    public int getMove() {
        return move;
    }


    /** 
     * @return String
     */
    public String getOpponent() {
        return opponent;
    }


    /** 
     * @return String
     */
    public String getSender() {
        return sender;
    }


    /** 
     * @return String
     */
    public String getTurn() {
        return turn;
    }


    /**
     * @param eventId
     */
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }


    /**
     * @param move
     */
    public void setMove(int move) {
        this.move = move;
    }


    /**
     * @param opponent
     */
    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }


    /**
     * @param sender
     */
    public void setSender(String sender) {
        this.sender = sender;
    }


    /**
     * @param status
     */
    public void setStatus(EventStatus status) {
        this.status = status;
    }


    /**
     * @param turn
     */
    public void setTurn(String turn) {
        this.turn = turn;
    }

    
    /** 
     * @param otherEvent
     * @return boolean
     */
    public boolean equals(Event otherEvent) {
        return eventId == otherEvent.eventId;
    }
}
