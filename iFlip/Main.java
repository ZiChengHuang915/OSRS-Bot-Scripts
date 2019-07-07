package iFlip;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.PaintListener;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.NPC;

@ScriptManifest(name = "iFlip", author = "IcCookies", version = 1.0, description = "Flips items in the GE. See thread for more details.", category = Category.MONEYMAKING)
public class Main extends AbstractScript implements PaintListener {

	public boolean isGUIDone = false;

	public String itemName = new String();
	public int amount;
	public int buyPrice;
	public int sellPrice;

	private long startTime;
	private Area GEArea = new Area(3157, 3494, 3175, 3479, 0);

	private int yaw;
	private int pitch;

	@Override
	public void onStart() {

		GUI GUI = new GUI(this);
		startTime = System.currentTimeMillis();
	}

	@Override
	public int onLoop() {
		if (isGUIDone) {
			if (GEArea.contains(getLocalPlayer())) {
				if (getGrandExchange().isOpen()) {
					if (getInventory().contains(itemName)) {
						getGrandExchange().openSellScreen(0);
						sleepUntil(() -> (getGrandExchange().isSellOpen()),
								6000);
						getGrandExchange()
								.sellItem(itemName, amount, sellPrice);
						sleep(Calculations.random(1000, 2000));
						getGrandExchange().confirm();
					} else {
						if (getGrandExchange().isReadyToCollect(0)) {
							getGrandExchange().collect();
						} else {
							if (getGrandExchange().slotContainsItem(0)) {
								sleep(1);
							} else {
								getGrandExchange().openBuyScreen(0);
								sleepUntil(
										() -> (getGrandExchange().isBuyOpen()),
										6000);
								getGrandExchange().buyItem(itemName, amount,
										buyPrice);
								sleep(Calculations.random(1000, 2000));
								getGrandExchange().confirm();
							}
						}
					}
				} else {
					NPC Guy = getNpcs().closest(
							npc -> npc != null && npc.hasAction("Exchange"));
					if (Guy != null) {
						Guy.interactForceRight("Exchange");
					}
				}
			} else {
				getWalking().walk(GEArea.getRandomTile());
				sleep(Calculations.random(100, 2000));
				Random srand11 = new Random();
				double chances11 = srand11.nextDouble();
				if (chances11 <= 0.3) {
					yaw = Calculations.random(0, 2000);
					pitch = Calculations.random(128, 360);
					getCamera().mouseRotateTo(yaw, pitch);
				}
			}
		}
		return (100);
	}

	@Override
	public void onPaint(Graphics g) {
		super.onPaint(g);

		g.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 14));
		g.setColor(Color.WHITE);
		g.drawString(
				"Runtime: "
						+ Timer.formatTime(System.currentTimeMillis()
								- startTime), 23, 57);

	}

}
