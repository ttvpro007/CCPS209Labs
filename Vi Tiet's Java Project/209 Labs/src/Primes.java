import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Primes {

	public static boolean isPrime(int n) {
		
		return Collections.binarySearch(getPrimes(), n) >= 0;
	}
	
	public static int kthPrime(int k) {
		
		while (getPrimes().size() < k) {
			
			expandPrimes(k * 15);
		}

		return getPrimes().get(k);
	}
	
	public static List<Integer> factorize(int n) {
		
		int index = 0;
		int prime = 0;
		List<Integer> factors = new ArrayList<>();	
		
		do {
			
			prime = getPrimes().get(index);
			index++;
			
			while (n % prime == 0) {
				
				n /= prime;
				factors.add(prime);
			}
		}
		while ( prime < n);
		
		return factors;
	}
	
	public static List<Integer> sieveOfAtkinRange(int lowerLimit, int upperLimit){
	
		boolean[] sieve = new boolean[upperLimit + 1];
		
//		for (int i = sieve.size() > 0 ? lowerLimit : 0; i <= upperLimit; i++) {
//			
//			sieve.add(false);
//		}
		
		int r = (int) Math.sqrt(upperLimit);
		
		lowerLimit = Math.max(1, lowerLimit);
		List<Integer> primes = lowerLimit >= 5 ? new ArrayList<>() : new ArrayList<>( Arrays.asList(2, 3) );

//		for (int x = 1; x <= Math.sqrt(upperLimit + upperLimit * upperLimit) / Math.sqrt(3); x++) {
		for (int x = 1; x <= r; x++) {
			
			int xx = x * x;
			int xx3 = 3 * xx;
			for (int y = 1; y <= r; y++) {
				
				int yy = y * y;
				int n = 4 * xx + yy;
				int nMod12 = n % 12;
				if (n <= upperLimit && (nMod12 == 1 || nMod12 == 5) ) {

					sieve[n] = !sieve[n];
//					sieve.set(n, !sieve.get(n) );
				}
				
				n = xx3 + yy;
				if (n <= upperLimit && n % 12 == 7) {

					sieve[n] = !sieve[n];
//					sieve.set(n, !sieve.get(n) );
				}
				
				n = xx3 - yy;
				if (x > y && n <= upperLimit && n % 12 == 11) {

					sieve[n] = !sieve[n];
//					sieve.set(n, !sieve.get(n) );
				}
			}
		}
		
		for (int i = 5; i < r; i++) {

			if ( sieve[i] ) {
//			if ( sieve.get(i) ) {
				
				for (int j = i * i; j <= upperLimit; j += i * i) {
					
					sieve[j] = false;
//					sieve.set(j, false);
				}
			}
		}
		
		for (int i = Math.max(5, lowerLimit); i <= upperLimit; i++) {
			
			if ( sieve[i] ) {
//			if ( sieve.get(i) ) {
				
				primes.add(i);
			}
		}
		
		return primes;
	}
	
	private static void expandPrimes(int upperLimit) {

		if (getPrimes().size() > 0 && upperLimit > getPrimes().get(getPrimes().size() - 1) ){

			Utils.print("Expanding primes list from " + (getPrimes().get(getPrimes().size() - 1) + 1) + " to " + upperLimit);
			getPrimes().addAll( sieveOfAtkinRange( getPrimes().get(getPrimes().size() - 1) + 1, upperLimit ) );
		}
		else if (getPrimes().size() == 0) {
			
			Utils.print("Initialize primes list");
			getPrimes().addAll( sieveOfAtkinRange(1, upperLimit) );
		}
		else {
			
			Utils.print("Upper limit " + upperLimit + " is already in primes list. Expanding is not necessary.");
		}
	}
	
	public static List<Integer> getPrimes() {
		
		return primes;
	}

	private static List<Integer> primes = new ArrayList<>();
//	private static List<Boolean> sieve = new ArrayList<>();
}
