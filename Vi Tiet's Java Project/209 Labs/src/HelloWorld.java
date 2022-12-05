import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HelloWorld {
	
	public static void main(String[] args) {
		
		List<Integer> items = Arrays.asList( new Integer[] {7, 5, 2, 3, 10, 2, 9999999, 4, 6, 3, 1, 9, 2} );
		Utils.print( P2J4.firstMissingPositive(items) );
	}
}