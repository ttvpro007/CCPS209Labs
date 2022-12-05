import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JPanel;

public class HTree extends JPanel {

	public HTree(int w, int h) {
		
		width = w;
		height = h;
		
		this.setPreferredSize( new Dimension(width, height) );
		
		hTree = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		g2D = (Graphics2D) hTree.getGraphics();
		
		GraphicsUtils.setSolidStroke(g2D, STROKE_SIZE);
		GraphicsUtils.setAntiAliasing(g2D, true);
		
		g2D.setColor(bgColor);
		g2D.fillRect(0, 0, width, height);
//		g2D.setColor(Color.BLACK);
		
		center = new Vector2(width / 2, height / 2);
		
		render(center, 1, Math.min(width, height) / Math.PI, g2D);
		render(center, 3, Math.min(width, height) / Math.PI, g2D);
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);		
		g.drawImage(hTree, 0, 0, this);
	}
	
	private void render(Vector2 p, int i, double len, Graphics2D g2D) {
		
		render(p.getX(), p.getY(), i, len, g2D);
	}
	
	private void render(double x, double y, int i, double len, Graphics2D g2D) {
		
		if (len < CUTOFF) {
			
			// Circle color
			g2D.setColor(circleColor);
			GraphicsUtils.drawCircle(g2D, x, y, R, rng.nextDouble() < 0.5);
		}
		else {
			
			double nx = x + DIRS[i][0] * len, ny = y + DIRS[i][1] * len;

			// Line color
			g2D.setColor(lineColor);
			GraphicsUtils.drawLine(g2D, x, y, nx, ny);

			// Circle color
			g2D.setColor(circleColor);
			GraphicsUtils.drawCircle(g2D, x, y, R, true);
			
			len /= Math.sqrt(2);
			render(nx, ny, Utils.wrap(i - 1, DIRS.length), len, g2D);
			render(nx, ny, (i + 1) % DIRS.length, len, g2D);
		}
	}
	
	private static final int STROKE_SIZE = 1; 
	// Once line segment length is shorter than cutoff, stop subdividing.
	private static final float CUTOFF = 6f;
	// Radius of the little dot drawn on each intersection.
	private static final float R = 6f;
	// Four possible direction vectors that a line segment can have.
	private static final int[][] DIRS = { {0, 1}, {1, 0}, {0, -1}, {-1, 0} };
	// A random number generator for choosing the end piece colors.
	private static final Random rng = new Random();
	// The image inside which the H-Tree fractal is rendered.
	private Image hTree;
	
	private Graphics2D g2D;
	private Color circleColor = new Color(181, 130, 91);
	private Color lineColor = new Color(252, 220, 194);
	private Color bgColor = new Color(54, 38, 27);
	
	private Vector2 center;
	
	private int width;
	private int height;
}
