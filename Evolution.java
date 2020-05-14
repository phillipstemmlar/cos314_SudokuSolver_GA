import java.util.*; 

public class Evolution {

	int populationSize;
	int elitism;
	int maxgen;
	Integer[][] initValues;

	List<Element> elements;
	Element winner;
	Element best;

	Boolean solved;

	private int N;

	public int genCount;
	int changeCount;
	int prevMinFitness;

	double mutationChance;


	public Evolution(Integer[][] initvalues, int n){
		winner = null;
		best = null;
		solved = false;
		populationSize = 0;
		initValues = initvalues;
		N = n;
		genCount = 0;
		changeCount = 0;
	}

	//===============================================================

	public void generatePopulation(int popSize){
		populationSize = popSize;
		winner = null;
		best = null;
		solved = false;
		genCount = 0;
		changeCount = 0;
		prevMinFitness = Integer.MAX_VALUE;

		elements = new ArrayList<>();
		for(int i = 0; i < populationSize; ++i){
			elements.add( new Element(initValues,N));
		}
	}

	public void runSimulation(double mutProb, int elite, int maxg){

		maxgen = maxg;
		mutationChance = mutProb;
		line();
		elitism = elite;
		genCount = 0;
		prevMinFitness = Integer.MAX_VALUE;
		Helper.saveCursor();
		while(!solved){
			genCount++;
			
			calcFitness();
			selection();
			calcValidity();
			// mutation();
			// calcValidity();
			checkSolved();

			printBest();
			if( changeCount >= maxgen ) {
				generatePopulation(populationSize);
			};
			// break;
		}
		genCount--;
		System.out.print("\n");
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
		if(prevMinFitness > minFit){
			changeCount = 0;
		}else{
			changeCount++;
		}
		prevMinFitness = minFit;
	}

	private void selection(){
		List<Element> pool = new ArrayList<>();
		for(Element el : elements){
			pool.add(el);
		}
		Collections.sort(pool,new SortElementByFitnessDESC());

		elements = new ArrayList<>();
		for(int i = populationSize-1; i >= 0; --i){
			Element ii = pool.get(i);
			if(i >= elitism){
				Element p1 = pool.get((int)(i * Math.random()));
				Element p2 = pool.get((int)(i * Math.random()));
				Element newElement;
				if(Math.random() < 0.55) newElement = crossover3(ii, p1, p2);
				else newElement = crossover2(p1, p2);
				mutate(newElement);
				elements.add(newElement);
			}else{
				elements.add(ii);
			}
		}
	}

	private Element crossover3(Element a, Element b, Element c){
		Element res = a.clone();

		boolean row = (Math.random() < 0.5);

		if(row){		//row
			for(int oi = 1; oi < 3; ++oi){
				for(int oj = 0; oj < 3; ++oj){

					for(int ii = 0; ii < 3; ++ii){
						for(int ij = 0; ij < 3; ++ij){

							int i = oi*3 + ii;
							int j = oj*3 + ij;

							if(oi == 1){
								res.values[i][j] = b.values[i][j];
							}else if(oi == 2){
								res.values[i][j] = b.values[i][j];
							}

						}
					}

				}
			}
		}else{
			for(int oi = 0; oi < 3; ++oi){
				for(int oj = 1; oj < 3; ++oj){

					for(int ii = 0; ii < 3; ++ii){
						for(int ij = 0; ij < 3; ++ij){

							int i = oi*3 + ii;
							int j = oj*3 + ij;

							if(oj == 1){
								res.values[i][j] = b.values[i][j];
							}else if(oj == 2){
								res.values[i][j] = b.values[i][j];
							}

						}
					}

				}
			}
		}


		// boolean row = (Math.random() < 0.5);

		// if(row){		//row
		// 	for(int i = 3; i < N; ++i){
		// 		for(int j = 0; j < N; ++j){

		// 			if(i < 6)	res.values[i][j] = b.values[i][j];
		// 			else res.values[i][j] = c.values[i][j];

		// 		}
		// 	}
		// }else{ 		  //col
		// 	for(int i = 0; i < N; ++i){
		// 		for(int j = 3; j < N; ++j){

		// 			if(j < 6)	res.values[i][j] = b.values[i][j];
		// 			else res.values[i][j] = c.values[i][j];

		// 		}
		// 	}

		// }


		return res;
	}

	private Element crossover2(Element a, Element b){
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
				// if(i >= midpoint_i && j >= midpoint_j){
					res.values[i][j] = b.values[i][j];
				}

			}
		}

		return res;
	}


	private void mutation(){
		for(Element el : elements) mutate(el);
	}

	private void mutate(Element el){
		for(int oj = 0; oj < 3; ++oj){
			for(int oi = 0; oi < 3; ++oi){

				int fx = 0, fy = 0, sx = 0, sy = 0;
				if(Math.random()  < mutationChance){
					do{
					int first = Helper.randomRange(0, 9);
					int second = 0;
					do{ second = Helper.randomRange(0, 9); }while(second == first);

					fx = oj*3 + Helper.col_x(first, 3);
					fy = oi*3 + Helper.row_y(first, 3);

					sx = oj*3 + Helper.col_x(second, 3);
					sy = oi*3 + Helper.row_y(second, 3);

					}while(!el.swapValues(fy,fx,sy,sx));

				}

			}
		}

		// for(int oi = 0; oi < 3; ++oi){
		// 	for(int oj = 0; oj < 3; ++oj){

		// 		if(initValues[oi][oj] == 0 && !el.validCell(oi, oj)){
		// 			double rand = Math.random();
		// 			if(rand  < mutationChance){
					
						

		// 			}
		// 		}

		// 	}
		// }
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

	private void calcValidity(){
		for(Element el : elements){
			el.calcValidity();
		}			
	}

	//===============================================================

	public void printPop(){
		System.out.println("___POP____");
		for(Element el: elements) el.print();		
	}

	public void printBest(){

		Helper.cursorToStart();
		Helper.cursorMoveUp(6);
		Helper.cursorMoveRight(14);
		Helper.eraseLine();

		Helper.restoreCursor();
		if(best == null) System.out.print("null\n");
		else {
			System.out.print("Best of Current generation:\n");
			best.calcFitness();
			best.print();
			System.out.println("fitnessP: " + best.fitnessPerc() + " | " + best.fitnessString() );
		}
		// System.out.print("Generations: " + genCount + " | " + changeCount + " | " + prevMinFitness + "\n");
		System.out.print("Generations: " + genCount + "\n");
		System.out.print("Population Size: " + populationSize + "\n");
		System.out.print("Elitism: " + elitism + "\n");
		System.out.print("Mutation Chance: " + Helper.round(mutationChance,4) + " | " + (int)(mutationChance*100) + "%\n");
		System.out.print("Restart after constant generations: " + maxgen + "\n");
	}

	private void line()  {System.out.println("=========================================");}
	private void line2() {System.out.println("-----------------------------------------");}
}