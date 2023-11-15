import os

print('bulding project...')

if(os.path.exists(os.getcwd() + '/../build')):
    os.chdir('../build')
else:
    os.chdir('../')
    os.system('mkdir build')
    os.chdir('build')

dir = os.getcwd()

# build model package
print('generating model package...')
os.system('javac -d ' + dir + ' ../src/model/Event.java ../src/model/User.java')

# build socket package
print('generating socket package')
os.system('javac -d ' + dir + ' ../src/socket/Response.java ../src/socket/Request.java ../src/socket/GamingResponse.java ../src/socket/PairingResponse.java')

# build server package
print('generating server package')

os.system('javac -cp .;' + dir + '/../lib/gson-2.10.1.jar -d ' + dir + ' ../src/server/ServerHandler.java ../src/server/SocketServer.java ../src/server/DatabaseHelper.java')

# build test package
print('generating test package')
os.system('javac -cp .;' + dir + '/../lib/gson-2.10.1.jar -d ' + dir + ' ../src/test/PairingTest.java ../src/test/SocketClientHelper.java ../src/test/EventTest.java ../src/test/RequestTest.java ../src/test/ResponseTest.java ../src/test/SocketServerTest.java ../src/test/UserTest.java ../src/test/Main.java')

print('build complete')

# to run use 
# java -cp ".;<path to gson>" <path to executable>
# ex. (run test.Main from build directory)
# java -cp ".;../lib/gson-2.10.1.jar;../lib/sqlite-jdbc-3.43.0.0.jar" test/Main
