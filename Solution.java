public class Solution {

	public Integer[][] values;

	public Boolean logging = true;

	final int N = 9;

	public Solution(String sudokuFile){
		values = parseValueList(Helper.removeWhiteSpace(cleanUp(Helper.filetoString(sudokuFile))));
	}
	
	private String cleanUp(String input){
		input = input.replace("-", Helper.empty);
		input = input.replace("|", Helper.empty);
		input = input.replace("=", Helper.empty);
		input = input.replace("0", Helper.empty);
		input = input.replace("45", Helper.empty);
		input = input.replace(Helper.comma, Helper.empty);
		input = input.replace(Helper.space + Helper.space, Helper.space);
		return input;
	}

	public Boolean checkAnswer(Sudoku sud){
		if(sud == null) return false;
		for(int i = 0; i < N; i++){
			for(int j = 0; j < N; ++j){
				if(values[i][j] == null || sud.values[i][j] == null || values[i][j] != sud.values[i][j]) return false;
			}
		}
		return true;
	}

	private Integer[][] parseValueList(String valList){
		Integer[][] res = new Integer[N][N];
		for(int i = 0; i < N; ++i){
			for(int j = 0; j < N; ++j){
				int pos = valList.indexOf(Helper.comma);
				res[i][j] = Integer.parseInt(valList.substring(0,pos));
				valList = valList.substring(pos+1);
			}
		}
		return res;
	}
	public void printValues(){
		line();
		if(values == null){
			logln("Values is null");
		}else{
			for(int i = 0; i < N; ++i){
				for(int j = 0; j < N; ++j){
					log(values[i][j] + Helper.space);
				}
				log(Helper.newline);
			}
		}
		line();
	}

	private void line(){logln("=========================================");}
	private void log(String msg){if(logging) System.out.print(msg);}
	private void logln(String msg){if(logging) System.out.println(msg);}
}