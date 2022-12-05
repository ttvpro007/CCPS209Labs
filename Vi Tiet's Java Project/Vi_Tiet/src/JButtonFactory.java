package src;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class JButtonFactory implements IFactory<JButton> {

	public static JButton create(String buttonText, ActionListener listener) {
		
		return new JButtonFactory(buttonText, listener).create();
	}
	
	public JButtonFactory(String buttonText, ActionListener listener) {
		
		this.buttonText = buttonText;
		this.listener = listener;
	}
	
	@Override
	public JButton create() {

		var textField = new JButton(buttonText);
		
		if (listener != null) {
			
			textField.addActionListener(listener);
		}
		
		return textField;
	}
	
	private String buttonText;
	private ActionListener listener;
}