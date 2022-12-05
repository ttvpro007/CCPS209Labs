import java.awt.Graphics2D;
import java.util.Stack;

public class Turtle {
	private float xPosOrigin;
	private float yPosOrigin;
	private Vector2 position;
	private Stack<Vector2> positionStack = new Stack<>();
	
	private float xDirOrigin;
	private float yDirOrigin;
    private Vector2 direction;
    private Stack<Vector2> directionStack = new Stack<>();
    

    public Turtle(float xPos, float yPos, float xDir, float yDir) {
    	
    	setOriginTransform(xPos, yPos, xDir, yDir);
    }
    
    public Turtle(Vector2 startPosition, Vector2 startDirection) {

    	setOriginTransform(startPosition, startDirection);
    }
    
    public void setOriginTransform(float xPos, float yPos, float xDir, float yDir) {
    	
    	xPosOrigin = xPos;
    	yPosOrigin = yPos;
        
        xDirOrigin = xDir;
        yDirOrigin = yDir;

        resetTransform();
    }
    
    public void setOriginTransform(Vector2 startPosition, Vector2 startDirection) {

    	setOriginTransform( startPosition.getX(), startPosition.getY(), startDirection.getX(), startDirection.getY() );
    }
    
    public float getOriginalPositionX() {
    	
    	return xPosOrigin;
    }
    
    public float getOriginalPositionY() {
    	
    	return yPosOrigin;
    }
    
    public float getOriginalDirectionX() {
    	
    	return xDirOrigin;
    }
    
    public float getOriginalDirectionY() {
    	
    	return yDirOrigin;
    }

    public void rotate(float delta) {
    	
    	direction.rotate(delta);
    }

    public void move(Graphics2D g2D, float step) {
    	
        Vector2 oldPosition = position.copy();
        position = Vector2.add( oldPosition, Vector2.mul(direction, step) );
        GraphicsUtils.drawLine(g2D, oldPosition, position);
    }
    
    public void saveTransform() {
    	
    	positionStack.push( position.copy() );
    	directionStack.push( direction.copy() );
    }
    
    public void loadLastTransform() {
    	
    	position = positionStack.pop();
    	direction = directionStack.pop();
    }

    public void resetTransform() {
    	
    	if (position == null) position = new Vector2(xPosOrigin, yPosOrigin);
    	else position.setXY(xPosOrigin, yPosOrigin);
    	if (direction == null) direction = new Vector2(xDirOrigin, yDirOrigin);
    	else direction.setXY(xDirOrigin, yDirOrigin);
    }

}