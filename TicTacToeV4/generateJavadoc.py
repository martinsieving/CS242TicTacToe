import os

print('bulding project...')

if(os.path.exists(os.getcwd() + '/doc')):
    os.chdir('doc')
else:
    os.system('mkdir doc')
    os.chdir('doc')

dir = os.getcwd()

os.system('javadoc -author ../socket/*.java ../model/*.java ../client/*.java')