package iRunecrafter;

import iRunecrafter.Main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Font;

import javax.swing.DefaultComboBoxModel;

import org.dreambot.api.methods.map.Area;

import java.awt.Color;

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
		frame.setForeground(Color.WHITE);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 249, 235);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel lblNewLabel = new JLabel("iRunecrafter");
		lblNewLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));

		JComboBox runeChoice = new JComboBox();
		runeChoice.addItem("Air rune");
		runeChoice.addItem("Mind rune");
		runeChoice.addItem("Water rune");
		runeChoice.addItem("Earth rune");
		runeChoice.addItem("Fire rune");
		runeChoice.addItem("Body rune");

		JLabel lblNewLabel_1 = new JLabel(
				"Select which rune to craft here:\r\n");
		frame.setVisible(true);

		JButton startBtn = new JButton("Start");
		startBtn.addActionListener((e) -> {
			if (runeChoice.getSelectedIndex() == 0) {
				ctx.runeName = "Air rune";
				ctx.altarArea = new Area(2982, 3296, 2988, 3289, 0);
				ctx.bankArea = new Area(3009, 3358, 3016, 3355, 0);
				ctx.craftArea = new Area(2839, 4840, 2849, 4828, 0);
				ctx.portalArea = new Area(2839, 4840, 2849, 4828, 0);
				ctx.rockArea = new Area(2839, 4840, 2849, 4828, 0);
			} else if (runeChoice.getSelectedIndex() == 1) {
				ctx.runeName = "Mind rune";
				ctx.altarArea = new Area(2979, 3517, 2985, 3511, 0);
				ctx.bankArea = new Area(2945, 3370, 2949, 3368, 0);
				ctx.craftArea = new Area(2782, 4846, 2796, 4825, 0);
				ctx.portalArea = new Area(2791, 4829, 2795, 4825, 0);
				ctx.rockArea = new Area(2782, 4846, 2796, 4825, 0);
			} else if (runeChoice.getSelectedIndex() == 2) {
				ctx.runeName = "Water rune";
				ctx.altarArea = new Area(3182, 3168, 3189, 3162, 0);
				ctx.bankArea = new Area(3092, 3245, 3095, 3241, 0);
				ctx.craftArea = new Area(2713, 4838, 2728, 4829, 0);
				ctx.portalArea = new Area(2725, 4834, 2728, 4829, 0);
				ctx.rockArea = new Area(2714, 4838, 2720, 4832, 0);
			}
			frame.dispose();
		});
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout.createSequentialGroup().addGap(85)
										.addComponent(startBtn))
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGap(41)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																lblNewLabel)
														.addComponent(
																lblNewLabel_1,
																GroupLayout.PREFERRED_SIZE,
																211,
																GroupLayout.PREFERRED_SIZE)))
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGap(73)
										.addComponent(runeChoice,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblNewLabel)
						.addGap(11)
						.addComponent(lblNewLabel_1)
						.addGap(18)
						.addComponent(runeChoice, GroupLayout.PREFERRED_SIZE,
								33, GroupLayout.PREFERRED_SIZE).addGap(49)
						.addComponent(startBtn)));
		frame.getContentPane().setLayout(groupLayout);
		frame.setVisible(true);

	}
}
