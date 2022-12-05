import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JTextField;

public class LissajousMain implements ActionListener {

	private static final int SIZE = 500;
	private static final float SPEED = 100f;
	private static final double INITIAL_COSINE_DEGREE_STEP = 1.0;
	private static final double INITIAL_SINE_DEGREE_STEP = -2.0;
	private static final double INITIAL_DEGREE_ANGLE = 4.5;
	
	private LissajousPanel lissajousPanel;
	private JTextField speedTextField;
	private JTextField cosineStepTextField;
	private JTextField sineStepTextField;
	private JTextField degreeAngleTextField;

	public LissajousMain(LissajousPanel lissajousPanel, JTextField speedTextField, JTextField cosineStepTextField, JTextField sineStepTextField, JTextField degreeAngleTextField) {
		
		this.lissajousPanel = lissajousPanel;
		this.speedTextField = speedTextField;
		this.cosineStepTextField = cosineStepTextField;
		this.sineStepTextField = sineStepTextField;
		this.degreeAngleTextField = degreeAngleTextField;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		float speed = (float) Utils.eval( speedTextField.getText() );
		double cosineStep = Utils.eval( cosineStepTextField.getText() );
		double sineStep = Utils.eval( sineStepTextField.getText() );
		double degAngle = Utils.eval( degreeAngleTextField.getText() );
		
		lissajousPanel.setParams(speed, cosineStep, sineStep, degAngle);
	}
	
	public static void main(String[] args) {
		
		JTextField speedTextField = JTextFieldFactory.create(5, String.format("%.2f", SPEED), null);
		JTextField cosineStepTextField = JTextFieldFactory.create(5, String.format("%.2f", INITIAL_COSINE_DEGREE_STEP), null);
		JTextField sineStepTextField = JTextFieldFactory.create(5, String.format("%.2f", INITIAL_SINE_DEGREE_STEP), null);
		JTextField degreeAngleTextField = JTextFieldFactory.create(5, String.format("%.2f", INITIAL_DEGREE_ANGLE), null);
		
		LissajousPanel lissajousPanel = new LissajousPanel(SIZE, SPEED);
		
		LissajousMain lm = new LissajousMain(lissajousPanel, speedTextField, cosineStepTextField, sineStepTextField, degreeAngleTextField);
		
		JFrameExtends f = new JFrameExtends (
			
			new JPanelExtends (
				
				SIZE, 60,
				JLabelFactory.create("Speed:"), speedTextField,
				JLabelFactory.create("Cosine Step:"), cosineStepTextField,
				JLabelFactory.create("Sine Step:"), sineStepTextField,
				JLabelFactory.create("Angle:"), degreeAngleTextField,
				JButtonFactory.create("Submit", lm)
			),
			lissajousPanel
		);
        
        f.setupOnWindowClosing( () -> lissajousPanel.terminate() );		
		f.setLayout( new BoxLayout(f.getContentPane(), BoxLayout.Y_AXIS) );
        f.activate(true);
	}
}
