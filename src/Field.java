
public class Field {
	private int value;
	private final boolean INITIAL; 
	
	public Field() {
		value = 0;
		INITIAL = false;
	}
	
	public Field(int val, boolean init) {
		value = val;
		INITIAL = init;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int val) {
		value = val;
	}
	
	public boolean getInitial() {
		return INITIAL;
	}
	
	public String toString() {
		
		if (INITIAL) {
			return value + "'";
		}
		else return value + "";
	}
}
