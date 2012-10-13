OBJDIR = ../../build
JFLAGS = -g -d $(OBJDIR) -classpath $(OBJDIR)
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	MoveHandler.java \
	StatusHandler.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	-rm -rf ../../build/handler
