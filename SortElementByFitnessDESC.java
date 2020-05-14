import java.util.*; 
public class SortElementByFitnessDESC implements Comparator<Element>{

	public int compare(Element a, Element b) 
	{ 
			return b.fitnessPerc() - a.fitnessPerc(); 
	} 

} 