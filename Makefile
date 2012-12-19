
.PHONY: all
all: lib/bukkit.jar
	javac -cp '.:lib/bukkit.jar' com/asdflolinternet/cookiecounter/*.java
	jar cvf CookieCounter.jar com *.yml

lib/bukkit.jar:
	mkdir -p lib/
	wget -O lib/bukkit.jar "http://dl.bukkit.org/latest-dev/bukkit.jar"

