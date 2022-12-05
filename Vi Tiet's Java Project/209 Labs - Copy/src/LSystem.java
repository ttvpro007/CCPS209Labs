import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LSystem {

	private static final List<Character> CONSTANTS = 
			new ArrayList<>( Arrays.asList('X', 'F', 'G', '+', '-', '[', ']') );
	
	private String axiom = "X";
	
//	private String fRule = "FF+[+F-F-F]-[-F+F+F]";
//	private float angle = (float) Math.toRadians(22.5);

//	private String fRule = "F[+F]F[-F]F";
//	private float angle = (float) Math.toRadians(25.7);

//	private String fRule = "F[+F]F[-F][F]";
//	private float angle = (float) Math.toRadians(20);
	
//	private String fRule = "FF";
//	private String xRule = "F[+X]F[-X]+X";
//	private float angle = (float) Math.toRadians(20);
	
	private float angle;
	
	private float len;
	private float lenOrigin;
	
	private Map<Character, String> rules = new HashMap<>();
	
	private String sequence = "";
	
	private Turtle turtle;
	
	public LSystem(String axiom, String fRule, String gRule, String xRule, float angle, float len, Vector2 originPos, Vector2 originDir) {

		sequence = axiom;
		this.axiom = axiom;
		rules.put('F', fRule);
		rules.put('G', gRule);
		rules.put('X', xRule);
		this.angle = angle;
		lenOrigin = len;
		this.len = len;
		turtle = new Turtle( originPos, originDir );
	}
	
	public boolean paramChanged(String axiom, String fRule, String gRule, String xRule, float angle, float len, float xPos, float yPos, float xDir, float yDir) {
		
		return !this.axiom.equals(axiom)				||
			   !rules.get('F').equals(fRule) 			||
			   !rules.get('G').equals(gRule) 			||
			   !rules.get('X').equals(xRule) 			||
			   this.angle != angle 						||
			   lenOrigin != len 						||
			   turtle.getOriginalPositionX() != xPos 	||
			   turtle.getOriginalPositionY() != yPos 	||
			   turtle.getOriginalDirectionX() != xDir	||
			   turtle.getOriginalDirectionY() != yDir;
	}
	
	public void setParams(String axiom, String fRule, String gRule, String xRule, float angle, float len, float xPos, float yPos, float xDir, float yDir) {
		
		if ( validateAxiom(axiom) && validateRule(fRule) && validateRule(gRule) && validateRule(xRule) ) {
			
			Utils.print("Params set successfully");
			this.axiom = axiom;
			rules.put('F', fRule);
			rules.put('G', gRule);
			rules.put('X', xRule);
			this.angle = angle;
			lenOrigin = len;
			this.len = len;
			turtle.setOriginTransform(xPos, yPos, xDir, yDir);
		}
	}
	
	public String generate() {
		
		len /= 2f;
		
		String newSequence = "";
		
		for (int i = 0; i < sequence.length(); i++) {
			
			char currentChar = sequence.charAt(i);
			
			if ( rules.containsKey(currentChar) ) {
				
				newSequence += rules.get(currentChar);
			}
			else {
				
				newSequence += currentChar;
			}
		}
		
		sequence = newSequence;
		Utils.print("Sequence: " + sequence);
		
		return newSequence;
	}
	
	public void draw(Graphics2D g2D) {

		turtle.resetTransform();
		
		for (int i = 0; i < sequence.length(); i++) {

			char currentChar = sequence.charAt(i);
			
			if (currentChar == 'F' || currentChar == 'G') {
				
				turtle.move(g2D, len);
			}
			else if (currentChar == '[') {
				
				turtle.saveTransform();
			}
			else if (currentChar == ']') {

				turtle.loadLastTransform();
			}
			else if (currentChar == '+') {
				
				turtle.rotate( (float) angle );
			}
			else if (currentChar == '-') {
				
				turtle.rotate( (float) -angle );
			}
		}
	}
	
	public void reset() {
		
		sequence = axiom;
		len = lenOrigin;
		turtle.resetTransform();
	}
	
	private boolean validateAxiom(String axiom) {
		
		if ( axiom.equals("F") || axiom.equals("X") ) {
			
			return true;
		}
		else {

			Utils.print( String.format("Axiom %s is invalid. Should be either F or X.", axiom) );
			return false;
		}
	}
	
	private boolean validateRule(String rule) {
		
		for ( var c : rule.toCharArray() ) {
			
			if ( !CONSTANTS.contains(c) ) {

				Utils.print( String.format("Rule %s is invalid. Should be includes characters in %s only.", rule, CONSTANTS.toString() ) );
				return false;
			}
		}

		return true;
	}
}
