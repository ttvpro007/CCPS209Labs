import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class WordCount extends FileProcessor< List<Integer> > {

	private int characters;
	private int words;
	private int lines;
	
	@Override
	protected void startFile() {

		characters = 0;
		words = 0;
		lines = 0;
	}

	@Override
	protected void processLine(String line) {

		characters += line.length();
		words += new StringTokenizer(line).countTokens();
		this.lines++;
	}

	@Override
	protected List<Integer> endLine() {

		return Arrays.asList(characters, words, lines);
	}
}