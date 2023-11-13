package test;
import model.User;

/**
 * UserTest class
 * Tests the getters, setters, and constructors for the User class
 * @author Alexander Odom
 * Clarkson University CS 242, October 2023
 */

public class UserTest
{
    /**
     * default Constructor
     */
    public UserTest() {
        
    }
    
    /** 
     * @return boolean
     * true if all getters, setters, and constructors for the User class work as intended
     * false otherwise
     */
    public boolean test() {
        String usernameTest = "test username";
        String passwordTest = "test password";
        String displayNameTest = "test displayName";
        boolean onlineTest = true;

        User user1 = new User();

        user1.setUsername(usernameTest);
        user1.setPassword(passwordTest);
        user1.setDisplayName(displayNameTest);
        user1.setOnline(onlineTest);

        User user2 = new User(usernameTest, passwordTest, displayNameTest, onlineTest);

        boolean everythingWorks = true;

        if(!user1.getUsername().equals(usernameTest))
        {
            System.out.println("Problem with User class username");
            everythingWorks = false;
        }

        if(!user2.getUsername().equals(usernameTest))
        {
            System.out.println("Problem with User class username");
            everythingWorks = false;
        }

        if(!user1.getPassword().equals(passwordTest))
        {
            System.out.println("Problem with User class password");
            everythingWorks = false;
        }

        if(!user2.getPassword().equals(passwordTest))
        {
            System.out.println("Problem with User class password");
            everythingWorks = false;
        }

        if(!user1.getDisplayName().equals(displayNameTest))
        {
            System.out.println("Problem with User class displayName");
            everythingWorks = false;
        }

        if(!user2.getDisplayName().equals(displayNameTest))
        {
            System.out.println("Problem with User class displayName");
            everythingWorks = false;
        }
        
        if(user1.isOnline() != onlineTest)
        {
            System.out.println("Problem with User class online");
            everythingWorks = false;
        }

        if(user2.isOnline() != onlineTest)
        {
            System.out.println("Problem with User class online");
            everythingWorks = false;
        }

        return everythingWorks;
    }
}