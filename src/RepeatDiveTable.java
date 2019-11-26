
public class RepeatDiveTable {
	RepeatDiveRow[] repeatDiveTable;
	int length;

	public RepeatDiveTable(RepeatDiveRow[] repeatDiveTable) {
		super();
		this.repeatDiveTable = repeatDiveTable;
		this.length = repeatDiveTable.length;
	}

	public RepeatDiveTable(int length) {
		this.repeatDiveTable = new RepeatDiveRow[length];
		this.length = length;
	}
}
