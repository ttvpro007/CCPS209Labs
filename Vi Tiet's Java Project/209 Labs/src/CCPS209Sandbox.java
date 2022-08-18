import javax.script.ScriptException;

public class CCPS209Sandbox {
	
	public static void main(String[] args) throws ScriptException {

		var soa = new SieveOfAtkin(10000000);

		for (var n : soa.getGenerator()) {
			
			Utils.print(n);
		}
	}
}
