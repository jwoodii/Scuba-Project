
public class DiveRow {
	int depth;
	DiveCell[] diveRow;
	int length;
	

	public DiveRow(int depth, int length) {
		this.depth = depth;
		this.diveRow = new DiveCell[length];
		this.length = length;

	}
}
