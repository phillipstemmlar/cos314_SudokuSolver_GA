import java.util.*; 

public class Sudoku {

	public final Integer[][] initValues;
	public Integer[][] values;

	public Boolean logging = false;
	final int N = 9;

	Evolution  evo;

	public Sudoku(String sudokuFile){
		initValues = parseValueList(Helper.removeWhiteSpace(Helper.filetoString(sudokuFile)));
		// initValues = parseValueList(Helper.removeWhiteSpace("8 4 5 6 3 2 1 7 9 7 3 2 9 1 8 6 5 4 1 9 6 7 4 5 3 2 8 6 8 3 5 7 4 9 1 2 4 5 7 2 9 1 8 3 6 2 1 9 8 6 3 5 4 7 3 6 1 4 2 9 7 8 5 5 7 4 1 8 6 2 9 3 9 2 8 3 5 7 4 6 1 "));
		values = null;
		evo = null;
	}
	
	public void solve(Integer[][] answer){
		// line();
		// printInitValues();
		// line();

		int pop = 1000;
		double mut = 0.03;

		evo = new Evolution(initValues, mut, N, answer);
		evo.generatePopulation(pop);
		
		// Element el = evo.elements.get(0);
		
		// el.print();

		// int ro = 0;
		// int co = 0;

		// el.calcFitness();

		// evo.best = el;
		// evo.printBest();

		evo.runSimulation();
		Element res = evo.winner;

		if(res == null){
			// line();
			// System.out.println("Res is null");
		}else{
			evo.best = res;
			// evo.printBest();
			// line();
			// res.print();
			// line();
			values = res.values;
		}

	}

	public int Generations(){
		if(evo == null) return 0;
		return evo.genCount;
	}

	private Integer[][] parseValueList(String values){
		Integer[][] res = new Integer[N][N];
		for(int i = 0; i < N; ++i){
			for(int j = 0; j < N; ++j){
				int pos = values.indexOf(Helper.comma);
				res[i][j] = Integer.parseInt(values.substring(0,pos));
				values = values.substring(pos+1);
			}
		}
		return res;
	}
	public void printInitValues(){
		if(initValues == null){
			logln("Values is null");
		}else{
			for(int i = 0; i < N; ++i){
				for(int j = 0; j < N; ++j){
					if(initValues[i][j] != 0){
						log(Helper.red + initValues[i][j] + Helper.white + Helper.space);
					}else{
						log(Helper.grey + initValues[i][j] + Helper.white + Helper.space);
					}
				}
				log(Helper.newline);
			}
		}
	}

	private void line(){logln("=========================================");}
	private void log(String msg){if(logging) System.out.print(msg);}
	private void logln(String msg){if(logging) System.out.println(msg);}
}