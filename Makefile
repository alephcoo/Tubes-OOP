.PHONY: build run clean package

build:
	mvn compile

run:
	mvn exec:java -Dexec.mainClass="com.nangorblast.Main"

package:
	mvn package

clean:
	mvn clean
