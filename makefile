OBJDIR = build
LAUNCH = json-server

default: | $(OBJDIR) $(LAUNCH)
	$(MAKE) -C lib
	$(MAKE) -C src

$(OBJDIR):
	mkdir -p $(OBJDIR)

$(LAUNCH):
	touch $(LAUNCH)
	echo "java -classpath $(OBJDIR) server/ServerBootstrap" > $(LAUNCH)
	chmod a+x $(LAUNCH)

clean:
	rm $(LAUNCH)
	rm -rf $(OBJDIR)
