package src;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Tail extends FileProcessor< List<String> > {

//	private List<String> lines;
	private LinkedList<String> lines;
	private int takeLines;
	
	public Tail(int n) {
		
		takeLines = n;
	}
	
	@Override
	protected void startFile() {
		
//		lines = new ArrayList<>();
		lines = new LinkedList<>();
	}

	@Override
	protected void processLine(String line) {

		lines.add(line);
		
		if (lines.size() > takeLines) {
			
			lines.removeFirst();
		}
	}

	@Override
	protected List<String> endLine() {

//		if (lines.size() >= takeLines) {
//			
//			return lines.subList( lines.size() - takeLines, lines.size() );
//		}
//		else {
//			
//			return lines;
//		}
		
		return lines;
	}
}