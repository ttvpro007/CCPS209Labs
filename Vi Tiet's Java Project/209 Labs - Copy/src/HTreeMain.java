import javax.swing.BoxLayout;

public class HTreeMain {

	public static void main(String[] args) {
		
		int w = 1000, h = 800;
		var hTree = new HTree(w, h);
		
		JFrameExtends f = new JFrameExtends(
			
			new JPanelExtends (
				
				w, h,
				hTree
			)
		);
		
		f.setupOnWindowClosing();
		f.setLayout( new BoxLayout(f.getContentPane(), BoxLayout.Y_AXIS) );
        f.activate(true);
	}
}
