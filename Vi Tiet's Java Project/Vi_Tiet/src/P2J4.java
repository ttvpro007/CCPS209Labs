package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Hashtable;

public class P2J4 {

	public static List<Integer> runningMedianOfThree(List<Integer> items) {
		
		var r = new ArrayList<Integer>();
		if (items.size() <= 2) {
			
			for (int i = 0; i < items.size(); i++) {
				
				r.add(items.get(i));
			}
		} else {

			for (int i = 0; i < 2; i++) {

				r.add(items.get(i));
			}
			
			for (int i = 2; i < items.size(); i++) {
				
				var subList = new ArrayList<Integer>();
				for (int j = 0; j < 3; j++) {
					
					subList.add(items.get(i - j));
				}
				Collections.sort(subList);
				r.add(subList.get(1));
			}
		}
		return r;
	}

	
	public static int firstMissingPositive(List<Integer> items) {
		
		var appearance_set = new HashSet<Integer>();
		
		for (int i = 0; i < items.size(); i++) {
			
			if ( !appearance_set.contains( items.get(i) ) ) {
				
				appearance_set.add( items.get(i) );
			}
		}
		
		int current_int = 1;
		while(current_int < items.size() + 1) {
			
			if ( !appearance_set.contains(current_int) ) {
				
				return current_int;
			}
			current_int++;
		}
		
		return current_int;
	}
	
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
