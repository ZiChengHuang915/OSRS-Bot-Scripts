package iHelper;

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
import org.dreambot.api.wrappers.widgets.WidgetChild;

@ScriptManifest(name = "iHelper", author = "IcCookies", version = 1.0, description = "", category = Category.MISC)
public class Main extends AbstractScript implements PaintListener {

	private long startTime;

	private int yaw;
	private int pitch;

	private Area bankArea = new Area(3182, 3437, 3185, 3435, 0);
	private Area tradeArea = new Area(3182, 3378, 3184, 3376, 0);

	//WidgetChild theWidget = getWidgets().getWidgetChild(335, 30);
//	WidgetChild theWidget2 = getWidgets().getWidgetChild(334, 4);

	@Override
	public void onStart() {

		// GUI GUI = new GUI(this);
		startTime = System.currentTimeMillis();
	}

	@Override
	public int onLoop() {
		if (getInventory().isEmpty()) {
			if (tradeArea.contains(getLocalPlayer())) {
				log("is in trade area");
				if (getTrade().isOpen()) {
					sleepUntil(() -> getTrade().isOpen(), 10000);
				/*	if (!theWidget.isVisible()) {
						sleepUntil(() -> theWidget.isVisible(), 10000);
					}*/
					getTrade().acceptTrade();
					/*if (!theWidget2.isVisible()) {
						sleepUntil(() -> theWidget2.isVisible(), 10000);
					}*/
					getTrade().acceptTrade();
				} else {
					getTrade().tradeWithPlayer("ziziawesome");
					sleep(1000, 2000);
				}
			} else {
				log("is not in trade area");
				if (!tradeArea.contains(getWalking().getDestination())) {
					getWalking().walk(tradeArea.getRandomTile());
					sleep(Calculations.random(100, 2000));
					Random srand = new Random();
					double chances = srand.nextDouble();
					if (chances <= 0.5) {
						yaw = Calculations.random(0, 2000);
						pitch = Calculations.random(128, 360);
						getCamera().mouseRotateTo(yaw, pitch);
					}
				}
			}
		} else {
			if (bankArea.contains(getLocalPlayer())) {
				log("is in bank area");
				if (getBank().isOpen()) {
					getBank().depositAllItems();
					sleepUntil(() -> getInventory().isEmpty(), 10000);
				} else {
					getBank().open();
					sleepUntil(() -> getBank().isOpen(), 10000);
				}
			} else {
				if (!bankArea.contains(getWalking().getDestination())) {
					log("is not in bank area");
					getWalking().walk(bankArea.getRandomTile());
					sleep(Calculations.random(100, 2000));
					Random srand = new Random();
					double chances = srand.nextDouble();
					if (chances <= 0.5) {
						yaw = Calculations.random(0, 2000);
						pitch = Calculations.random(128, 360);
						getCamera().mouseRotateTo(yaw, pitch);
					}
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
