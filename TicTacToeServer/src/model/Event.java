package model;

public class Event {
    enum EventStatus {
        PENDING, DECLINED, ACCEPTED, PLAYING, COMPLETED, ABORTED
    }
    private int eventId;
    private String sender;
    private String opponent;
    private EventStatus status;
    private String turn;
    private int move;

    Event()
    {
        this.eventId = new int();
        this.sender = new String();
        this.opponent = new String();
        this.turn = new EventStatus();
        this.move = new int();
    }

    Event(int eventId, String sender, String opponent, EventStatus status, String turn, int move)
    {
        this.eventId = eventId;
        this.sender = sender;
        this.opponent = opponent;
        this.turn = turn;
        this.move = move;
    }

    public int getEventId() {
        return eventId;
    }

    public EventStatus getStatus() {
        return status;
    }

    public int getMove() {
        return move;
    }

    public String getOpponent() {
        return opponent;
    }

    public String getSender() {
        return sender;
    }

    public String getTurn() {
        return turn;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public boolean equals(Event otherEvent) {
        return status == otherEvent.status;
    }
}
