
public class DiveTable {
	DiveRow[] diveTable;
	int length;
	public DiveTable(DiveRow[] diveTable, int length) {
		super();
		this.diveTable = diveTable;
		this.length = diveTable.length;
	}
	public DiveTable() {
		// TODO Auto-generated constructor stub
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
