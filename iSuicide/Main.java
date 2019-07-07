package iSuicide;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
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
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.wrappers.widgets.message.Message;

@ScriptManifest(name = "iSuicide", author = "IcCookies", version = 1.0, description = "Dies.", category = Category.COMBAT)
public class Main extends AbstractScript implements PaintListener {

	private NPC currentNpc;
	public String npcName = "Goblin";
	public String equipment = "Bronze dagger";
	private boolean hasEquipment = true;

	private Area killArea = new Area(3242, 3242, 3256, 3229, 0);

	private int yaw;
	private int pitch;
	private long startTime;

	private final Image bg = getImage("http://imgur.com/NBd7ZKE.png");

	@Override
	public void onStart() {
		startTime = System.currentTimeMillis();

	}

	@Override
	public int onLoop() {
		if (getInventory().contains(equipment)) {
			getInventory().interact(equipment, "Wield");
			hasEquipment = true;
		}

		if (killArea.contains(getLocalPlayer())) {
			GroundItem item = getGroundItems().closest(equipment);
			if (item != null && item.exists() && hasEquipment == false) {
				item.interactForceRight("Take");
			}

			int hello = Calculations.random(1, 11);
			if (hello == 1) {
				GameObject random = getGameObjects().closest("Dead tree");
				GameObject random2 = getGameObjects().closest("Tree");
				GameObject random3 = getGameObjects().closest("Plant");
				int hi = Calculations.random(1, 4);
				if (hi == 1) {
					getCamera().rotateToEntity(random);
					getMouse().click(random, true);
					sleep(Calculations.random(400, 500));
				} else if (hi == 2) {
					getCamera().rotateToEntity(random2);
					getMouse().click(random2, true);
					sleep(Calculations.random(400, 500));
				} else if (hi == 3) {
					getCamera().rotateToEntity(random3);
					getMouse().click(random3, true);
					sleep(Calculations.random(400, 500));
				}
			}

			currentNpc = getNpcs().closest(
					npc -> npc != null && npc.getName() != null
							&& npc.getName().equals(npcName)
							&& !npc.isInCombat()
							&& npc.getInteractingCharacter() == null);
			if (currentNpc != null) {
				kill();
			}
		} else {
			walk();
		}
		return Calculations.random(300, 600);
	}

	private void walk() {
		if (!killArea.contains(getWalking().getDestination())) {
			getWalking().walk(killArea.getRandomTile());
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

	private void kill() {
		if (!getLocalPlayer().isInCombat()
				&& getLocalPlayer().getInteractingCharacter() == null) {

			getCamera().rotateToEntity(currentNpc);
			currentNpc.interactForceRight("Attack");
			sleepUntil(() -> getLocalPlayer().isInCombat(), 2000);
			int what = Calculations.random(1, 3);
			int what2 = Calculations.random(1, 3);
			if (what == 1) {
				getCamera().mouseRotateTo(yaw, pitch);
				if (what2 == 1) {
					int Y = getCamera().getYaw();
					int P = getCamera().getPitch();
					sleep(Calculations.random(300, 500));
					getCamera().mouseRotateTo(Y + Calculations.random(10, 100),
							P + Calculations.random(10, 100));
				}
			}
			sleepUntil(() -> !getLocalPlayer().isInCombat(),
					Calculations.random(1000, 10000));
			int what3 = Calculations.random(1, 3);
			int what4 = Calculations.random(1, 3);
			if (what3 == 1) {
				getCamera().mouseRotateTo(yaw, pitch);
				if (what4 == 1) {
					int Y = getCamera().getYaw();
					int P = getCamera().getPitch();
					sleep(Calculations.random(300, 500));
					getCamera().mouseRotateTo(Y + Calculations.random(10, 100),
							P + Calculations.random(10, 100));
				}
			}
			sleepUntil(() -> !getLocalPlayer().isInCombat(), 30000);
		}
	}

	public void onMessage(Message m) {
		if (m.getMessage().contains("Oh dear, you are dead!")) {
			hasEquipment = false;
			int a = Calculations.random(1, 6);
			int b = Calculations.random(1, 6);
			if (a == 1) {
				getMouse().moveMouseOutsideScreen();
				sleep(Calculations.random(60000, 120000));
				if (b == 1) {
					getMouse().moveMouseOutsideScreen();
					sleep(Calculations.random(300000, 600000));
				}
			}
		}
	}

	public void onMessage1(Message m) {
		if (m.getMessage().contains("I can't reach that!")) {
			GameObject Door = getGameObjects().closest("Door");
			Door.interactForceRight("Open");
		}
	}

	@Override
	public void onPaint(Graphics g) {
		super.onPaint(g);

	}

	private Image getImage(String url) {
		try {
			return ImageIO.read(new URL(url));
		} catch (IOException e) {
		}
		return null;
	}
}
