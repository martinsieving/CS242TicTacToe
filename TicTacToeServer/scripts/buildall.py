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
os.system('javac -d ' + dir + ' ../src/model/Event.java ../src/model/User.java')

# build server package
os.system('javac -d ' + dir + ' ../src/server/ServerHandler.java ../src/server/SocketServer.java')

# build socket package
os.system('javac -d ' + dir + ' ../src/socket/Response.java ../src/socket/Request.java ../src/socket/Response.java')

# build test package
os.system('javac -d ' + dir + ' ../src/test/EventTest.java ../src/test/RequestTest.java ../src/test/ResponseTest.java ../src/test/SocketServerTest.java ../src/test/UserTest.java ../src/test/Main.java')

print('build complete')