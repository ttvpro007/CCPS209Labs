import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class LSystemPanel extends JPanel {

	private LSystem lSys;
	private Color green = new Color(25, 111, 61);
	
	public LSystemPanel(int size, String axiom, String fRule, String gRule, String xRule, float angle, float len, float xPos, float yPos, float xDir, float yDir) {
		
		this.setPreferredSize( new Dimension(size, size) );
		lSys = new LSystem(axiom, fRule, gRule, xRule, angle, len, new Vector2(xPos, yPos), new Vector2(xDir, yDir));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		g.setColor(green);
		lSys.draw( (Graphics2D) g );
	}
	
	public void setParams(String axiom, String fRule, String gRule, String xRule, float angle, float len, float xPos, float yPos, float xDir, float yDir) {
		
		if ( lSys.paramChanged(axiom, fRule, gRule, xRule, angle, len, xPos, yPos, xDir, yDir) ) {
			
			Utils.print("Set new parameters");
			lSys.setParams(axiom, fRule, gRule, xRule, angle, len, xPos, yPos, xDir, yDir);
			lSys.reset();
		}
	}

	public void generate() {
		
		lSys.generate();
		this.repaint();
	}
	
	public void clear() {

		lSys.reset();
		this.removeAll();
		this.revalidate();
		this.repaint();
	}
}
