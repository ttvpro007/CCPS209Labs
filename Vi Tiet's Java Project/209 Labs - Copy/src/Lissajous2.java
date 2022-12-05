import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

//public class Lissajous2 extends JPanel implements Runnable {
public class Lissajous2 implements IAnimation {
	
	public Lissajous2(int size) {

		isDoubleDraw = false;
		
		points = new ArrayList<>();
		
		minPos = (int) Utils.lerp(0, size, 0.2);
		maxPos = (int) Utils.lerp(0, size, 0.8);
		offset = (int) Utils.lerp(0, size, 0.05);
		
		setParams(1, -2, 4.5);
	}
	
	@Override
	public void update(Graphics2D g2D, double t) {
		
		cosineRadianAngle += cosineRadianStep * t;
		if (cosineRadianAngle >= 2 * Math.PI) cosineRadianAngle -= 2 * Math.PI;
		sineRadianAngle += sineRadianStep * t;
		if (sineRadianAngle >= 2 * Math.PI) sineRadianAngle -= 2 * Math.PI;
		
		setCosinePoint();
		setSinePoint();
		
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
		
		
		int length = maxPos - minPos + offset * 2;
		
		// grid
		GraphicsUtils.drawGridRect(g2D, minPos - offset, minPos - offset, length, length, 4, offset);

		// cosine point
		GraphicsUtils.drawCircle(g2D, cosinePoint, POINT_SIZE, true);
		// cosine point guide line
		drawGuideVerticalLine(g2D, cosinePoint, length);
		
		// sine point
		GraphicsUtils.drawCircle(g2D, sinePoint, POINT_SIZE, true);
		// sine point guide line
		drawGuideHorizontalLine(g2D, sinePoint, length);

		// intersect point of guild lines
		GraphicsUtils.drawCircle(g2D, intersectPoint, POINT_SIZE, true);
		
		// lissajous
		drawLissajous(g2D);
		
		GraphicsUtils.setSolidStroke(g2D, 1);
	}
	
	public void setParams(double cosineDegreeStep, double sineDegreeStep, double degreeAngle) {
		
		cosineRadianStep = cosineDegreeStep * DEG_2RAD;
		sineRadianStep = sineDegreeStep * DEG_2RAD;
		radianAngle = degreeAngle * DEG_2RAD;
		sineRadianAngle = radianAngle;
		cosineRadianAngle = radianAngle;

		setCosinePoint();
		setSinePoint();
		setIntersectPoint();

		clearLissajousPoints();
		addToLissajousPoints(intersectPoint);
	}
	
	private void setIntersectPoint() {

		if (intersectPoint == null) {
			
			intersectPoint = new Vector2( cosinePoint.getX(), sinePoint.getY() );
		}
		else {
			
			intersectPoint.setXY( cosinePoint.getX(), sinePoint.getY() );
		}
	}
	
	private void setCosinePoint() {
		
		double cosine = Math.cos(cosineRadianAngle);
		double transformCosineTo01Scale = (cosine + 1) / 2;

		if (cosinePoint == null) {

			cosinePoint = new Vector2( (int) Utils.lerp( minPos, maxPos, transformCosineTo01Scale), maxPos + offset );
		}
		else {
			
			cosinePoint.setX( (int) Utils.lerp( minPos, maxPos, transformCosineTo01Scale) );
		}
	}
	
	private void setSinePoint() {

		double sine = Math.sin(sineRadianAngle);
		double transformSineTo01Scale = (sine + 1) / 2;
		
		if (sinePoint == null) {
			
			sinePoint = new Vector2( minPos - offset, (int) Utils.lerp( minPos, maxPos, transformSineTo01Scale) );
		}
		else {
			
			sinePoint.setY( (int) Utils.lerp( minPos, maxPos, transformSineTo01Scale) );
		}
	}
	
	private void drawLissajous(Graphics2D g2D) {
		
		if (points.size() > 0) {
			
			GraphicsUtils.setSolidStroke(g2D, 2);
			
			Path2D path = new Path2D.Double();
			for (int i = 0; i < points.size(); i++) {
				
				Vector2 currentPoint = points.get(i);
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
	
	private void drawGuideVerticalLine(Graphics2D g2D, Vector2 point, int length) {
		
		GraphicsUtils.drawDashedLine(g2D, point.getX(), point.getY(), point.getX(), point.getY() - length, 8);
	}
	
	private void drawGuideHorizontalLine(Graphics2D g2D, Vector2 point, int length) {
		
		GraphicsUtils.drawDashedLine(g2D, point.getX(), point.getY(), point.getX() + length, point.getY(), 8);
	}
	
	private void addToLissajousPoints(Vector2 point) {

		points.add( point.copy() );
	}
	
	private void clearLissajousPoints() {
		
		isDoubleDraw = false;
		points.clear();
//		System.gc();
//		System.runFinalization();
	}
	
	private static double DEG_2RAD = 0.01745329;
	private static final int POINT_SIZE = 6;

	private int minPos;
	private int maxPos;
	private int offset;
	
	private Vector2 cosinePoint;
	private double cosineRadianAngle;
	private double cosineRadianStep;
	
	private Vector2 sinePoint;
	private double sineRadianAngle;
	private double sineRadianStep;
	
	private double radianAngle;

	private Vector2 intersectPoint;
	
	private List<Vector2> points;
	private boolean isDoubleDraw;
}
