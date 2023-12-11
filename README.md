Alexander Odom
Martin Sieving

Milestone 4

1.
all tests passed, screenshots on Moodle.

2.
We must send a REQUEST_MOVE request even when it is the current users turn to move to determine if the game is still active.

3.
When player B's device closes the program will halt, calling onDestroy. This will send either a COMPLETE_GAME or an ABORT_GAME request to the server. From there the server will notify player A.

4.
It would be possible to design the board using xml as xml can be used to create and design elements, similar to html. Each cell of the board could be a button or a text view of some sort that displays either nothing, an x, or an o.

Milestone 3

1.
all tests passed. Full output in TestOutput.txt

2.
The COMPLETE_GAME and ABORT_GAME requests are almost identical except for the status that the event is set to. The COMPLETE_GAME request sets the users event to COMPLETED. The ABORT_GAME request sets the users event to ABORTED.

3.
With the current implementation it is possible for two users to login to the system using the same account credentials. The handleLogin() function does not check if the user that is logging in is already logged in.

4.
The stages of class Event during a game process are as follows:
    1. Event object is created with a status of PENDING when an invitation is sent to another player
    2. If the opponent accepts the invitation the status changes to ACCEPTED
        a. Once the user that sent the invitation acknowledges this response the event's status is set to PLAYING
        b. The event keeps its PLAYING status until one of the users sends an ABORT_GAME or COMPLETE_GAME request
        c. If an ABORT_GAME request is sent the status is set to ABORTED
        d. If a COMPLETE_GAME request is sent the status is set to COMPLETED
    3. If the opponent declines the invitation the status changes to DECLINED
        a. Once the user that sent the invitation acknowledges this response the event's status is set to ABORTED

5.
Clearing the database before starting the server is necessary. If you do not clear the database before each test run, the database will store users between sessions. This will cause the registration and login tests to fail.

Milestone 2

1.
We do not have a working TicTacToe system. We completed all parts of the milestone, but were unable to build the client side as we were unable to get the gradle script to find tge gson repository, despite copying the link given by the online maven repository. We were able to build the server side through the use of the command line but were unable to test how features of the server interact with the client. Although we were able to test serverside features that did not require the TicTacToeV4 build.

2.
It is no longer possible to manage game states between the two game boards without introducing the player attribute. The turn variable is enough information to determine whos turn it is, but the player attribute is needed to determine which player owns the game board.

3.
It is not strictly necessary to reset the game moves after every move request. Reseting the game move after each move request allows us to see when we've sent a request to the other player, but after we have sent that request it is no longer possible to check the last move.

4.
It would be possible to search for the pressed button without looping through all of the buttons through the use of list manipulation. If all of the buttons were put in a list it would be possible to use indexOf() with the view and the list and then turing the output of that into rows and columns.

Milestone 1

a.
If we pass a negative port value to the SocketServer class it will set the PORT value to be negative, which will cause problems when connecting using that PORT value.
To fix this we could check to ensure that PORT > 0 when updating the value of PORT and throw an error if not

b.
If run is not overridden, then the super classes implementation is used. The super class, Thread, is programmed to throw an exception whenever its implementation of run is used as every subclass of Thread should overide run. This causes the ServerHandler class to throw an error when run is not overridden.