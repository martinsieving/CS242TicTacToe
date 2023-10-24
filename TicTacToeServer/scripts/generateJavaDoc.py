import os

print('bulding project...')

if(os.path.exists(os.getcwd() + '/../doc')):
    os.chdir('../doc')
else:
    os.chdir('../')
    os.system('mkdir doc')
    os.chdir('doc')

os.system('javadoc -author ../src/model/*.java ../src/server/*.java ../src/socket/*.java ../src/test/*.java')