import javax.swing.BoxLayout;

public class MondrianMain {
	
	public static void main(String[] args) {
		
		int w = 500, h = 500;
		var mon = new Mondrian(w, h - 35);
		
		JFrameExtends f = new JFrameExtends(
			
			new JPanelExtends (
				
				w, h,
				JButtonFactory.create("Refresh", mon),
				mon
			)
		);
		
		f.setupOnWindowClosing();
		f.setLayout( new BoxLayout(f.getContentPane(), BoxLayout.Y_AXIS) );
        f.activate(true);
	}
}
