
public class CCPS209Sandbox {
	
	public static void main(String[] args) {

		Utils.print(Primes.kthPrime(5));
		Utils.print(Primes.getPrimes());
		Utils.print(Primes.kthPrime(11));
		Utils.print(Primes.getPrimes());
		Utils.print(Primes.kthPrime(20));
		Utils.print(Primes.getPrimes());
		Utils.print(Primes.kthPrime(42));
		Utils.print(Primes.getPrimes());
		Utils.print(Primes.kthPrime(100));
		Utils.print(Primes.getPrimes());
		Utils.print(Primes.kthPrime(444));
		Utils.print(Primes.kthPrime(1234));
		Utils.print(Primes.kthPrime(2500));
		Utils.print(Primes.kthPrime(9999));
		Utils.print(Primes.kthPrime(12345));
		Utils.print(Primes.kthPrime(30000));
		Utils.print(Primes.kthPrime(67890));
		Utils.print(Primes.kthPrime(123456));
		Utils.print(Primes.getPrimes());
		
//		Utils.print(Primes.sieveOfAtkinRange(0, 215));
		
		
//		Utils.print(Primes.sieveOfAtkinRange(48, 215));
//		Utils.print(Primes.sieveOfAtkinRange(0, 997));
		
//		class PrimeGenCaller implements Utils.Callable {
//
//			private int lower;
//			private int upper;
//
//			public PrimeGenCaller(int lower, int upper) {
//				
//				this.lower = lower;
//				this.upper = upper;
//			}
//			
//			public void setLower(int lower) {
//				
//				this.lower = lower;
//			}
//			
//			public void setUpper(int upper) {
//				
//				this.upper = upper;
//			}
//			
//			@Override
//			public void call() {
//
//				Utils.print(Primes.sieveOfAtkinRange(lower, upper));
//			}
//		}
//		
//		var p1 = new PrimeGenCaller(0, 10000);
//		Utils.printExecutionTime(p1);
//
//		p1.setLower(48);
//		p1.setUpper(30000);
//		Utils.printExecutionTime(p1);
	}
}
