public class P2J1 {
	
	public static long fallingPower(int n, int k) {
		
		long result = 1;
		
		for (int i = 0; i < k; i++) {
			result *= n - i;
		}
		
		return result;
	}
	
	
	public static int[] everyOther(int[] arr) {

		int[] result = new int[ (arr.length + 1) / 2 ];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = arr[i * 2];
		}
		
		return result;
	}
	
	
	public static int[][] createZigZag(int rows, int cols, int start) {
		
		int[][] result = new int[rows][cols];
		
		for (int i = 0; i < rows; i++) {
			
			for (int j = 0; j < cols; j++) {
				
				if (i % 2 == 0) {
					
					result[i][j] = i * cols + j + start;
				}
				else {
					
					result[i][cols - j - 1] = i * cols + j + start;
				}
			}			
		}
		
		return result;
	}
	
	
	public static int countInversions(int[] arr) {
				
		int count = 0;
		
		for (int i = 0; i < arr.length - 1; i++) {
			
			for (int j  = i + 1; j < arr.length; j++) {
				
				if ( arr[i] > arr[j] ) {
					
					count++;
				}
			}				
		}
		
		return count;
	}
}