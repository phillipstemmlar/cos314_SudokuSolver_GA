import java.util.*; 

public class Sudoku {

	public final Integer[][] initValues;
	public Integer[][] values;

	public Boolean logging = true;
	final int N = 9;

	Evolution  evo;

	int pop ;
	int elitism ;
	double mut;
	int maxgen;

	public Sudoku(String paramsFile, String sudokuFile){
		initValues = parseValueFile(sudokuFile);
		values = null;
		evo = null;

		double[] params = parseParamFile(paramsFile); 

		pop = (int)Math.floor(params[0]);
		elitism = (int)Math.floor(params[1]);
		mut = params[2];
		maxgen = (int)Math.floor(params[3]);
	}
	
	public void solve(){
		// line();
		// printInitValues();
		// line();

		evo = new Evolution(initValues, N);
		evo.generatePopulation(pop);
		evo.runSimulation(mut,elitism,maxgen);
		Element res = evo.winner;

		if(res == null){
			line();
			evo.printBest();
			values = evo.best.values;
		}else{
			evo.best = res;
			evo.printBest();
			line();
			res.print();
			line();
			values = res.values;
		}

	}

	public int Generations(){
		if(evo == null) return 0;
		return evo.genCount;
	}

	private Integer[][] parseValueFile(String valuesFile){
		String values = Helper.removeWhiteSpace(Helper.filetoString(valuesFile));
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

	public double[] parseParamFile(String paramsInputFile){
		String paramsFile = Helper.filetoString(paramsInputFile);
		// System.out.println(paramsFile);
		
		double[] result = new double[4];
		try{
			int prev = 0;
			int pos = 0;
			for(int i = 0; i < 4; ++i){
				pos = paramsFile.indexOf("\n",prev);
				if(pos < 0) pos = paramsFile.length();
				result[i] = Double.parseDouble(paramsFile.substring(prev, pos));
				prev = pos+1;
			}

		}catch(StringIndexOutOfBoundsException e){
			System.out.println("Error in parameter file: " + paramsInputFile);
		}

		// System.out.println(Arrays.toString(result));

		return result;
	}

	private void line(){logln("=========================================");}
	private void log(String msg){if(logging) System.out.print(msg);}
	private void logln(String msg){if(logging) System.out.println(msg);}
}