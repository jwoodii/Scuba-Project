
public class SITColumn {
	String initialGroup;
	SITCell[] sitColumn;
	int length;
	
	public SITColumn(String initialGroup, int length) {
		this.initialGroup = initialGroup;
		this.sitColumn = new SITCell[length];
		this.length = length;

	}
}
