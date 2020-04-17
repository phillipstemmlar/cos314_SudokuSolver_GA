import java.util.Comparator;

public class DNAsorter implements Comparator<DNA>{

	public int compare(DNA a, DNA b){
		return a.fitness - b.fitness;
	}
	
}