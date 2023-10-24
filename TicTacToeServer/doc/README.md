Alexander Odom
Martin Sieving

a.
If we pass a negative port value to the SocketServer class it will set the PORT value to be negative, which will cause problems when connecting using that PORT value.
To fix this we could check to ensure that PORT > 0 when accepting a value and throw an error if not


b.