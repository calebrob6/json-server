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
gameId = -1

while True:
    print "(1) Connect"
    print "(2) Get game status"
    print "(3) Play"
    print "(0) Exit"
    print " "
    userIn = int(raw_input("Enter command:"))

    if userIn==1:
        userData = doRequest("",0)
        userId = userData[u'ID'] #there is an "u" in front of the strings because they are unicode (u is the unicode flag)
        userAuth = userData[u'AUTH']
        gameId = userData[u'GAMEID']
    elif userIn==2:
        rBoard = doRequest({'GAMEID':gameId,"ID":userId,"AUTH":userAuth},1)
        board = rBoard[u'BOARD']
        for a in board:
            for b in a:
                if b==-1:
                    print "_ ",
                else:
                    print str(b) + " ",
            print " "
        print " "
    elif userIn==3:

        x1 = int(raw_input("Enter x coordinate(piece): "))
        y1 = int(raw_input("Enter y coordinate(piece): "))
        x2 = int(raw_input("Enter x coordinate(move): "))
        y2 = int(raw_input("Enter y coordinate(move): "))
        rData = {"COMMAND":["MOVE",x1,y1,x2,y2],'GAMEID':gameId,"ID":userId,"AUTH":userAuth}
        request = doRequest(rData,2)
        if request[u'WON'] == True:
            rBoard = doRequest("",1)
            board = rBoard[u'BOARD']
            for a in board:
                for b in a:
                    if b==-1:
                        print "_ ",
                    else:
                        print str(b) + " ",
                print " "
            print " "
        elif request[u'ERROR']==0:
            print "Move accepted"
        else:
            print "Error"
    elif userIn==0:
        break
    else:
        print "Input not recognized"
        



