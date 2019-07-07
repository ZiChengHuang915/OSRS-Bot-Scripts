package PrivateLooter;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import PrivateLooter.Main;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;

import org.dreambot.api.methods.map.Area;

public class GUI {

	private JFrame frame;
	private Main ctx;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI(Main ctx) {
		this.ctx = ctx;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 242, 156);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel lblNewLabel = new JLabel("Private Script");
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));

		JComboBox selectBox = new JComboBox();
		selectBox.setToolTipText("");
		selectBox.setMaximumRowCount(3);
		selectBox.addItem("Chickens");
		selectBox.addItem("Cows");
		selectBox.addItem("Goblins");

		JLabel lblNewLabel_1 = new JLabel("Location:");
		lblNewLabel_1.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));

		JButton startBtn = new JButton("Start");
		startBtn.addActionListener((e) -> {
			if (selectBox.getSelectedIndex() == 0) {
				ctx.pickArea = new Area(3225, 3300, 3236, 3295, 0);
				
			} else if (selectBox.getSelectedIndex() == 1) {
				ctx.pickArea = new Area(3254, 3296, 3265, 3255, 0);
				
			} else {
				ctx.pickArea = new Area(3242, 3243, 3256, 3229, 0);
				
			}
			ctx.start = true;
			frame.dispose();
		});

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				lblNewLabel_1)
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				selectBox,
																				GroupLayout.PREFERRED_SIZE,
																				121,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(46)
																		.addComponent(
																				lblNewLabel))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(79)
																		.addComponent(
																				startBtn)))
										.addContainerGap(18, Short.MAX_VALUE)));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGap(6)
										.addComponent(lblNewLabel)
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblNewLabel_1)
														.addComponent(
																selectBox,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(18).addComponent(startBtn)
										.addContainerGap(15, Short.MAX_VALUE)));
		frame.getContentPane().setLayout(groupLayout);
		frame.setVisible(true);
	}
}
