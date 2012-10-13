OBJDIR = ../../build
JFLAGS = -g -d $(OBJDIR) -classpath $(OBJDIR)
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        GameExecutor.java \
        Player.java \
        Game.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	-rm -rf ../../build/server
