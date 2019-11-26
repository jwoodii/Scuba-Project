
public class SITColumn {
	String initialGroup;
	SITCell[] SITColumn;
	int length;
	
	public SITColumn(String initialGroup, int length) {
		this.initialGroup = initialGroup;
		this.SITColumn = new SITCell[length];
		this.length = length;

	}
}
