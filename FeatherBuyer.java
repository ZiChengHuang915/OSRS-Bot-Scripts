package bots;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.widgets.message.Message;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.grandexchange.GrandExchange;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Modified by IcCookies
 */
@ScriptManifest(category = Category.MONEYMAKING, name = "iFeather Buyer", author = "IcCookies", version = 1.0)
public class FeatherBuyer extends AbstractScript {

	Area BuyArea = new Area(3011, 3228, 3017, 3220, 0);

	private long startTime;
	private int status;
	private double FNum;
	private double gpgained;
	private int gpperhour;

	private final Image bg = getImage("http://s12.postimg.org/8ouudwm8d/Untitled.png");

	@Override
	public void onStart() {
		startTime = System.currentTimeMillis();
		int FNum = 0;
		gpgained = FNum * 0.95;
		status = 1;
	}

	private enum State {
		BUY, WALKTOBUY, STOP
	}

	private State getState() {
		if (getInventory().count("Coins") > 3000) {
			if (BuyArea.contains(getLocalPlayer())) {
				return State.BUY;
			} else {
				return State.WALKTOBUY;
			}
		} else {
			return State.STOP;
		}
	}

	/*
	 * status: 1 is buying feathers 3 is walking 5 is GE 10 is anti-ban
	 */
	@Override
	public int onLoop() {
		switch (getState()) {
		case BUY:
			status = 1;
			if (!getInventory().isFull()) {
				NPC Gerrant = getNpcs().closest(
						npc -> npc != null && npc.hasAction("Trade"));
				if (!getShop().isOpen()) {
					if (Gerrant.interact("Trade")) {
					}
					if (sleepUntil(() -> getShop().isOpen(), 5000)) {
					}
				} else {
					
					getShop().purchaseTen(11881);
					 FNum = FNum + 1000;
					 gpgained = FNum * 0.95;
					if (getInventory().count("Feather") < 10) {
						getShop().purchaseOne(11881);
					}
					getMouse().move(
							new Point(Calculations.random(461, 509),
									Calculations.random(21, 61)));
					if (getShop().close()) {
						sleepUntil(() -> !getShop().isOpen(), 3000);
					}
				}

				Random srand = new Random();
				double chances = srand.nextDouble();
				if (chances <= 0.005) {
					status = 10;
					getMouse().moveMouseOutsideScreen();
					sleep(Calculations.random(10000, 30000));
					status = 1;
				} else {
					status = 1;
				}

			} else if (getInventory().isFull()) {

				for (int i = 0; i <= 46; i++) {
					if (getInventory().get(11881) != null) {
						getInventory().get(11881).interact();
					}
					sleep(Calculations.random(50, 126));
					if (getShop().isOpen()) {
						if (getShop().close()) {
							sleepUntil(() -> !getShop().isOpen(), 3000);
						}
					}
				}

			}
			break;
		case WALKTOBUY:
			status = 3;
			getWalking().walk(BuyArea.getRandomTile());
			sleep(Calculations.random(100, 2000));
			break;
		case STOP:
			stop();
			break;
		}

		return Calculations.random(200, 400);
	}

	public void onMessage(Message m) {
		if (m.getMessage().contains("You don't have enough inventory space.")) {
			FNum = FNum - 1000;
		}
	}

	@Override
	public void onPaint(Graphics g) {
		super.onPaint(g);

		g.drawImage(bg, 0, 340, null);
		g.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 14));
		g.setColor(Color.WHITE);
		g.drawString("IcCookies' Feather Buyer", 18, 355);
		g.drawString(
				"Runtime: "
						+ Timer.formatTime(System.currentTimeMillis()
								- startTime), 280, 355);
		g.drawString("Money made: " + gpgained, 18, 395);
		g.drawString("Feathers bought:" + FNum, 18, 375);

		gpperhour = (int) (gpgained / ((System.currentTimeMillis() - startTime) / 3600000.0D));
		g.drawString("Money Per Hour: " + gpperhour, 18, 415);

		if (status <= 1) {
			g.drawString("State: Buying feathers!", 18, 458);
		} else if (status > 2 && status < 4) {
			g.drawString("State: Walking!", 18, 458);
		} else if (status > 4 && status < 6) {
			g.drawString("State: Grand Exchange Stuff!", 18, 458);
		} else if (status > 6) {
			g.drawString("State: Antiban!", 18, 458);
		}

	}

	private Image getImage(String url) {
		try {
			return ImageIO.read(new URL(url));
		} catch (IOException e) {
		}
		return null;
	}

	@Override
	public void onExit() {
		log("-----------------------------------");
		log("Runtime: "
				+ Timer.formatTime(System.currentTimeMillis() - startTime));
		log("Money made: " + gpgained);
		log("-----------------------------------");
	}

	/*
	 * Random random1 = new Random(); double chance1 = random1.nextDouble(); if
	 * (chance1 >= 0.0) { state = 10; getMouse().move(new
	 * Point(Calculations.random(50, 700),Calculations.random(50, 400)));
	 * sleep(Calculations.random(5, 50)); getMouse().move(new
	 * Point(Calculations.random(50, 700),Calculations.random(50, 400)));
	 * sleep(Calculations.random(5, 50)); getMouse().move(new
	 * Point(Calculations.random(50, 700),Calculations.random(50, 400))); state
	 * = 2; } else { state = 2; }
	 */

	/*
	 * Random srand2 = new Random(); double chances2 = srand2.nextDouble(); if
	 * (chances2 < 0.001) { log("Taking break for 2 to 3 minutes!"); state = 10;
	 * getMouse().moveMouseOutsideScreen(); sleep(Calculations.random(120000,
	 * 180000)); } else { sleep(1000); state = 2; }
	 * 
	 * Random srand3 = new Random(); double chances3 = srand3.nextDouble(); if
	 * (chances3 < 0.005) { log("Taking break for 10 to 30 seconds!"); state =
	 * 10; getMouse().moveMouseOutsideScreen(); sleep(Calculations.random(10000,
	 * 30000)); } else { sleep(1000); state = 2; }
	 * 
	 * Random srand4 = new Random(); double chances4 = srand4.nextDouble(); if
	 * (chances4 < 0.0001) { log("Taking break for 10 to 20 minutes!"); state =
	 * 10; getMouse().moveMouseOutsideScreen();
	 * sleep(Calculations.random(600000, 1200000)); } else { sleep(1000); state
	 * = 2; }
	 */

}
