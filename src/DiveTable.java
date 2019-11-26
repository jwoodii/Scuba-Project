
public class DiveTable {
	DiveRow[] diveTable;
	int length;

	public DiveTable(DiveRow[] diveTable) {
		super();
		this.diveTable = diveTable;
		this.length = diveTable.length;
	}

	public DiveTable(int length) {
		this.diveTable = new DiveRow[length];
		this.length = length;
	}
}
