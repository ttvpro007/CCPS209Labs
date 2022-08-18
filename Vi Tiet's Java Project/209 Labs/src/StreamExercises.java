import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamExercises {

	public static int countLines(Path path, int thres) throws IOException {
		
		try ( var stream = Files.lines(path) ) {
			
			return (int) stream.filter(x -> x.length() >= thres).count();
		}
	}
	
	public static List<String> collectWords(Path path) throws IOException {
		
		try ( var stream = Files.lines(path) ) {
						
			return stream.flatMap( s -> Stream.of( s.toLowerCase().split("[^a-z]+") ) )
					  .filter( s -> !s.isEmpty() )
					  .sorted()
					  .distinct()
					  .collect( Collectors.toList() );
		}
	}
}
