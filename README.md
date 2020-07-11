# cos314_SudokuSolver_GA
Genetic Alogrithm that solves sudokus.<br/>

This repository contains a Genetic Algorithm written in JAVA, that takes an empty sudoku puzzle with only the initial values filled in as input and then correctly generates the solution to the sudoku puzzle.
<br/>
****************************************************************************************************************<br/>
PLEASE CLEAR THE TERMINAL EVERYTIME BEFORE YOUR RUN THE PROGRAM, OTHERWISE THE OUTPUT GETS MESSED UP. THANK YOU.<br/>
****************************************************************************************************************<br/>
<br/>
================JAR FILE================br/>
<br/>
To compile the code into a JAR file, use

	make jar

The JAR file will be available in the bin/ directory.<br/>
<br/>
To run the JAR file, called COS314_A2_u18171185.jar, use

	java -jar COS314_A2_u18171185.jar <param-file> <suduko-file>
<br/>
================JAVA FILE================br/>
<br/>
To compile the code into Java Class files, use

	make

The Java Class files will be available in this directory.<br/>
<br/>
The Main Java Main is the Compiler Class. To run it, use

	java Main <param-file> <suduko-file>
<br/>
================PARAMETER FILE FORMAT================br/>
<br/>
Given filename: params.txt<br/>
Content:

	2000
	1000
	0.2
	200

The 1st value (2000) specifies the population size.<br/>
The 2nd value (1000) specifies the elitism value for the crossover operation.<br/>
The 3rd value (0.2)  specifies the mutation chance.<br/>
The 4th value (200)  specifies the number of generations, where no improvements have been made, after which to restart with new initial values.<br/>
<br/>
*Each value has to be on a new line with no empty lines in between.<br/>
<br/>
================DEVELOPMENT ENVIRONMENT================br/>
<br/>
openjdk 11.0.7 2020-04-14<br/>
OpenJDK Runtime Environment (build 11.0.7+10-post-Ubuntu-2ubuntu218.04)<br/>
OpenJDK 64-Bit Server VM (build 11.0.7+10-post-Ubuntu-2ubuntu218.04, mixed mode, sharing)<br/>
