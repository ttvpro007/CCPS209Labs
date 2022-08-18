import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class JFrameExtends extends JFrame {

	public JFrameExtends(JPanel... panels) {

		for (var p : panels) {
			
			this.add(p);
		}
	}
	
	public void activate(boolean show) {
		
        this.pack();
        this.setVisible(show);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
