package socket;

import java.util.List;

import model.User;
import model.Event;;

public class PairingResponse extends Response
{
    private List<User> availableUsers;
    private Event invitation;
    private Event invitationResponse;

    public PairingResponse()
    {
        super();
        this.availableUsers = null;
        this.invitation = null;
        this.invitationResponse = null;
    }

    public PairingResponse(ResponseStatus status, String message, List<User> availableUsers, Event invitation, Event invitationResponse)
    {
        super(status, message);
        this.availableUsers = availableUsers;
        this.invitation = invitation;
        this.invitationResponse = invitationResponse;
    }

    public List<User> getAvailableUsers()
    {
        return availableUsers;
    }

    public Event getInvitation()
    {
        return invitation;
    }

    public Event getInvitationResponse()
    {
        return invitationResponse;
    }

    public void setAvailableUsers(List<User> availableUsers)
    {
        this.availableUsers = availableUsers;
    }

    public void setInvitation(Event invitation)
    {
        this.invitation = invitation;
    }

    public void setInvitationResponse(Event invitationResponse)
    {
        this.invitationResponse = invitationResponse;
    }
}
