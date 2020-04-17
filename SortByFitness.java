import java.util.Comparator;

public class SortByFitness implements Comparator<Element>{

	public int compare(Element a, Element b){
		return a.fitness - b.fitness;
	}
	
}