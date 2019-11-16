package BackSide;
public class DiveRow {
	int depth;
	DiveCell[] diveRow;
	int length;
	
	public DiveRow(int depth, DiveCell[] diveRow, int length) {
		super();
		this.depth = depth;
		this.diveRow = diveRow;
		this.length = diveRow.length;
	}
	public DiveRow() {
		// TODO Auto-generated constructor stub
	}
	public DiveRow(int depth, int length) {
		this.depth = depth;
		this.diveRow = new DiveCell[length];
		this.length = length;

	}
	public int getDepth() {
		return depth;
	}
	public DiveCell[] getDiveRow() {
		return diveRow;
	}
	public void setDiveRow(DiveCell[] diveRow) {
		this.diveRow = diveRow;
	}
	public int getLength() {
		return length;
	}
	public void setLength() {
		this.length = diveRow.length;
	}
}
