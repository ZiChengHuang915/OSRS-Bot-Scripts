package bots;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.widgets.message.Message;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;

@ScriptManifest(category = Category.FISHING, name = "iLobster Fisher", author = "IcCookies", version = 1.0, description = "Fishes lobsters in Port Sarim!")
public class LobsterFisher extends AbstractScript {
	private int status;
	private long startTime;
	private int lobNum;
	private int yaw;
	private int pitch;
	// private long lobPerHour;
	// yaw is 0 to 2000
	// pitch 128 to 360
	Area fishArea = new Area(2924, 3180, 2925, 3176, 0);

	Area sailmainlandArea = new Area(3026, 3220, 3029, 3216, 0);
	Area sailislandArea = new Area(2947, 3150, 2954, 3146, 0);

	Area mainlandArea = new Area(3019, 3242, 3050, 3202, 0);
	Area islandArea = new Area(2859, 3198, 2958, 3140, 0);

	Area plankislandArea = new Area(2956, 3143, 2956, 3143, 1);
	Area plankmainlandArea = new Area(3032, 3217, 3032, 3217, 1);

	Area bankArea = new Area(3043, 3237, 3047, 3234, 0);

	private final Image bg = getImage("http://s12.postimg.org/8ouudwm8d/Untitled.png");

	@Override
	public void onStart() {
		startTime = System.currentTimeMillis();
		lobNum = 0;
		getSkillTracker().start(Skill.FISHING);
	}

	private enum State {
		WALKTOSAILONMAINLAND, SAILTOISLAND, SAILTOMAINLAND, WALKTHEPLANK, WALKTOFISH, FISH, WALKTOSAILONISLAND, WALKTOBANK, BANK, DONTKNOW
	}

	private State getState() {
		if (!getInventory().isFull()) {
			if (mainlandArea.contains(getLocalPlayer().getTile())) {
				if (sailmainlandArea.contains(getLocalPlayer().getTile())) {
					return State.SAILTOISLAND;
				} else {
					return State.WALKTOSAILONMAINLAND;
				}
			} else if (islandArea.contains(getLocalPlayer().getTile())) {
				if (fishArea.contains(getLocalPlayer().getTile())) {
					return State.FISH;
				} else {
					return State.WALKTOFISH;
				}
			} else if (plankislandArea.contains(getLocalPlayer().getTile())) {
				return State.WALKTHEPLANK;
			} else {
				return State.DONTKNOW;
			}
		} else {
			if (islandArea.contains(getLocalPlayer().getTile())) {
				if (sailislandArea.contains(getLocalPlayer().getTile())) {
					return State.SAILTOMAINLAND;
				} else {
					return State.WALKTOSAILONISLAND;
				}
			} else if (mainlandArea.contains(getLocalPlayer().getTile())) {
				if (bankArea.contains(getLocalPlayer().getTile())) {
					return State.BANK;
				} else {
					return State.WALKTOBANK;
				}
			} else if (plankmainlandArea.contains(getLocalPlayer().getTile())) {
				return State.WALKTHEPLANK;
			} else {
				return State.DONTKNOW;
			}
		}
	}

	@Override
	public int onLoop() {

		Random srand = new Random();
		double chances = srand.nextDouble();
		if (chances <= 0.08) {
			status = 10;
			yaw = Calculations.random(0, 2000);
			pitch = Calculations.random(128, 360);
			getCamera().mouseRotateTo(yaw, pitch);
		}

		switch (getState()) {
		case SAILTOISLAND:

			status = 1;
			NPC Seamman_Lorris = getNpcs().closest(
					npc -> npc != null && npc.hasAction("Pay-fare"));
			if (Seamman_Lorris != null && Seamman_Lorris.interact("Talk-to")) {

				sleep(Calculations.random(1000, 2000));
				getDialogues().continueDialogue();

				sleep(Calculations.random(1000, 2000));
				getDialogues().continueDialogue();

				sleep(Calculations.random(1000, 2000));
				getMouse().click(
						new Point(Calculations.random(235, 289), Calculations
								.random(397, 406)));

				sleep(Calculations.random(1000, 2000));
				getDialogues().continueDialogue();

				sleep(10000);
			}
			break;
		case SAILTOMAINLAND:
			status = 2;
			NPC Customs_officer = getNpcs().closest(
					npc -> npc != null && npc.hasAction("Pay-Fare"));
			if (Customs_officer != null && Customs_officer.interact("Talk-to")) {

				sleep(Calculations.random(1000, 2000));
				getDialogues().continueDialogue();

				sleep(Calculations.random(1000, 2000));
				getMouse().click(
						new Point(Calculations.random(235, 289), Calculations
								.random(397, 406)));

				sleep(Calculations.random(1000, 2000));
				getDialogues().continueDialogue();

				sleep(Calculations.random(1000, 2000));
				getDialogues().continueDialogue();

				sleep(Calculations.random(1000, 2000));
				getMouse().click(
						new Point(Calculations.random(169, 356), Calculations
								.random(415, 420)));

				sleep(Calculations.random(1000, 2000));
				getDialogues().continueDialogue();

				sleep(Calculations.random(1000, 2000));
				getDialogues().continueDialogue();

				sleep(Calculations.random(1000, 2000));
				getMouse().click(
						new Point(Calculations.random(255, 260), Calculations
								.random(398, 405)));

				sleep(Calculations.random(1000, 2000));
				getDialogues().continueDialogue();
				sleep(10000);
			}
			break;
		case BANK:
			status = 3;
			GameObject Bank_deposit_box = getGameObjects().closest(
					Gameobject -> Gameobject != null
							&& Gameobject.hasAction("Deposit"));
			if (Bank_deposit_box != null
					&& Bank_deposit_box.interact("Deposit")) {
				if (sleepUntil(() -> getDepositBox().isOpen(), 5000)) {
					if (getInventory().contains("Raw tuna")
							|| getInventory().contains("Raw swordfish")) {
						if (getDepositBox().depositAll("Raw tuna"))
							;
						if (getDepositBox().depositAll("Raw swordfish"))
							;
					} else {
						if (getDepositBox().depositAll("Raw lobster"))
							;
					}
					if (sleepUntil(() -> !getInventory().isFull(), 5000)) {
						if (getDepositBox().close()) {
							sleepUntil(() -> !getDepositBox().isOpen(), 5000);
						}
					}
				}

			}
			break;
		case DONTKNOW:
			status = 4;
			break;
		case FISH:
			status = 5;
			NPC Fishing_spot = getNpcs().closest(
					npc -> npc != null && npc.hasAction("Harpoon"));
			if (Fishing_spot != null && Fishing_spot.interact("Cage")) {
				Random srand4 = new Random();
				double chances4 = srand4.nextDouble();
				if (chances4 <= 0.08) {
					status = 10;
					yaw = Calculations.random(0, 2000);
					pitch = Calculations.random(128, 360);
					getCamera().mouseRotateTo(yaw, pitch);
				}
				Random srand2 = new Random();
				double chances2 = srand2.nextDouble();
				if (chances2 <= 0.7) {
					status = 10;
					getMouse().moveMouseOutsideScreen();
					status = 5;
				}

				sleepUntil(() -> getLocalPlayer().isStandingStill(), 30000);

				Random srand1 = new Random();
				double chances1 = srand1.nextDouble();
				if (chances1 <= 0.5) {
					status = 10;
					sleep(Calculations.random(1000, 5000));
					status = 5;
				} else {
					status = 10;
					sleep(Calculations.random(200, 300));
					status = 5;
				}
			}
			break;
		case WALKTHEPLANK:
			status = 6;
			GameObject Gangplank = getGameObjects().closest(
					gameobject -> gameobject != null
							&& gameobject.hasAction("Cross"));
			if (Gangplank != null && Gangplank.interact("Cross")) {
				sleep(Calculations.random(3000, 3500));
			}
			Random srand5 = new Random();
			double chances5 = srand5.nextDouble();
			if (chances5 <= 0.08) {
				status = 10;
				yaw = Calculations.random(0, 2000);
				pitch = Calculations.random(128, 360);
				getCamera().mouseRotateTo(yaw, pitch);
			}
			break;
		case WALKTOBANK:
			status = 7;
			getWalking().walk(bankArea.getRandomTile());
			sleep(Calculations.random(100, 2000));
			Random srand6 = new Random();
			double chances6 = srand6.nextDouble();
			if (chances6 <= 0.08) {
				status = 10;
				yaw = Calculations.random(0, 2000);
				pitch = Calculations.random(128, 360);
				getCamera().mouseRotateTo(yaw, pitch);
			}
			break;
		case WALKTOFISH:
			status = 8;
			getWalking().walk(fishArea.getRandomTile());
			sleep(Calculations.random(100, 2000));
			Random srand7 = new Random();
			double chances7 = srand7.nextDouble();
			if (chances7 <= 0.08) {
				status = 10;
				yaw = Calculations.random(0, 2000);
				pitch = Calculations.random(128, 360);
				getCamera().mouseRotateTo(yaw, pitch);
			}
			break;
		case WALKTOSAILONISLAND:
			status = 9;
			getWalking().walk(sailislandArea.getRandomTile());
			sleep(Calculations.random(100, 2000));
			Random srand8 = new Random();
			double chances8 = srand8.nextDouble();
			if (chances8 <= 0.08) {
				status = 10;
				yaw = Calculations.random(0, 2000);
				pitch = Calculations.random(128, 360);
				getCamera().mouseRotateTo(yaw, pitch);
			}
			break;
		case WALKTOSAILONMAINLAND:
			status = 10;
			getWalking().walk(sailmainlandArea.getRandomTile());
			sleep(Calculations.random(100, 2000));
			Random srand9 = new Random();
			double chances9 = srand9.nextDouble();
			if (chances9 <= 0.08) {
				status = 10;
				yaw = Calculations.random(0, 2000);
				pitch = Calculations.random(128, 360);
				getCamera().mouseRotateTo(yaw, pitch);
			}
			break;

		}
		return Calculations.random(200, 400);
	}

	public void onMessage(Message m) {
		if (m.getMessage().contains("You catch a lobster.")) {
			lobNum = lobNum + 1;
		}
	}

	@Override
	public void onPaint(Graphics g) {
		super.onPaint(g);

		getSkillTracker().getGainedExperience(Skill.FISHING);
		getSkillTracker().getGainedExperiencePerHour(Skill.FISHING);

		int ExpPerHour = getSkillTracker().getGainedExperiencePerHour(
				Skill.FISHING);

		g.drawImage(bg, 0, 340, null);
		g.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 14));
		g.setColor(Color.WHITE);
		g.drawString("IcCookies' Lobster Fisher", 18, 355);

		g.drawString(
				"Runtime: "
						+ Timer.formatTime(System.currentTimeMillis()
								- startTime), 280, 355);
		// lobPerHour = (int) lobNum
		// / ((int) System.currentTimeMillis() - startTime / 3600000);
		g.drawString("Lobsters caught:  " + lobNum, 18, 375);

		g.drawString("Experience gained: "
				+ getSkillTracker().getGainedExperience(Skill.FISHING), 18, 395);
		g.drawString("Experience per hour: " + ExpPerHour, 18, 415);
		// status: 10 antiban 1 sailtoisland 2 sailtomainland 3 bank 4 dontknow
		// 5 fishing 6 walktheplank 7 walktobank 8 walktofish 9
		// walktosailonisland 10 walktosailmainland
		if (status < 2 && status > 0) {
			g.drawString("Status: Sailing to island!", 18, 458);
		} else if (status < 3 && status > 1) {
			g.drawString("Status: Sailing to Port Sarim!", 18, 458);
		} else if (status < 4 && status > 2) {
			g.drawString("Status: Banking!", 18, 458);
		} else if (status < 5 && status > 3) {
			g.drawString("Status: I have no idea...", 18, 458);
		} else if (status < 6 && status > 4) {
			g.drawString("Status: Fishing!", 18, 458);
		} else if (status < 7 && status > 5) {
			g.drawString("Status: Walk the plank!", 18, 458);
		} else if (status < 8 && status > 6) {
			g.drawString("Status: Walking to bank!", 18, 458);
		} else if (status < 9 && status > 7) {
			g.drawString("Status: Walking to fish!", 18, 458);
		} else if (status < 10 && status > 8) {
			g.drawString("Status: On island, walking to sail!", 18, 458);
		} else if (status < 11 && status > 9) {
			g.drawString("Status: In Port Sarim, walking to sail!", 18, 458);
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

	}

}