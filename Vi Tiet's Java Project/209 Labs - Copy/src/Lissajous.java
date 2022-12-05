import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Path2D;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Lissajous extends JPanel implements ActionListener {
	
	public static void display(int size) {
		
		Lissajous lissajous = new Lissajous(size);
		
		JFrame f = new JFrame("Lissajous Demo");
		
		f.add(lissajous);
		f.pack();
		f.setVisible(true);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public Lissajous(int size) {
		
		this.size = size;
		
		this.setPreferredSize( new Dimension(size, size) );
		
		JLabel aLabel = JLabelFactory.create("a:");
		aTextField = JTextFieldFactory.create(TEXT_FIELD_COLUMNS, "6", this);
		
		JLabel bLabel = JLabelFactory.create("b:");
		bTextField = JTextFieldFactory.create(TEXT_FIELD_COLUMNS, "5", this);
		
		JLabel deltaLabel = JLabelFactory.create("delta:");
		deltaTextField = JTextFieldFactory.create(TEXT_FIELD_COLUMNS, "0.5", this);
		
		var layout = new FlowLayout();
		
		this.setLayout(layout);
		this.add(aLabel);
		this.add(aTextField);
		this.add(bLabel);
		this.add(bTextField);
		this.add(deltaLabel);
		this.add(deltaTextField);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		try {
			
			int a = (int) Utils.eval( aTextField.getText() );
			aTextField.setText( Integer.toString(a) );
			
			int b = (int) Utils.eval( bTextField.getText() );
			bTextField.setText( Integer.toString(b) );
			
			double delta = Utils.eval( deltaTextField.getText() );
			delta = Utils.round(delta, 2);
			deltaTextField.setText( Double.toString(delta) );
			
			int gdc = Utils.gcd(a, b);
			
			Graphics2D g2D = (Graphics2D) g;
	        
			g2D.setStroke( new BasicStroke(STROKE_THICKNESS, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND) );
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			Path2D path = new Path2D.Double();
			
			for (double t = 0; t < a / gdc * b; t += STEP ) {

				double x = size / 2 + 2 * size / 5 * Math.sin(a * t * Math.PI + delta);
				double y = size / 2 + 2 * size / 5 * Math.sin(b * t * Math.PI);
				
				if (t == 0) {
					
					path.moveTo(x, y);
				}
				else {
					
					path.lineTo(x, y);
				}
			}
			
			g2D.draw(path);
		}
		catch (NumberFormatException e) {
			
			Utils.print("Error: " + e + " .Input is not an integer");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		this.repaint();
	}
	
	public static void main(String[] args) {
		
		Lissajous.display(500);
	}

	private static final int TEXT_FIELD_COLUMNS = 10;
	private static final float STROKE_THICKNESS = 3.0f;
	private static final double STEP = 0.005;
	
	private int size;
	private JTextField aTextField;
	private JTextField bTextField;
	private JTextField deltaTextField;
}
