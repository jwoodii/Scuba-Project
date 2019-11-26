
public class DiveCell {
	int depth;
	int time;
	String group;
	boolean safetyStop;
	int stopTime;

	public DiveCell(int depth, int time, String group, boolean safetyStop, int stopTime) {
		this.depth = depth;
		this.time = time;
		this.group = group;
		this.safetyStop = safetyStop;
		this.stopTime = stopTime;
	}
}
