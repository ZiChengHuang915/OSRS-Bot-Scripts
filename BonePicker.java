package bots;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.GroundItem;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

@ScriptManifest(category = Category.MISC, name = "IcCookies' Bone Picker", author = "IcCookies", version = 1.0)
public class BonePicker extends AbstractScript {

	Area boneArea = new Area(3226, 3301, 3233, 3295, 0);
	Area floor1stairArea = new Area(3205, 3209, 3206, 3209, 0);
	Area floor2stairArea = new Area(3205, 3210, 3205, 3209, 1);
	Area bankArea = new Area(3207, 3220, 3209, 3219, 2);
	Area floor3stairArea = new Area(3205, 3210, 3205, 3209, 2);
	private int yaw;
	private int pitch;
	private int boneNum;
	private long startTime;

	@Override
	public void onStart() {
		log("Welcome to IcCookies' private script!");
		boneNum = 0;
		startTime = System.currentTimeMillis();
	}

	private enum State {
		PICK;
	}

	private State getState() {
		return State.PICK;
	}

	@Override
	public int onLoop() {
		switch (getState()) {
		case PICK:
			pick();
			break;
		}
		return 100;
	}

	private void pick() {
		if (!getInventory().isFull()) {
			if (getLocalPlayer().getZ() == 0) {
				if (boneArea.contains(getLocalPlayer())) {
				GroundItem Bone = getGroundItems().closest(
						GroundItem -> GroundItem != null
								&& GroundItem.getName().equals("Bones"));
				Bone.interactForceRight("Take");
				boneNum = boneNum + 1;
				int countLog = getInventory().count("Bones");
				sleepUntil(() -> getInventory().count("Bones") > countLog, 10000);
				Random srand11 = new Random();
				double chances11 = srand11.nextDouble();
				if (chances11 <= 0.08) {
					yaw = Calculations.random(0, 2000);
					pitch = Calculations.random(128, 360);
					getCamera().mouseRotateTo(yaw, pitch);
				}
				}else{
					getWalking().walk(boneArea.getRandomTile());
					sleep(Calculations.random(100, 2000));
				}
			} else if (getLocalPlayer().getZ() == 1) {
				climbdown();
			} else {
				if (floor3stairArea.contains(getLocalPlayer())) {
					climbdown();
				} else {
					getWalking().walk(floor3stairArea.getRandomTile());
					sleep(Calculations.random(100, 2000));
				}
			}
		} else {
			if (getLocalPlayer().getZ() == 0) {
				if (floor1stairArea.contains(getLocalPlayer())) {
					climbup();
				} else {
					getWalking().walk(floor1stairArea.getRandomTile());
					sleep(Calculations.random(100, 2000));
				}
			}
			if (getLocalPlayer().getZ() == 1) {
				if (floor2stairArea.contains(getLocalPlayer())) {
					climbup();
				} else {
					getWalking().walk(floor2stairArea.getRandomTile());
					sleep(Calculations.random(100, 2000));
				}
			} else {
				if (bankArea.contains(getLocalPlayer())) {
					bank();
				} else {
					getWalking().walk(bankArea.getRandomTile());
					sleep(Calculations.random(100, 2000));
				}
			}
		}
	}

	private void bank() {
		if (getBank().isOpen()) {
			if (getInventory().contains("Bones")) {
				getBank().depositAll("Bones");
				sleep(Calculations.random(800, 1250));
			}
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

	@Override
	public void onExit() {
		log("Thank you for using this script.");
	}

	@Override
	public void onPaint(Graphics g) {
		super.onPaint(g);
		g.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 14));
		g.setColor(Color.WHITE);
		g.drawString("Bones taken:" + boneNum, 10, 65);
		g.drawString(
				"Runtime: "
						+ Timer.formatTime(System.currentTimeMillis()
								- startTime), 10, 50);
	}
}
