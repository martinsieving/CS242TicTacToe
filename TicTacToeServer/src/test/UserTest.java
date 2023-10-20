package test;

public class UserTest
{
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
    
    if(user1.getOnline() != onlineTest)
    {
        System.out.println("Problem with User class online");
        everythingWorks = false;
    }

    if(user2.getOnline() != onlineTest)
    {
        System.out.println("Problem with User class online");
        everythingWorks = false;
    }

    return everythingWorks;
}
