package socket;

import java.util.List;

import model.User;
import model.Event;

/**
 * PairingResponse class extends Response
 * Models the response to a request of type UPDATE_PAIRING
 * @author Alexander Odom
 * Clarkson University CS 242, November 2023
 */

public class PairingResponse extends Response
{
    /**
     * List of all users availabe for pairing
     */
    private List<User> availableUsers;

    /**
     * Invitation sent to another user
     */
    private Event invitation;

    /**
     * Response to an invitation sent to another user
     */
    private Event invitationResponse;

    /**
     * Default constructor
     */
    public PairingResponse()
    {
        super();
        this.availableUsers = null;
        this.invitation = null;
        this.invitationResponse = null;
    }

    /**
     * Constructor
     * @param status initialize status class variable
     * @param message initializes message class variable
     * @param availableUsers initializes availableUsers class variable
     * @param invitation initializes invitation class variable
     * @param invitationResponse initializes invitationResponse class variable
     */
    public PairingResponse(ResponseStatus status, String message, List<User> availableUsers, Event invitation, Event invitationResponse)
    {
        super(status, message);
        this.availableUsers = availableUsers;
        this.invitation = invitation;
        this.invitationResponse = invitationResponse;
    }

    /**
     * 
     * @return availableUsers class variable
     */
    public List<User> getAvailableUsers()
    {
        return availableUsers;
    }

    /**
     * 
     * @return invitation class variable
     */
    public Event getInvitation()
    {
        return invitation;
    }

    /**
     * 
     * @return invitationResponse class variable
     */
    public Event getInvitationResponse()
    {
        return invitationResponse;
    }

    /**
     * 
     * @param availableUsers sets availableUsers class variable
     */
    public void setAvailableUsers(List<User> availableUsers)
    {
        this.availableUsers = availableUsers;
    }

    /**
     * 
     * @param invitation sets invitation class variable
     */
    public void setInvitation(Event invitation)
    {
        this.invitation = invitation;
    }

    /**
     * 
     * @param invitationResponse sets invitationResponse class variable
     */
    public void setInvitationResponse(Event invitationResponse)
    {
        this.invitationResponse = invitationResponse;
    }
}
