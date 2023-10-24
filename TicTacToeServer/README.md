Alexander Odom
Martin Sieving

a.
If we pass a negative port value to the SocketServer class it will set the PORT value to be negative, which will cause problems when connecting using that PORT value.
To fix this we could check to ensure that PORT > 0 when updating the value of PORT and throw an error if not


b.
If run is not overridden, then the super classes implementation is used. The super class, Thread, is programmed to throw an exception whenever its implementation of run is used as every subclass of Thread should overide run. This causes the ServerHandler class to throw an error when run is not overridden.