import java.util.List;

public class Primes {

	public static boolean isPrime(int n) {
		
		for (int i = 2; i < (int) Math.sqrt(n) + 1; i++) {
			
			if (n % i == 0) {
				
				return false;
			}
		}
		
		return true;
	}
	
	public static int kthPrime(int k) {

		return 0;
	}
	
	public static List<Integer> factorize(int n) {
		
		return null;
	}
}
