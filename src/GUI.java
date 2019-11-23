
//Usually you will require both swing and awt packages
// even if you are working with just swings.
import javax.swing.*;
import BackSide.BackEnd;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener {
	JTextField tfd, tft;
	JTextArea tfa;
	JScrollPane sp;

	public GUI() {
		// Creating the Frame
		JFrame frame = new JFrame("Dive Calculator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);

		// creating the panel at bottom and adding instructions
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
		tfd = new JTextField(10); // accepts up to 10 characters
		tft = new JTextField(10);
		JButton Enter = new JButton("Enter");

		panel.add(Depth); // Components Added using Flow Layout
		panel.add(tfd);
		panel.add(Time);
		panel.add(tft);
		panel.add(Enter);
		Enter.addActionListener(this);

		// Text Area at the Center
		tfa = new JTextArea();
		sp = new JScrollPane(tfa);

		// Adding Components to the frame.
		frame.getContentPane().add(BorderLayout.NORTH, inst);
		frame.getContentPane().add(BorderLayout.SOUTH, panel);
		frame.getContentPane().add(BorderLayout.CENTER, sp);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String s1 = tfd.getText();
		String s2 = tft.getText();
		try {
			int depth = Integer.parseInt(s1);
			int time = Integer.parseInt(s2);
			String sentence = BackEnd.sentence(depth, time);
			System.out.println(sentence);
			tfa.append(" " + sentence + "\n");
		} catch (NumberFormatException ex) {
			tfa.append(" Invalid Parameters\n");
		}
	}

	public static void main(String args[]) {
		new GUI();

	}
}