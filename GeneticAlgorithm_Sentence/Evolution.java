import java.util.*;

public class Evolution {

	String goalText; 
	Boolean solved;
	public static Boolean AllowSpace;

	DNA winner;
	DNA best;

	List<DNA> DNAs;
	int populationSize;

	int genCount;
	int avgFit;

	final double mutationChance;

	public Evolution(String text,double mutProb,boolean allowSpace){
		winner = null;
		best = null;
		solved = false;
		mutationChance = mutProb;
		AllowSpace = allowSpace;
		goalText = text;
		populationSize = 0;
	}

	//===============================================================

	public void generatePopulation(int popSize){

		populationSize = popSize;

		DNAs = new ArrayList<>();
		for(int i = 0; i < populationSize; ++i) DNAs.add(new DNA(goalText));

	}

	public void runSimulation(){

		line();
		System.out.println(goalText + "\t("+goalText.length()+")");
		line();

		printPop();
		line();

		genCount = 0;
		while(!solved){
			genCount++;
			
			calcFitness();
			selection();
			mutation();
			checkSolved();

			printBest();
		}
		System.out.print("\n");
		// line();
	}
	
	//===============================================================

	private void calcFitness(){
		int maxFit = -1;
		avgFit = 0;
		for(DNA dna : DNAs){
			dna.calcFitness();
			if(dna.fitness > maxFit){
				maxFit = dna.fitness;
				best = dna;
			}
			avgFit += dna.fitnessPerc();
		}
		avgFit = (int)Math.round((float)avgFit/(float)populationSize);
	}

	private void selection(){
		List<DNA> pool = new ArrayList<>();
		for(DNA dna : DNAs){
			int fitp = dna.fitnessPerc();
			for(int i = 0; i < fitp*fitp +1; ++i) pool.add(dna);
		}

		DNAs = new ArrayList<>();
		for(int i = 0; i < populationSize; ++i){
			int a = (int)(Math.random() * pool.size());
			int b = (int)(Math.random() * pool.size());
			DNA newDNA = crossover(pool.get(a), pool.get(b));
			DNAs.add(newDNA);
		}
	}

	private DNA crossover(DNA a, DNA b){
		DNA res = a.clone();
		int midpoint = (int)(Math.random() * a.values.length);
		for(int i = midpoint; i < a.values.length; ++i ) res.values[i] = b.values[i];
		return res;
	}

	private void mutation(){
		for(DNA dna : DNAs){
			for(int i = 0; i < dna.values.length; ++i){
				double rand = Math.random();
				if(rand  < mutationChance){
					dna.values[i] = DNA.randomAlphabetChar();
				}
			}
		}
	}

	private void checkSolved(){
		char[] goal = goalText.toCharArray();
		for(DNA dna: DNAs){

			Boolean correct = true;
			for(int i = 0; correct && i < dna.values.length; ++i){
				if(dna.values[i] != goal[i]){correct = false;}
			}

			if(correct){
				winner = dna;
				best = winner;
				solved = true;
				return;
			}
		}
	}

	//===============================================================

	public void printPop(){
		System.out.println("___POP____");
		for(DNA dna : DNAs) dna.print();
	}

	public void printBest(){
		System.out.print("\r");
		if(best == null) System.out.print("null | ");
		else {
			best.calcFitness();
			System.out.print(best.toString() +  " | " + best.fitnessToStr() + " | ");
		}

		System.out.print("Gen: " + genCount + " | avg: " + avgFit + "%");
	}

	private void line()  {System.out.println("=========================================");}
	private void line2() {System.out.println("-----------------------------------------");}

}