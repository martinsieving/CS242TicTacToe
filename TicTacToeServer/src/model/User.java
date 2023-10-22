package model;

public class User {
    private String username;
    private String password;
    private String displayName;
    private boolean online;

    public User() {
        this.username = new String();
        this.password = new String();
        this.displayName = new String();
        this.online = false;
    }

    public User(String username, String password, String displayName, boolean online) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.online = online;
    }

    
    /** 
     * @param newUsername
     */
    public void setUsername(String newUsername) {
        this.username = newUsername;
    }

    
    /** 
     * @param newPassword
     */
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    
    /** 
     * @param newDisplayName
     */
    public void setDisplayName(String newDisplayName) {
        this.displayName = newDisplayName;
    }

    
    /** 
     * @param newOnline
     */
    public void setOnline(boolean newOnline) {
        this.online = newOnline;
    }

    
    /** 
     * @return String
     */
    public String getUsername() {
        return username;
    }

    
    /** 
     * @return String
     */
    public String getPassword() {
        return password;
    }

    
    /** 
     * @return String
     */
    public String getDisplayName() {
        return displayName;
    }

    
    /** 
     * @return boolean
     */
    public boolean getOnline() {
        return online;
    }

    
    /** 
     * @param otherUser
     * @return boolean
     */
    public boolean equals(User otherUser) {
        return this.username.equals(otherUser.username);
    }
}
