import java.util.Scanner;

public class BackEnd {
	public static DiveTable table = new DiveTable(10);

	public static void main(String[] args) {
		boolean x = true;
		Scanner scan = new Scanner(System.in);
		generateDiveTable();
		while (x) {
			System.out.println("Please enter in a Depth in feet");
			int depth = scan.nextInt();
			System.out.println("Please enter in a Time in minutes");
			int time = scan.nextInt();
			System.out.println(diveCalc(depth, time));
		}

		scan.close();
	}

	// dive table based on NAUI Dive table. built as array to allow for easy change.
	// builds dive table
	public static void generateDiveTable() {
		table.diveTable[0] = new DiveRow(40, 12);
		table.diveTable[0].diveRow[0] = new DiveCell(table.diveTable[0].depth, 5, "A", false, 0);
		table.diveTable[0].diveRow[1] = new DiveCell(table.diveTable[0].depth, 15, "B", false, 0);
		table.diveTable[0].diveRow[2] = new DiveCell(table.diveTable[0].depth, 25, "C", false, 0);
		table.diveTable[0].diveRow[3] = new DiveCell(table.diveTable[0].depth, 30, "D", false, 0);
		table.diveTable[0].diveRow[4] = new DiveCell(table.diveTable[0].depth, 40, "E", false, 0);
		table.diveTable[0].diveRow[5] = new DiveCell(table.diveTable[0].depth, 50, "F", false, 0);
		table.diveTable[0].diveRow[6] = new DiveCell(table.diveTable[0].depth, 70, "G", false, 0);
		table.diveTable[0].diveRow[7] = new DiveCell(table.diveTable[0].depth, 80, "H", false, 0);
		table.diveTable[0].diveRow[8] = new DiveCell(table.diveTable[0].depth, 100, "I", false, 0);
		table.diveTable[0].diveRow[9] = new DiveCell(table.diveTable[0].depth, 110, "J", false, 0);
		table.diveTable[0].diveRow[10] = new DiveCell(table.diveTable[0].depth, 130, "K", false, 0);
		table.diveTable[0].diveRow[11] = new DiveCell(table.diveTable[0].depth, 150, "L", true, 5);

		table.diveTable[1] = new DiveRow(50, 10);
		table.diveTable[1].diveRow[0] = new DiveCell(table.diveTable[1].depth, 10, "B", false, 0);
		table.diveTable[1].diveRow[1] = new DiveCell(table.diveTable[1].depth, 15, "C", false, 0);
		table.diveTable[1].diveRow[2] = new DiveCell(table.diveTable[1].depth, 25, "D", false, 0);
		table.diveTable[1].diveRow[3] = new DiveCell(table.diveTable[1].depth, 30, "E", false, 0);
		table.diveTable[1].diveRow[4] = new DiveCell(table.diveTable[1].depth, 40, "F", false, 0);
		table.diveTable[1].diveRow[5] = new DiveCell(table.diveTable[1].depth, 50, "G", false, 0);
		table.diveTable[1].diveRow[6] = new DiveCell(table.diveTable[1].depth, 60, "H", false, 0);
		table.diveTable[1].diveRow[7] = new DiveCell(table.diveTable[1].depth, 70, "I", false, 0);
		table.diveTable[1].diveRow[8] = new DiveCell(table.diveTable[1].depth, 80, "J", false, 0);
		table.diveTable[1].diveRow[9] = new DiveCell(table.diveTable[1].depth, 100, "L", true, 5);

		table.diveTable[2] = new DiveRow(60, 10);
		table.diveTable[2].diveRow[0] = new DiveCell(table.diveTable[2].depth, 10, "B", false, 0);
		table.diveTable[2].diveRow[1] = new DiveCell(table.diveTable[2].depth, 15, "C", false, 0);
		table.diveTable[2].diveRow[2] = new DiveCell(table.diveTable[2].depth, 20, "D", false, 0);
		table.diveTable[2].diveRow[3] = new DiveCell(table.diveTable[2].depth, 25, "E", false, 0);
		table.diveTable[2].diveRow[4] = new DiveCell(table.diveTable[2].depth, 30, "F", false, 0);
		table.diveTable[2].diveRow[5] = new DiveCell(table.diveTable[2].depth, 40, "G", false, 0);
		table.diveTable[2].diveRow[6] = new DiveCell(table.diveTable[2].depth, 50, "H", false, 0);
		table.diveTable[2].diveRow[7] = new DiveCell(table.diveTable[2].depth, 55, "I", false, 0);
		table.diveTable[2].diveRow[8] = new DiveCell(table.diveTable[2].depth, 60, "J", true, 5);
		table.diveTable[2].diveRow[9] = new DiveCell(table.diveTable[2].depth, 80, "L", true, 7);

		table.diveTable[3] = new DiveRow(70, 11);
		table.diveTable[3].diveRow[0] = new DiveCell(table.diveTable[3].depth, 5, "B", true, 7);
		table.diveTable[3].diveRow[1] = new DiveCell(table.diveTable[3].depth, 10, "C", false, 0);
		table.diveTable[3].diveRow[2] = new DiveCell(table.diveTable[3].depth, 15, "D", false, 0);
		table.diveTable[3].diveRow[3] = new DiveCell(table.diveTable[3].depth, 20, "E", false, 0);
		table.diveTable[3].diveRow[4] = new DiveCell(table.diveTable[3].depth, 30, "F", false, 0);
		table.diveTable[3].diveRow[5] = new DiveCell(table.diveTable[3].depth, 35, "G", false, 0);
		table.diveTable[3].diveRow[6] = new DiveCell(table.diveTable[3].depth, 40, "H", false, 0);
		table.diveTable[3].diveRow[7] = new DiveCell(table.diveTable[3].depth, 45, "I", false, 0);
		table.diveTable[3].diveRow[8] = new DiveCell(table.diveTable[3].depth, 50, "J", true, 5);
		table.diveTable[3].diveRow[9] = new DiveCell(table.diveTable[3].depth, 60, "K", true, 8);
		table.diveTable[3].diveRow[10] = new DiveCell(table.diveTable[3].depth, 70, "L", true, 14);

		table.diveTable[4] = new DiveRow(80, 10);
		table.diveTable[4].diveRow[0] = new DiveCell(table.diveTable[4].depth, 5, "B", false, 0);
		table.diveTable[4].diveRow[1] = new DiveCell(table.diveTable[4].depth, 10, "C", false, 0);
		table.diveTable[4].diveRow[2] = new DiveCell(table.diveTable[4].depth, 15, "D", false, 0);
		table.diveTable[4].diveRow[3] = new DiveCell(table.diveTable[4].depth, 20, "E", false, 0);
		table.diveTable[4].diveRow[4] = new DiveCell(table.diveTable[4].depth, 25, "F", false, 0);
		table.diveTable[4].diveRow[5] = new DiveCell(table.diveTable[4].depth, 30, "G", false, 0);
		table.diveTable[4].diveRow[6] = new DiveCell(table.diveTable[4].depth, 35, "H", false, 0);
		table.diveTable[4].diveRow[7] = new DiveCell(table.diveTable[4].depth, 40, "I", true, 5);
		table.diveTable[4].diveRow[8] = new DiveCell(table.diveTable[4].depth, 50, "K", true, 10);
		table.diveTable[4].diveRow[9] = new DiveCell(table.diveTable[4].depth, 60, "L", true, 17);

		table.diveTable[5] = new DiveRow(90, 9);
		table.diveTable[5].diveRow[0] = new DiveCell(table.diveTable[5].depth, 5, "B", false, 0);
		table.diveTable[5].diveRow[1] = new DiveCell(table.diveTable[5].depth, 10, "C", false, 0);
		table.diveTable[5].diveRow[2] = new DiveCell(table.diveTable[5].depth, 12, "D", false, 0);
		table.diveTable[5].diveRow[3] = new DiveCell(table.diveTable[5].depth, 15, "E", false, 0);
		table.diveTable[5].diveRow[4] = new DiveCell(table.diveTable[5].depth, 20, "F", false, 0);
		table.diveTable[5].diveRow[5] = new DiveCell(table.diveTable[5].depth, 25, "G", false, 0);
		table.diveTable[5].diveRow[6] = new DiveCell(table.diveTable[5].depth, 30, "H", true, 5);
		table.diveTable[5].diveRow[7] = new DiveCell(table.diveTable[5].depth, 40, "J", true, 7);
		table.diveTable[5].diveRow[8] = new DiveCell(table.diveTable[5].depth, 50, "L", true, 18);

		table.diveTable[6] = new DiveRow(100, 8);
		table.diveTable[6].diveRow[0] = new DiveCell(table.diveTable[6].depth, 5, "B", false, 0);
		table.diveTable[6].diveRow[1] = new DiveCell(table.diveTable[6].depth, 7, "C", false, 0);
		table.diveTable[6].diveRow[2] = new DiveCell(table.diveTable[6].depth, 10, "D", false, 0);
		table.diveTable[6].diveRow[3] = new DiveCell(table.diveTable[6].depth, 15, "E", false, 0);
		table.diveTable[6].diveRow[4] = new DiveCell(table.diveTable[6].depth, 20, "F", false, 0);
		table.diveTable[6].diveRow[5] = new DiveCell(table.diveTable[6].depth, 22, "G", false, 0);
		table.diveTable[6].diveRow[6] = new DiveCell(table.diveTable[6].depth, 25, "H", true, 5);
		table.diveTable[6].diveRow[7] = new DiveCell(table.diveTable[6].depth, 40, "K", true, 15);

		table.diveTable[7] = new DiveRow(110, 6);
		table.diveTable[7].diveRow[0] = new DiveCell(table.diveTable[7].depth, 5, "C", false, 0);
		table.diveTable[7].diveRow[1] = new DiveCell(table.diveTable[7].depth, 10, "D", false, 0);
		table.diveTable[7].diveRow[2] = new DiveCell(table.diveTable[7].depth, 13, "E", false, 0);
		table.diveTable[7].diveRow[3] = new DiveCell(table.diveTable[7].depth, 15, "F", false, 0);
		table.diveTable[7].diveRow[4] = new DiveCell(table.diveTable[7].depth, 20, "G", true, 5);
		table.diveTable[7].diveRow[5] = new DiveCell(table.diveTable[7].depth, 30, "J", true, 7);

		table.diveTable[8] = new DiveRow(120, 6);
		table.diveTable[8].diveRow[0] = new DiveCell(table.diveTable[8].depth, 5, "C", false, 0);
		table.diveTable[8].diveRow[1] = new DiveCell(table.diveTable[8].depth, 10, "D", false, 0);
		table.diveTable[8].diveRow[2] = new DiveCell(table.diveTable[8].depth, 12, "E", false, 0);
		table.diveTable[8].diveRow[3] = new DiveCell(table.diveTable[8].depth, 15, "F", true, 5);
		table.diveTable[8].diveRow[4] = new DiveCell(table.diveTable[8].depth, 25, "I", true, 6);
		table.diveTable[8].diveRow[5] = new DiveCell(table.diveTable[8].depth, 30, "J", true, 14);

		table.diveTable[9] = new DiveRow(130, 4);
		table.diveTable[9].diveRow[0] = new DiveCell(table.diveTable[9].depth, 5, "C", false, 0);
		table.diveTable[9].diveRow[1] = new DiveCell(table.diveTable[9].depth, 8, "D", false, 0);
		table.diveTable[9].diveRow[2] = new DiveCell(table.diveTable[9].depth, 10, "E", true, 5);
		table.diveTable[9].diveRow[3] = new DiveCell(table.diveTable[9].depth, 25, "J", true, 10);

	}

	public static String diveCalc(int depth, int time) {
		// if the depth is above the max depth we can automatically return invalid
		// depth. this
		// prevents array out of bounds.
		// this can be put into main later so that depth is checked automagically
		if (depth > table.diveTable[table.diveTable.length - 1].depth) {
			return "Invalid Depth";
		}
		// for when user wants the max depth they can dive to for a given time
		if (depth == 0) {
			// to keep the while loop running
			boolean go = true;
			// index for what dive row
			int x = 0;
			// place holder for the length of the dive row, allows us to jump to the max
			// time at depth.
			int length;
			// starts off as invalid because if time is greater then the most shallow depth,
			// the time is invalid
			String safety = "Invalid Time";
			while (go) {
				// set length to max length
				length = table.diveTable[x].length - 1;
				// check if time is beyond max time for x depth
				if (time > table.diveTable[x].diveRow[length].time) {
					// checks that the next depth doesn't have the same max time for the depth.
					if (table.diveTable[x].diveRow[length].stopTime != table.diveTable[x
							+ 1].diveRow[table.diveTable[x + 1].diveRow.length].time)
						// return if the next depth isn't the same max time
						return safety;

				} else {
					// replace safety with current max depth
					safety = "You can dive to a maximum depth of " + table.diveTable[x].diveRow[length].depth
							+ " feet for " + time + " minutes.";
					// increment to next depth
					x++;
					// check that we haven't hit the maximum depth of the table
					if (x == table.diveTable.length) {
						//return the current depth if we have hit the max depth
						return "You can dive to a maximum depth of " + table.diveTable[x - 1].diveRow[length].depth
								+ " feet for " + time + " minutes.";
					}

				}
			}
			return safety;
		}

		/*
		 * add in time = 0 check to give max time you can dive at depth x.
		 */

		// if depth is valid we move on.
		String safety = "";
		// x checks to see if depth is a multiple of ten while y gets the tens place.
		int x = depth % 10;
		int y = depth / 10;
		// if x is not zero this means it is not a multiple of ten and we add one to y
		if (x != 0) {
			y++;
		}
		// we minus 4 to change y into index to get us the right depth row.
		y = y - 4;
		// this checks if the time is above the max time or not. if so return invalid
		// time
		if (table.diveTable[y].diveRow[table.diveTable[y].diveRow.length - 1].time < time) {
			return "Invalid Time";
		}
		// if time is valid we run through each time interval of the given row.
		for (int i = 0; i < table.diveTable[y].length; i++) {
			// we check if time is in the time interval and return the results or continue
			// on/
			if (time <= table.diveTable[y].diveRow[i].time) {
				safety = "You are in group " + table.diveTable[y].diveRow[i].group;
				if (table.diveTable[y].diveRow[i].safetyStop) {
					safety = "You need to stop " + table.diveTable[y].diveRow[i].stopTime + " at 15'. " + safety;
				}
				// breaks for loop and allows us to skip any further iterations while also
				// avoiding comparisons with
				// clearly larger time interval resulting in wrong group/info
				return safety;
			}
		}
		return safety;
	}
}
