Alexander Odom
Martin Sieving

Milestone 2

1.

2.
It is no longer possible to manage game states between the two game boards without introducing the player attribute. The turn variable is enough information to determine whos turn it is, but the player attribute is needed to determine which player owns the game board.

3.
It is not strictly necessary to reset the game moves after every move request. Reseting the game move after each move request allows us to see when we've sent a request to the other player, but after we have sent that request it is no longer possible to check the last move.

4.

Milestone 1

a.
If we pass a negative port value to the SocketServer class it will set the PORT value to be negative, which will cause problems when connecting using that PORT value.
To fix this we could check to ensure that PORT > 0 when updating the value of PORT and throw an error if not

b.
If run is not overridden, then the super classes implementation is used. The super class, Thread, is programmed to throw an exception whenever its implementation of run is used as every subclass of Thread should overide run. This causes the ServerHandler class to throw an error when run is not overridden.