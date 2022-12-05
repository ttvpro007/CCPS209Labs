package src;

import java.util.Iterator;

public class PrimeGens {

	public static class TwinPrimes implements Iterator<Integer> {
		
		@Override
		public boolean hasNext() { return true; }

		@Override
		public Integer next() {
			
			while (true) {
				
				int prime = Primes.kthPrime(n++);
				if ( isTwinPrime(prime) ) return prime;
			}
		}
		
		private boolean isTwinPrime(int prime) {
			
			return Primes.isPrime(prime + 2);
		}

		private int n = 1;
	}

	public static class SafePrimes implements Iterator<Integer> {

		@Override
		public boolean hasNext() { return true; }

		@Override
		public Integer next() {
			
			while (true) {

				int prime = Primes.kthPrime(n++);
				if ( isSafePrime(prime) ) return prime;
			}
		}
		
		private boolean isSafePrime(int prime) {
			
			return (prime - 1) % 2 == 0 && Primes.isPrime( (prime - 1) / 2);
		}

		private int n = 2;
	}

	public static class StrongPrimes implements Iterator<Integer> {

		@Override
		public boolean hasNext() { return true; }

		@Override
		public Integer next() {
			
			while (true) {
				
				int prime = Primes.kthPrime(n++);
				if ( isStrongPrime(n - 1) ) return prime;
			}
		}
		
		private boolean isStrongPrime(int n) {
			
			return Primes.kthPrime(n) * 2 > Primes.kthPrime(n + 1) + Primes.kthPrime(n - 1);
		}
		
		private int n = 4;
	}
}
