
//https://stackoverflow.com/questions/1569127/c-implementation-of-the-sieve-of-atkin
public class SieveOfAtkin {
	
    private int[] buf = null;
    private long cnt = 0;
    private static byte[] modPRMS = { 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 49, 53, 59, 61 };
    private static int[] modLUT;
    private static byte[] cntLUT;
    
    //initialization and all the work producing the prime bit array done in the constructor...
    public SieveOfAtkin(long range) {
    	modLUT = new int[60];
        for (int i = 0, m = 0; i < modLUT.length; ++i) {
            if ((i & 1) != 0 || (i + 7) % 3 == 0 || (i + 7) % 5 == 0) modLUT[i] = 0;
            else modLUT[i] = (int)(1 << (m++));
        }
        cntLUT = new byte[65536];
        for (int i = 0; i < cntLUT.length; ++i) {
            var c = 0;
            for (int j = i; j > 0; j >>= 1) c += j & 1;
            cntLUT[i] = (byte)c;
        }
        
        if (range < 7) {
            if (range > 1) {
                cnt = 1;
                if (range > 2) this.cnt += (long)(range - 1) / 2;
            }
            this.buf = new int[0];
        }
        else {
            this.cnt = 3;
            var nrng = range - 7; var lmtw = nrng / 60;
            //initialize sufficient wheels to non-prime
            this.buf = new int[(int) (lmtw + 1)];

            //Put in candidate primes:
            //for the 4 * x ^ 2 + y ^ 2 quadratic solution toggles - all x odd y...
            long n = 6; // equivalent to 13 - 7 = 6...
            for (int x = 1, y = 3; n <= nrng; n += (x << 3) + 4, ++x, y = 1) {
                var cb = n; if (x <= 1) n -= 8; //cancel the effect of skipping the first one...
                for (int i = 0; i < 15 && cb <= range; cb += (y << 2) + 4, y += 2, ++i) {
                    var cbd = cb / 60; var cm = modLUT[(int) (cb % 60)];
                    if (cm != 0)
                        for (int c = (int)cbd, my = y + 15; c < buf.length; c += my, my += 30) {
                            buf[c] ^= cm;
                        }
                }
            }
            //for the 3 * x ^ 2 + y ^ 2 quadratic solution toggles - x odd y even...
            n = 0; // equivalent to 7 - 7 = 0...
            for (int x = 1, y = 2; n <= nrng; n += ((x + x + x) << 2) + 12, x += 2, y = 2) {
                var cb = n;
                for (var i = 0; i < 15 && cb <= range; cb += (y << 2) + 4, y += 2, ++i) {
                    var cbd = cb / 60; var cm = modLUT[(int) (cb % 60)];
                    if (cm != 0)
                        for (int c = (int)cbd, my = y + 15; c < buf.length; c += my, my += 30) {
                            buf[c] ^= cm;
                        }
                }
            }
            //for the 3 * x ^ 2 - y ^ 2 quadratic solution toggles all x and opposite y = x - 1...
            n = 4; // equivalent to 11 - 7 = 4...
            for (int x = 2, y = x - 1; n <= nrng; n += (long)(x << 2) + 4, y = x, ++x) {
                var cb = n; int i = 0;
                for (; y > 1 && i < 15 && cb <= nrng; cb += (long)(y << 2) - 4, y -= 2, ++i) {
                    var cbd = cb / 60; var cm = modLUT[(int) (cb % 60)];
                    if (cm != 0) {
                    	int c = (int)cbd, my = y;
                        for (; my >= 30 && c < buf.length; c += my - 15, my -= 30) {
                            buf[c] ^= cm;
                        }
                        if (my > 0 && c < buf.length) { buf[c] ^= cm; }
                    }
                }
                if (y == 1 && i < 15) {
                    var cbd = cb / 60; var cm = modLUT[(int) (cb % 60)];
                    if ((cm & 0x4822) != 0 && cbd < (long)buf.length) { buf[(int) cbd] ^= cm; }
                }
            }

            //Eliminate squares of base primes, only for those on the wheel:
            for (int w = 0, pd = 0, pn = 0, msk = 1; w < this.buf.length;) {
            	int p = pd + modPRMS[pn];
                long sqr = (long)p * (long)p; //to handle ranges above UInt32.MaxValue
                if (sqr > range) break;
                if ((this.buf[w] & msk) != 0) { //found base prime, square free it...
                    long s = sqr - 7;
                    for (int j = 0; s <= nrng && j < modPRMS.length; s = sqr * modPRMS[j] - 7, ++j) {
                        var cd = s / 60; var cm = (int)(modLUT[(int) (s % 60)] ^ 0xFFFF);
                        //may need ulong loop index for ranges larger than two billion
                        //but buf length only good to about 2^31 * 60 = 120 million anyway,
                        //even with large array setting and half that with 32-bit...
                        for (long c = cd; c < (long)this.buf.length; c += sqr) {
                            this.buf[(int) c] &= cm;
                        }
                    }
                }
                if (msk >= 0x8000) { msk = 1; pn = 0; ++w; pd += 60; }
                else { msk <<= 1; ++pn; }
            }

            //clear any overflow primes in the excess space in the last wheel/word:
            var ndx = nrng % 60; //clear any primes beyond the range
            for (; modLUT[(int) ndx] == 0; --ndx) ;
            this.buf[(int) lmtw] &= (int)((modLUT[(int) ndx] << 1) - 1);
        }
    }

    //uses a fast pop count Look Up Table to return the total number of primes...
    public long getCount() {
        long cnt = this.cnt;
        for (int i = 0; i < this.buf.length; ++i) cnt += cntLUT[this.buf[i]];
        return cnt;
    }
    
    //generate the enumeration of primes...
    public Generator<Long> getGenerator() {
    	
    	return new Generator<Long>() {
    		
    		public void run() throws InterruptedException {
    			
    			this.yield((long) 2);
    			this.yield((long) 3);
    			this.yield((long) 5);
    			long pd = 0;
    	        for (int w = 0, pn = 0, msk = 1; w < buf.length;) {
    	            if ((buf[w] & msk) != 0) //found a prime bit...
    	                this.yield( pd + modPRMS[pn] ); //add it to the list
    	            if (msk >= 0x8000) { msk = 1; pn = 0; ++w; pd += 60; }
    	            else { msk <<= 1; ++pn; }
    	        }
    		}
    	};
    }
}