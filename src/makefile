OBJDIR = ../build
JFLAGS = -g -d $(OBJDIR) -classpath $(OBJDIR)
JC = javac

default:
	$(MAKE) -C game
	$(MAKE) -C handlers
	$(MAKE) -C server
	$(JC) $(JFLAGS) server/ServerBootstrap.java handlers/ConnectionHandler.java
