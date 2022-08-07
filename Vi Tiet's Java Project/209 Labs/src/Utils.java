import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class Utils {
	
	public static void print(Object message) {
		System.out.println(message);
	}
	
	public static List< int[] > getConsecutiveIntegersWithCondition(int[] items, BiFunction<Integer, Integer, Boolean> condition) {
		
		List< int[] > subLists = new ArrayList<>();
		int start = -1;

		for (int i = 0; i < items.length - 1; i++) {
			
			if ( condition.apply( items[i], items[i + 1] ) ) {
				
				if (start == -1) {

					start = i;
				}
			}
			else if (start != -1) {
				
				subLists.add( Arrays.copyOfRange(items, start, i + 1) );
				start = -1;
			}
			else if (start == -1) {
				
				subLists.add( new int[] { items[i] } );
			}
			
			if (i == items.length - 2) {
				
				if (start != -1) {
				
					subLists.add( Arrays.copyOfRange(items, start, i + 2) );
				}
				else {

					subLists.add( new int[] { items[i + 1] } );
				}
			}
		}
		
		return subLists;
	}
	
	public static void reverseIntArray(int[] intArray) {
		
		int halfIndex = intArray.length / 2;
		
		for (int i = 0; i < halfIndex; i++) {
			
			int temp = intArray[i];
			intArray[i] = intArray[intArray.length - i - 1];
			intArray[intArray.length - i - 1] = temp;
		}
	}
}
