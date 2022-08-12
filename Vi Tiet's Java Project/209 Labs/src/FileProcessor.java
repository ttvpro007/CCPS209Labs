import java.io.BufferedReader;
import java.io.IOException;

public abstract class FileProcessor<R> {

	protected abstract void startFile();
	protected abstract void processLine(String line);
	protected abstract R endLine();
	
	public final R processFile(BufferedReader in) throws IOException {
		
		startFile();
		in.lines().forEach( line -> processLine(line) );
		return endLine();
	}
}