import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class P2J4 {

//	public static List<Integer> runningMedianOfThree(List<Integer> items) {
//		
//	}
//	
//	
//	public static int firstMissingPositive(List<Integer> items) {
//		
//	}
	
	
	public static void sortByElementFrequency(List<Integer> items) {
		
		Map<Integer, Integer> integerFrequencyMap = Utils.getIntegerElementFrequencyMap(items);
		
	}
	
	
//	public static List<Integer> factorFactorial(int n) {
//		
//	}
	
	
	public static void main(String[] args) {

		Map<Integer, Integer> m = Utils.getIntegerElementFrequencyMap( Arrays.asList(4, 4, 4, 4, 2, 2, 99999, 99999) );
		
		m.entrySet().forEach( entry -> {
			Utils.print( entry.getKey() + ":" + entry.getValue() );
		});
	}
}
