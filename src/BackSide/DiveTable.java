package BackSide;
public class DiveTable {
	DiveRow[] diveTable;
	int length;
	public DiveTable(DiveRow[] diveTable, int length) {
		super();
		this.diveTable = diveTable;
		this.length = diveTable.length;
	}
	public DiveTable(int length) {
		this.diveTable = new DiveRow[length];
		this.length = length;
	}
	public DiveRow[] getDiveTable() {
		return diveTable;
	}
	public void setDiveTable(DiveRow[] diveTable) {
		this.diveTable = diveTable;
	}
	public int getLength() {
		return length;
	}
	public void setLength() {
		this.length = diveTable.length;
	}
}
