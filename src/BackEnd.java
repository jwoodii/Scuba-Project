
public class BackEnd {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static String diveCalc(int depth, int time) {
		String interval = "";
		int x = depth%10;
		int y = 0;

		
		if(x != 0) {
			 y = 1;
		}
		//Rounds depth to the nearest 10.
		depth = ((depth/10) + y) * 10;
		//if depth is less than 40 assume depth 40 for calculations
		if(depth < 40) {
			depth = 40;
		}
		if(depth == 40) {
			interval = dive40(depth,time);
		}
		else if (depth == 50) {
			interval = dive50(depth,time);
		}
		else if (depth == 60) {
			interval = dive60(depth,time);
		}
		else if (depth == 70) {
			interval = dive70(depth,time);
		}
		else if (depth == 80) {
			interval = dive80(depth,time);
		}
		else if (depth == 90) {
			interval = dive90(depth,time);
		}
		else if (depth == 100) {
			interval = dive100(depth,time);
		}
		else if (depth == 110) {
			interval = dive110(depth,time);
		}
		else if (depth == 120) {
			interval = dive120(depth,time);
		}
		else if (depth == 130) {
			interval = dive130(depth,time);
		}
		
return interval;
	}
	public static String dive40(int depth, int time) {
		String interval = "";
		return interval;
		
	}
	public static String dive50(int depth, int time) {
		String interval = "";
		return interval;
		
	}
	public static String dive60(int depth, int time) {
		String interval = "";
		return interval;
		
	}
	public static String dive70(int depth, int time) {
		String interval = "";
		return interval;
		
	}
	public static String dive80(int depth, int time) {
		String interval = "";
		return interval;
		
	}
	public static String dive90(int depth, int time) {
		String interval = "";
		return interval;
		
	}
	public static String dive100(int depth, int time) {
		String interval = "";
		return interval;
		
	}
	public static String dive110(int depth, int time) {
		String interval = "";
		return interval;
		
	}
	public static String dive120(int depth, int time) {
		String interval = "";
		return interval;
		
	}
	public static String dive130(int depth, int time) {
		String interval = "";
		return interval;
		
	}
	
}
