import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class JTextFieldFactory implements IFactory<JTextField> {

	public static JTextField create(int columns, String defaultText, ActionListener listener) {
		
		return new JTextFieldFactory(columns, defaultText, listener).create();
	}
	
	public JTextFieldFactory(int columns, String defaultText, ActionListener listener) {
		
		this.columns = columns;
		this.defaultText = defaultText;
		this.listener = listener;
	}
	
	@Override
	public JTextField create() {

		var textField = new JTextField(columns);
		textField.setText(defaultText);
		
		if (listener != null) {
			
			textField.addActionListener(listener);
		}
		
		return textField;
	}
	
	private int columns;
	private String defaultText;
	private ActionListener listener;
}