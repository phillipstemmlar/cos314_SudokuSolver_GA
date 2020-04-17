public class Element {

	public Integer[][] initValues;
	public Integer[][] values;

	Integer[][] answer;

	public int fitness;
	public int dupCount;
	public int misCount;

	private int N;
	public static final int maxFitness = 600;

	public static final int pref = 45;

	Boolean[] rowsCorrect;
	Boolean[] colsCorrect;
	Boolean[] blocksCorrect;

	public Element(Integer[][] init, int n, Integer[][] ans){
		N = n;
		fitness = 0;

		answer = ans;

		//fill with random integers;
		values = new Integer[N][N];

		for(int i = 0; i < N; ++i){
			for(int j = 0; j < N; ++j){
				if(init[i][j] == 0) values[i][j] = Helper.randomRange(1, 10);
				else values[i][j] = init[i][j];
			}
		}

		initValues = init;
		dupCount = 0;
		misCount = 0;

		rowsCorrect = new Boolean[N];
		colsCorrect = new Boolean[N];
		blocksCorrect = new Boolean[N];

		for(int i = 0; i < N; ++i){
			rowsCorrect[i] = false;
			colsCorrect[i] = false;
			blocksCorrect[i] = false;
		}
	}

	public void calcFitness(){
		dupCount = 0;
		misCount = 0;

		// fitnessBlock();
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

	public boolean validCell(int i, int j){
		return (values[i][j] == answer[i][j]);
	}

	public boolean rowContains(int r, int val){
		for(int i = 0; i < N; ++i) if(values[r][i] == val) return true;
		return false;
	}

	public Element clone(){
		Element el = new Element(initValues, N, answer);
		el.values = Helper.copyMatrix(values, N, N);
		el.fitness = fitness;
		return el;
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
					else if(values[i][j] == 0)  color = Helper.grey;
					else color = Helper.green;

					System.out.print(color + values[i][j] + Helper.white + Helper.space);
				}

				System.out.print(Helper.newline);
			}
		}
	}

	public static Element emptyElement(Integer[][] init, int n, Integer[][] ans){
		Element res = new Element(init, n, ans);
		for(int i = 0; i < n; ++i){
			for(int j = 0; j < n; ++j){
				res.values[i][j] = 0;
			}
		}
		return res;
	}

}