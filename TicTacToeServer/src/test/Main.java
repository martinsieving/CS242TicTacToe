package test;
public class Main {
    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        boolean allSuccess = true;
        EventTest test1 = new EventTest();
        if (!test1.test()) {
            System.out.println("EventTest failed.");
            allSuccess = false;
        }
        
        RequestTest test2 = new RequestTest();
        if (!test2.test()) {
            System.out.println("RequestTest failed.");
            allSuccess = false;
        }

        ResponseTest test3 = new ResponseTest();
        if (!test3.test()) {
            System.out.println("ResponseTest failed.");
            allSuccess = false;
        }

        SocketServerTest test4 = new SocketServerTest();
        if (!test4.test()) {
            System.out.println("SocketServerTest failed.");
            allSuccess = false;
        }

        UserTest test5 = new UserTest();
        if (!test5.test()) {
            System.out.println("UserTest failed.");
            allSuccess = false;
        }

        if (allSuccess) {
            System.out.println("All success!!!");
        }
    }
}