CC=javac
JAR=jar
EXE=java
MAIN=Main
BIN=./bin
JAR_FILE=COS314_A2.jar
MANIFEST_FILE=Manifest.txt
INPUT_FILE=./sudokus/s01a.txt
PARAMS_FILE=./params.txt

make: *.java
	$(CC) *.java

jar: make
	$(JAR) cmvf $(MANIFEST_FILE) $(JAR_FILE) *.class 
	mv $(JAR_FILE) ${BIN}

run: 
	clear
	$(EXE) $(MAIN) $(PARAMS_FILE) $(INPUT_FILE)

exec:
	${EXE} -${JAR} ${BIN}/${JAR_FILE} $(PARAMS_FILE) $(INPUT_FILE)

clean:
	-rm *.class

wipe: clean
	-rm ${BIN}/*

again: make
	make run