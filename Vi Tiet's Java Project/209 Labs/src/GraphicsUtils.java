import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

public class GraphicsUtils {
	
	public static void drawLine(Graphics2D g2D, double x0, double y0, double x1, double y1) {
		
		Path2D path = new Path2D.Double();
		path.moveTo(x0, y0);
		path.lineTo(x1, y1);
		g2D.draw(path);
	}
	
	public static void drawLine(Graphics2D g2D, Point pointFrom, Point pointTo) {
		
		drawLine( g2D, pointFrom.getX(), pointFrom.getY(), pointTo.getX(), pointTo.getY() );
	}
	
	public static void drawSolidLine(Graphics2D g2D, double x0, double y0, double x1, double y1) {
		
		GraphicsUtils.setSolidStroke(g2D, 1);
		drawLine(g2D, x0, y0, x1, y1);
	}
	
	public static void drawSolidLine(Graphics2D g2D, Point pointFrom, Point pointTo) {

		GraphicsUtils.setSolidStroke(g2D, 1);
		drawLine(g2D, pointFrom, pointTo);
	}
	
	public static void drawDashedLine(Graphics2D g2D, double x0, double y0, double x1, double y1, int dashLength) {
		
		GraphicsUtils.setDottedStroke(g2D, 1, dashLength);
		drawLine(g2D, x0, y0, x1, y1);
	}
	
	public static void drawDashedLine(Graphics2D g2D, Point pointFrom, Point pointTo, float strokeSize, int dashLength) {

		GraphicsUtils.setDottedStroke(g2D, strokeSize, dashLength);
		drawLine(g2D, pointFrom, pointTo);
	}
	
	public static void drawCircle(Graphics2D g2D, double x, double y, double diameter, boolean fill) {
		
		double radius = diameter / 2;
		Ellipse2D e = new Ellipse2D.Double(x - radius, y - radius, diameter, diameter);
		if (fill) g2D.fill(e);
		g2D.draw(e);
	}
	
	public static void drawCircle(Graphics2D g2D, Point center, double diameter, boolean fill) {
		
		drawCircle(g2D, center.getX(), center.getY(), diameter, fill);
	}
	
	public static void drawRect(Graphics2D g2D, double x, double y, double w, double h) {

		g2D.draw( new Rectangle2D.Double(x, y, w, h) );
	}
	
	public static void drawGridRect(Graphics2D g2D, double x, double y, double w, double h, int sections, double offset) {
		
		drawRect(g2D, x, y, w, h);
		
		boolean highlightMidLine = sections % 2 == 0;
		
		double minPosX = x + offset;
		double maxPosX = x + w - offset;
		double minPosY = y + offset;
		double maxPosY = y + h - offset;
		double sectionSize = Math.min(maxPosX - minPosX, maxPosY - minPosY) / sections;
		
		for (int i = 0; i <= sections; i++) {

			// highlight mid line if sections is even
			if (highlightMidLine) {
				
				if (i == sections / 2) {

					// horizontal solid lines
					GraphicsUtils.drawSolidLine(g2D, minPosX + (sectionSize * i), y, minPosX + (sectionSize * i), y + h);
					// vertical dotted lines
					GraphicsUtils.drawSolidLine( g2D, x, minPosY + (sectionSize * i), x + w, minPosY + (sectionSize * i) );
				}
				else {

					// horizontal dotted lines
					GraphicsUtils.drawDashedLine(g2D, minPosX + (sectionSize * i), y, minPosX + (sectionSize * i), y + h, 2);
					// vertical dotted lines
					GraphicsUtils.drawDashedLine(g2D, x, minPosY + (sectionSize * i), x + w, minPosY + (sectionSize * i), 2);
				}
			}
			else {

				// horizontal dotted lines
				GraphicsUtils.drawDashedLine(g2D, minPosX + (sectionSize * i), y, minPosX + (sectionSize * i), y + h, 2);
				// vertical dotted lines
				GraphicsUtils.drawDashedLine(g2D, x, minPosY + (sectionSize * i), x + w, minPosY + (sectionSize * i), 2);	
			}
		}
	}
	
	public static void setSolidStroke(Graphics2D g2D, float strokeSize) {
		
		g2D.setStroke( new BasicStroke(strokeSize, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND) );
	}
	
	public static void setDottedStroke(Graphics2D g2D, float strokeSize, int dashLength) {
		
		g2D.setStroke( new BasicStroke(strokeSize, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{dashLength}, 0) );
	}
	
	public static void setAntiAliasing(Graphics2D g2D, boolean on) {

		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, on ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
	}
	
}
