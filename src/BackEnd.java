import java.util.Scanner;

public class BackEnd {
	public static DiveTable table = new DiveTable();
	public static void main(String[] args) {
		boolean x = true;
		Scanner scan = new Scanner(System.in);
		generateDiveTable();
		while(x) {
			System.out.println("Please enter in a Depth in feet");
			int depth = scan.nextInt();
			System.out.println("Please enter in a Time in feet");
			int time = scan.nextInt();
			System.out.println(diveCalc(depth, time));
		}
		scan.close();
	}
	
	public static void generateDiveTable() {
		table.diveTable[0] = new DiveRow(40,12);
		table.diveTable[0].diveRow[0] = new DiveCell(40,5,"A",false,0);
		table.diveTable[0].diveRow[1] = new DiveCell(40,15,"B", false,0);
		table.diveTable[0].diveRow[2] = new DiveCell(40,25,"C", false,0);
		table.diveTable[0].diveRow[3] = new DiveCell(40,30,"D", false,0);
		table.diveTable[0].diveRow[4] = new DiveCell(40,40,"E", false,0);
		table.diveTable[0].diveRow[5] = new DiveCell(40,50,"F", false,0);
		table.diveTable[0].diveRow[6] = new DiveCell(40,70,"G", false,0);
		table.diveTable[0].diveRow[7] = new DiveCell(40,80,"H", false,0);
		table.diveTable[0].diveRow[8] = new DiveCell(40,100,"I", false,0);
		table.diveTable[0].diveRow[9] = new DiveCell(40,110,"J", false,0);
		table.diveTable[0].diveRow[10] = new DiveCell(40,130,"K", false,0);
		table.diveTable[0].diveRow[11] = new DiveCell(40,150,"L", true,5);
		table.diveTable[1] = new DiveRow(50);
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
		if(time > 40) {
      return "Invalid Time";
    }
    else if(time > 25) {
      safety = "You need to stop at 15 minutes at 15'. You are in group K.";
    }
    else if(time > 22){
      safety = "You need to stop at 5 minutes at 15'. You are in group H.";
    }
    else if (time > 20) {
      safety = "You are in group G.";
      }
    else if (time > 15) {
      safety = "You are in group F";  
      }
    else if (time > 10) {
      safety = "You are in group E";  
      }
    else if (time > 7) {
      safety = "You are in group D";
      }
    else if (time > 5) {
    	safety = "You are in group C.";
    }
    else if (time <= 5) {
      safety = "You are in group B";  
      }
		return safety;
		
	}
	public static String dive110(int depth, int time) {
		String safety = "";
		if(time > 30) {
      return "Invalid Time";
    }
    else if(time > 20) {
      safety = "You need to stop at 7 minutes at 15'. You are in group J.";
    }
    else if(time > 15){
      safety = "You need to stop at 5 minutes at 15'. You are in group G.";
    }
    else if (time > 13) {
      safety = "You are in group F.";
      }
    else if (time > 10) {
      safety = "You are in group E";  
      }
    else if (time > 5) {
    	safety = "You are in group D.";
    }
    else if (time <= 5) {
      safety = "You are in group C";  
      }
		return safety;
		
	}
	public static String dive120(int depth, int time) {
		String safety = "";
		if(time > 30) {
      return "Invalid Time";
    }
    else if(time > 25) {
      safety = "You need to stop at 14 minutes at 15'. You are in group J.";
    }
    else if(time > 15){
      safety = "You need to stop at 6 minutes at 15'. You are in group I.";
    }
    else if (time > 12) {
      safety = "You need to stop at 5 minutes at 15. You are in group F.";
      }
    else if (time > 10) {
      safety = "You are in group E";  
      }
    else if (time > 5) {
      safety = "You are in group D.";
    }
    else if (time <= 5) {
      safety = "You are in group C";  
      }
		return safety;

		
	}
	public static String dive130(int depth, int time) {
		String safety = "";
		if(time > 25) {
      return "Invalid Time";
    }
    else if(time > 10) {
      safety = "You need to stop at 10 minutes at 15'. You are in group J.";
    }
    else if(time > 8){
      safety = "You need to stop at 5 minutes at 15'. You are in group E.";
    }
    else if (time > 5) {
      safety = "You are in group D.";
      }
    else if (time <= 5) {
    	safety = "You are in group C.";
    }
		return safety;
		
	}

}
