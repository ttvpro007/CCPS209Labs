public class MutableInt {

	public int value;
	
	public MutableInt(int value) {
		
		this.value = value;
	}
	
	public MutableInt add(MutableInt other) {

		value += other.value;
		return this;
	}
	public MutableInt add(int other) {

		value += other;
		return this;
	}
	
	public MutableInt sub(MutableInt other) {

		value -= other.value;
		return this;
	}
	public MutableInt sub(int other) {

		value -= other;
		return this;
	}
	
	public MutableInt mul(MutableInt other) {

		value *= other.value;
		return this;
	}
	public MutableInt mul(int other) {

		value *= other;
		return this;
	}
	
	public MutableInt div(MutableInt other) {
		
		value /= other.value;
		return this;
	}
	public MutableInt div(int other) {
		
		value /= other;
		return this;
	}
	
	public MutableInt mod(MutableInt other) {
		
		value %= other.value;
		return this;
	}
	public MutableInt mod(int other) {
		
		value %= other;
		return this;
	}
}