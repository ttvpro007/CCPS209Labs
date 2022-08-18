import java.util.Map.Entry;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Utils {
	
	public static void print(Object message) {
		
		System.out.println(message);
	}
	
	public static int[] trimEndZeroes(int[] integerSequence) {
		
		int end = 1;
		
		for (int i = 1; i < integerSequence.length; i++) {
			
			if (integerSequence[i] != 0) {
				
				end = i + 1;
			}
		}

		return Arrays.copyOfRange(integerSequence, 0, end);
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
	
	public static <K> Map<K, Integer> getElementFrequencyMap(List<K> items) {
		
		Map<K, Integer> intFrequencyMap = new HashMap<>();
		
		for (var item : items) {
			
			if ( intFrequencyMap.containsKey(item) ) {
				
				intFrequencyMap.put(item, intFrequencyMap.get(item) + 1);
			}
			else {
				
				intFrequencyMap.put(item, 1);
			}
		}
		
		return intFrequencyMap;
	}
	
	public enum SORT_ORDER {
		
		ASCENDING,
		DESCENDING
	}
	
	// https://stackoverflow.com/questions/8119366/sorting-hashmap-by-values
	public static <K extends Comparable<K>, V extends Comparable<V> > Map<K, V> sortByValueThenKey(Map<K, V> unsortedMap, SORT_ORDER valueSortOrder, SORT_ORDER keySortOrder) {
		
		var list = new LinkedList<>( unsortedMap.entrySet() );
		
		list.sort( (o1, o2) -> {
			
			var first = valueSortOrder == SORT_ORDER.ASCENDING ? o1 : o2; 
			var second = valueSortOrder == SORT_ORDER.ASCENDING ? o2 : o1; 
			
			if ( first.getValue().equals( second.getValue() ) ) {

				if (keySortOrder == SORT_ORDER.ASCENDING) {
				
					return o1.getKey().compareTo( o2.getKey() );
				}
				else {
					
					return o2.getKey().compareTo( o1.getKey() );
				}
			}
			else {

				return first.getValue().compareTo( second.getValue() );
			}
		} );
		
		return list.stream().collect( Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, LinkedHashMap::new) );
	}
	
	public static <K extends Comparable<K>, V extends Comparable<V> > Map<K, V> sortByKey(Map<K, V> unsortedMap, SORT_ORDER keySortOrder) {
		
		var list = new LinkedList<>( unsortedMap.entrySet() );
		
		list.sort( (o1, o2) -> {
			
			if (keySortOrder == SORT_ORDER.ASCENDING) {
			
				return o1.getKey().compareTo( o2.getKey() );
			}
			else {
				
				return o2.getKey().compareTo( o1.getKey() );
			}
		} );
		
		return list.stream().collect( Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, LinkedHashMap::new) );
	}
	
	public static <K, V> Map<V, List<K> > groupByValue(Map<K, V> map) {
		
		var groupedMap = new LinkedHashMap<V, List<K> >();
		
		for ( var e : map.entrySet()  ) {

			if ( groupedMap.containsKey( e.getValue() ) ) {
				
				groupedMap.get( e.getValue() ).add( e.getKey() );
			}
			else {
				
				groupedMap.put( e.getValue(), new ArrayList<> ( Arrays.asList( e.getKey() ) ) );
			}
		}
		
		return groupedMap;
	}
	
	public static <T> List<T> flatten2DList(List< List<T> > list2D) {
		
		List<T> flattenedList = new ArrayList<>();
		
		for (var list1D : list2D) {
			
			list1D.stream().forEach(flattenedList::add);
		}
		
		return flattenedList.stream().collect( Collectors.toList() );
	}
	
	public static int gcd(int a, int b) {
		
		while(b != 0) {
			
			int t = a;
			a = b;
			b = t % b;
		}
		
		return a;
	}
	
	// https://stackoverflow.com/questions/3422673/how-to-evaluate-a-math-expression-given-in-string-form
	public static double eval(final String str) {
		
	    return new Object() {
	    	
	        int pos = -1, ch;
	        
	        void nextChar() {
	        	
	            ch = (++pos < str.length()) ? str.charAt(pos) : -1;
	        }
	        
	        boolean eat(int charToEat) {
	        	
	            while (ch == ' ') nextChar();	            
	            if (ch == charToEat) {
	            	
	                nextChar();
	                return true;
	            }
	            return false;
	        }
	        
	        double parse() {
	        	
	            nextChar();
	            double x = parseExpression();
	            if (pos < str.length()) {
	            	
	            	throw new RuntimeException("Unexpected: " + (char)ch);
	            }
	            return x;
	        }
	        
	        // Grammar:
	        // expression = term | expression `+` term | expression `-` term
	        // term = factor | term `*` factor | term `/` factor
	        // factor = `+` factor | `-` factor | `(` expression `)` | number
	        //        | functionName `(` expression `)` | functionName factor
	        //        | factor `^` factor
	        
	        double parseExpression() {
	        	
	            double x = parseTerm();
	            for (;;) {
	            	
	                if      (eat('+')) x += parseTerm(); // addition
	                else if (eat('-')) x -= parseTerm(); // subtraction
	                else return x;
	            }
	        }
	        
	        double parseTerm() {
	        	
	            double x = parseFactor();
	            for (;;) {
	            	
	                if      (eat('*')) x *= parseFactor(); // multiplication
	                else if (eat('/')) x /= parseFactor(); // division
	                else return x;
	            }
	        }
	        
	        double parseFactor() {
	        	
	            if (eat('+')) return +parseFactor(); // unary plus
	            if (eat('-')) return -parseFactor(); // unary minus
	            
	            double x;
	            int startPos = this.pos;
	            if (eat('(')) { // parentheses
	            	
	                x = parseExpression();
	                if (!eat(')')) throw new RuntimeException("Missing ')'");
	            }
	            else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
	            	
	                while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
	                x = Double.parseDouble(str.substring(startPos, this.pos));
	            }
	            else if (ch >= 'a' && ch <= 'z') { // functions
	            	
	                while (ch >= 'a' && ch <= 'z') nextChar();
	                String func = str.substring(startPos, this.pos);
	                if (eat('(')) {
	                    x = parseExpression();
	                    if (!eat(')')) throw new RuntimeException("Missing ')' after argument to " + func);
	                } else {
	                    x = parseFactor();
	                }
	                if (func.equals("sqrt")) x = Math.sqrt(x);
	                else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
	                else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
	                else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
	                else throw new RuntimeException("Unknown function: " + func);
	            }
	            else {
	            	
	                throw new RuntimeException("Unexpected: " + (char)ch);
	            }
	            
	            if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation
	            
	            return x;
	        }
	    }.parse();
	}
	
	public static int clamp(int value, int min, int max) {

		return Math.min( Math.max(min, value), max );
	}
	
	public static double clamp(double value, double min, double max) {

		return Math.min( Math.max(min, value), max );
	}
	
	public static double lerp(double a, double b, double t) {

		t = clamp(t, 0, 1);
		return t * (b - a) + a;
	}
	
	public static int Oscillate(int value, int min, int max)
	{
		value = clamp(value, min, max);
	    int range = max - min;
	    return min + Math.abs( ( (value + range) % (range * 2) ) - range );
	}
	
	public static double Oscillate(double value, double min, double max)
	{
		value = clamp(value, min, max);
		double range = max - min;
	    return min + Math.abs( ( (value + range) % (range * 2) ) - range );
	}
	
	public static double round(double value, int places) {
		
		double scale = Math.pow(10, places);
		return Math.round(value * scale) / scale;
	}
}
