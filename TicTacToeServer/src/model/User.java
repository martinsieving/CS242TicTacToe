package model;

public class User {
    private String username;
    private String password;
    private String displayName;
    private boolean online;

    User() {
        username = "username";
        password = "password";
        displayName = "player";
        online = false;
    }

    User(String user, String pass, String display, boolean isOnline) {
        username = user;
        password = pass;
        displayName = display;
        online = isOnline;
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
