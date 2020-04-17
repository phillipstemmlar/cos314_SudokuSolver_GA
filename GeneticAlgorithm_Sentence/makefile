CC=javac
JAR=jar
EXE=java
MAIN=Main
BIN=./bin
JAR_FILE=COS314_A2.jar
MANIFEST_FILE=Manifest.txt
INPUT="to be or no to be that is the question"

make: *.java
	$(CC) *.java

jar: make
	$(JAR) cmvf $(MANIFEST_FILE) $(JAR_FILE) *.class 
	mv $(JAR_FILE) ${BIN}

run: 
	$(EXE) $(MAIN) $(INPUT)

exec:
	${EXE} -${JAR} ${BIN}/${JAR_FILE}  $(INPUT)

clean:
	-rm *.class

wipe: clean
	-rm ${BIN}/*

again: make
	clear
	make run