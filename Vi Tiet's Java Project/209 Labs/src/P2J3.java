import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.IntStream;

public class P2J3 {
	
	public static void reverseAscendingSubarrays(int[] items) {
		
		List< int[] > ascendingChunks = Utils.getConsecutiveIntegersWithCondition(items, (a, b) -> a < b );
		int addIndex = 0;
		
		for (int i = 0; i < ascendingChunks.size(); i++) {
			
			int[] temp = ascendingChunks.get(i);
			Utils.reverseIntArray( temp );
			
			for (int j = 0; j < temp.length; j++) {
				
				items[addIndex] = temp[j];
				addIndex++;
			}
		}
	}
	
	
	public static String pancakeScramble(String text) {
		
		StringBuilder sb = new StringBuilder();
		int[] pancakeFlipPositions = IntStream.range( 0, text.length() ).toArray();
		
		for (int i = 2; i <= text.length(); i++) {
			
			int[] t = Arrays.copyOfRange(pancakeFlipPositions, 0, i);
			Utils.reverseIntArray(t);
			
			for (int j = 0; j < t.length; j++) {
				
				pancakeFlipPositions[j] = t[j];
			}
		}
		
		for (int i = 0; i < text.length(); i++) {
			
			sb.append( text.charAt( pancakeFlipPositions[i] ) );
		}
		
		return sb.toString();
	}
	
	
	public static String reverseVowels(String text) {
		
		char[] textChars = text.toCharArray();
		Character[] vowels = new Character[] { 'a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U' };
		Stack<Character> vowelsInText = new Stack<>();
		List<Integer> upperCasePositions = new ArrayList<>();
		String result = "";
		
		for (int i = 0; i < textChars.length; i++) {
			
			if ( Arrays.asList(vowels).contains( textChars[i] ) ) {
				
				vowelsInText.push( textChars[i] );
				if ( Character.isUpperCase( textChars[i] ) ) {
					
					upperCasePositions.add(i);
				}
			}
		}
		
		for (int i = 0; i < textChars.length; i++) {
			
			if ( Arrays.asList(vowels).contains( textChars[i] ) ) {
				
				char charToAdd = vowelsInText.pop();
				boolean isUpper = upperCasePositions.contains(i);
				result += isUpper ? Character.toUpperCase(charToAdd) : Character.toLowerCase(charToAdd);
			}
			else {
				
				result += textChars[i];
			}
			
		}
		
		return result;
	}
}