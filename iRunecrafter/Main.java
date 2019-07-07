package iRunecrafter;

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
import org.dreambot.api.wrappers.interactive.Entity;
import org.dreambot.api.wrappers.interactive.GameObject;

@ScriptManifest(name = "iRunecrafter", author = "IcCookies", version = 1.0, description = "", category = Category.RUNECRAFTING)
public class Main extends AbstractScript implements PaintListener {

	private long startTime;

	private int yaw;
	private int pitch;

	String runeName = new String("");
	Area bankArea = new Area();
	Area altarArea = new Area();
	Area craftArea = new Area();
	Area portalArea = new Area();
	Area rockArea = new Area();

	@Override
	public void onStart() {
		GUI GUI = new GUI(this);
		startTime = System.currentTimeMillis();
	}

	@Override
	public int onLoop() {

		if (getWalking().getRunEnergy() > Calculations.random(5, 10)
				&& getWalking().isRunEnabled() == false) {
			getWalking().toggleRun();
		}

		if (craftArea.contains(getLocalPlayer())) {
			if (getInventory().isFull()) {
				craft();
			} else {
				tpout();
			}
		} else {
			if (getInventory().isFull()) {
				if (altarArea.contains(getLocalPlayer())) {
					tpin();
				} else {
					getWalking().walk(altarArea.getRandomTile());
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

		return (100);
	}

	private void craft() {
		if (rockArea.contains(getLocalPlayer())) {
			GameObject altar = getGameObjects().closest("Altar");
			if (altar != null) {
				altar.interactForceRight("Craft-rune");
				sleepUntil(() -> !getInventory().isFull(), 3000);
			}
		}else{
			getWalking().walk(rockArea.getRandomTile());
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

	private void bank() {
		if (getBank().isOpen()) {
			getBank().depositAll(runeName);
			sleepUntil(() -> !getInventory().contains(runeName), 3000);
			getBank().withdrawAll("Rune essence");
			sleepUntil(() -> getInventory().isFull(), 3000);
		} else {
			getBank().open();
			sleepUntil(() -> getBank().isOpen(), Calculations.random(500, 1250));
		}
	}

	private void tpin() {
		GameObject altar = getGameObjects().closest("Mysterious ruins");
		if (altar != null) {
			altar.interactForceRight("Enter");
			sleep(1500, 2000);
		}
	}

	private void tpout() {
		if (portalArea.contains(getLocalPlayer())) {
			Entity p = getGameObjects().closest("Portal");
			if (p != null) {
				getMouse().click(p);
				sleep(2000);
			}
		} else {
			getWalking().walk(portalArea.getRandomTile());
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
