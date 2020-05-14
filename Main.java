import java.io.File;
import java.util.*;

public class Main{

	static final File folder = new File("./sudokus");

	public static void main(String[] args) {
		Test(args[0], args[1]);

		// List<String> files	= listFilesForFolder(folder);
		// // System.out.println("=========================================");
		// for( String file : files){
		// 	Test(file);
		// }
		// System.out.println("=========================================");
	}

	public static List<String> listFilesForFolder(final File folder) {
		List<String> res = new ArrayList<>();
    for (final File fileEntry : folder.listFiles()) {
        if (fileEntry.isDirectory()) {
            listFilesForFolder(fileEntry);
        } else {
					res.add(folder + "/" + fileEntry.getName());
        }
		}
		Collections.sort(res);
		return res;
	}

	public static void Solve(String params, String filename){
		Sudoku sud = new Sudoku(params,filename);
		sud.solve();
		System.out.println("");
		Helper.saveCursor();
	}

	public static void Test(String params, String filename){
		String sudFile = filename;
		String solFile = sudFile.replace("sudokus", "solutions").replace(".txt", "_s.txt");

		Sudoku sud = new Sudoku(params,filename);
		Solution sol = new Solution(solFile);

		sud.solve();

		// sol.printValues();

		if(sol.checkAnswer(sud)){
			System.out.println(filename + "\t - CORRECT! \t - " + sud.Generations());
		}else{
			System.out.println(filename + "\t - WRONG!");
		}
		System.out.println("");
		Helper.saveCursor();
	}

}