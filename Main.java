import java.io.File;
import java.util.*;

public class Main{

	static final File folder = new File("./sudokus");

	public static void main(String[] args) {
		List<String> files	= listFilesForFolder(folder);
		System.out.println("=========================================");
		for( String file : files){
			Solve(file);
		}
		System.out.println("=========================================");
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

	public static void Solve(String filename){
		String sudFile = filename;
		String solFile = sudFile.replace("sudokus", "solutions").replace(".txt", "_s.txt");

		Sudoku sud = new Sudoku(sudFile);
		Solution sol = new Solution(solFile);

		sud.solve(sol.values);

		// sol.printValues();

		if(sol.checkAnswer(sud)){
			System.out.println(filename + "\t - CORRECT! \t - " + sud.Generations());
		}else{
			System.out.println(filename + "\t - WRONG!");
		}
	}

}