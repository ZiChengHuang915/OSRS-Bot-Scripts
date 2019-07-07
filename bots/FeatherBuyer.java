package bots;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.randoms.RandomEvent;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.message.Message;
import org.dreambot.api.methods.tabs.Tab;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Modified by IcCookies
 */
@ScriptManifest(category = Category.MONEYMAKING, name = "iFeather Buyer Pro", author = "IcCookies", version = 1.0)
public class FeatherBuyer extends AbstractScript {

	Point[] lastPositions = new Point[15];

	Area BuyArea = new Area(3010, 3230, 3018, 3220, 0);
	Area GEArea = new Area(3161, 3489, 3169, 3485, 0);

	private String state = "Starting up the script!";
	private long startTime;

	private int FNum;
	private int MoneyMade;
	private int gpperhour;

	private int yaw;
	private int pitch;

	private final Image bg = getImage("http://imgur.com/rjPKWDD.png");

	@Override
	public void onStart() {
		startTime = System.currentTimeMillis();
		MoneyMade = getInventory().count("Coins") + (getInventory().count("Feather") * 3);
		getClient().getInstance().setDrawMouse(false);
		
	}

	private enum State {
		BUY, WALKTOBUY, WALKTOGE, GE
	}

	private State getState() {
		if (getInventory().count("Coins") > 3000) {
			if (BuyArea.contains(getLocalPlayer())) {
				return State.BUY;
			} else {
				return State.WALKTOBUY;
			}
		} else {
			if (GEArea.contains(getLocalPlayer())) {
				return State.GE;
			} else {
				return State.WALKTOGE;
			}
		}

	}

	@Override
	public int onLoop() {
		switch (getState()) {
		case BUY:
			state = "Buying feathers!";
			/*
			 * if (BuyArea.contains(getPlayers().closest(Player -> Player !=
			 * null))) { Hop(); }
			 */
			if (!getTabs().isOpen(Tab.INVENTORY)) {
				getTabs().openWithMouse(Tab.INVENTORY);
			}
			if (!getInventory().isFull()) {
				NPC Gerrant = getNpcs().closest(
						npc -> npc != null && npc.hasAction("Trade"));
				if (!getShop().isOpen()) {
					Gerrant.interact("Trade");
					sleepUntil(() -> getShop().isOpen(), 5000);
				} else {
					getMouse().drag(
							new Point(Calculations.random(84, 100),
									Calculations.random(120, 140)));

					Item item = getShop().get("Feather pack");
					if (item.getAmount() <= 99) {
						getShop().close();
						sleepUntil(() -> !getShop().isOpen(), 5000);
						Hop();
						// sleepUntil(() -> item.getAmount() > 90, 15000);
					}
					getShop().purchaseTen(11881);
					FNum = FNum + 1000;
					if (getInventory().count("Feather") < 10) {
						getShop().purchaseOne(11881);
						FNum = FNum + 100;
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
				if (chances <= 0.05) {
					Random srand1 = new Random();
					double chances1 = srand1.nextDouble();
					state = "Antiban!";
					getMouse().moveMouseOutsideScreen();
					sleep(Calculations.random(10000, 30000));
					if (chances1 <= 0.1) {
						state = "Antiban!";
						getMouse().moveMouseOutsideScreen();
						sleep(Calculations.random(300000, 600000));
					}
				} else {
					state = "Buying Feathers!";
				}
			} else if (getInventory().isFull()) {
				if (!getTabs().isOpen(Tab.INVENTORY)) {
					getTabs().openWithMouse(Tab.INVENTORY);
				}
				if (!getShop().isOpen()) {
					state = "Opening packs!";
					for (int i = 0; i <= 30; i++) {
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
				} else {
					getShop().close();
				}
			}
			break;
		case GE:
			state = "GE trading!";
			NPC Grand_Exchange_Clerk = getNpcs().closest(
					npc -> npc != null && npc.hasAction("Exchange"));
			if (getInventory().count("Feather") > 10) {
				if (!getGrandExchange().isGeneralOpen()) {
					if (Grand_Exchange_Clerk.interact("Exchange")) {
						sleepUntil(() -> getGrandExchange().isGeneralOpen(),
								5000);
					}
				} else {
					getGrandExchange().addSellItem("Feather");
					getGrandExchange().confirm();
				}
			} else {
				if (getGrandExchange().isGeneralOpen()) {
					if (getGrandExchange().isReadyToCollect()) {
						getGrandExchange().collect();
					} else {
						Log();
					}
				} else {
					Grand_Exchange_Clerk.interact("Exchange");
					sleepUntil(() -> getGrandExchange().isGeneralOpen(), 5000);
				}
			}
			break;
		case WALKTOBUY:
			state = "Walking to shop!";
			getWalking().walk(BuyArea.getRandomTile());
			sleep(Calculations.random(100, 2000));
			Random srand = new Random();
			double chances = srand.nextDouble();
			if (chances <= 0.1) {
				yaw = Calculations.random(0, 2000);
				pitch = Calculations.random(128, 360);
				getCamera().mouseRotateTo(yaw, pitch);
			}
			break;
		case WALKTOGE:
			state = "Walking to GE!";
			if (getInventory().count(11881) > 0) {
				getInventory().get(11881).interact();

			}
			getWalking().walk(GEArea.getRandomTile());
			sleep(Calculations.random(100, 2000));
			Random srand1 = new Random();
			double chances1 = srand1.nextDouble();
			if (chances1 <= 0.1) {
				yaw = Calculations.random(0, 2000);
				pitch = Calculations.random(128, 360);
				getCamera().mouseRotateTo(yaw, pitch);
			}
			break;

		}
		return Calculations.random(200, 400);
	}

	private void Log() {
		if (getClient().isLoggedIn()) {
			state = "Waiting for feathers to sell!";
			getRandomManager().disableSolver(RandomEvent.LOGIN);
			getRandomManager().disableSolver(RandomEvent.WELCOME_SCREEN);
			getTabs().logout();
			sleep(Calculations.random(3600000, 18000000));// 1 to 5 hours
			getRandomManager().enableSolver(RandomEvent.LOGIN);
			getRandomManager().enableSolver(RandomEvent.WELCOME_SCREEN);
			sleepUntil(() -> getLocalPlayer().isOnScreen(), 5000);
			if (getGrandExchange().isReadyToCollect()) {
				state = "Collecting feathers!";
				getGrandExchange().collect();
			} else {
				Log();
			}
		}
	}

	private void Hop() {
		if (getClient().getCurrentWorld() == 301) {
			getWorldHopper().hopWorld(308);
		} else if (getClient().getCurrentWorld() == 308) {
			getWorldHopper().hopWorld(316);
		} else if (getClient().getCurrentWorld() == 316) {
			getWorldHopper().hopWorld(326);
		} else if (getClient().getCurrentWorld() == 326) {
			getWorldHopper().hopWorld(335);
		} else if (getClient().getCurrentWorld() == 335) {
			getWorldHopper().hopWorld(381);
		} else if (getClient().getCurrentWorld() == 381) {
			getWorldHopper().hopWorld(382);
		} else if (getClient().getCurrentWorld() == 382) {
			getWorldHopper().hopWorld(383);
		} else if (getClient().getCurrentWorld() == 383) {
			getWorldHopper().hopWorld(384);
		} else if (getClient().getCurrentWorld() == 384) {
			getWorldHopper().hopWorld(385);
		} else if (getClient().getCurrentWorld() == 385) {
			getWorldHopper().hopWorld(393);
		} else if (getClient().getCurrentWorld() == 393) {
			getWorldHopper().hopWorld(394);
		} else {
			getWorldHopper().hopWorld(301);
		}
		sleepUntil(() -> getLocalPlayer().exists() && getClient().isLoggedIn(),
				5000);
		if (!getTabs().isOpen(Tab.INVENTORY)) {
			getTabs().openWithMouse(Tab.INVENTORY);
		}
	}

	public void onMessage(Message m) {
		if (m.getMessage().contains("You don't have enough inventory space.")) {
			FNum = FNum - 1000;
		}
	}

	@Override
	public void onPaint(Graphics2D g) {
		Point currentPosition = getMouse().getPosition();

		// Shift all elements down and insert the new element
		for (int i = 0; i < lastPositions.length - 1; i++) {
			lastPositions[i] = lastPositions[i + 1];
		}
		lastPositions[lastPositions.length - 1] = new Point(currentPosition.x,
				currentPosition.y);

		// This is the point before the new point to draw to
		Point lastpoint = null;

		Color mColor = new Color(30, 108, 176);
		// Go in reverse
		for (int i = lastPositions.length - 1; i >= 0; i--) {
			Point p = lastPositions[i];
			if (p != null) {
				if (lastpoint == null)
					lastpoint = p;

				g.setColor(mColor);
				g.drawLine(lastpoint.x, lastpoint.y, p.x, p.y);
			}
			lastpoint = p;

			// Every 2 steps - mouse fade out
			if (i % 1 == 0)
				mColor = mColor.brighter();
		}

		g.setColor(Color.BLACK);
		g.drawRect(currentPosition.x - 3, currentPosition.y - 3, 5, 5);
		g.setColor(Color.WHITE);
		g.drawRect(currentPosition.x, currentPosition.y, 1, 1);
	}

	@Override
	public void onPaint(Graphics g) {
		super.onPaint(g);

		g.drawImage(bg, 0, 340, null);
		g.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 14));
		g.setColor(Color.WHITE);
		g.drawString(
				"Runtime: "
						+ Timer.formatTime(System.currentTimeMillis()
								- startTime), 280, 355);
		g.drawString("IcCookies' Feather Buyer", 18, 355);
		g.drawString("Feathers bought:" + FNum, 18, 375);
		if((getInventory().count("Coins") + (getInventory().count("Feather") * 3) - MoneyMade) >= 0){
		g.drawString("Money made: " + (getInventory().count("Coins") + (getInventory().count("Feather") * 3) - MoneyMade), 18, 395);
		}else{
			g.drawString("Money made: Selling Feathers or Buying At Start(inaccurate)!", 18, 395);
		}
		gpperhour = (int) ((getInventory().count("Coins") + (getInventory().count("Feather") * 3) - MoneyMade) / ((System.currentTimeMillis() - startTime) / 3600000.0D));
		g.drawString("Money Per Hour: " + gpperhour, 18, 415);
		g.setColor(Color.LIGHT_GRAY);
		if ((System.currentTimeMillis() - startTime) < 5000) {
			g.drawString("State: Welcome to the script!", 18, 465);
		} else {
			g.drawString("State: " + state, 18, 465);
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
		log("Runtime: "
				+ Timer.formatTime(System.currentTimeMillis() - startTime));
		log("Money made: " + (getInventory().count("Coins") + (getInventory().count("Feather") * 3) - MoneyMade));
		getClient().getInstance().setDrawMouse(true);
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
