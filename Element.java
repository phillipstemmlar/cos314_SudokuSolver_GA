import java.util.Arrays;

public class Element {

	public Integer[][] initValues;
	public Integer[][] values;

	public int fitness;
	public int dupCount;
	public int misCount;

	private int N;
	public static final int maxFitness = 600;

	public static final int pref = 45;

	Boolean[] rowsCorrect;
	Boolean[] colsCorrect;
	Boolean[] blocksCorrect;

	Boolean[][] isValid;
	Boolean[][] isAlmostValid;

	public Element(Integer[][] init, int n){
		N = n;
		fitness = 0;

		//fill with random integers;
		values = new Integer[N][N];

		for(int ro = 0; ro < 3; ++ro){
			for(int co = 0; co < 3; ++co){

				int p = 0;
				int[] vals = new int[N];

				for(int ri = 0; ri < 3; ++ri){
					for(int ci = 0; ci < 3; ++ci){
						int i = ro*3 + ri;
						int j = co*3 + ci;
						if(init[i][j] != 0) vals[p] = init[i][j];
						p++;
					}
				}

				// System.out.println(Arrays.toString(vals));

				for(p = 0; p < N; ++p){
					Integer val = 0;
					// System.out.println("\t" +Arrays.toString(vals));
					if(vals[p] == 0){
						do{
							val = Helper.randomRange(1, 10);
							// System.out.println("\t\t" +val);
						}while(Helper.contains(vals, val));
						vals[p] = val;
					}
				}
	
				// System.out.println(Arrays.toString(vals));

				p = 0;
				for(int ri = 0; ri < 3; ++ri){
					for(int ci = 0; ci < 3; ++ci){
						int i = ro*3 + ri;
						int j = co*3 + ci;
	
						values[i][j] = vals[p];
						p++;
					}
				}

			}
		}

		initValues = init;
		dupCount = 0;
		misCount = 0;

		rowsCorrect = new Boolean[N];
		colsCorrect = new Boolean[N];
		blocksCorrect = new Boolean[N];
		isValid = new Boolean[N][N];
		isAlmostValid = new Boolean[N][N];

		for(int i = 0; i < N; ++i){
			rowsCorrect[i] = false;
			colsCorrect[i] = false;
			blocksCorrect[i] = false;
			for(int j = 0; j < N; ++j){
				isValid[i][j] = false;
				isAlmostValid[i][j] = false;
			}
		}
	}

	public void calcFitness(){
		dupCount = 0;
		misCount = 0;

		fitnessBlock();
		fitnessRow();
		fitnessCol();
		fitness = dupCount ;//+ misCount;
	}

	public void fitnessBlock(){
		
		for(int ro = 0; ro < 3; ++ro){
			for(int co = 0; co < 3; ++co){

				int[] count = new int[N];
				for(int n = 0; n < N; ++n) count[n] = 0;

				for(int ri = 0; ri < 3; ++ri){
					for(int ci = 0; ci < 3; ++ci){
						int i = ro*3 + ri;
						int j = co*3 + ci;
						if(values[i][j] <= N && values[i][j] > 0) count[values[i][j]-1] += 1;
					}
				}
	
				for(int n = 0; n < N; ++n){
					count[n]--;				
					// System.out.println("Block: count[" + (n+1) + "] = " + count[n]);
					if( count[n] > 0 ) dupCount += count[n];
					if( count[n] < 0 ) misCount += 1;

				}

			}
		}

	}

	public void fitnessRow(){
		int sum = 0;
		for(int i = 0; i < N; ++i){
			int[] count = new int[N];

			for(int j = 0; j < N; ++j){
				if(values[i][j] <= N && values[i][j] > 0) count[values[i][j]-1] += 1;
			}

			for(int n = 0; n < N; ++n){
				count[n]--;		
				// System.out.println("Row: count[" + (n+1) + "] = " + count[n]);
				if( count[n] > 0 ) dupCount += count[n];
				if( count[n] < 0 ) misCount += 1;

			}
			
		}
	}

	public void fitnessCol(){
		int sum = 0;
		for(int i = 0; i < N; ++i){
			int[] count = new int[N];

			for(int j = 0; j < N; ++j){
				if(values[j][i] <= N && values[j][i] > 0) count[values[j][i]-1] += 1;
			}

			for(int n = 0; n < N; ++n){
				count[n]--;		
				// System.out.println("Col: count[" + (n+1) + "] = " + count[n]);
				if( count[n] > 0 ) dupCount += count[n];
				if( count[n] < 0 ) misCount += 1;

			}
			
		}
	}

	public int fitnessPerc(){
		// return (int)Math.round((((float)fitness/(float)maxFitness)*100.0));
		return (int)((1.0 / (double)(fitness + 1)) * maxFitness);
	}

	public String fitnessString(){
		return "" + Helper.round((double)fitnessPerc()/(double)maxFitness, 4) + "%";
	}

	public boolean rowContains(int r, int val){
		for(int i = 0; i < N; ++i) if(values[r][i] == val) return true;
		return false;
	}

	public Element clone(){
		Element el = new Element(initValues, N);
		el.values = Helper.copyMatrix(values, N, N);
		el.fitness = fitness;
		return el;
	}

	public void calcValidity(){
		for(int i = 0; i < N; ++i){
			for(int j = 0; j < N; ++j){
				// if(!validCell(i,j)){
					int count = 0;
					for(int r = 0; r < values.length; ++r) if(values[r][j] ==  values[i][j]) count++;
					for(int c = 0; c < values[i].length; ++c) if(values[i][c] ==  values[i][j]) count++;
					for(int ri = 0; ri < 3; ++ri){
						for(int ci = 0; ci < 3; ++ci){
							int x = (i/3) *3 + ri;
							int y = (j/3) *3 + ci;
							if(values[x][y] ==  values[i][j]) count++;
						}
					}
					isValid[i][j] = (count == 3);
					isAlmostValid[i][j] = (count <= 4);
				// }
			}
		}
	}

	public Boolean swapValues(int i1, int j1, int i2, int j2){
		if(i1 < 0 || i1 >= N) return false;
		if(j1 < 0 || j1 >= N) return false;
		if(i2 < 0 || i2 >= N) return false;
		if(j2 < 0 || j2 >= N) return false;

		if(initValues[i1][j1] != 0)return false;
		if(initValues[i2][j2] != 0)return false;

		Integer temp = values[i1][j1];
		values[i1][j1] = values[i2][j2];
		values[i2][j2] = temp;
		return true;
	}

	public Boolean validCell(int i, int j){
		return isValid[i][j];
	}

	public Boolean almostValid(int i, int j){
		return isAlmostValid[i][j];
	}

	public void print(){
		if(values == null){
			System.out.println("Values is null");
		}else{
			for(int i = 0; i < N; ++i){

				String color = Helper.white;
				
				for(int j = 0; j < N; ++j){

					if(initValues[i][j] != 0) color = Helper.red;
					else if(validCell(i,j))    color = Helper.blue;
					else if(almostValid(i,j))    color = Helper.white;
					else if(values[i][j] == 0)  color = Helper.grey;
					else color = Helper.green;

					System.out.print(color + values[i][j] + Helper.white + Helper.space);
				}

				System.out.print(Helper.newline);
			}
		}
	}

	public static Element emptyElement(Integer[][] init, int n, Integer[][] ans){
		Element res = new Element(init, n);
		for(int i = 0; i < n; ++i){
			for(int j = 0; j < n; ++j){
				res.values[i][j] = 0;
			}
		}
		return res;
	}

}