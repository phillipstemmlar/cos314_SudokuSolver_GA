public class Main{
	public static void main(String[] args) {
		

		Boolean space = false;
		for(char c : args[0].toCharArray()){
			if(c == ' '){
				space = true;
				break;
			}
		}

		int pop = 1000;
		double mut = 0.01;

		Evolution evo = new Evolution(args[0],mut,space);

		evo.generatePopulation(pop);
		evo.runSimulation();

		DNA res = evo.winner;

		if(res != null){
			System.out.println("====================WINNER==================");
			System.out.println(res.toString());
		}else{
			System.out.println("======================================\nNo Solution found!");

		}
		System.out.println("===========================================");

	}
}