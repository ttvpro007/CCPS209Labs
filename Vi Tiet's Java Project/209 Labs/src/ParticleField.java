import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class ParticleField extends JPanel implements Runnable {

	public static void display(int n, int width, int height) {
		
		ParticleField particleField = new ParticleField(n, width, height);
		
		JFrame f = new JFrame("Particle Field Demo");
		
		f.addWindowListener( new WindowAdapter() {
			
			public void windowClosing(WindowEvent we) {
				
				particleField.terminate();
				f.dispose();
			}
		});
		
		f.setLayout( new FlowLayout() );
		f.add(particleField);
        f.pack();
        f.setVisible(true);
	}
	
	public ParticleField(int n, int width, int height) {

		this.setPreferredSize( new Dimension(width, height) );
		this.setBorder( BorderFactory.createBevelBorder(BevelBorder.RAISED) );
		
		for (int i = 0; i < n; i++) {
			
			particles.add( new Particle(width, height) );
		}
		
		running = true;
		
		animator = new Thread(this);
		animator.start();
	}

	@Override
	public void run() {
		
		while (running) {
			
			try {
				
				Thread.sleep( (long) DELTA_TIME );
			}
			catch (InterruptedException e) {

				e.printStackTrace();
			}
			
			for (var p : particles) {
				
				p.Move();
			}
			
			this.repaint();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		g2D = (Graphics2D) g;
		drawParticles();
	}
	
	public void terminate() {
		
		running = false;
	}

	private static final int FPS = 50;
	private static final double DELTA_TIME = 1000 / FPS;
	private static final int PARTICLE_SIZE = 3;
	private boolean running;
	private List<Particle> particles = new ArrayList<Particle>();
	private Graphics2D g2D;
	private Thread animator;
	
	private void drawParticles() {
		
		for (var p : particles) {
			
			g2D.draw( new Rectangle2D.Double(p.getX() - PARTICLE_SIZE / 2, p.getY() - PARTICLE_SIZE / 2, PARTICLE_SIZE, PARTICLE_SIZE) );
		}
	}
}
