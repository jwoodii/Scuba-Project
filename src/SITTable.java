
public class SITTable {
	SITColumn[] sitTable;
	int length;
	
	public SITTable(SITColumn[] sitTable) {
		super();
		this.sitTable = sitTable;
		this.length = sitTable.length;
	}
	public SITTable(int length) {
		this.sitTable = new SITColumn[length];
		this.length = length;
	}
}
