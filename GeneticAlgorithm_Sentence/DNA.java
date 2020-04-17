import java.util.*;

public class DNA implements Comparable<DNA>{

	public int fitness;

	public char[] values;
	public String Goal;

	public DNA(String goal){
		fitness = 0;
		Goal = goal;
		randomizeVales();
	}

	//==================================================================================

	public void calcFitness(){
		int counter = 0;
		char[] goal = Goal.toCharArray();
		for(int i = 0; i < values.length; ++i){
			if(values[i] == goal[i]) counter++;
		}
		fitness = counter;
	}

	public int fitnessPerc(){
		return (int)Math.round((((float)fitness/(float)Goal.length())*100.0));
	}

	//==================================================================================

	public void randomizeVales(){
		values = new char[Goal.length()];
		for(int i = 0; i < Goal.length(); ++i) values[i] = randomAlphabetChar();
	}

	public static char randomAlphabetChar(){
		Random r = new Random();
		if(Evolution.AllowSpace){
			int c = r.nextInt(27);
			if( c == 26) return ' ';
			else return (char)(c + 'a'); 
		}
		return (char)(r.nextInt(26) + 'a'); 
	}

	//==================================================================================
	
	public DNA clone(){
		DNA res = new DNA(Goal);
		res.fitness = fitness;
		res.values = new char[values.length];
		for(int i = 0; i < values.length; ++i) res.values[i] = values[i];
		return res;
	}

	public String fitnessToStr(){
		return "" + Math.round((((float)fitness/(float)Goal.length())*100.0)) + "%";
	}

	public String toString(){
		return new String(values);
	}
	public void print(){
		System.out.println(toString() + " | " + fitnessToStr());
	}

	@Override
	public int compareTo(DNA other){
			return this.fitness - other.fitness;
	}
}