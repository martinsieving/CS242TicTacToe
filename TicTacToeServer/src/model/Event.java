package model;

public class Event {
    enum EventStatus {
        PENDING, DECLINED, ACCEPTED, PLAYING, COMPLETED, ABORTED
    }
    public int eventId;
    public String sender;
    public String opponent;
    public EventStatus status;
    public String turn;
    public int move;

    Event() {

    }

    Event() {

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
