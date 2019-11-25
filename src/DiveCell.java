

public class DiveCell {
	int depth;
	int time;
	String group;
	boolean safetyStop;
	int stopTime;

	public DiveCell(int depth, int time, String group, boolean safetyStop, int stopTime) {
		super();
		this.depth = depth;
		this.time = time;
		this.group = group;
		this.safetyStop = safetyStop;
		this.stopTime = stopTime;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public boolean isSafetyStop() {
		return safetyStop;
	}

	public void setSafetyStop(boolean safetyStop) {
		this.safetyStop = safetyStop;
	}

	public int getStopTime() {
		return stopTime;
	}

	public void setStopTime(int stopTime) {
		this.stopTime = stopTime;
	}

}
