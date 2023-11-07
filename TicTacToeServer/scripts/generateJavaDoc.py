import os

print('bulding project...')

if(os.path.exists(os.getcwd() + '/../doc')):
    os.chdir('../doc')
else:
    os.chdir('../')
    os.system('mkdir doc')
    os.chdir('doc')

dir = os.getcwd()

os.system('javadoc -cp .;../lib/gson-2.10.1.jar -author ../src/model/*.java ../src/server/*.java ../src/socket/*.java ../src/test/*.java')