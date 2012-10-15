OBJDIR = ../../../build
JFLAGS = -g -d $(OBJDIR) -classpath $(OBJDIR)
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

# Super serial circular dependency
CLASSES = \
	JSONException.java\ \
	JSONTokener.java\ \
	JSONObject.java\ \
	JSONArray.java\ \
	JSONML.java\ \
	JSONStringer.java\ \
	JSONString.java\ \
	JSONWriter.java\ \
	CDL.java\ \
	Cookie.java\ \
	CookieList.java\ \
	HTTP.java\ \
	HTTPTokener.java\ \
	XML.java\ \
	XMLTokener.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	-rm -rf ../../../build/org/json
