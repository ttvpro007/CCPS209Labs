import java.util.ArrayList;
import java.util.List;

public class Tail extends FileProcessor< List<String> > {

	private List<String> lines;
	private int takeLines;
	
	public Tail(int n) {
		
		takeLines = n;
	}
	
	@Override
	protected void startFile() {
		
		lines = new ArrayList<>();
	}

	@Override
	protected void processLine(String line) {

		lines.add(line);
	}

	@Override
	protected List<String> endLine() {

		if (lines.size() >= takeLines) {
			
			return lines.subList( lines.size() - takeLines, lines.size() );
		}
		else {
			
			return lines;
		}
	}

}