import java.util.Arrays;
import java.util.HashSet;

public class P2J2 {

	public static String removeDuplicates(String text) {
		
		if (text == "") {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		Character addedChar = text.charAt(0);
		
		sb.append(addedChar);
		
		for (int i = 1; i < text.length(); i++) {
			
			Character currentChar = text.charAt(i);
			
			if ( !currentChar.equals(addedChar) ) {
				
				sb.append(currentChar);
				addedChar = currentChar;
			}
		}
		
		return sb.toString();
	}
	
	
	public static String uniqueCharacters(String text) {
		
		HashSet<Character> uniqueCharSet = new HashSet<Character>();
		StringBuilder sb = new StringBuilder();
		
		for( Character c : text.toCharArray() ) {
			
			if ( uniqueCharSet.add(c) ) {

				sb.append(c);
			}
		}

		return sb.toString();
	}
	
	
	public static int countSafeSquaresRooks(int n, boolean[][] rooks) {

		int result = 0;
		
		boolean[] safeRows = new boolean[n];
		Arrays.fill(safeRows, true);
		boolean[] safeCols = new boolean[n];
		Arrays.fill(safeCols, true);
		
		for (int i = 0; i < n; i++) {
			
			for (int j = 0; j < n; j++) {
				
				if (rooks[i][j] == true) {
					
					safeRows[i] = false;
					safeCols[j] = false;
				}
			}
		}
		
		result = countTrue(safeRows) * countTrue(safeCols);
		
		return result;
	}
	
	private static int countTrue(boolean[] a) {
		
		int count = 0;
		
		for (boolean b : a) {
			
			if (b == true) {
				
				count++;
			}
		}
		
		return count;
	}
	
	
	public static int recaman(int n) {
		
		boolean[] seen = new boolean[10 * n];
		seen[0] = true;
		int result = 0;
		
		for (int i = 1; i <= n; i++) {


			int potentialResult = result - i;
			
			if ( potentialResult > 0 && !seen[potentialResult] ) {

				result = potentialResult;
			}
			else {

				result += i;
			}
			
			seen[result] = true;
		}
		
		return result;
	}
}