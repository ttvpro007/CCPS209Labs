public class CCPS209Sandbox {
	
	public static void main(String[] args) {
		
		String a = "Hello";
		String b = "Hello";
		String c = new String("Hello");
		
		Utils.print("string a = b " + Boolean.toString(a == b));
		Utils.print("a.equals(b) " + a.equals(b));
		Utils.print("string a = c " + Boolean.toString(a == c));
		Utils.print("a.equals(c) " + a.equals(c));
	}
}