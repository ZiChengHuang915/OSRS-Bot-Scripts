package AIO_iChop;

import AIO_iChop.Main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;

import javax.swing.JCheckBox;

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
		frame.setResizable(false);
		frame.setBounds(100, 100, 429, 369);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(23, 43, 365, 246);
		frame.getContentPane().add(tabbedPane);

		JPanel panel = new JPanel();
		panel.setToolTipText("");
		tabbedPane.addTab("Custom", null, panel, null);
		panel.setLayout(null);

		JButton chopsetNW = new JButton("Set North West");
		chopsetNW.setBounds(10, 34, 107, 23);
		panel.add(chopsetNW);
		chopsetNW.addActionListener((e) -> {
			ctx.chopNW = ctx.getLocalPlayer().getTile();
		});

		JButton chopsetSE = new JButton("Set South East");
		chopsetSE.setBounds(75, 112, 103, 23);
		panel.add(chopsetSE);
		chopsetSE.addActionListener((e) -> {
			ctx.chopSE = ctx.getLocalPlayer().getTile();
		});

		JLabel lblSetArea = new JLabel("Set Area:");
		lblSetArea.setFont(new Font("Papyrus", Font.PLAIN, 15));
		lblSetArea.setHorizontalAlignment(SwingConstants.CENTER);
		lblSetArea.setBounds(10, 11, 75, 23);
		panel.add(lblSetArea);

		JLabel lblSetBankLocation = new JLabel("Set Bank Location:");
		lblSetBankLocation.setHorizontalAlignment(SwingConstants.CENTER);
		lblSetBankLocation.setFont(new Font("Papyrus", Font.PLAIN, 15));
		lblSetBankLocation.setBounds(204, 11, 145, 23);
		panel.add(lblSetBankLocation);

		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		tabbedPane_1.setBounds(10, 34, 168, 101);
		panel.add(tabbedPane_1);

		JLabel lblOtherOptions = new JLabel("Other Options:");
		lblOtherOptions.setHorizontalAlignment(SwingConstants.CENTER);
		lblOtherOptions.setFont(new Font("Papyrus", Font.PLAIN, 15));
		lblOtherOptions.setBounds(206, 78, 112, 23);
		panel.add(lblOtherOptions);

		JComboBox choosetree = new JComboBox();
		choosetree.setModel(new DefaultComboBoxModel(new String[] { "Tree ",
				"Oak", "Willow", "Maple", "Yew", "Magic" }));
		choosetree.setToolTipText("");
		choosetree.setBackground(Color.WHITE);
		choosetree.setBounds(227, 135, 82, 23);
		panel.add(choosetree);

		JLabel Lbltreetype = new JLabel("Tree type:");
		Lbltreetype.setHorizontalAlignment(SwingConstants.CENTER);
		Lbltreetype.setBounds(216, 112, 70, 23);
		panel.add(Lbltreetype);

		JCheckBox chckbxBanking = new JCheckBox("Banking?");
		chckbxBanking.setBounds(227, 172, 97, 23);
		panel.add(chckbxBanking);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Help", null, panel_1, null);
		panel_1.setLayout(null);

		JComboBox chooseBank = new JComboBox();
		chooseBank.setForeground(Color.BLACK);
		chooseBank.setBackground(Color.WHITE);
		chooseBank.setModel(new DefaultComboBoxModel(new String[] {
				"Lumbridge", "Falador West\t", "Falador East", "Varrock West",
				"Varrock East", "Grand Exchange", "Edgeville",
				"Draynor Village" }));
		chooseBank.setToolTipText("");
		chooseBank.setBounds(227, 37, 122, 30);
		panel.add(chooseBank);

		JButton startBtn = new JButton("Start");
		startBtn.setBounds(344, 300, 57, 23);
		startBtn.addActionListener((e) -> {
			if (chooseBank.getSelectedIndex() == 0) {
				ctx.bankNW = new Tile(3208, 3220, 2);
				ctx.bankSE = new Tile(3210, 3218, 2);
			} else if (chooseBank.getSelectedIndex() == 1) {
				ctx.bankNW = new Tile(2945, 3370);
				ctx.bankSE = new Tile(2947, 3368);
			} else if (chooseBank.getSelectedIndex() == 2) {
				ctx.bankNW = new Tile(3010, 3357);
				ctx.bankSE = new Tile(3015, 3355);
			} else if (chooseBank.getSelectedIndex() == 3) {
				ctx.bankNW = new Tile(3183, 3437);
				ctx.bankSE = new Tile(3185, 3436);
			} else if (chooseBank.getSelectedIndex() == 4) {
				ctx.bankNW = new Tile(3251, 3422);
				ctx.bankSE = new Tile(3256, 3420);
			} else if (chooseBank.getSelectedIndex() == 5) {
				ctx.bankNW = new Tile(3161, 3494);
				ctx.bankSE = new Tile(3168, 3486);
			} else if (chooseBank.getSelectedIndex() == 6) {
				ctx.bankNW = new Tile(3092, 3493);
				ctx.bankSE = new Tile(3094, 3489);
			} else if (chooseBank.getSelectedIndex() == 7) {
				ctx.bankNW = new Tile(3092, 3245);
				ctx.bankSE = new Tile(3094, 3242);
			}
			if (choosetree.getSelectedIndex() == 0) {
				ctx.logName = "Tree";
			} else if (choosetree.getSelectedIndex() == 1) {
				ctx.logName = "Oak";
			} else if (choosetree.getSelectedIndex() == 2) {
				ctx.logName = "Willow";
			} else if (choosetree.getSelectedIndex() == 3) {
				ctx.logName = "Maple";
			} else if (choosetree.getSelectedIndex() == 4) {
				ctx.logName = "Yew";
			} else if (choosetree.getSelectedIndex() == 5) {
				ctx.logName = "Magic";
			}
			if (chckbxBanking.isSelected()) {
				ctx.bankMode = true;
			} else {
				ctx.bankMode = false;
			}
			ctx.start = true;
			ctx.startTime = System.currentTimeMillis();
			frame.dispose();
		});
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("AIO iChop");
		lblNewLabel.setBounds(160, 11, 108, 27);
		lblNewLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));
		frame.getContentPane().add(lblNewLabel);
		frame.getContentPane().add(startBtn);

		frame.setVisible(true);
	}
}
