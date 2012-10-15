import json
import urllib2

url = "http://localhost:8000"
urlPaths = ["/connect","/game/status","/game/move"]

def doRequest(jsonData,urlPathIndex):
    data = json.dumps(jsonData)
    req = urllib2.Request(url+urlPaths[urlPathIndex], data, {'Content-Type': 'application/json'})
    f = urllib2.urlopen(req)
    response = f.read()
    f.close()
    return json.loads(response)


userData = {}
userId = -1
userAuth = -1
userIn = -1

while True:
    print "(1) Connect"
    print "(2) Get game status"
    print "(3) Play"
    print "(0) Exit"
    print " "
    userIn = int(raw_input("Enter command:"))

    if userIn==1:
        userData = doRequest("",0)
        userId = userData[u'id'] #there is an "u" in front of the strings because they are unicode (u is the unicode flag)
        userAuth = userData[u'auth']
    elif userIn==2:
        rBoard = doRequest("",1)
        board = rBoard[u'board']
        for a in board:
            for b in a:
                if b==-1:
                    print "_ ",
                else:
                    print str(b) + " ",
            print " "
        print " "
    elif userIn==3:

        x = int(raw_input("Enter x coordinate: "))
        pType = int(raw_input("Enter type(1=square,2=circle): "))
        rData = {'x':x,'type':pType,'id':userId,'auth':userAuth}
        request = doRequest(rData,2)
        if request[u'won'] == True:
            rBoard = doRequest("",1)
            board = rBoard[u'board']
            for a in board:
                for b in a:
                    if b==-1:
                        print "_ ",
                    else:
                        print str(b) + " ",
                print " "
            print " "
        elif request[u'error']==0:
            print "Move accepted"
        else:
            print "Error"
    elif userIn==0:
        break
    else:
        print "Input not recognized"
        



