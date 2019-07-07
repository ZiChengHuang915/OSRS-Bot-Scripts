package bots;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.PaintListener;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.Entity;
import org.dreambot.api.wrappers.interactive.NPC;

@ScriptManifest(name = "iRune Essence Miner", author = "IcCookies", version = 1.1, description = "Mines Rune Essence in Varrock. See thread for more details.", category = Category.MINING)
public class EssenceMiner extends AbstractScript implements PaintListener {

	private String state;

	private int yaw;
	private int pitch;
	private long startTime;

	private Area landArea = new Area(3245, 3429, 3268, 3397, 0);

	private Area bankArea = new Area(3251, 3422, 3256, 3420, 0);
	private Area shopArea = new Area(3252, 3404, 3255, 3399, 0);

	private final Image bg = getImage("http://imgur.com/NBd7ZKE.png");

	@Override
	public void onStart() {
		startTime = System.currentTimeMillis();
		getSkillTracker().start(Skill.MINING);
		getSkillTracker().start();
	}

	@Override
	public int onLoop() {
		if (landArea.contains(getLocalPlayer())) {
			if (!getInventory().isFull()) {
				if (shopArea.contains(getLocalPlayer())) {
					tp();
				} else {
					state = "Walking to magic shop!";
					getWalking().walk(shopArea.getRandomTile());
					sleep(Calculations.random(100, 2000));
					Random srand = new Random();
					double chances = srand.nextDouble();
					if (chances <= 0.1) {
						yaw = Calculations.random(0, 2000);
						pitch = Calculations.random(128, 360);
						getCamera().mouseRotateTo(yaw, pitch);
					}
				}
			} else {
				if (bankArea.contains(getLocalPlayer())) {
					bank();
				} else {
					state = "Walking to bank!";
					getWalking().walk(bankArea.getRandomTile());
					sleep(Calculations.random(100, 2000));
					Random srand = new Random();
					double chances = srand.nextDouble();
					if (chances <= 0.1) {
						yaw = Calculations.random(0, 2000);
						pitch = Calculations.random(128, 360);
						getCamera().mouseRotateTo(yaw, pitch);
					}
				}
			}
		} else {
			if (!getInventory().isFull()) {
				mine();
			} else {
				portal();
			}

		}
		return Calculations.random(300, 600);
	}

	private void tp() {
		state = "Teleporting!";
		NPC Guy = getNpcs().closest(
				npc -> npc != null && npc.hasAction("Teleport"));
		Guy.interactForceRight("Teleport");
		sleep(Calculations.random(3000, 3100));
	}

	private void bank() {
		state = "Banking!";
		if (getBank().isOpen()) {
			if (getInventory().contains("Rune essence")) {
				getBank().depositAll("Rune essence");
				sleep(Calculations.random(800, 1250));
			}
		} else {
			getBank().open();
			sleepUntil(() -> getBank().isOpen(), Calculations.random(500, 1250));
		}
	}

	private void mine() {
		state = "Mining rune essence!";
		Entity Ess = getGameObjects().closest("Rune Essence");
		if (Ess.distance(getPlayers().localPlayer().getTile()) < 6) {
			if (Ess != null) {
				Ess.interact("Mine");
				sleep(5000, 6000);
				int random = Calculations.random(1, 3);
				if (random == 1) {
					state = "Antiban!";
					getMouse().moveMouseOutsideScreen();
					sleep(Calculations.random(10000, 30000));
					state = "Mining rune essence!";
				}
				sleepUntil(() -> getLocalPlayer().isStandingStill(), 50000);
			}
		} else {
			state = "Walking to rune essence!";
			getWalking().walk(Ess);
		}
	}

	private void portal() {
		state = "Teleporting out!";
		Tile portalTile = new Tile();
		Entity p = getGameObjects().closest("Portal");
		if (p != null) {
			portalTile = p.getTile();

			getWalking().walk(portalTile);
			sleep(Calculations.random(2000, 3000));
			getWalking().walk(portalTile);
			sleep(Calculations.random(2000, 3000));

			getMouse().click(p);
			sleep(Calculations.random(500, 600));
			yaw = Calculations.random(0, 2000);
			pitch = Calculations.random(128, 360);
			getCamera().mouseRotateTo(yaw, pitch);

			sleep(1000);
		} else {
			Tile portalTile2 = new Tile();
			Entity o = getNpcs().closest("<col=00ffff>Portal</col>");
			if (o != null) {
				portalTile2 = o.getTile();

				getWalking().walk(portalTile2);
				sleep(Calculations.random(2000, 3000));
				getWalking().walk(portalTile2);
				sleep(Calculations.random(2000, 3000));

				getMouse().click(o);
				sleep(Calculations.random(500, 600));
				yaw = Calculations.random(0, 2000);
				pitch = Calculations.random(128, 360);
				getCamera().mouseRotateTo(yaw, pitch);
			}
		}
	}

	// Tile t = portal.getTile();
	// getWalking().walkOnScreen(t);
	@Override
	public void onPaint(Graphics g) {
		super.onPaint(g);
		getSkillTracker().getGainedExperience(Skill.MINING);
		getSkillTracker().getGainedExperiencePerHour(Skill.MINING);
		int ExpPerHour = getSkillTracker().getGainedExperiencePerHour(
				Skill.MINING);
		long EssNum = getSkillTracker().getGainedExperience(Skill.MINING) / 5;
		g.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 14));
		g.setColor(Color.WHITE);
		g.drawImage(bg, 0, 340, null);
		g.drawString("iRune Essence Miner", 18, 355);
		g.drawString(
				"Runtime: "
						+ Timer.formatTime(System.currentTimeMillis()
								- startTime), 280, 355);
		g.drawString("Experience gained: "
				+ getSkillTracker().getGainedExperience(Skill.MINING), 18, 390);
		g.drawString("Experience per hour: " + ExpPerHour, 18, 410);
		g.drawString("Rune essence mined: " + EssNum, 18, 430);
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
}


