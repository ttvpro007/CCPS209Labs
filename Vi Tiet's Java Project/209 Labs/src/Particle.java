import java.awt.Color;
import java.util.Random;

public class Particle {

	public Particle(int width, int height) {
		
		x = rng.nextInt(width);
		y = rng.nextInt(height);
		
		heading = Math.PI * 2 * rng.nextDouble();
		
		color = new Color( rng.nextInt(255), rng.nextInt(255), rng.nextInt(255) );
	}
	
	public double getX() {
		
		return x;
	}
	
	public double getY() {
		
		return y;
	}
	
	public Color getColor() {
		
		return color;
	}
	
	public void Move() {
		
		x += Math.cos(heading);
		y += Math.sin(heading);
		heading += rng.nextGaussian() * BUZZY;
	}
	
	private static final Random rng = new Random();
	private static final double BUZZY = 0.7;
	
	private double x;
	private double y;
	private double heading;
	private Color color;
}
