import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DiveCalculator implements ActionListener {
	JTextField tfd, tft, tfih, tfim;
	JTextArea tfa;
	JScrollPane sp;
	// tells if its a repeat dive or not
	boolean repeatDive = false;
	// used to keep track of number of inquiries, instructions are printed out every
	// * dives
	int counter = 0;
	// Dive group, only changed by button press and diveCalc, diveCalcRD
	String Group = null;
	// anything above max is auto group A
	static final int maxSurfaceTime = 1440;
	// anything below min is rejected
	static final int minSurfaceTime = 10;
	// Table 1 of Naui tables, used to tell group based on time and depth
	public static DiveTable table = new DiveTable(10);
	// Table 2 of Naui tables, used to tell new group based on initial group and
	// Surface Interval Time (SIT)
	public static SITTable sitTable = new SITTable(12);
	// Table 3 of Naui tables, used to tell rnt based on group and depth, RNT must
	// be subtracted from dive times listed in Table 1.
	public static RepeatDiveTable rpTable = new RepeatDiveTable(12);

	public static void main(String[] args) {
		new DiveCalculator();
	}

	public DiveCalculator() {
		// Creating the Frame
		JFrame frame = new JFrame("Dive Calculator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 600);

		// creating the panel at top and adding instructions
		JPanel inst = new JPanel();
		JLabel instruct = new JLabel("Enter in a Depth and time to check for group letter.");
		JLabel instruct2 = new JLabel(
				"Use a zero in either depth or time to be given the maximum Depth/Time for given value.");
		inst.add(instruct);
		inst.add(instruct2);

		// Creating the panel at bottom and adding components
		JPanel panel = new JPanel(); // the panel is not visible in output
		JLabel Depth = new JLabel("Enter Depth");
		JLabel Time = new JLabel("Enter Time");
		JLabel IntervalH = new JLabel("Enter Hour Interval");
		JLabel IntervalM = new JLabel("Enter Minute Interval");
		tfd = new JTextField(10); // accepts up to 10 characters
		tft = new JTextField(10);
		tfih = new JTextField(10);
		tfim = new JTextField(10);
		JButton Enter = new JButton("Enter");

		panel.add(Depth); // Components Added using Flow Layout
		panel.add(tfd);
		panel.add(Time);
		panel.add(tft);
		panel.add(IntervalH);
		panel.add(tfih);
		panel.add(IntervalM);
		panel.add(tfim);
		panel.add(Enter);
		tfim.setEditable(false);
		tfih.setEditable(false);
		Enter.addActionListener(this);

		// Text Area at the Center
		tfa = new JTextArea();
		sp = new JScrollPane(tfa);

		// Adding Components to the frame.
		frame.getContentPane().add(BorderLayout.NORTH, inst);
		frame.getContentPane().add(BorderLayout.SOUTH, panel);
		frame.getContentPane().add(BorderLayout.CENTER, sp);
		frame.setVisible(true);

		// generate the dive tables
		generateDiveTable();
		generateSITTable();
		generateRepeatTable();
	}

	@Override
	/*
	 * CHECK IF DEPTH IS > 130 or < 0
	 */
	public void actionPerformed(ActionEvent e) {
		// if it is not a repeat dive
		if (!repeatDive) {
			// get time and depth
			String s1 = tfd.getText();
			String s2 = tft.getText();
			int depth = 0, time = 0;
			// double check in case of non-integer inputs
			try {
				depth = Integer.parseInt(s1);
				time = Integer.parseInt(s2);
				// check if its an actual dive or just checking what can be done
				if (depth == 0 || time == 0) {
					if (depth == 0) {
						tfa.append(maxDepthForTime(time) + "\n");
						counter++;
					} else if (time == 0) {
						tfa.append(maxTimeForDepth(depth) + "\n");
						counter++;
					}
				} else {
					tfa.append(diveCalc(depth, time) + "\n");
					counter++;
					repeatDive = true;
					tfih.setEditable(true);
					tfim.setEditable(true);
				}
			} catch (NumberFormatException ex) {
				tfa.append("Invalid Parameter\n");
			}
			// Start repeat dive calcs
		} else {
			String s1 = tfd.getText();
			String s2 = tft.getText();
			String s3 = tfih.getText();
			String s4 = tfim.getText();
			int depth, time, intervalHours, intervalMinutes;
			// double check in case of non-integer inputs
			try {
				// part integers from strings
				depth = Integer.parseInt(s1);
				time = Integer.parseInt(s2);
				intervalHours = Integer.parseInt(s3);
				intervalMinutes = Integer.parseInt(s4);
				// turn SIT into minutes
				int newTime = intervalHours * 60 + intervalMinutes;
				// if time is less than minimum exit
				if (newTime < minSurfaceTime) {
					tfa.append("Interval time must be larger than 10 minutes");
				} else {
					// if time is greater than max SIT auto A
					if (newTime > maxSurfaceTime) {
						Group = "A";
						// else calc new group
					} else {
						Group = newGroup(Group, newTime);
					}
					// if depth = 0 run maxDepthForTimeRD
					if (depth == 0) {
						tfa.append(maxDepthForTimeRD(time) + "\n");
					}
					// else if time = 0 run maxTimeForDepthRD
					else if (time == 0) {
						tfa.append(maxTimeForDepth(depth));
					}
					// else run diveCalcRD
					else {
						tfa.append(diveCalcRD(time, depth) + "\n");
					}
				}
			} catch (NumberFormatException ex) {
				tfa.append("Invalid Parameter\n");
			}
		}
		tfd.setText(null);
		tft.setText(null);
		tfih.setText(null);
		tfim.setText(null);
	}

	public String diveCalcRD(int depth, int time) {
		String safety = "";

		return safety;
	}

	public String maxDepthForTimeRD(int time) {
		String safety = "";
		// find max depth for time given

		// calculate rnt for max depth

		// if time + rnt > max time for given depth

		// move up one depth and begin again

		// else return results;
		return safety;
	}

	public String maxTimeForDepthRD(int depth) {
		String safety = "";
		int time;
		// calc rnt for given depth
		int rnt = newRNT(Group, depth);
		// find max time for the given depth
		for (int i = 0; i < table.diveTable.length; i++) {
			// check depth
			if (table.diveTable[i].depth == depth) {
				// set index to last cell of row
				int length = table.diveTable[i].length - 1;
				// check for safety stops, and creates safety stop/ time at 15'
				if (table.diveTable[i].diveRow[length].safetyStop) {
					// calc safety stop time - rnt
					time = table.diveTable[i].diveRow[length].time - rnt;
					safety = "You can dive for a maximum of " + time + " minutes at " + depth
							+ ".\nYou will need to stop " + table.diveTable[i].diveRow[length].stopTime
							+ " minutes at 15'.";
				}
				// checks if there a safety stop is still needed
				while (table.diveTable[i].diveRow[length].safetyStop) {
					// goes one back to till we find a non safety stop time
					length--;
				}
				// calc non-safety stop time - rnt
				time = table.diveTable[i].diveRow[length].time - rnt;
				// appends longest time allowed without safety stop to safety stop time
				return "You can dive for " + time + " minutes without making a safety stop.\n" + safety;
			}
		}
		return safety;
	}

	public String newGroup(String initGroup, int minutes) {
		if (minutes > maxSurfaceTime) {
			return "A";
		}
		if (minutes < minSurfaceTime) {
			return null;
		}
		// First find the column with the init group
		for (int i = 0; i < sitTable.length; i++) {
			if (sitTable.sitTable[i].initialGroup == initGroup) {
				for (int j = 0; j < sitTable.sitTable[i].length; j++) {
					if (minutes > sitTable.sitTable[i].sitColumn[j].surfaceIntervalTime) {
						return sitTable.sitTable[i].sitColumn[j - 1].EndGroup;
					}
				}
			}
		}
		return null;
	}

	public int newRNT(String group, int depth) {
		for (int i = 0; i < rpTable.repeatDiveTable.length; i++) {
			if (rpTable.repeatDiveTable[i].group == group) {
				int ones = depth / 10;
				int newDepth = depth;
				if (ones != 0) {
					newDepth = (((depth % 10) + 1) * 10);
				}
				for (int j = 0; j < rpTable.repeatDiveTable[i].length; j++) {
					if (rpTable.repeatDiveTable[i].repeatDiveRow[j].depth == newDepth) {
						return rpTable.repeatDiveTable[i].repeatDiveRow[j].RNT;
					}
				}
			}
		}
		return 0;
	}

	public String maxTimeForDepth(int depth) {
		String safety = "";
		// run through table to find right depth
		for (int i = 0; i < table.diveTable.length; i++) {
			// check depth
			if (table.diveTable[i].depth == depth) {
				// set index to last cell of row
				int length = table.diveTable[i].length - 1;
				// check for safety stops, and creates safety stop/ time at 15'
				if (table.diveTable[i].diveRow[length].safetyStop) {
					safety = "You can dive for a maximum of " + table.diveTable[i].diveRow[length].time + " minutes at "
							+ depth + ".\nYou will need to stop " + table.diveTable[i].diveRow[length].stopTime
							+ " minutes at 15'.";
				}
				// checks if there a safety stop is needed
				while (table.diveTable[i].diveRow[length].safetyStop) {
					// goes one back to till we find a non safety stop time
					length--;
				}
				// appends longest time allowed without safety stop to safety stop time
				return "You can dive for " + table.diveTable[i].diveRow[length].time
						+ " minutes without making a safety stop.\n" + safety;
			}
		}
		return safety;
	}

	public String maxDepthForTime(int time) {
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
				return safety;
			} else {
				// replace safety with current max depth
				safety = "You can dive to a maximum depth of " + table.diveTable[x].diveRow[length].depth + " feet for "
						+ time + " minutes.";
				// increment to next depth
				x++;
				// check that we haven't hit the maximum depth of the table
				if (x == table.diveTable.length) {
					// return the current depth if we have hit the max depth
					return "You can dive to a maximum depth of " + table.diveTable[x - 1].diveRow[length].depth
							+ " feet for " + time + " minutes.";
				}

			}
		}
		return safety;
	}

	/*
	 * we have verified that both time and depth are non zero integer at this point.
	 */
	public String diveCalc(int depth, int time) {
		// if the depth is above the max depth we can automatically return invalid
		// depth. this
		// prevents array out of bounds.
		// this can be put into main later so that depth is checked automagically
		if (depth > table.diveTable[table.diveTable.length - 1].depth) {
			return "Invalid Depth";
		}
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
				safety = " You are diving to a depth of " + depth + " feet for " + time
						+ " minutes.\n You are in group " + table.diveTable[y].diveRow[i].group + ".\n";
				Group = table.diveTable[y].diveRow[i].group;
				if (table.diveTable[y].diveRow[i].safetyStop) {
					safety = safety + " You need to stop " + table.diveTable[y].diveRow[i].stopTime
							+ " minutes at 15'.\n ";
				}
				return safety;
			}
		}
		return safety;
	}

	// generates Table 1 of NAUI dive Tables
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

	public static void generateSITTable() {
		sitTable.sitTable[0] = new SITColumn("A", 1);
		sitTable.sitTable[0].sitColumn[0] = new SITCell(maxSurfaceTime, sitTable.sitTable[0].initialGroup, "A");

		sitTable.sitTable[1] = new SITColumn("B", 2);
		sitTable.sitTable[1].sitColumn[0] = new SITCell(maxSurfaceTime, sitTable.sitTable[1].initialGroup, "A");
		sitTable.sitTable[1].sitColumn[1] = new SITCell(200, sitTable.sitTable[1].initialGroup, "B");

		sitTable.sitTable[2] = new SITColumn("C", 3);
		sitTable.sitTable[2].sitColumn[0] = new SITCell(maxSurfaceTime, sitTable.sitTable[2].initialGroup, "A");
		sitTable.sitTable[2].sitColumn[1] = new SITCell(200, sitTable.sitTable[2].initialGroup, "B");
		sitTable.sitTable[2].sitColumn[2] = new SITCell(200, sitTable.sitTable[2].initialGroup, "C");

		sitTable.sitTable[3] = new SITColumn("D", 4);
		sitTable.sitTable[3].sitColumn[0] = new SITCell(maxSurfaceTime, sitTable.sitTable[3].initialGroup, "A");
		sitTable.sitTable[3].sitColumn[1] = new SITCell(348, sitTable.sitTable[3].initialGroup, "B");
		sitTable.sitTable[3].sitColumn[2] = new SITCell(158, sitTable.sitTable[3].initialGroup, "C");
		sitTable.sitTable[3].sitColumn[3] = new SITCell(69, sitTable.sitTable[3].initialGroup, "D");

		sitTable.sitTable[4] = new SITColumn("E", 5);
		sitTable.sitTable[4].sitColumn[0] = new SITCell(maxSurfaceTime, sitTable.sitTable[4].initialGroup, "A");
		sitTable.sitTable[4].sitColumn[1] = new SITCell(394, sitTable.sitTable[4].initialGroup, "B");
		sitTable.sitTable[4].sitColumn[2] = new SITCell(204, sitTable.sitTable[4].initialGroup, "C");
		sitTable.sitTable[4].sitColumn[3] = new SITCell(117, sitTable.sitTable[4].initialGroup, "D");
		sitTable.sitTable[4].sitColumn[4] = new SITCell(54, sitTable.sitTable[4].initialGroup, "E");

		sitTable.sitTable[5] = new SITColumn("F", 6);
		sitTable.sitTable[5].sitColumn[0] = new SITCell(maxSurfaceTime, sitTable.sitTable[4].initialGroup, "A");
		sitTable.sitTable[5].sitColumn[1] = new SITCell(425, sitTable.sitTable[5].initialGroup, "B");
		sitTable.sitTable[5].sitColumn[2] = new SITCell(237, sitTable.sitTable[5].initialGroup, "C");
		sitTable.sitTable[5].sitColumn[3] = new SITCell(148, sitTable.sitTable[5].initialGroup, "D");
		sitTable.sitTable[5].sitColumn[4] = new SITCell(89, sitTable.sitTable[5].initialGroup, "E");
		sitTable.sitTable[5].sitColumn[5] = new SITCell(45, sitTable.sitTable[5].initialGroup, "F");

		sitTable.sitTable[6] = new SITColumn("G", 7);
		sitTable.sitTable[6].sitColumn[0] = new SITCell(maxSurfaceTime, sitTable.sitTable[6].initialGroup, "A");
		sitTable.sitTable[6].sitColumn[1] = new SITCell(455, sitTable.sitTable[6].initialGroup, "B");
		sitTable.sitTable[6].sitColumn[2] = new SITCell(265, sitTable.sitTable[6].initialGroup, "C");
		sitTable.sitTable[6].sitColumn[3] = new SITCell(178, sitTable.sitTable[6].initialGroup, "D");
		sitTable.sitTable[6].sitColumn[4] = new SITCell(119, sitTable.sitTable[6].initialGroup, "E");
		sitTable.sitTable[6].sitColumn[5] = new SITCell(75, sitTable.sitTable[6].initialGroup, "F");
		sitTable.sitTable[6].sitColumn[6] = new SITCell(40, sitTable.sitTable[6].initialGroup, "G");

		sitTable.sitTable[7] = new SITColumn("H", 8);
		sitTable.sitTable[7].sitColumn[0] = new SITCell(maxSurfaceTime, sitTable.sitTable[7].initialGroup, "A");
		sitTable.sitTable[7].sitColumn[1] = new SITCell(479, sitTable.sitTable[7].initialGroup, "B");
		sitTable.sitTable[7].sitColumn[2] = new SITCell(289, sitTable.sitTable[7].initialGroup, "C");
		sitTable.sitTable[7].sitColumn[3] = new SITCell(200, sitTable.sitTable[7].initialGroup, "D");
		sitTable.sitTable[7].sitColumn[4] = new SITCell(143, sitTable.sitTable[7].initialGroup, "E");
		sitTable.sitTable[7].sitColumn[5] = new SITCell(101, sitTable.sitTable[7].initialGroup, "F");
		sitTable.sitTable[7].sitColumn[6] = new SITCell(66, sitTable.sitTable[7].initialGroup, "G");
		sitTable.sitTable[7].sitColumn[7] = new SITCell(36, sitTable.sitTable[7].initialGroup, "H");

		sitTable.sitTable[8] = new SITColumn("I", 9);
		sitTable.sitTable[8].sitColumn[0] = new SITCell(maxSurfaceTime, sitTable.sitTable[8].initialGroup, "A");
		sitTable.sitTable[8].sitColumn[1] = new SITCell(501, sitTable.sitTable[8].initialGroup, "B");
		sitTable.sitTable[8].sitColumn[2] = new SITCell(321, sitTable.sitTable[8].initialGroup, "C");
		sitTable.sitTable[8].sitColumn[3] = new SITCell(223, sitTable.sitTable[8].initialGroup, "D");
		sitTable.sitTable[8].sitColumn[4] = new SITCell(164, sitTable.sitTable[8].initialGroup, "E");
		sitTable.sitTable[8].sitColumn[5] = new SITCell(122, sitTable.sitTable[8].initialGroup, "F");
		sitTable.sitTable[8].sitColumn[6] = new SITCell(89, sitTable.sitTable[8].initialGroup, "G");
		sitTable.sitTable[8].sitColumn[7] = new SITCell(59, sitTable.sitTable[8].initialGroup, "H");
		sitTable.sitTable[8].sitColumn[8] = new SITCell(33, sitTable.sitTable[8].initialGroup, "I");

		sitTable.sitTable[9] = new SITColumn("J", 10);
		sitTable.sitTable[9].sitColumn[1] = new SITCell(530, sitTable.sitTable[9].initialGroup, "B");
		sitTable.sitTable[9].sitColumn[2] = new SITCell(340, sitTable.sitTable[9].initialGroup, "C");
		sitTable.sitTable[9].sitColumn[3] = new SITCell(243, sitTable.sitTable[9].initialGroup, "D");
		sitTable.sitTable[9].sitColumn[4] = new SITCell(184, sitTable.sitTable[9].initialGroup, "E");
		sitTable.sitTable[9].sitColumn[5] = new SITCell(140, sitTable.sitTable[9].initialGroup, "F");
		sitTable.sitTable[9].sitColumn[6] = new SITCell(107, sitTable.sitTable[9].initialGroup, "G");
		sitTable.sitTable[9].sitColumn[7] = new SITCell(79, sitTable.sitTable[9].initialGroup, "H");
		sitTable.sitTable[9].sitColumn[8] = new SITCell(54, sitTable.sitTable[9].initialGroup, "I");
		sitTable.sitTable[9].sitColumn[9] = new SITCell(31, sitTable.sitTable[9].initialGroup, "J");

		sitTable.sitTable[10] = new SITColumn("K", 11);
		sitTable.sitTable[10].sitColumn[0] = new SITCell(maxSurfaceTime, sitTable.sitTable[10].initialGroup, "A");
		sitTable.sitTable[10].sitColumn[1] = new SITCell(538, sitTable.sitTable[10].initialGroup, "B");
		sitTable.sitTable[10].sitColumn[2] = new SITCell(348, sitTable.sitTable[10].initialGroup, "C");
		sitTable.sitTable[10].sitColumn[3] = new SITCell(259, sitTable.sitTable[10].initialGroup, "D");
		sitTable.sitTable[10].sitColumn[4] = new SITCell(201, sitTable.sitTable[10].initialGroup, "E");
		sitTable.sitTable[10].sitColumn[5] = new SITCell(158, sitTable.sitTable[10].initialGroup, "F");
		sitTable.sitTable[10].sitColumn[6] = new SITCell(123, sitTable.sitTable[10].initialGroup, "G");
		sitTable.sitTable[10].sitColumn[7] = new SITCell(95, sitTable.sitTable[10].initialGroup, "H");
		sitTable.sitTable[10].sitColumn[8] = new SITCell(71, sitTable.sitTable[10].initialGroup, "I");
		sitTable.sitTable[10].sitColumn[9] = new SITCell(49, sitTable.sitTable[10].initialGroup, "J");
		sitTable.sitTable[10].sitColumn[10] = new SITCell(28, sitTable.sitTable[10].initialGroup, "K");

		sitTable.sitTable[11] = new SITColumn("L", 12);
		sitTable.sitTable[11].sitColumn[1] = new SITCell(552, sitTable.sitTable[11].initialGroup, "B");
		sitTable.sitTable[11].sitColumn[2] = new SITCell(362, sitTable.sitTable[11].initialGroup, "C");
		sitTable.sitTable[11].sitColumn[3] = new SITCell(275, sitTable.sitTable[11].initialGroup, "D");
		sitTable.sitTable[11].sitColumn[4] = new SITCell(216, sitTable.sitTable[11].initialGroup, "E");
		sitTable.sitTable[11].sitColumn[5] = new SITCell(173, sitTable.sitTable[11].initialGroup, "F");
		sitTable.sitTable[11].sitColumn[6] = new SITCell(139, sitTable.sitTable[11].initialGroup, "G");
		sitTable.sitTable[11].sitColumn[7] = new SITCell(109, sitTable.sitTable[11].initialGroup, "H");
		sitTable.sitTable[11].sitColumn[8] = new SITCell(85, sitTable.sitTable[11].initialGroup, "I");
		sitTable.sitTable[11].sitColumn[9] = new SITCell(64, sitTable.sitTable[11].initialGroup, "J");
		sitTable.sitTable[11].sitColumn[10] = new SITCell(45, sitTable.sitTable[11].initialGroup, "K");
		sitTable.sitTable[11].sitColumn[11] = new SITCell(26, sitTable.sitTable[11].initialGroup, "L");

	}

	public static void generateRepeatTable() {
		rpTable.repeatDiveTable[0] = new RepeatDiveRow("A", 10);
		rpTable.repeatDiveTable[0].repeatDiveRow[0] = new RepeatDiveCell(rpTable.repeatDiveTable[0].group, 40, 7);
		rpTable.repeatDiveTable[0].repeatDiveRow[1] = new RepeatDiveCell(rpTable.repeatDiveTable[0].group, 50, 6);
		rpTable.repeatDiveTable[0].repeatDiveRow[2] = new RepeatDiveCell(rpTable.repeatDiveTable[0].group, 60, 5);
		rpTable.repeatDiveTable[0].repeatDiveRow[3] = new RepeatDiveCell(rpTable.repeatDiveTable[0].group, 70, 4);
		rpTable.repeatDiveTable[0].repeatDiveRow[4] = new RepeatDiveCell(rpTable.repeatDiveTable[0].group, 80, 4);
		rpTable.repeatDiveTable[0].repeatDiveRow[5] = new RepeatDiveCell(rpTable.repeatDiveTable[0].group, 90, 3);
		rpTable.repeatDiveTable[0].repeatDiveRow[6] = new RepeatDiveCell(rpTable.repeatDiveTable[0].group, 100, 3);
		rpTable.repeatDiveTable[0].repeatDiveRow[7] = new RepeatDiveCell(rpTable.repeatDiveTable[0].group, 110, 3);
		rpTable.repeatDiveTable[0].repeatDiveRow[8] = new RepeatDiveCell(rpTable.repeatDiveTable[0].group, 120, 3);
		rpTable.repeatDiveTable[0].repeatDiveRow[9] = new RepeatDiveCell(rpTable.repeatDiveTable[0].group, 130, 3);

		rpTable.repeatDiveTable[1] = new RepeatDiveRow("B", 10);
		rpTable.repeatDiveTable[1].repeatDiveRow[0] = new RepeatDiveCell(rpTable.repeatDiveTable[1].group, 40, 17);
		rpTable.repeatDiveTable[1].repeatDiveRow[1] = new RepeatDiveCell(rpTable.repeatDiveTable[1].group, 50, 13);
		rpTable.repeatDiveTable[1].repeatDiveRow[2] = new RepeatDiveCell(rpTable.repeatDiveTable[1].group, 60, 11);
		rpTable.repeatDiveTable[1].repeatDiveRow[3] = new RepeatDiveCell(rpTable.repeatDiveTable[1].group, 70, 9);
		rpTable.repeatDiveTable[1].repeatDiveRow[4] = new RepeatDiveCell(rpTable.repeatDiveTable[1].group, 80, 8);
		rpTable.repeatDiveTable[1].repeatDiveRow[5] = new RepeatDiveCell(rpTable.repeatDiveTable[1].group, 90, 7);
		rpTable.repeatDiveTable[1].repeatDiveRow[6] = new RepeatDiveCell(rpTable.repeatDiveTable[1].group, 100, 7);
		rpTable.repeatDiveTable[1].repeatDiveRow[7] = new RepeatDiveCell(rpTable.repeatDiveTable[1].group, 110, 6);
		rpTable.repeatDiveTable[1].repeatDiveRow[8] = new RepeatDiveCell(rpTable.repeatDiveTable[1].group, 120, 6);
		rpTable.repeatDiveTable[1].repeatDiveRow[9] = new RepeatDiveCell(rpTable.repeatDiveTable[1].group, 130, 6);

		rpTable.repeatDiveTable[2] = new RepeatDiveRow("C", 10);
		rpTable.repeatDiveTable[2].repeatDiveRow[0] = new RepeatDiveCell(rpTable.repeatDiveTable[2].group, 40, 25);
		rpTable.repeatDiveTable[2].repeatDiveRow[1] = new RepeatDiveCell(rpTable.repeatDiveTable[2].group, 50, 21);
		rpTable.repeatDiveTable[2].repeatDiveRow[2] = new RepeatDiveCell(rpTable.repeatDiveTable[2].group, 60, 17);
		rpTable.repeatDiveTable[2].repeatDiveRow[3] = new RepeatDiveCell(rpTable.repeatDiveTable[2].group, 70, 15);
		rpTable.repeatDiveTable[2].repeatDiveRow[4] = new RepeatDiveCell(rpTable.repeatDiveTable[2].group, 80, 13);
		rpTable.repeatDiveTable[2].repeatDiveRow[5] = new RepeatDiveCell(rpTable.repeatDiveTable[2].group, 90, 11);
		rpTable.repeatDiveTable[2].repeatDiveRow[6] = new RepeatDiveCell(rpTable.repeatDiveTable[2].group, 100, 10);
		rpTable.repeatDiveTable[2].repeatDiveRow[7] = new RepeatDiveCell(rpTable.repeatDiveTable[2].group, 110, 10);
		rpTable.repeatDiveTable[2].repeatDiveRow[8] = new RepeatDiveCell(rpTable.repeatDiveTable[2].group, 120, 9);
		rpTable.repeatDiveTable[2].repeatDiveRow[9] = new RepeatDiveCell(rpTable.repeatDiveTable[2].group, 130, 8);

		rpTable.repeatDiveTable[3] = new RepeatDiveRow("D", 10);
		rpTable.repeatDiveTable[3].repeatDiveRow[0] = new RepeatDiveCell(rpTable.repeatDiveTable[3].group, 40, 37);
		rpTable.repeatDiveTable[3].repeatDiveRow[1] = new RepeatDiveCell(rpTable.repeatDiveTable[3].group, 50, 29);
		rpTable.repeatDiveTable[3].repeatDiveRow[2] = new RepeatDiveCell(rpTable.repeatDiveTable[3].group, 60, 24);
		rpTable.repeatDiveTable[3].repeatDiveRow[3] = new RepeatDiveCell(rpTable.repeatDiveTable[3].group, 70, 20);
		rpTable.repeatDiveTable[3].repeatDiveRow[4] = new RepeatDiveCell(rpTable.repeatDiveTable[3].group, 80, 18);
		rpTable.repeatDiveTable[3].repeatDiveRow[5] = new RepeatDiveCell(rpTable.repeatDiveTable[3].group, 90, 16);
		rpTable.repeatDiveTable[3].repeatDiveRow[6] = new RepeatDiveCell(rpTable.repeatDiveTable[3].group, 100, 14);
		rpTable.repeatDiveTable[3].repeatDiveRow[7] = new RepeatDiveCell(rpTable.repeatDiveTable[3].group, 110, 13);
		rpTable.repeatDiveTable[3].repeatDiveRow[8] = new RepeatDiveCell(rpTable.repeatDiveTable[3].group, 120, 12);
		rpTable.repeatDiveTable[3].repeatDiveRow[9] = new RepeatDiveCell(rpTable.repeatDiveTable[3].group, 130, 11);

		rpTable.repeatDiveTable[4] = new RepeatDiveRow("E", 10);
		rpTable.repeatDiveTable[4].repeatDiveRow[0] = new RepeatDiveCell(rpTable.repeatDiveTable[4].group, 40, 49);
		rpTable.repeatDiveTable[4].repeatDiveRow[1] = new RepeatDiveCell(rpTable.repeatDiveTable[4].group, 50, 38);
		rpTable.repeatDiveTable[4].repeatDiveRow[2] = new RepeatDiveCell(rpTable.repeatDiveTable[4].group, 60, 30);
		rpTable.repeatDiveTable[4].repeatDiveRow[3] = new RepeatDiveCell(rpTable.repeatDiveTable[4].group, 70, 26);
		rpTable.repeatDiveTable[4].repeatDiveRow[4] = new RepeatDiveCell(rpTable.repeatDiveTable[4].group, 80, 23);
		rpTable.repeatDiveTable[4].repeatDiveRow[5] = new RepeatDiveCell(rpTable.repeatDiveTable[4].group, 90, 20);
		rpTable.repeatDiveTable[4].repeatDiveRow[6] = new RepeatDiveCell(rpTable.repeatDiveTable[4].group, 100, 18);
		rpTable.repeatDiveTable[4].repeatDiveRow[7] = new RepeatDiveCell(rpTable.repeatDiveTable[4].group, 110, 16);
		rpTable.repeatDiveTable[4].repeatDiveRow[8] = new RepeatDiveCell(rpTable.repeatDiveTable[4].group, 120, 15);
		rpTable.repeatDiveTable[4].repeatDiveRow[9] = new RepeatDiveCell(rpTable.repeatDiveTable[4].group, 130, 13);

		rpTable.repeatDiveTable[5] = new RepeatDiveRow("F", 10);
		rpTable.repeatDiveTable[5].repeatDiveRow[0] = new RepeatDiveCell(rpTable.repeatDiveTable[5].group, 40, 61);
		rpTable.repeatDiveTable[5].repeatDiveRow[1] = new RepeatDiveCell(rpTable.repeatDiveTable[5].group, 50, 47);
		rpTable.repeatDiveTable[5].repeatDiveRow[2] = new RepeatDiveCell(rpTable.repeatDiveTable[5].group, 60, 36);
		rpTable.repeatDiveTable[5].repeatDiveRow[3] = new RepeatDiveCell(rpTable.repeatDiveTable[5].group, 70, 31);
		rpTable.repeatDiveTable[5].repeatDiveRow[4] = new RepeatDiveCell(rpTable.repeatDiveTable[5].group, 80, 28);
		rpTable.repeatDiveTable[5].repeatDiveRow[5] = new RepeatDiveCell(rpTable.repeatDiveTable[5].group, 90, 24);
		rpTable.repeatDiveTable[5].repeatDiveRow[6] = new RepeatDiveCell(rpTable.repeatDiveTable[5].group, 100, 22);
		rpTable.repeatDiveTable[5].repeatDiveRow[7] = new RepeatDiveCell(rpTable.repeatDiveTable[5].group, 110, 20);
		rpTable.repeatDiveTable[5].repeatDiveRow[8] = new RepeatDiveCell(rpTable.repeatDiveTable[5].group, 120, 18);
		rpTable.repeatDiveTable[5].repeatDiveRow[9] = new RepeatDiveCell(rpTable.repeatDiveTable[5].group, 130, 16);

		rpTable.repeatDiveTable[6] = new RepeatDiveRow("G", 10);
		rpTable.repeatDiveTable[6].repeatDiveRow[0] = new RepeatDiveCell(rpTable.repeatDiveTable[6].group, 40, 73);
		rpTable.repeatDiveTable[6].repeatDiveRow[1] = new RepeatDiveCell(rpTable.repeatDiveTable[6].group, 50, 56);
		rpTable.repeatDiveTable[6].repeatDiveRow[2] = new RepeatDiveCell(rpTable.repeatDiveTable[6].group, 60, 44);
		rpTable.repeatDiveTable[6].repeatDiveRow[3] = new RepeatDiveCell(rpTable.repeatDiveTable[6].group, 70, 37);
		rpTable.repeatDiveTable[6].repeatDiveRow[4] = new RepeatDiveCell(rpTable.repeatDiveTable[6].group, 80, 32);
		rpTable.repeatDiveTable[6].repeatDiveRow[5] = new RepeatDiveCell(rpTable.repeatDiveTable[6].group, 90, 29);
		rpTable.repeatDiveTable[6].repeatDiveRow[6] = new RepeatDiveCell(rpTable.repeatDiveTable[6].group, 100, 26);
		rpTable.repeatDiveTable[6].repeatDiveRow[7] = new RepeatDiveCell(rpTable.repeatDiveTable[6].group, 110, 24);
		rpTable.repeatDiveTable[6].repeatDiveRow[8] = new RepeatDiveCell(rpTable.repeatDiveTable[6].group, 120, 21);
		rpTable.repeatDiveTable[6].repeatDiveRow[9] = new RepeatDiveCell(rpTable.repeatDiveTable[6].group, 130, 19);

		rpTable.repeatDiveTable[7] = new RepeatDiveRow("H", 10);
		rpTable.repeatDiveTable[7].repeatDiveRow[0] = new RepeatDiveCell(rpTable.repeatDiveTable[7].group, 40, 87);
		rpTable.repeatDiveTable[7].repeatDiveRow[1] = new RepeatDiveCell(rpTable.repeatDiveTable[7].group, 50, 66);
		rpTable.repeatDiveTable[7].repeatDiveRow[2] = new RepeatDiveCell(rpTable.repeatDiveTable[7].group, 60, 52);
		rpTable.repeatDiveTable[7].repeatDiveRow[3] = new RepeatDiveCell(rpTable.repeatDiveTable[7].group, 70, 43);
		rpTable.repeatDiveTable[7].repeatDiveRow[4] = new RepeatDiveCell(rpTable.repeatDiveTable[7].group, 80, 38);
		rpTable.repeatDiveTable[7].repeatDiveRow[5] = new RepeatDiveCell(rpTable.repeatDiveTable[7].group, 90, 33);
		rpTable.repeatDiveTable[7].repeatDiveRow[6] = new RepeatDiveCell(rpTable.repeatDiveTable[7].group, 100, 30);
		rpTable.repeatDiveTable[7].repeatDiveRow[7] = new RepeatDiveCell(rpTable.repeatDiveTable[7].group, 110, 27);
		rpTable.repeatDiveTable[7].repeatDiveRow[8] = new RepeatDiveCell(rpTable.repeatDiveTable[7].group, 120, 25);
		rpTable.repeatDiveTable[7].repeatDiveRow[9] = new RepeatDiveCell(rpTable.repeatDiveTable[7].group, 130, 22);

		rpTable.repeatDiveTable[8] = new RepeatDiveRow("I", 10);
		rpTable.repeatDiveTable[8].repeatDiveRow[0] = new RepeatDiveCell(rpTable.repeatDiveTable[8].group, 40, 101);
		rpTable.repeatDiveTable[8].repeatDiveRow[1] = new RepeatDiveCell(rpTable.repeatDiveTable[8].group, 50, 76);
		rpTable.repeatDiveTable[8].repeatDiveRow[2] = new RepeatDiveCell(rpTable.repeatDiveTable[8].group, 60, 61);
		rpTable.repeatDiveTable[8].repeatDiveRow[3] = new RepeatDiveCell(rpTable.repeatDiveTable[8].group, 70, 50);
		rpTable.repeatDiveTable[8].repeatDiveRow[4] = new RepeatDiveCell(rpTable.repeatDiveTable[8].group, 80, 43);
		rpTable.repeatDiveTable[8].repeatDiveRow[5] = new RepeatDiveCell(rpTable.repeatDiveTable[8].group, 90, 38);
		rpTable.repeatDiveTable[8].repeatDiveRow[6] = new RepeatDiveCell(rpTable.repeatDiveTable[8].group, 100, 34);
		rpTable.repeatDiveTable[8].repeatDiveRow[7] = new RepeatDiveCell(rpTable.repeatDiveTable[8].group, 110, 31);
		rpTable.repeatDiveTable[8].repeatDiveRow[8] = new RepeatDiveCell(rpTable.repeatDiveTable[8].group, 120, 28);
		rpTable.repeatDiveTable[8].repeatDiveRow[9] = new RepeatDiveCell(rpTable.repeatDiveTable[8].group, 130, 25);

		rpTable.repeatDiveTable[9] = new RepeatDiveRow("J", 7);
		rpTable.repeatDiveTable[9].repeatDiveRow[0] = new RepeatDiveCell(rpTable.repeatDiveTable[9].group, 40, 116);
		rpTable.repeatDiveTable[9].repeatDiveRow[1] = new RepeatDiveCell(rpTable.repeatDiveTable[9].group, 50, 87);
		rpTable.repeatDiveTable[9].repeatDiveRow[2] = new RepeatDiveCell(rpTable.repeatDiveTable[9].group, 60, 70);
		rpTable.repeatDiveTable[9].repeatDiveRow[3] = new RepeatDiveCell(rpTable.repeatDiveTable[9].group, 70, 57);
		rpTable.repeatDiveTable[9].repeatDiveRow[4] = new RepeatDiveCell(rpTable.repeatDiveTable[9].group, 80, 48);
		rpTable.repeatDiveTable[9].repeatDiveRow[5] = new RepeatDiveCell(rpTable.repeatDiveTable[9].group, 90, 43);
		rpTable.repeatDiveTable[9].repeatDiveRow[6] = new RepeatDiveCell(rpTable.repeatDiveTable[9].group, 100, 38);

		rpTable.repeatDiveTable[10] = new RepeatDiveRow("K", 6);
		rpTable.repeatDiveTable[10].repeatDiveRow[0] = new RepeatDiveCell(rpTable.repeatDiveTable[10].group, 40, 138);
		rpTable.repeatDiveTable[10].repeatDiveRow[1] = new RepeatDiveCell(rpTable.repeatDiveTable[10].group, 50, 99);
		rpTable.repeatDiveTable[10].repeatDiveRow[2] = new RepeatDiveCell(rpTable.repeatDiveTable[10].group, 60, 79);
		rpTable.repeatDiveTable[10].repeatDiveRow[3] = new RepeatDiveCell(rpTable.repeatDiveTable[10].group, 70, 64);
		rpTable.repeatDiveTable[10].repeatDiveRow[4] = new RepeatDiveCell(rpTable.repeatDiveTable[10].group, 80, 54);
		rpTable.repeatDiveTable[10].repeatDiveRow[5] = new RepeatDiveCell(rpTable.repeatDiveTable[10].group, 90, 47);

		rpTable.repeatDiveTable[11] = new RepeatDiveRow("L", 6);
		rpTable.repeatDiveTable[11].repeatDiveRow[0] = new RepeatDiveCell(rpTable.repeatDiveTable[11].group, 40, 161);
		rpTable.repeatDiveTable[11].repeatDiveRow[1] = new RepeatDiveCell(rpTable.repeatDiveTable[11].group, 50, 111);
		rpTable.repeatDiveTable[11].repeatDiveRow[2] = new RepeatDiveCell(rpTable.repeatDiveTable[11].group, 60, 88);
		rpTable.repeatDiveTable[11].repeatDiveRow[3] = new RepeatDiveCell(rpTable.repeatDiveTable[11].group, 70, 72);
		rpTable.repeatDiveTable[11].repeatDiveRow[4] = new RepeatDiveCell(rpTable.repeatDiveTable[11].group, 80, 61);
		rpTable.repeatDiveTable[11].repeatDiveRow[5] = new RepeatDiveCell(rpTable.repeatDiveTable[11].group, 90, 53);

	}
}