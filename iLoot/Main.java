package iLoot;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.PaintListener;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.widgets.WidgetChild;

@ScriptManifest(name = "iLoot", author = "IcCookies", version = 1.0, description = "", category = Category.MISC)
public class Main extends AbstractScript implements PaintListener {

	private long startTime;

	private int yaw;
	private int pitch;

	// private Area EdgeArea = new Area();
	private Area WildArea = new Area(3064, 3545, 3104, 3523, 0);
	private Area DitchEdge = new Area(3085, 3520, 3090, 3519, 0);
	private Area DitchWild = new Area(3085, 3524, 3090, 3523, 0);

	private Area BankArea = new Area(3094, 3495, 3096, 3494, 0);

	@Override
	public void onStart() {

		// GUI GUI = new GUI(this);
		startTime = System.currentTimeMillis();
	}

	@Override
	public int onLoop() {
		if (getSkills().getBoostedLevels(Skill.HITPOINTS) < getSkills()
				.getRealLevel(Skill.HITPOINTS)) {
			if (DitchWild.contains(getLocalPlayer())) {
				if (getInventory().contains("Lobster")) {
					getInventory().interact("Lobster", "Eat");
				} else if (getInventory().contains("Swordfish")) {
					getInventory().interact("Swordfish", "Eat");
				} else {
					sleepUntil(
							() -> getSkills().getBoostedLevels(Skill.HITPOINTS) == getSkills()
									.getRealLevel(Skill.HITPOINTS), 60000);
				}
			} else {
				log("running away");
				if (!DitchWild.contains(getWalking().getDestination())) {
					getWalking().walk(DitchWild.getRandomTile());
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
			if (WildArea.contains(getLocalPlayer())) {
				if (getInventory().isFull()) {
					if (DitchWild.contains(getLocalPlayer())) {
						log("crossing ditch");
						CrossDitch();
					} else {
						if (!DitchWild.contains(getWalking().getDestination())) {
							getWalking().walk(DitchWild.getRandomTile());
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
					log("looting");
					GameObject item = getGameObjects().closest(
							g -> g != null && g.hasAction("Take"));
					if (item != null) {
						log("non null item");
						int invcount = getInventory().fullSlotCount();
						item.interact("Take");
						sleepUntil(() -> invcount > invcount, 5000);
					}
				}
			} else {
				if (getInventory().isFull()) {
					if (BankArea.contains(getLocalPlayer())) {
						log("banking");
						if (getBank().isOpen()) {
							getBank().depositAllItems();
							sleepUntil(() -> getInventory().isEmpty(), 3000);
							getBank().close();
						} else {
							getBank().open();
							sleepUntil(() -> getBank().isOpen(), 3000);
						}
					} else {
						if (!BankArea.contains(getWalking().getDestination())) {
							getWalking().walk(BankArea.getRandomTile());
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
					if (DitchEdge.contains(getLocalPlayer())) {
						log("crossing ditch");
						CrossDitch();
					} else {
						if (!DitchEdge.contains(getWalking().getDestination())) {
							getWalking().walk(DitchEdge.getRandomTile());
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
			}
		}
		return (100);
	}

	private void CrossDitch() {
		GameObject Ditch = getGameObjects().closest(
				g -> g != null && g.getID() == 23271);
		if (Ditch != null) {
			Ditch.interact("Cross");
		}
		// WidgetChild EnterWild = getWidgets().getWidgetChild(382, 24);
		sleepUntil(() -> WildArea.contains(getLocalPlayer()), 5000);
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
