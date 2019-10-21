import java.util.Scanner;

public class BackEnd {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		while(true) {
			System.out.println("Please enter in a Depth in feet");
			int depth = scan.nextInt();
			System.out.println("Please enter in a Time in feet");
			int time = scan.nextInt();
			System.out.println(diveCalc(depth, time));
		}

	}
	public static String diveCalc(int depth, int time) {
		String safety = "";
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
			safety = dive40(depth,time);
		}
		else if (depth == 50) {
			safety = dive50(depth,time);
		}
		else if (depth == 60) {
			safety = dive60(depth,time);
		}
		else if (depth == 70) {
			safety = dive70(depth,time);
		}
		else if (depth == 80) {
			safety = dive80(depth,time);
		}
		else if (depth == 90) {
			safety = dive90(depth,time);
		}
		else if (depth == 100) {
			safety = dive100(depth,time);
		}
		else if (depth == 110) {
			safety = dive110(depth,time);
		}
		else if (depth == 120) {
			safety = dive120(depth,time);
		}
		else if (depth == 130) {
			safety = dive130(depth,time);
		}
		
return safety;
	}
	public static String dive40(int depth, int time) {
		String safety = "";
		if(time > 150) {
			return "Invalid Time";
		}
		else if(time > 130) {
			safety = "You need to stop 5 minutes at 15'. You are in group L.";
		}
		else if (time > 110) {
		safety = "You are in group K";	
		}
		else if (time > 100) {
			safety = "You are in group J";	
			}
		else if (time > 80) {
			safety = "You are in group I";	
			}
		else if (time > 70) {
			safety = "You are in group H";	
			}
		else if (time > 50) {
			safety = "You are in group G";	
			}
		else if (time > 40) {
			safety = "You are in group F";	
			}
		else if (time > 30) {
			safety = "You are in group E";	
			}
		else if (time > 25) {
			safety = "You are in group D";	
			}
		else if (time > 15) {
			safety = "You are in group C";	
			}
		else if (time > 5) {
			safety = "You are in group B";	
			}
		else if (time <= 5) {
			safety = "You are in group A";	
			}
		return safety;
		
	}
	public static String dive50(int depth, int time) {
		String safety = "";
		if(time > 100) {
			return "Invalid Time";
		}
		else if(time > 80) {
			safety = "You need to stop 5 minutes at 15'. You are in group L.";
		}
		else if (time > 70) {
		safety = "You are in group J";	
		}
		else if (time > 60) {
			safety = "You are in group I";	
			}
		else if (time > 50) {
			safety = "You are in group H";	
			}
		else if (time > 40) {
			safety = "You are in group G";	
			}
		else if (time > 30) {
			safety = "You are in group F";	
			}
		else if (time > 25) {
			safety = "You are in group E";	
			}
		else if (time > 15) {
			safety = "You are in group D";	
			}
		else if (time > 10) {
			safety = "You are in group C";	
			}
		else if (time <= 10) {
			safety = "You are in group B";	
			}
		return safety;
		
	}
	public static String dive60(int depth, int time) {
		String safety = "";
		if(time > 80) {
			return "Invalid Time";
		}
		else if(time > 60) {
			safety = "You need to stop 7 minutes at 15'. You are in group L.";
		}
		else if (time > 55) {
		safety = "You need to stop 5 minutes at 15'. You are in group J.";	
		}
		else if (time > 50) {
			safety = "You are in group I";	
			}
		else if (time > 40) {
			safety = "You are in group H";	
			}
		else if (time > 30) {
			safety = "You are in group G";	
			}
		else if (time > 25) {
			safety = "You are in group F";	
			}
		else if (time > 20) {
			safety = "You are in group E";	
			}
		else if (time > 15) {
			safety = "You are in group D";	
			}
		else if (time > 10) {
			safety = "You are in group C";	
			}
		else if (time <= 10) {
			safety = "You are in group B";	
			}
		return safety;
		
	}
	public static String dive70(int depth, int time) {
		String safety = "";
		if(time > 70) {
			return "Invalid Time";
		}
		else if(time > 60) {
			safety = "You need to stop at 14 minutes at 15'. You are in group L.";
		}
		else if(time > 50){
			safety = "You need to stop at 8 minutes at 15'. You are in group K.";
		}
		else if(time > 45){
			safety = "You need to stop at 5 minutes at 15'. You are in group J.";
		}
		else if (time > 40) {
			safety = "You are in group I";	
			}
		else if (time > 35) {
			safety = "You are in group H";	
			}
		else if (time > 30) {
			safety = "You are in group G";	
			}
		else if (time > 20) {
			safety = "You are in group F";	
			}
		else if (time > 15) {
			safety = "You are in group E";	
			}
		else if (time > 10) {
			safety = "You are in group D";	
			}
		else if (time > 5) {
			safety = "You are in group C";	
			}
		else if (time <= 5) {
			safety = "You are in group B";	
			}
		return safety;
		
	}
	public static String dive80(int depth, int time) {
		String safety = "";
		if(time > 60) {
			return "Invalid Time";
		}
		else if(time > 50) {
			safety = "You need to stop at 17 minutes at 15'. You are in group L.";
		}
		else if(time > 40){
			safety = "You need to stop at 10 minutes at 15'. You are in group K.";
		}
		else if(time > 35){
			safety = "You need to stop at 5 minutes at 15'. You are in group I.";
		}
		else if (time > 30) {
			safety = "You are in group H";	
			}
		else if (time > 25) {
			safety = "You are in group G";	
			}
		else if (time > 20) {
			safety = "You are in group F";	
			}
		else if (time > 15) {
			safety = "You are in group E";	
			}
		else if (time > 10) {
			safety = "You are in group D";	
			}
		else if (time > 5) {
			safety = "You are in group C";	
			}
		else if (time <= 5) {
			safety = "You are in group B";	
			}
		return safety;
		
	}
	public static String dive90(int depth, int time) {
		String safety = "";
		if(time > 50) {
			return "Invalid Time";
		}
		else if(time > 40) {
			safety = "You need to stop at 18 minutes at 15'. You are in group L.";
		}
		else if(time > 30){
			safety = "You need to stop at 7 minutes at 15'. You are in group J.";
		}
		else if (time > 25) {
			safety = "You need to stop at 5 minutes at 15'. You are in group H.";
			}
		else if (time > 20) {
			safety = "You are in group G";	
			}
		else if (time > 15) {
			safety = "You are in group F";	
			}
		else if (time > 12) {
			safety = "You are in group E";	
			}
		else if (time > 10) {
			safety = "You are in group D";	
			}
		else if (time > 5) {
			safety = "You are in group C";	
			}
		else if (time <= 5) {
			safety = "You are in group B";	
			}
		return safety;
		
	}
	public static String dive100(int depth, int time) {
		String safety = "";
		return safety;
		
	}
	public static String dive110(int depth, int time) {
		String safety = "";
		return safety;
		
	}
	public static String dive120(int depth, int time) {
		String safety = "";
		return safety;

		
	}
	public static String dive130(int depth, int time) {
		String safety = "";
		return safety;
		
	}
	
}
