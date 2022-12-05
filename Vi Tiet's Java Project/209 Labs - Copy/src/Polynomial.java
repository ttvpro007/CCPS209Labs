import java.lang.Math;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Polynomial implements Comparable<Polynomial> {

	private int degree;
	private int[] coefficients;
	private Map<Integer, Integer> powersAndCoefficients;
	
	private void updatePowersAndCoefficients() {
		
		if (powersAndCoefficients == null) {
		
			powersAndCoefficients = new HashMap<>();
		}
		
		for (int i = 0; i < coefficients.length; i++) {
			
			if (coefficients[i] != 0) {
				
				powersAndCoefficients.put(i, coefficients[i]);
			}
			else if (powersAndCoefficients.containsKey(i)) {
				
				powersAndCoefficients.remove(i);
			}
		}
		
		powersAndCoefficients = Utils.sortByKey(powersAndCoefficients, Utils.SORT_ORDER.DESCENDING);
	}
	
	public Polynomial(int[] coefficients) {
		
		this.coefficients = Utils.trimEndZeroes(coefficients);
		
		updatePowersAndCoefficients();
		
		degree =  this.coefficients.length - 1;
	}
	
	public int getDegree() {
		
		return degree;
	}
	
	public int getCoefficient(int k) {
		
		if (0 <= k && k < coefficients.length) {
			
			return coefficients[k];
		}
		
		return 0;
	}
	
	@Override
	public String toString() {
		
		String[] chunks = new String[ powersAndCoefficients.size() ];
		
		int i = 0;
		for ( var e : powersAndCoefficients.entrySet() ) {

			if (e.getKey() == 0) {
				
				chunks[i] = Integer.toString( e.getValue() );
			}
			else {
				
				if (e.getValue() != 1) {
					
					chunks[i] = e.getValue() + "x^" + e.getKey();
				}
				else {

					chunks[i] = "x^" + e.getKey();
				}
			}
			
			i++;
		}
		
		return "Polynomial [ " + String.join(" + ", chunks) + " ]";
	}
	
	@Override
	public boolean equals(Object other) {
		
		if ( !other.getClass().equals( getClass() ) ) {
			
			return false;
		}
		
		return compareTo( (Polynomial) other ) == 0;
	}

	@Override
	public int compareTo(Polynomial other) {
		
		if ( degree > other.getDegree() ) {

			return 1;
		}
		else if ( degree < other.getDegree() ) {

			return -1;
		}
		else {
			
			for (int i = degree; i >= 0; i--) {
				
				if ( getCoefficient(i) > other.getCoefficient(i) ) {
					
					return 1;
				}
				else if ( getCoefficient(i) < other.getCoefficient(i) ) {

					return -1;
				}
			}
		}
		
		return 0;
	}

	public long evaluate(int x) {
		
		long result = 0;
		
		for (int i = 0; i < coefficients.length; i++) {
			
			result += coefficients[i] * Math.pow(x, i);
		}
		
		return result;
	}
	
	public Polynomial add(Polynomial other) {
		
		int range = degree >= other.getDegree() ? degree + 1 : other.getDegree() + 1;
		
		int[] coefficients = new int[range];
		
		for (int i = 0; i < coefficients.length; i++) {
			
			coefficients[i] = getCoefficient(i) + other.getCoefficient(i);
		}
		
		return new Polynomial(coefficients);
	}
	
	public Polynomial multiply(Polynomial other) {
		
		int range = degree + other.getDegree() + 1;
		
		int[] coefficients = new int[range];
		
		for (int i = 0; i <= degree; i++) {
			
			for (int j = 0; j <= other.getDegree(); j++ ) {
				
				coefficients[i + j] += getCoefficient(i) * other.getCoefficient(j);
			}
		}

		return new Polynomial(coefficients);
	}
}
