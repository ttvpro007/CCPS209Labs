package src;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;

public class JPanelExtends extends JPanel {
	
	public JPanelExtends(int sizeX, int sizeY, Component... components) {
		
		this.setPreferredSize( new Dimension(sizeX, sizeY) );
		
		for (var c : components) {
			
			this.add(c);
		}
	}
}
