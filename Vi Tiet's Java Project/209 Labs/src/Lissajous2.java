import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Lissajous2 extends JPanel implements Runnable {

	public static void display(int size) {
		
		Lissajous2 lissajous = new Lissajous2(size);
		
		JFrame f = new JFrame("Lissajous2 Demo");
		
		f.addWindowListener( new WindowAdapter() {
			
			public void windowClosing(WindowEvent we) {
				
				lissajous.terminate();
				f.dispose();
			}
		});
		
		f.add(lissajous);
        f.pack();
        f.setVisible(true);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		Thread thread = new Thread( lissajous );
		thread.run();
	}
	
	public Lissajous2(int size) {
		
		this.setPreferredSize( new Dimension(size, size) );

		isDoubleDraw = false;
		
		pointsSet = new LinkedHashSet<>();
		points = new ArrayList<>();
		
		minPos = (int) Utils.lerp(0, size, 0.2);
		maxPos = (int) Utils.lerp(0, size, 0.8);
		offset = (int) Utils.lerp(0, size, 0.05);
		
		setParams(2f, 1, -2, 4.5);
		
		running = true;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		g2D = (Graphics2D) g;
		
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		int length = maxPos - minPos + offset * 2;
		
		// grid
		GraphicsUtils.drawGridRect(g2D, minPos - offset, minPos - offset, length, length, 4, offset);

		// cosine point
		GraphicsUtils.drawCircle(g2D, cosinePoint, POINT_SIZE, true);
		// cosine point guide line
		drawGuideVerticalLine(cosinePoint, length);
		
		// sine point
		GraphicsUtils.drawCircle(g2D, sinePoint, POINT_SIZE, true);
		// sine point guide line
		drawGuideHorizontalLine(sinePoint, length);

		// intersect point of guild lines
		GraphicsUtils.drawCircle(g2D, intersectPoint, POINT_SIZE, true);
		
		// lissajous
		drawLissajous();
		
		GraphicsUtils.setSolidStroke(g2D, 1);
	}
	
	@Override
	public void run() {

		while (running) {
			
			try {
				
				Thread.sleep(REFRESH_INTERVAL);
			}
			catch (InterruptedException e) {

				e.printStackTrace();
			}

			sineRadianAngle += sineRadianStep * speed;
			if (sineRadianAngle >= 2 * Math.PI) sineRadianAngle -= 2 * Math.PI;
			setSinePoint();
			
			cosineRadianAngle += cosineRadianStep * speed;
			if (cosineRadianStep >= 2 * Math.PI) cosineRadianStep -= 2 * Math.PI;
			setCosinePoint();
			
			setIntersectPoint();
			
			// clear points if current intersect point is the first point in list or points count exceeds 1000
			if ( points.size() > 0 && intersectPoint.equals( points.get(0) ) ) {

				if (isDoubleDraw) {

					clearLissajousPoints();
				}
				else {
					
					isDoubleDraw = true;
				}
			}
			
			if (points.size() >= 10000) {

				clearLissajousPoints();
			}
			
			addToLissajousPoints(intersectPoint);
			
			this.repaint();
		}
	}
	
	public void terminate() {
		
		running = false;
	}
	
	public void setParams(float speed, double cosineDegreeStep, double sineDegreeStep, double degreeAngle) {
		
		this.speed = speed;
		cosineRadianStep = cosineDegreeStep * DEG_2RAD;
		sineRadianStep = sineDegreeStep * DEG_2RAD;
		radianAngle = degreeAngle * DEG_2RAD;
		sineRadianAngle = radianAngle;
		cosineRadianAngle = radianAngle;
		
		Utils.print( "sine:" + Math.sin(sineRadianAngle) );
		Utils.print( "cosine:" + Math.sin(cosineRadianAngle) );

		setCosinePoint();
		setSinePoint();
		setIntersectPoint();

		clearLissajousPoints();
		addToLissajousPoints(intersectPoint);
	}
	
	private void setIntersectPoint() {

		if (intersectPoint == null) {
			
			intersectPoint = new Point( cosinePoint.getX(), sinePoint.getY() );
		}
		else {
			
			intersectPoint.setXY( cosinePoint.getX(), sinePoint.getY() );
		}
	}
	
	private void setCosinePoint() {
		
		double cosine = Math.cos(cosineRadianAngle);
		double transformCosineTo01Scale = (cosine + 1) / 2;

		if (cosinePoint == null) {

			cosinePoint = new Point( (int) Utils.lerp( minPos, maxPos, transformCosineTo01Scale), maxPos + offset );
		}
		else {
			
			cosinePoint.setX( (int) Utils.lerp( minPos, maxPos, transformCosineTo01Scale) );
		}
	}
	
	private void setSinePoint() {

		double sine = Math.sin(sineRadianAngle);
		double transformSineTo01Scale = (sine + 1) / 2;
		
		if (sinePoint == null) {
			
			sinePoint = new Point( minPos - offset, (int) Utils.lerp( minPos, maxPos, transformSineTo01Scale) );
		}
		else {
			
			sinePoint.setY( (int) Utils.lerp( minPos, maxPos, transformSineTo01Scale) );
		}
	}
	
	private void drawLissajous() {
		
		if (points.size() > 0) {
			
			GraphicsUtils.setSolidStroke(g2D, 2);
			
			Path2D path = new Path2D.Double();
			for (int i = 0; i < points.size(); i++) {
				
				Point currentPoint = points.get(i);
				if (i == 0) {
					
					path.moveTo( currentPoint.getX(), currentPoint.getY() );
				}
				else {
					
					path.lineTo( currentPoint.getX(), currentPoint.getY() );
				}
			}
			
			g2D.draw(path);
		}
	}
	
	private void drawGuideVerticalLine(Point point, int length) {
		
		GraphicsUtils.drawDashedLine(g2D, point.getX(), point.getY(), point.getX(), point.getY() - length, 8);
	}
	
	private void drawGuideHorizontalLine(Point point, int length) {
		
		GraphicsUtils.drawDashedLine(g2D, point.getX(), point.getY(), point.getX() + length, point.getY(), 8);
	}
	
	private void addToLissajousPoints(Point point) {

		points.add( point.copy() );
	}
	
	private void clearLissajousPoints() {
		
		isDoubleDraw = false;
		points.clear();
		pointsSet.clear();
//		System.gc();
//		System.runFinalization();
	}

	public static void main(String[] args) {
		
		Lissajous2.display(500);
	}
	
	private static double DEG_2RAD = 0.01745329;
	private static final int POINT_SIZE = 6;
	private static final int REFRESH_INTERVAL = 33;

	private int minPos;
	private int maxPos;
	private int offset;
	private boolean running;
	private Graphics2D g2D;
	
	private float speed;
	
	private Point cosinePoint;
	private double cosineRadianAngle;
	private double cosineRadianStep;
	
	private Point sinePoint;
	private double sineRadianAngle;
	private double sineRadianStep;
	
	private double radianAngle;

	private Point intersectPoint;
	
	private Set<Point> pointsSet;
	private List<Point> points;
	private boolean isDoubleDraw;
}
