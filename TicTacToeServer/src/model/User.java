package model;

/**
 * User class
 * Models a user that will play a game. 
 * @author Martin Sieving
 * Clarkson University CS 242, October 2023
 */

public class User {
    /**
     * username is a String that represents the user's username.
     */
    private String username;

    /**
     * password is a String that represents the user's password.
     */
    private String password;

    /**
     * displayName is a String that represents the user's display name.
     */
    private String displayName;

    /**
     * online is a boolean that is true when the user is online, false when offline.
     */
    private boolean online;

    /**
     * default constructor
     * sets username, password, and displayName to null
     * sets online to false.
     */
    public User() {
        this.username = null;
        this.password = null;
        this.displayName = null;
        this.online = false;
    }

    /**
     * Constructor
     * @param username the String username will be set to.
     * @param password the String password will be set to.
     * @param displayName the String displayName will be set to.
     * @param online the boolean online will be set to.
     */
    public User(String username, String password, String displayName, boolean online) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.online = online;
    }

    /** 
     * @param newUsername class variable, represented as a String.
     */
    public void setUsername(String newUsername) {
        this.username = newUsername;
    }

    /** 
     * @param newPassword class variable, represented as a String.
     */
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
    
    /** 
     * @param newDisplayName class variable, represented as a String.
     */
    public void setDisplayName(String newDisplayName) {
        this.displayName = newDisplayName;
    }
    
    /** 
     * @param newOnline class variable, represented as a boolean.
     */
    public void setOnline(boolean newOnline) {
        this.online = newOnline;
    }
    
    /** 
     * @return username class variable, represented as a String.
     */
    public String getUsername() {
        return username;
    }
    
    /** 
     * @return password class variable, represented as a String.
     */
    public String getPassword() {
        return password;
    }
    
    /** 
     * @return displayName class variable, represented as a String.
     */
    public String getDisplayName() {
        return displayName;
    }
    
    /** 
     * @return online class variable, represented as a boolean.
     */
    public boolean getOnline() {
        return online;
    }
    
    /** 
     * @param otherUser
     * @return true if this user equals otherUser, using username as the unique attribute.
     */
    public boolean equals(User otherUser) {
        return this.username.equals(otherUser.username);
    }
}
