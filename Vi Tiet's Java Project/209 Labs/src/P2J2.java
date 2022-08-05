

public class P2J2 {
	
	public static void Print(Object message) {
		System.out.println(message);
	}
	

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
	
	
	public static void main( String args[] ) {
		
	}
}