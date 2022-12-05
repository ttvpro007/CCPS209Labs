package src;

public class Vector2 {
	
	public Vector2(float x, float y) {
		
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
		
		return getX() == ( (Vector2) other).getX() && getY() == ( (Vector2) other).getY();
	}
	
	@Override
	public int hashCode() {
		
		return 31 * Float.hashCode(x) + 13 * Float.hashCode(y);
	}
	
	public float getX() {
		
		return x;
	}
	
	public float getY() {
		
		return y;
	}
	
	public void setX(float x) {
		
		this.x= x ;
	}
	
	public void setY(float y) {
		
		this.y = y;
	}
	
	public void setXY(float x, float y) {
		
		this.x = x;
		this.y = y;
	}
	
	public Vector2 copy() {
		
		return new Vector2(x, y);
	}
	
	public void rotate(float radianAngle) {
		
		float nx = (float) ( x * Math.cos(radianAngle) - y * Math.sin(radianAngle) );
		float ny = (float) ( x * Math.sin(radianAngle) + y * Math.cos(radianAngle) );
		x = nx;
		y = ny;
	}
	
	public static Vector2 add(Vector2 a, Vector2 b) {
		
		return new Vector2( a.getX() + b.getX(), a.getY() + b.getY() );
	}
	
	public static Vector2 mul(Vector2 a, float m) {
		
		return new Vector2(a.getX() * m, a.getY() * m);
	}
	
	public static double distance(Vector2 a, Vector2 b) {
		
		return Math.sqrt( Math.pow(b.getX() - a.getX(), 2) + Math.pow(b.getY() - a.getY(), 2) );
	}
	
	public static Vector2 zero() {
		
		return new Vector2(0, 0);
	}
	
	public static Vector2 forward() {
		
		return new Vector2(0, 1);
	}
	
	public static Vector2 backward() {
		
		return new Vector2(0, -1);
	}
	
	private float x;
	private float y;
}