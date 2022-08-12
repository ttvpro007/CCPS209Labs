import java.util.Arrays;
import java.util.List;

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
		
		var integerFrequencySortedByValueThenKeyMap = Utils.sortByValueThenKey( Utils.getElementFrequencyMap(items), Utils.SORT_ORDER.DESCENDING, Utils.SORT_ORDER.ASCENDING );

		int setIndex = 0;
		for ( var e : integerFrequencySortedByValueThenKeyMap.entrySet() ) {
			
			for (int i = 0; i < e.getValue(); i++) {
				
				items.set( setIndex, e.getKey() );
				setIndex++;
			}
		}
	}
	
	
//	public static List<Integer> factorFactorial(int n) {
//		
//	}
	
	
	public static void main(String[] args) {

		List<Integer> a = Arrays.asList(42, 42, 17, 42, 42, 17, 5, 5);
		sortByElementFrequency(a);
		Utils.print(a);
	}
}
