import java.io.*;
import java.util.*; 

public class Helper {

	public static final String space = " ";
	public static final String newline = "\n";
	public static final String comma = ",";
	public static final String empty = "";

	public static final String bold = "\033[0;1m";
	public static final String red = "\033[0;31m";
	public static final String green = "\033[0;32m";
	public static final String blue = "\033[0;34m";
	public static final String white = "\033[0;37m";
	public static final String grey = "\033[1;30m";

	// public static final String grey = "\033[1;30m";
	//\033[2J

	public static Integer[][] copyMatrix(Integer[][] matrix, int N, int M){
		Integer[][] res = new Integer[N][M];
		for(int i = 0; i < N ; ++i)
			for(int j = 0; j < M; ++j)
				res[i][j] = matrix[i][j];
		return res;
	}
	
	public static String filetoString(String filename){
		String file = "";
		try {
			File srcFile = new File(filename);
			Scanner srcReader = new Scanner(srcFile);

			while(srcReader.hasNextLine()){
				String line = srcReader.nextLine();
				file += line;
				if(srcReader.hasNextLine()) file += "\n";
			}
			srcReader.close();
			
    } catch (FileNotFoundException e) {
      System.out.println("File could not be read: " + filename);
      e.printStackTrace();
		}
		return file;		
	}
	public static String removeWhiteSpace(String input){
		int startLength = 10;
		input = input.replace(newline, empty);
		for(int i = startLength; i > 0; --i){
			String wspace = "";
			for(int j = 0; j < i; ++j) wspace += space;
			input = input.replace(wspace,comma);
		}
		return input;
	}
	
	public static int randomRange(int min, int max){
		double rand = Math.random();
		int randInt = (int)Math.floor((rand * (max - min))+ min);
		return randInt;
	}

	public static double round(double value, int decimals){
		double tens = Math.pow(10, decimals);
		return Math.round(value * tens) / tens;
	}

	public static void saveCursor(){System.out.print("\0337");}
	public static void restoreCursor(){System.out.print("\0338");}
	public static void eraseLine(){System.out.print("\033[K");}
	public static void cursorMoveUp(int pn){System.out.print("\033["+pn+"A");}
	public static void cursorMoveRight(int pn){System.out.print("\033["+pn+"C");}
	public static void cursorToStart(){System.out.print("\r");}

}