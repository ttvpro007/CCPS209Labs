package src;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class LissajousPanel extends JPanel {

    public static void display(int size) {
        
        LissajousPanel lissajousPanel = new LissajousPanel(size, 100);
        
        JFrame f = new JFrame("Lissajous Demo");
        
        f.addWindowListener( new WindowAdapter() {
            
            public void windowClosing(WindowEvent we) {
                
                lissajousPanel.terminate();
                f.dispose();
            }
        });
        
        f.add(lissajousPanel);
        f.pack();
        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        
        LissajousPanel.display(500);
    }
    
    public LissajousPanel(int size, float speed) {

        this.setPreferredSize( new Dimension(size, size) );
        
        lissajous = new Lissajous(size);
        
        running = true;
        
        timer = new Timer( 1000 / FPS, new LissajousTimerListener() );
        timer.start();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        ( (Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        lissajous.update( (Graphics2D) g, t);
    }
    
    public void setParams(float speed, double cosineStep, double sineStep, double degAngle) {
        
        this.speed = speed;
        lissajous.setParams(cosineStep, sineStep, degAngle);
    }
    
    public void terminate() {
        
        running = false;
        timer.stop();
    }
    
    private static final int FPS = 50;
    
    private final Timer timer;
    private boolean running;
    private double t;
    private float speed = 100f;
    private Lissajous lissajous;
    
    private class LissajousTimerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (running) {
                
                t = speed * timer.getDelay() / 1000;
                repaint();
            }
        }
    }
}
