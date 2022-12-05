import javax.swing.JLabel;

public class JLabelFactory implements IFactory<JLabel> {

	public static JLabel create(String labelText) {
		
		return new JLabelFactory(labelText).create();
	}
	
	public JLabelFactory(String labelText) {
		
		this.labelText = labelText;
	}
	
	@Override
	public JLabel create() {

		return new JLabel(labelText);
	}
	
	private String labelText;
}