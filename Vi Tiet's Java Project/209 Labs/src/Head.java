
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.*;
import javax.swing.border.BevelBorder;

public class Head extends JPanel {
	
	public enum DISPLAY_MODE {
    	
    	VERTICAL,
    	HORIZONTAL
    }
	
	public static void display(int n, DISPLAY_MODE displayMode) {
		
		JFrame f = new JFrame("Head Demo");
        
        // Tell the frame to obey the close button
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Grid layout n x n
        double s = Math.sqrt(n);
        int rows = displayMode == DISPLAY_MODE.HORIZONTAL ? (int) Math.floor(s) : (int) Math.ceil(s);
        int cols = displayMode == DISPLAY_MODE.VERTICAL ? (int) Math.floor(s) : (int) Math.ceil(s);
        
        f.setLayout( new GridLayout( rows, cols ) );
        
        // Add heads
        for (int i = 0; i < n; i++) {

            f.add(new Head());
        }
        
        f.pack();
        f.setVisible(true);
	}
	
	public Head() {

		this.setPreferredSize( new Dimension(PANEL_SIZE, PANEL_SIZE) );
		this.setBorder( BorderFactory.createBevelBorder(BevelBorder.RAISED) );
		this.addMouseListener( new HeadMouseListener() );
		
		headRadius = PANEL_SIZE * FRAME_TO_FACE_SCALE / 2;
		eyeRadius = headRadius * HEAD_TO_EYE_SCALE;
		isSurprised = false;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		g2D = (Graphics2D) g;
		
        g2D.setStroke( new BasicStroke(STROKE_THICKNESS, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND) );
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// draw head
		double headPosition = PANEL_SIZE / 2;
		drawCircle(headPosition, headPosition, headRadius);
		
		// draw eyes
		boolean eyesOpen = isSurprised;
		drawEyesOpen(headPosition, eyesOpen);
		
		// draw mouth
		boolean mouthOpen = isSurprised;
		drawMouth(headPosition, mouthOpen);
	}
	
	public void setSurprised(boolean isSurprised) {

		this.isSurprised = isSurprised;
		this.repaint();
	}
	
	private class HeadMouseListener extends MouseAdapter {
				
		@Override
		public void mouseEntered(MouseEvent e) {

			( (Head) e.getComponent() ).setSurprised(true);
		}

		@Override
		public void mouseExited(MouseEvent e) {

			( (Head) e.getComponent() ).setSurprised(false);
		}
	}

	private static final int PANEL_SIZE = 500;
	private static final double FRAME_TO_FACE_SCALE = 0.75;
	private static final double HEAD_TO_EYE_SCALE = 0.2;
	private static final float STROKE_THICKNESS = 3.0f;
	
	private double headRadius;
	private double eyeRadius;
	private Graphics2D g2D;
	private boolean isSurprised;
	
	private void drawCircle(double x, double y, double r) {
		
		double d = r * 2;
		g2D.draw( new Ellipse2D.Double(x - d / 2, y - d/2, d, d) );
	}
	
	private void drawRect(double x, double y, double w, double h) {
		
		g2D.draw( new Rectangle2D.Double(x - w / 2, y - h / 2, w, h) );
	}
	
	private void drawEyesOpen(double headPosition, boolean open) {
		
		double eyeLevel = headPosition - headRadius / 3;
		double eyeSpacing = headRadius / 2;
		
		if (open) {
			
			drawCircle(headPosition - eyeSpacing, eyeLevel, eyeRadius);
			drawCircle(headPosition + eyeSpacing, eyeLevel, eyeRadius);
		}
		else {
			
			drawRect(headPosition - eyeSpacing, eyeLevel, eyeRadius * 2, 0);
			drawRect(headPosition + eyeSpacing, eyeLevel, eyeRadius * 2, 0);
		}
	}
	
	private void drawMouth(double headPosition, boolean open) {

		double mouthLevel = headPosition + headRadius / 3;
		double mouthWidth = headRadius;
		double mouthHeight = headRadius / 2;
		
		if (open) {
			
			drawRect(headPosition, mouthLevel, mouthWidth, mouthHeight);
		}
		else {

			drawRect(headPosition, mouthLevel, mouthWidth, 0);
		}
	}
}
