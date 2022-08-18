public class Point {
	
	public Point(int x, int y) {
		
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		
		return "Point[" + x + ", " + y + "]";
	}
	
	@Override
	public boolean equals(Object other) {

		if ( !other.getClass().equals( getClass() ) ) {
			
			return false;
		}
		
		return getX() == ( (Point) other).getX() && getY() == ( (Point) other).getY();
	}
	
	@Override
	public int hashCode() {
		
		return 31 * x + 13 * y;
	}
	
	public int getX() {
		
		return x;
	}
	
	public int getY() {
		
		return y;
	}
	
	public void setX(int x) {
		
		this.x= x ;
	}
	
	public void setY(int y) {
		
		this.y = y;
	}
	
	public void setXY(int x, int y) {
		
		this.x = x;
		this.y = y;
	}
	
	public Point copy() {
		
		return new Point(x, y);
	}
	
	private int x;
	private int y;
}