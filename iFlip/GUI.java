package iFlip;

import iFlip.Main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Font;
public class GUI {

	private JFrame frame;
	private Main ctx;
	private JTextField nameField;
	private JTextField amountField;
	private JTextField buyField;
	private JTextField sellField;

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
		frame.setBounds(100, 100, 154, 367);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel lblNewLabel = new JLabel("iFlip");
		lblNewLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));
		
		nameField = new JTextField();
		nameField.setColumns(10);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener((e) -> {
			ctx.itemName = nameField.getText();
			ctx.amount = Integer.parseInt(amountField.getText());
			ctx.buyPrice = Integer.parseInt(buyField.getText());
			ctx.sellPrice = Integer.parseInt(sellField.getText());
			ctx.isGUIDone = true;
			frame.dispose();
		});
		
		amountField = new JTextField();
		amountField.setColumns(10);
		
		buyField = new JTextField();
		buyField.setColumns(10);
		
		sellField = new JTextField();
		sellField.setColumns(10);
		
		JLabel lblItemName = new JLabel("Item Name:");
		lblItemName.setFont(new Font("Ebrima", Font.PLAIN, 14));
		
		JLabel lblAmount = new JLabel("Amount\r\n:");
		lblAmount.setFont(new Font("Ebrima", Font.PLAIN, 14));
		
		JLabel lblBuyPrice = new JLabel("Buy Price:");
		lblBuyPrice.setFont(new Font("Ebrima", Font.PLAIN, 14));
		
		JLabel lblSellPrice = new JLabel("Sell Price:");
		lblSellPrice.setFont(new Font("Ebrima", Font.PLAIN, 14));
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(sellField, Alignment.LEADING)
								.addComponent(buyField, Alignment.LEADING)
								.addComponent(amountField, Alignment.LEADING)
								.addComponent(nameField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(37)
							.addComponent(btnStart))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(33)
							.addComponent(lblNewLabel))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblItemName))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblAmount, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblBuyPrice, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblSellPrice, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(20, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(12)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblItemName)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblAmount, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(amountField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(lblBuyPrice, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(buyField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(lblSellPrice, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(sellField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnStart)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
		frame.setVisible(true);
	}
}
