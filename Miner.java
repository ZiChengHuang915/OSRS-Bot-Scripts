package bots;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.widgets.message.Message;
import org.dreambot.api.methods.skills.Skill;

import java.awt.*;
import java.util.*;

/**
 * Created by IcCookies on a certain day.
 */
@ScriptManifest(category = Category.MINING, name = "IcCookies' Varrock West Iron Miner", author = "IcCookies", version = 1.5)
public class Miner extends AbstractScript {

	private final Tile CLAYTILE = new Tile(3175, 3367, 0);
	private final Area BANKAREA = new Area(3180, 3441, 3185, 3433, 0);
	private final Area SAFEAREA = new Area(3173, 3382, 3178, 3377, 0);
	private Timer myTimer = new Timer();
	private int clayKnt = 0;

	private String status = "";

	private enum State {
		MINE, BANK, WALKTOBANK, WALKTOMINE, WAIT
	}

	private State getState() {

		if (getInventory().isFull()) {
			if (BANKAREA.contains(getLocalPlayer())) {
				return State.BANK;
			} else {
				return State.WALKTOBANK;
			}
		} else {
			if (getLocalPlayer().getTile().distance(CLAYTILE) == 0) {
				if (getGameObjects().closest(13446) != null
						|| getGameObjects().closest(13445) != null) {
					return State.MINE;
				} else {
					return State.WAIT;
				}
			} else {
				return State.WALKTOMINE;
			}
		}
	}

	@Override
	public int onLoop() {

		if (getLocalPlayer().isMoving()) {
			if (getClient().getDestination().distance() > 5) {
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return getClient().getDestination().distance() < 5;
					}
				}, Calculations.random(500, 732));
			}
		}

		if (getLocalPlayer().getHealth() < 4) {
			getWalking().walk(BANKAREA.getRandomTile());
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return BANKAREA.contains(getLocalPlayer());
				}
			}, Calculations.random(15000, 30000));
			sleep(500000);
		}

		if (getLocalPlayer().isInCombat()) {
			log("Player in accidental combat, moving to safety...");
			status = "Player in accidental combat, moving to safety...";
			getWalking().walk(SAFEAREA.getRandomTile());
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return SAFEAREA.contains(getLocalPlayer());
				}
			}, Calculations.random(4000, 8000));
		}

		if (getLocalPlayer().isAnimating()) {
			log("Suppressing unnecessary clicks...");
			return Calculations.random(150, 200);
		}

		Random srand = new Random();
		double chances = srand.nextDouble();
		if (chances < 0.096) {
			log("Antiban; changing camera angle...");
			status = "Antiban; changing camera angle...";
			getCamera().rotateToEvent(srand.nextInt() + 360,
					srand.nextInt() + 90);
		} else {
		}

		Random srand1 = new Random();
		double chances1 = srand1.nextDouble();
		if (chances1 < 0.096) {
			status = "Antiban2...";
			getCamera().rotateToEvent(60, 40);
		}

		switch (getState()) {

		case MINE:
			GameObject iron = getGameObjects().closest(13446);
			GameObject iron2 = getGameObjects().closest(13445);
			int temp = (Math.random() <= 0.5) ? 1 : 2;
			switch (temp) {
			case 1:
				if (iron != null) {
					log("Mining rock one...");
					status = "Mining rock...";
					iron.interact("Mine");
					sleepWhile(new Condition() {
						@Override
						public boolean verify() {
							return iron.exists();
						}
					}, Calculations.random(10, 100));
				}
				break;
			case 2:
				if (iron2 != null) {
					log("Mining rock two...");
					status = "Mining rock...";
					iron2.interact("Mine");
					sleepWhile(new Condition() {
						@Override
						public boolean verify() {
							return iron2.exists();
						}
					}, Calculations.random(10, 100));
				}
				break;
			}
			break;

		case WAIT:
			log("Waiting for new rocks...");
			status = "Waiting for new rocks...";
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					GameObject iron = getGameObjects().closest(13446);
					GameObject iron2 = getGameObjects().closest(13445);
					return (iron != null || iron2 != null);
				}
			}, Calculations.random(100, 200));
			break;

		case BANK:
			log("Banking the iron...");
			status = "Banking the clay...";
			if (getBank().isOpen()) {
				if (getInventory().contains("Bronze pickaxe")
						|| getInventory().contains("Iron pickaxe")
						|| getInventory().contains("Steel pickaxe")
						|| getInventory().contains("Black pickaxe")
						|| getInventory().contains("Mithril pickaxe")
						|| getInventory().contains("Adamant pickaxe")
						|| getInventory().contains("Rune pickaxe")
						|| getInventory().contains("Dragon pickaxe")) {
					getBank().depositAll("Iron ore");
				} else {
					getBank().depositAllItems();
				}
				sleepUntil(() -> getInventory().isEmpty(),
						Calculations.random(800, 1250));
			} else {
				getBank().open();
				sleepUntil(() -> getBank().isOpen(),
						Calculations.random(500, 1250));
			}
			log("Banked.");
			break;

		case WALKTOBANK:
			log("Inventory is full, walking to bank...");
			status = "Inventory is full, walking to bank...";
			getWalking().walk(BankLocation.VARROCK_WEST.getCenter());
			break;

		case WALKTOMINE:
			log("Walking to the iron mine...");
			status = "Walking to the clay mine...";
			getWalking().walk(CLAYTILE);
			break;

		}

		return Calculations.random(200, 400);
	}

	public void onExit() {
		log("Thanks for using script.");
	}

	public void onMessage(Message m) {
		if (m.getMessage().contains("You manage to mine some iron.")) {
			clayKnt++;
		}
	}

	public void onPaint(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.setFont(new Font("Arial", 1, 11));
		g.drawString("IcCookies' Miner", 5, 40);
		g.drawString("Run time: " + myTimer.formatTime(), 5, 55);
		g.drawString("Status: " + status, 5, 70);
		g.drawString("Ores mined: " + clayKnt, 5, 100);
		g.drawString("by IcCookies", 5, 115);

	}

}