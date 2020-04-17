import java.util.*; 

public class Evolution {

	int populationSize;
	Integer[][] initValues;

	Integer[][] answer;

	List<Element> elements;
	Element winner;
	Element best;

	Boolean solved;

	private final int N;

	public int genCount;

	final double mutationChance;


	public Evolution(Integer[][] initvalues, double mutProb, int n, Integer[][] ans){
		winner = null;
		best = null;
		solved = false;
		mutationChance = mutProb;
		populationSize = 0;
		initValues = initvalues;
		N = n;
		answer = ans;
	}

	//===============================================================

	public void generatePopulation(int popSize){
		populationSize = popSize;
		winner = null;
		best = null;
		solved = false;

		elements = new ArrayList<>();
		for(int i = 0; i < populationSize; ++i){
			elements.add( new Element(initValues,N, answer));
		}
	}

	public void runSimulation(){

		// line();

		genCount = 0;
		Helper.saveCursor();
		while(!solved){
			genCount++;
			
			calcFitness();
			selection();
			mutation();
			checkSolved();

			// printBest();
			if(genCount > 1000000) break;
		}
		// System.out.print("\n");
	}
	
	//===============================================================

	private void calcFitness(){
		int minFit = Integer.MAX_VALUE;
		for(Element el : elements){
			el.calcFitness();
			if(el.fitness < minFit){
				minFit = el.fitness;
				best = el;
			}
		}
	}

	private void selection(){
		List<Element> pool = new ArrayList<>();
		for(Element el : elements){
			int fit = el.fitnessPerc();
			for(int i = 0; i < fit; ++i) pool.add(el);
		}

		elements = new ArrayList<>();
		for(int i = 0; i < populationSize; ++i){
			int a = (int)(Math.random() * pool.size());
			int b = (int)(Math.random() * pool.size());
			int c = (int)(Math.random() * pool.size());
			// Element newElement = crossover(pool.get(a), pool.get(b));
			Element newElement = crossover3(pool.get(a), pool.get(b), pool.get(c));
			elements.add(newElement);
		}
	}

	private Element crossover(Element a, Element b){
		Element res = a.clone();

		int midpoint_i = (int)(Math.random() * N);
		int midpoint_j = (int)(Math.random() * N);


		// System.out.println("m_i: " + midpoint_i + " | m_j: " + midpoint_j);

		// for(int i = midpoint_i; i < N; ++i){
		// 	for(int j = midpoint_j; j < N; ++j){

		// 		res.values[i][j] = b.values[i][j];

		// 	}
		// }

		for(int i = 0; i < N; ++i){
			for(int j = 0; j < N; ++j){
				if(!res.validCell(i, j) && (b.validCell(i, j) || i >= midpoint_i && j >= midpoint_j) ){
					res.values[i][j] = b.values[i][j];
				}

			}
		}

		return res;
	}

	private Element crossover3(Element a, Element b, Element c){
		Element res = a.clone();

		boolean row = (Math.random() < 0.5);

		if(row){		//row
			for(int i = 3; i < N; ++i){
				for(int j = 0; j < N; ++j){

					if(i < 6)	res.values[i][j] = b.values[i][j];
					else res.values[i][j] = c.values[i][j];

				}
			}
		}else{ 		  //col
			for(int i = 0; i < N; ++i){
				for(int j = 3; j < N; ++j){

					if(j < 6)	res.values[i][j] = b.values[i][j];
					else res.values[i][j] = c.values[i][j];

				}
			}

		}


		return res;
	}

	private void mutation(){
		for(Element el : elements) mutate(el);
	}

	private void mutate(Element el){
		// System.out.println("___MUT____");
		for(int i = 0; i < N; ++i){
			for(int j = 0; j < N; ++j){

				if(initValues[i][j] == 0 && !el.validCell(i, j)){
					double rand = Math.random();
					if(rand  < mutationChance){
						el.values[i][j] = Helper.randomRange(1, 10);
					}
				}

			}
		}
	}

	private void checkSolved(){
		for(Element el : elements){
			el.calcFitness();
			if(el.fitness <= 0){
				winner = el;
				best = winner;
				// printBest();
				solved = true;
				return;
			}
		}			

	}

	//===============================================================

	public void printPop(){
		System.out.println("___POP____");
		for(Element el: elements) el.print();		
	}

	public void printBest(){

		Helper.cursorToStart();
		Helper.cursorMoveUp(4);
		Helper.cursorMoveRight(14);
		Helper.eraseLine();

		Helper.restoreCursor();
		//================ CHANGE
		//================

		if(best == null) System.out.print("null\n");
		else {
			best.calcFitness();
			best.print();
			System.out.println("dupCount: " + best.dupCount);
			System.out.println("misCount: " + best.misCount);
			System.out.println("fitness:  " + best.fitness);
			System.out.println("fitnessP: " + best.fitnessPerc() + " | " + best.fitnessString() );
		}
		System.out.print("Generations: " + genCount + "\n");
		System.out.print("Population Size: " + populationSize + "\n");
		System.out.print("Mutation Chance: " + (int)(mutationChance*100) + "%\n");
	}

	private void line()  {System.out.println("=========================================");}
	private void line2() {System.out.println("-----------------------------------------");}
}