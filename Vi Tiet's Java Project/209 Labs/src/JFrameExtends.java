import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class JFrameExtends extends JFrame {

	public JFrameExtends(JPanel... panels) {

		for (var p : panels) {
			
			this.add(p);
		}
	}
	
	public void setupOnWindowClosing(Runnable r) {
		
		addWindowListener( new WindowAdapter() {
			
			public void windowClosing(WindowEvent we) {
				
				if (r != null) r.run();
				dispose();
			}
		});
	}
	
	public void setupOnWindowClosing() {
		
		setupOnWindowClosing(null);
	}
	
	public void activate(boolean show) {
		
        this.pack();
        this.setVisible(show);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
