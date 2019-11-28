import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DiveCalculator implements ActionListener {
	JTextField tfd, tft, tfih, tfim;
	JTextArea tfa;
	JScrollPane sp;
	boolean repeatDive = false;
	int counter = 0;
	String Group = null;
	int RNT = 0;
	static final int maxSurfaceTime = 1440;
	static final int minSurfaceTime = 10;

	public static DiveTable table = new DiveTable(10);
	public static SITTable sitTable = new SITTable(12);
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
			} catch (NumberFormatException ex) {
				tfa.append("Invalid Parameter\n");
			}
			// check if its an actual dive or just checking what can be done
			if (depth == 0 || time == 0) {
				if (depth == 0) {
					tfa.append(maxDepthForTime(time) + "\n");
				} else if (time == 0) {
					tfa.append(maxTimeForDepth(depth) + "\n");
				}
			} else {
				tfa.append(diveCalc(depth, time) + "\n");
				counter++;
				repeatDive = true;
				tfih.setEditable(true);
				tfim.setEditable(true);
			}
			// Start repeat dive calcs
		} else {
			String s1 = tfd.getText();
			String s2 = tft.getText();
			String s3 = tfih.getText();
			String s4 = tfim.getText();
			int depth = 0, time = 0, intervalHours = 0, intervalMinutes = 0;
			// double check in case of non-integer inputs
			try {
				depth = Integer.parseInt(s1);
				time = Integer.parseInt(s2);
				intervalHours = Integer.parseInt(s3);
				intervalMinutes = Integer.parseInt(s4);
			} catch (NumberFormatException ex) {
				tfa.append("Invalid Parameter\n");
			}
		}

		tfd.setText(null);
		tft.setText(null);
		tfih.setText(null);
		tfim.setText(null);
	}

	// dive table based on NAUI Dive table. built as array to allow for easy change.
	// builds dive table
	public String sentence(int depth, int time) {
		String sentence = "";
		generateDiveTable();
		if (depth == 0) {
			sentence = maxDepthForTimeRD(time);
		} else if (time == 0) {
			sentence = maxTimeForDepthRD(depth);
		} else {
			sentence = diveCalc(depth, time);
		}
		return sentence;
	}

	public static String maxTimeForDepthRD(int depth) {
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

	public static String maxDepthForTimeRD(int time) {
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

	public static String maxTimeForDepth(int depth) {
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

	public static String maxDepthForTime(int time) {
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
		sitTable.sitTable[0].SITColumn[0] = new SITCell(maxSurfaceTime, sitTable.sitTable[0].initialGroup, "A");
		sitTable.sitTable[1] = new SITColumn("B", 2);
		sitTable.sitTable[1].SITColumn[0] = new SITCell(maxSurfaceTime, sitTable.sitTable[1].initialGroup, "A");
		sitTable.sitTable[1].SITColumn[1] = new SITCell(200, sitTable.sitTable[1].initialGroup, "B");
		sitTable.sitTable[2] = new SITColumn("C", 3);
		sitTable.sitTable[2].SITColumn[0] = new SITCell(maxSurfaceTime, sitTable.sitTable[2].initialGroup, "A");
		sitTable.sitTable[2].SITColumn[1] = new SITCell(200, sitTable.sitTable[2].initialGroup, "B");
		sitTable.sitTable[2].SITColumn[2] = new SITCell(200, sitTable.sitTable[2].initialGroup, "C");
		sitTable.sitTable[3] = new SITColumn("D", 4);
		
		sitTable.sitTable[4] = new SITColumn("E", 5);
		sitTable.sitTable[4].SITColumn[0] = new SITCell(maxSurfaceTime, sitTable.sitTable[4].initialGroup,"A");
		sitTable.sitTable[4].SITColumn[1] = new SITCell(394, sitTable.sitTable[4].initialGroup, "B");
		sitTable.sitTable[4].SITColumn[2] = new SITCell(204, sitTable.sitTable[4].initialGroup, "C");
		sitTable.sitTable[4].SITColumn[3] = new SITCell(117, sitTable.sitTable[4].initialGroup, "D");
		sitTable.sitTable[4].SITColumn[4] = new SITCell(54, sitTable.sitTable[4].initialGroup, "E");
		
		sitTable.sitTable[5] = new SITColumn("F", 6);
		

		sitTable.sitTable[6] = new SITColumn("G", 7);
		sitTable.sitTable[6].SITColumn[0] = new SITCell(maxSurfaceTime, sitTable.sitTable[6].initialGroup,"A");
    sitTable.sitTable[6].SITColumn[1] = new SITCell(455, sitTable.sitTable[6].initialGroup, "B");
    sitTable.sitTable[6].SITColumn[2] = new SITCell(265, sitTable.sitTable[6].initialGroup, "C");
    sitTable.sitTable[6].SITColumn[3] = new SITCell(178, sitTable.sitTable[6].initialGroup, "D");
    sitTable.sitTable[6].SITColumn[4] = new SITCell(119, sitTable.sitTable[6].initialGroup, "E");
    sitTable.sitTable[6].SITColumn[5] = new SITCell(75, sitTable.sitTable[6].initialGroup, "F");
    sitTable.sitTable[6].SITColumn[6] = new SITCell(40, sitTable.sitTable[6].initialGroup, "G");
    
		sitTable.sitTable[7] = new SITColumn("H", 8);
		
		sitTable.sitTable[8] = new SITColumn("I", 9);
		sitTable.sitTable[8].SITColumn[0] = new SITCell(maxSurfaceTime, sitTable.sitTable[8].initialGroup,"A");
    sitTable.sitTable[8].SITColumn[1] = new SITCell(501, sitTable.sitTable[8].initialGroup, "B");
    sitTable.sitTable[8].SITColumn[2] = new SITCell(321, sitTable.sitTable[8].initialGroup, "C");
    sitTable.sitTable[8].SITColumn[3] = new SITCell(223, sitTable.sitTable[8].initialGroup, "D");
    sitTable.sitTable[8].SITColumn[4] = new SITCell(164, sitTable.sitTable[8].initialGroup, "E");
    sitTable.sitTable[8].SITColumn[5] = new SITCell(122, sitTable.sitTable[8].initialGroup, "F");
    sitTable.sitTable[8].SITColumn[6] = new SITCell(89, sitTable.sitTable[8].initialGroup, "G");
    sitTable.sitTable[8].SITColumn[7] = new SITCell(59, sitTable.sitTable[8].initialGroup, "H");
    sitTable.sitTable[8].SITColumn[8] = new SITCell(33, sitTable.sitTable[8].initialGroup, "I");
    
		sitTable.sitTable[9] = new SITColumn("J", 10);

		sitTable.sitTable[10] = new SITColumn("K", 11);
		sitTable.sitTable[10].SITColumn[0] = new SITCell(maxSurfaceTime, sitTable.sitTable[10].initialGroup,"A");
		sitTable.sitTable[10].SITColumn[1] = new SITCell(538, sitTable.sitTable[10].initialGroup, "B");
		sitTable.sitTable[10].SITColumn[2] = new SITCell(348, sitTable.sitTable[10].initialGroup, "C");
		sitTable.sitTable[10].SITColumn[3] = new SITCell(259, sitTable.sitTable[10].initialGroup, "D");
		sitTable.sitTable[10].SITColumn[4] = new SITCell(201, sitTable.sitTable[10].initialGroup, "E");
		sitTable.sitTable[10].SITColumn[5] = new SITCell(158, sitTable.sitTable[10].initialGroup, "F");
		sitTable.sitTable[10].SITColumn[6] = new SITCell(123, sitTable.sitTable[10].initialGroup, "G");
		sitTable.sitTable[10].SITColumn[7] = new SITCell(95, sitTable.sitTable[10].initialGroup, "H");
		sitTable.sitTable[10].SITColumn[8] = new SITCell(71, sitTable.sitTable[10].initialGroup, "I");
		sitTable.sitTable[10].SITColumn[9] = new SITCell(49, sitTable.sitTable[10].initialGroup, "J");
		sitTable.sitTable[10].SITColumn[10] = new SITCell(28, sitTable.sitTable[10].initialGroup, "K");
		
		sitTable.sitTable[11] = new SITColumn("L", 12);
	}

	public static void generateRepeatTable() {

	}
}