import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JTextField;

public class LSystemMain {

	private LSystemPanel lSystemPanel;
	private JTextField axiomTextField;
	private JTextField fRuleTextField;
	private JTextField gRuleTextField;
	private JTextField xRuleTextField;
	private JTextField angleTextField;
	private JTextField lenTextField;
	private JTextField xOPosTextField;
	private JTextField yOPosTextField;
	private JTextField xODirTextField;
	private JTextField yODirTextField;
	
	public GenerateActionListener genListener = new GenerateActionListener();
	public ClearActionListener clrListener = new ClearActionListener();
	
	public LSystemMain(LSystemPanel lSystemPanel, JTextField... textFields) {
		
		if (textFields.length == 10) {
		
			this.lSystemPanel = lSystemPanel;
			this.axiomTextField = textFields[0];
			this.fRuleTextField = textFields[1];
			this.gRuleTextField = textFields[2];
			this.xRuleTextField = textFields[3];
			this.angleTextField = textFields[4];
			this.lenTextField   = textFields[5];
			this.xOPosTextField = textFields[6];
			this.yOPosTextField = textFields[7];
			this.xODirTextField = textFields[8];
			this.yODirTextField = textFields[9];
		}
	}

	public class GenerateActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			Utils.print("Generate");
			
			String axiom = axiomTextField.getText();
			String fRule = fRuleTextField.getText();
			String gRule = gRuleTextField.getText();
			String xRule = xRuleTextField.getText();
			float angle = (float) Math.toRadians( Utils.eval( angleTextField.getText() ) );
			float len   = (float) Utils.eval( lenTextField.getText() );
			float xOPos = (float) Utils.eval( xOPosTextField.getText() );
			float yOPos = (float) Utils.eval( yOPosTextField.getText() );
			float xODir = (float) Utils.eval( xODirTextField.getText() );
			float yODir = (float) Utils.eval( yODirTextField.getText() );
			
			lSystemPanel.setParams(axiom, fRule, gRule, xRule, angle, len, xOPos, yOPos, xODir, yODir);
			lSystemPanel.generate();
		}
	}

	public class ClearActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			Utils.print("Clear");
			
			lSystemPanel.clear();
		}
	}
	
	public static void main(String[] args) {
		
		int size = 800;
		String axiom = "F";
		String fRule = "F[+F]F[-F]F";
		String gRule = "";
//		String xRule = "F-[[X]+X]+F[+FX]-X";
		String xRule = "";
		float angle = 22.5f;
		float angleRadian = (float) Math.toRadians(angle);
		float len = 150f;
		float xPos = size / 2;
		float yPos = size;
		float xDir = 0;
		float yDir = -1;
		
		JTextField axiomTextField = JTextFieldFactory.create(3, axiom, null);
		JTextField fRuleTextField = JTextFieldFactory.create(12, fRule, null);
		JTextField gRuleTextField = JTextFieldFactory.create(12, gRule, null);
		JTextField xRuleTextField = JTextFieldFactory.create(12, xRule, null);
		JTextField angleTextField = JTextFieldFactory.create(3, Float.toString(angle), null);
		JTextField lenTextField   = JTextFieldFactory.create(3, Float.toString(len), null);
		JTextField xOPosTextField = JTextFieldFactory.create(3, Float.toString(xPos), null);
		JTextField yOPosTextField = JTextFieldFactory.create(3, Float.toString(yPos), null);
		JTextField xODirTextField = JTextFieldFactory.create(3, Float.toString(xDir), null);
		JTextField yODirTextField = JTextFieldFactory.create(3, Float.toString(yDir), null);
		
		LSystemPanel lSysPanel = new LSystemPanel(size, axiom, fRule, gRule, xRule, angleRadian, len, xPos, yPos, xDir, yDir);
		
		LSystemMain lSysMain = new LSystemMain(lSysPanel, axiomTextField, fRuleTextField, gRuleTextField, xRuleTextField, angleTextField,
												lenTextField, xOPosTextField, yOPosTextField, xODirTextField, yODirTextField);
		
		JFrameExtends f = new JFrameExtends (
			
			new JPanelExtends (
				size, 40,
				JLabelFactory.create("Axiom"), axiomTextField,
				JLabelFactory.create("F Rule"), fRuleTextField,
				JLabelFactory.create("G Rule"), gRuleTextField,
				JLabelFactory.create("X Rule"), xRuleTextField,
				JLabelFactory.create("Angle"), angleTextField
			),
			new JPanelExtends (
				size, 40,
				JLabelFactory.create("Length"), lenTextField,
				JLabelFactory.create("X Pos"), xOPosTextField,
				JLabelFactory.create("Y Pos"), yOPosTextField,
				JLabelFactory.create("X Dir"), xODirTextField,
				JLabelFactory.create("Y Dir"), yODirTextField,
				JButtonFactory.create("Generate", lSysMain.genListener ),
				JButtonFactory.create("Clear", lSysMain.clrListener )
			),
			lSysPanel
		);

		f.setupOnWindowClosing( () -> Utils.print("Closed") );
		f.setLayout( new BoxLayout(f.getContentPane(), BoxLayout.Y_AXIS) );
        f.activate(true);
	}
}