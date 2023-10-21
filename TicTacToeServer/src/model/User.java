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

    public void setUsername(String newUsername) {
        this.username = newUsername;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public void setDisplayName(String newDisplayName) {
        this.displayName = newDisplayName;
    }

    public void setOnline(boolean newOnline) {
        this.online = newOnline;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean getOnline() {
        return online;
    }

    public boolean equals(User otherUser) {
        return this.username.equals(otherUser.username);
    }
}
