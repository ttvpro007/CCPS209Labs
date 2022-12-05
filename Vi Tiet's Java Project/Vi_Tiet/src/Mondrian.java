package src;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class Mondrian extends JPanel implements ActionListener {
	
	public Mondrian(int w, int h) {
		
		width = w;
		height = h;
		
		this.setPreferredSize( new Dimension(width, height) );
		
		mondrian = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		g2D = (Graphics2D) mondrian.getGraphics();
		
		GraphicsUtils.setSolidStroke(g2D, OUTLINE_SIZE);
		
		subdivide(0, 0, width, height, g2D);
	}
	
	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		g2D.setColor(Color.BLACK);
		GraphicsUtils.drawRect(g2D, 0, 0, width, height);
		
		g.drawImage(mondrian, 0, 0, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		mondrian.flush();
		subdivide(0, 0, mondrian.getWidth(this), mondrian.getHeight(this), g2D);
		this.repaint();
	}
	
	private void subdivide(int tx, int ty, int w, int h, Graphics2D g2D) {

		Color c = rng.nextDouble() < WHITE ? Color.WHITE : COLORS[rng.nextInt(COLORS.length)];
		
		if (w < CUTOFF || h < CUTOFF) {

			// draw filled rect
			GraphicsUtils.drawRect(g2D, tx, ty, w, h, c);
			// draw outline
			g2D.setColor(Color.BLACK);
			GraphicsUtils.drawRect(g2D, tx, ty, w, h);
		}
		else {
			
			if (w > h) {
				
				var rw = (int) ( w * EDGE_BUFFER + (w - 2 * w * EDGE_BUFFER) * rng.nextDouble() );
				
				subdivide(tx, ty, rw, h, g2D);
				subdivide(tx + rw, ty, w - rw, h, g2D);
			}
			else {

				var rh = (int) ( h * EDGE_BUFFER + (h - 2 * h * EDGE_BUFFER) * rng.nextDouble() );

				subdivide(tx, ty, w, rh, g2D);
				subdivide(tx, ty + rh, w, h - rh, g2D);
			}
		}
	}

	private static final int OUTLINE_SIZE = 2;
	private static final float EDGE_BUFFER = 0.25f;
	
	// Cutoff size for when a rectangle is not subdivided further.
	private static final int CUTOFF = 40;
	// Percentage of rectangles that are white.
	private static final float WHITE = 0.65f;
	// Colors of non-white rectangles.
	private static final Color[] COLORS = {
		//		Red						Yellow						Blue					Cyan
		new Color(231, 76, 60), new Color(247, 220, 111), new Color(31, 97, 141), new Color(163, 228, 215)
	};
	// RNG instance to make the random decisions with.
	private Random rng = new Random();
	// The Image in which the art is drawn
	private Image mondrian;
	private Graphics2D g2D;
	
	private int width;
	private int height;
}
