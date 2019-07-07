package PrivateLooter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.PaintListener;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.wrappers.widgets.message.Message;

@ScriptManifest(name = "Private Script", author = "IcCookies", version = 1.0, description = "", category = Category.MISC)
public class Main extends AbstractScript implements PaintListener {

	public boolean start = false;

	public Area pickArea = new Area();
	private long startTime;

	Area floor1stairArea = new Area(new Tile(3205, 3209, 0), new Tile(3206,
			3209, 0));
	Area floor2stairArea = new Area(new Tile(3205, 3210, 1), new Tile(3205,
			3209, 1));
	Area bankArea = new Area(new Tile(3207, 3220, 2), new Tile(3209, 3219, 2));
	Area floor3stairArea = new Area(new Tile(3205, 3210, 2), new Tile(3205,
			3209, 2));
	Area goblinArea = new Area(3243, 3235, 3255, 3228, 0);
	private int yaw;
	private int pitch;

	@Override
	public void onStart() {
		GUI GUI = new GUI(this);
		startTime = System.currentTimeMillis();
	}

	@Override
	public int onLoop() {
		if (start = true) {
			if (getWalking().getRunEnergy() > Calculations.random(5, 10)
					&& getWalking().isRunEnabled() == false) {
				getWalking().toggleRun();
			}
			if (!getInventory().isFull()) {
				if (getLocalPlayer().getZ() == 0) {
					if (pickArea.contains(getLocalPlayer())) {
						take();
						Random srand11 = new Random();
						double chances11 = srand11.nextDouble();
						if (chances11 <= 0.3) {
							yaw = Calculations.random(0, 2000);
							pitch = Calculations.random(128, 360);
							getCamera().mouseRotateTo(yaw, pitch);
						}
					} else {
						log("1");
						getWalking().walk(pickArea.getRandomTile());
						sleep(Calculations.random(100, 2000));
						Random srand11 = new Random();
						double chances11 = srand11.nextDouble();
						if (chances11 <= 0.3) {
							yaw = Calculations.random(0, 2000);
							pitch = Calculations.random(128, 360);
							getCamera().mouseRotateTo(yaw, pitch);
						}
					}
				} else if (getLocalPlayer().getZ() == 1) {
					climbdown();
				} else {
					if (floor3stairArea.contains(getLocalPlayer())) {
						climbdown();
					} else {
						log("2");
						getWalking().walk(floor3stairArea.getRandomTile());
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
			} else {
				if (getLocalPlayer().getZ() == 0) {
					if (floor1stairArea.contains(getLocalPlayer())) {
						climbup();
					} else {
						log("3");
						getWalking().walk(floor1stairArea.getRandomTile());
						sleep(Calculations.random(100, 2000));
						Random srand11 = new Random();
						double chances11 = srand11.nextDouble();
						if (chances11 <= 0.3) {
							yaw = Calculations.random(0, 2000);
							pitch = Calculations.random(128, 360);
							getCamera().mouseRotateTo(yaw, pitch);
						}
					}

				} else if (getLocalPlayer().getZ() == 1) {
					if (floor2stairArea.contains(getLocalPlayer())) {
						climbup();
					} else {
						log("4");
						getWalking().walk(floor2stairArea.getRandomTile());
						sleep(Calculations.random(100, 2000));
						Random srand11 = new Random();
						double chances11 = srand11.nextDouble();
						if (chances11 <= 0.3) {
							yaw = Calculations.random(0, 2000);
							pitch = Calculations.random(128, 360);
							getCamera().mouseRotateTo(yaw, pitch);
						}
					}

				} else {
					if (bankArea.contains(getLocalPlayer())) {
						bank();
					} else {
						log("5");
						getWalking().walk(bankArea.getRandomTile());
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

			}

		}

		return Calculations.random(100, 110);
	}

	private void bank() {
		if (getBank().isOpen()) {
			getBank().depositAllItems();
			sleep(Calculations.random(800, 1250));
		} else {
			getBank().open();
			sleepUntil(() -> getBank().isOpen(), Calculations.random(500, 1250));
		}
	}

	private void climbup() {
		GameObject Staircase = getGameObjects().closest(
				GameObject -> GameObject != null
						&& GameObject.hasAction("Climb-up"));
		if (Staircase != null) {
			Staircase.interact("Climb-up");
		}
		sleep(Calculations.random(600, 700));
	}

	private void climbdown() {
		GameObject Staircase = getGameObjects().closest(
				GameObject -> GameObject != null
						&& GameObject.hasAction("Climb-down"));
		if (Staircase != null && Staircase.interact("Climb-down"))
			;
		sleep(Calculations.random(600, 700));
	}

	private void take() {
		GroundItem Thing = getGroundItems().closest(f -> f != null);
		Thing.interactForceRight("Take");
		int count = 28 - getInventory().getEmptySlots();
		sleepUntil(() -> (28 - getInventory().getEmptySlots()) > count, 4000);
		if (getInventory().getEmptySlots() > 1) {
			take();
		}
	}

	public void onMessage(Message m) {
		if (m.getMessage().contains("I can't reach that!")) {
			getWalking().walk(goblinArea.getRandomTile());
			sleep(Calculations.random(5000, 6000));
			take();
		}
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
		// g.drawString("", 1, 1);

	}

}
