package PrivateGarlic;


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
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.widgets.message.Message;

@ScriptManifest(name = "Garlic Looter", author = "IcCookies", version = 1.0, description = "Loots garlic.", category = Category.MISC)
public class Main extends AbstractScript implements PaintListener {

	private Area bankArea = new Area(3092, 3245, 3095, 3241, 0);
	private Area Morgan1 = new Area(3097, 3270, 3102, 3266, 0);
	
	private int garNum;

	private int yaw;
	private int pitch;
	private long startTime;
	

	@Override
	public void onStart() {
		startTime = System.currentTimeMillis();

	}

	@Override
	public int onLoop() {
		if (getLocalPlayer().getZ() == 0) {
			if (getInventory().isFull()) {
				if (bankArea.contains(getLocalPlayer())) {
					bank();
				} else {
					if (!bankArea.contains(getWalking().getDestination())) {
						getWalking().walk(bankArea.getRandomTile());
						sleep(Calculations.random(100, 2000));
						Random srand = new Random();
						double chances = srand.nextDouble();
						if (chances <= 0.3) {
							yaw = Calculations.random(0, 2000);
							pitch = Calculations.random(128, 360);
							getCamera().mouseRotateTo(yaw, pitch);
						}
					}
				}
			} else {
				if (Morgan1.contains(getLocalPlayer())) {
					GameObject Stair = getGameObjects().closest("Staircase");
					if (Stair != null) {
						Stair.interact("Climb-up");
						sleep(2000, 2200);
					}
				} else {
					if (!Morgan1.contains(getWalking().getDestination())) {
						getWalking().walk(Morgan1.getRandomTile());
						sleep(Calculations.random(100, 2000));
						Random srand = new Random();
						double chances = srand.nextDouble();
						if (chances <= 0.3) {
							yaw = Calculations.random(0, 2000);
							pitch = Calculations.random(128, 360);
							getCamera().mouseRotateTo(yaw, pitch);
						}
					}
				}
			}
		} else {
			if (getInventory().isFull()) {
				GameObject Stair = getGameObjects().closest("Staircase");
				if (Stair != null) {
					Stair.interact("Climb-down");
					sleep(2000, 2200);
				}
			} else {
				GameObject Cupboard = getGameObjects().closest("Cupboard");
				if (Cupboard != null) {
					if (Cupboard.hasAction("Open")) {
						Cupboard.interact("Open");
					} else {
						Cupboard.interactForceLeft("Search");
					}
				}
			}
		}
		return Calculations.random(300, 600);
	}

	private void bank() {
		if (getBank().isOpen()) {
			getBank().depositAll("Garlic");
			garNum++;
			getBank().close();
		} else {
			getBank().open();
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
								- startTime), 20, 70);
		g.drawString("Inventories: " + garNum, 20, 90);
		
	}

}
