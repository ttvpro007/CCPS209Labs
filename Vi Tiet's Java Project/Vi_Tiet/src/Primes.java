package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Primes {

	public static List<Integer> getPrimes() {
		
		return primes;
	}
	
	public static boolean isPrime(int n) {

		if (primes == null) {
			
			initializePrimes(100);
		}
		
		int k = n;
		if (n > getPrimes().get( getPrimes().size() - 1 ) ) {
			
			expandPrimes(k <<= 1, false);
		}
		
		return Collections.binarySearch(getPrimes(), n) >= 0;
	}
	
	public static int kthPrime(int k) {

		if (primes == null) {
			
			initializePrimes(100);
		}
		
		int n = k;
		while (getPrimes().size() <= k) {
			
			expandPrimes(n <<= 1, false);
		}

		return getPrimes().get(k);
	}
	
	public static List<Integer> factorize(int n) {

		if (primes == null) {
			
			initializePrimes(100);
		}
		
		int index = 0;
		int prime = 0;
		List<Integer> factors = new ArrayList<>();	
		
		do {
			
			prime = kthPrime(index);
			index++;
			
			while (n % prime == 0) {
				
				n /= prime;
				factors.add(prime);
			}
		}
		while (prime < n);
		
		return factors;
	}
	
	//https://stackoverflow.com/questions/1569127/c-implementation-of-the-sieve-of-atkin
	public static List<Integer> sieveOfAtkinRange(int lowerLimit, int upperLimit){

	    int[] buffer = null;
	    List<Integer> primes = new ArrayList<>();
		
	    if (upperLimit < 7) { buffer = new int[0]; }
        else {
            var nrng = upperLimit - 7; var lmtw = nrng / 60;
            //initialize sufficient wheels to non-prime
            buffer = new int[ (lmtw + 1) ];

            //Put in candidate primes:
            //for the 4 * x ^ 2 + y ^ 2 quadratic solution toggles - all x odd y...
            int n = 6; // equivalent to 13 - 7 = 6...
            for (int x = 1, y = 3; n <= nrng; n += (x << 3) + 4, ++x, y = 1) {
                var cb = n; if (x <= 1) n -= 8; //cancel the effect of skipping the first one...
                for (int i = 0; i < 15 && cb <= upperLimit; cb += (y << 2) + 4, y += 2, ++i) {
                    var cbd = cb / 60; var cm = modLUT[ (cb % 60) ];
                    if (cm != 0)
                        for (int c = cbd, my = y + 15; c < buffer.length; c += my, my += 30) {
                            buffer[c] ^= cm;
                        }
                }
            }
            //for the 3 * x ^ 2 + y ^ 2 quadratic solution toggles - x odd y even...
            n = 0; // equivalent to 7 - 7 = 0...
            for (int x = 1, y = 2; n <= nrng; n += ((x + x + x) << 2) + 12, x += 2, y = 2) {
                var cb = n;
                for (var i = 0; i < 15 && cb <= upperLimit; cb += (y << 2) + 4, y += 2, ++i) {
                    var cbd = cb / 60; var cm = modLUT[ (cb % 60) ];
                    if (cm != 0)
                        for (int c = cbd, my = y + 15; c < buffer.length; c += my, my += 30) {
                            buffer[c] ^= cm;
                        }
                }
            }
            //for the 3 * x ^ 2 - y ^ 2 quadratic solution toggles all x and opposite y = x - 1...
            n = 4; // equivalent to 11 - 7 = 4...
            for (int x = 2, y = x - 1; n <= nrng; n += (x << 2) + 4, y = x, ++x) {
                var cb = n; int i = 0;
                for (; y > 1 && i < 15 && cb <= nrng; cb += (y << 2) - 4, y -= 2, ++i) {
                    var cbd = cb / 60; var cm = modLUT[ (cb % 60) ];
                    if (cm != 0) {
                    	int c = cbd, my = y;
                        for (; my >= 30 && c < buffer.length; c += my - 15, my -= 30) {
                            buffer[c] ^= cm;
                        }
                        if (my > 0 && c < buffer.length) { buffer[c] ^= cm; }
                    }
                }
                if (y == 1 && i < 15) {
                    var cbd = cb / 60; var cm = modLUT[(cb % 60)];
                    if ((cm & 0x4822) != 0 && cbd < buffer.length) { buffer[cbd] ^= cm; }
                }
            }

            //Eliminate squares of base primes, only for those on the wheel:
            for (int w = 0, pd = 0, pn = 0, msk = 1; w < buffer.length;) {
            	int p = pd + modPRMS[pn];
                long sqr = (long)p * (long)p; //to handle ranges above UInt32.MaxValue
                if (sqr > upperLimit) break;
                if ((buffer[w] & msk) != 0) { //found base prime, square free it...
                    long s = sqr - 7;
                    for (int j = 0; s <= nrng && j < modPRMS.length; s = sqr * modPRMS[j] - 7, ++j) {
                        var cd = s / 60; var cm = (modLUT[(int) (s % 60)] ^ 0xFFFF);
                        //may need ulong loop index for ranges larger than two billion
                        //but buffer length only good to about 2^31 * 60 = 120 million anyway,
                        //even with large array setting and half that with 32-bit...
                        for (var c = cd; c < buffer.length; c += sqr) {
                            buffer[(int) c] &= cm;
                        }
                    }
                }
                if (msk >= 0x8000) { msk = 1; pn = 0; ++w; pd += 60; }
                else { msk <<= 1; ++pn; }
            }

            //clear any overflow primes in the excess space in the last wheel/word:
            var ndx = nrng % 60; //clear any primes beyond the upperLimit
            for (; modLUT[ndx] == 0; --ndx) ;
            buffer[lmtw] &= (modLUT[ndx] << 1) - 1;   
        }
		
		if (lowerLimit <= 2) primes.add(2);
        if (lowerLimit<= 3) primes.add(3);
        if (lowerLimit<= 5) primes.add(5);
		int pd = 0;
        for (int w = 0, pn = 0, msk = 1; w < buffer.length;) {
        	
            if ((buffer[w] & msk) != 0) { //found a prime bit...
            	
            	int p = pd + modPRMS[pn];
            	if (p >= lowerLimit) { primes.add(p); } //add it to the list
            }
            
            if (msk >= 0x8000) { msk = 1; pn = 0; ++w; pd += 60; }
            else { msk <<= 1; ++pn; }
        }
        
        return primes;
	}
	
	private static void initializePrimes(int limit) {
		
		primes = new ArrayList<>();
		expandPrimes(limit, false);
	}
	
	private static void expandPrimes(int upperLimit, boolean verbose) {
		
		if (getPrimes().size() > 0 && upperLimit > getPrimes().get(getPrimes().size() - 1) ){

			if (verbose) Utils.print("Expanding primes list from " + (getPrimes().get(getPrimes().size() - 1) + 1) + " to " + upperLimit);
			int lowerLimit = getPrimes().get(getPrimes().size() - 1) + 1;
			getPrimes().addAll( sieveOfAtkinRange(lowerLimit, upperLimit) );
		}
		else if (getPrimes().size() == 0) {
			
			if (verbose) Utils.print("Initialize primes list");
			getPrimes().addAll( sieveOfAtkinRange(1, upperLimit) );
		}
		else if (verbose) {
			
			Utils.print("Upper limit " + upperLimit + " is already in primes list. Expanding is not necessary.");
		}
	}

    private static byte[] modPRMS = { 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 49, 53, 59, 61 };
    private static int[] modLUT = { 1, 0, 0, 0, 2, 0, 4, 0, 0, 0, 8, 0, 16, 0, 0, 0, 32, 0, 0, 0, 0, 0, 64,
    								0, 128, 0, 0, 0, 0, 0, 256, 0, 0, 0, 512, 0, 1024, 0, 0, 0, 2048, 0,
    								4096, 0, 0, 0, 8192, 0, 0, 0, 0, 0, 16384, 0, 32768, 0, 0, 0, 0, 0 };
	private static List<Integer> primes;
}
