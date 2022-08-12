import java.util.ArrayList;

public class AccessCountArrayList<E> extends ArrayList<E> {

	private int accessCount;
	
	public AccessCountArrayList() {
		
		resetCount();
	}
	
	@Override
	public E get(int index) {
		
		accessCount++;
		return super.get(index);
	}
	
	@Override
	public E set(int index, E element) {
		
		accessCount++;
		return super.set(index, element);
	}
	
	public int getAccessCount() {
		
		return accessCount;
	}
	
	public void resetCount() {
		
		accessCount = 0;
	}
}
