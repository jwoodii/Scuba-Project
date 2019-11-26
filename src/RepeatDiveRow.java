
public class RepeatDiveRow {
	String group;
	RepeatDiveCell[] repeatDiveRow;
	int length;
	
	public RepeatDiveRow(String group, int length) {
		this.group = group;
		this.repeatDiveRow = new RepeatDiveCell[length];
		this.length = length;

	}
}
