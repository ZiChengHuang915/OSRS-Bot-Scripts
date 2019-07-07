package AIO_iChop;

import AIO_iChop.GUI;

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

@ScriptManifest(name = "AIO iChop", author = "IcCookies", version = 1.0, description = "", category = Category.MISC)
public class Main extends AbstractScript implements PaintListener {

	public boolean bankMode;
	public String logName = new String();
	
	Tile chopNW = new Tile();
	Tile chopSE = new Tile();
	Tile bankNW = new Tile();
	Tile bankSE = new Tile();
	
	Area chopArea = new Area(chopNW, chopSE);
	Area bankArea = new Area(bankNW, bankSE);

	public boolean start = false;
	public long startTime;
	private int yaw;
	private int pitch;

	GameObject currentTree;
	GameObject nextTree;

	@Override
	public void onStart() {
		GUI GUI = new GUI(this);
		
	}
	//EVERY AREA IS IN TILES, IF IT IS PRESET THE AREAS HAS TO BE DEFINED BY THOSE TILES!!!
	@Override
	public int onLoop() {
		if (getInventory().isFull()) {
			if (bankMode) {
				if (bankArea.contains(getLocalPlayer())) {
					bank();
				} else {
					if (!bankArea.contains(getWalking().getDestination())) {
						getWalking().walk(bankArea.getRandomTile());
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
				getInventory().dropAll(logName);
			}
		} else {
			if (chopArea.contains(getLocalPlayer())) {
				chop();
			} else {
				if (!chopArea.contains(getWalking().getDestination())) {
					getWalking().walk(chopArea.getRandomTile());
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
		return Calculations.random(100, 110);
	}

	private void chop() {
		if (currentTree == null || !currentTree.exists()) {
			if (nextTree == null) {
				currentTree = getGameObjects().closest(logName);
			} else {
				currentTree = nextTree;
			}
		} else {
			currentTree.interact("Chop down");
			sleepUntil(() -> getLocalPlayer().getAnimation() != -1, 5000);
			nextTree = getGameObjects().closest(
					g -> g != null && g.getName().equals(logName)
							&& !g.equals(currentTree));
			if (Calculations.random(1, 4) == 1) {
				getCamera().mouseRotateToEntity(currentTree);
			}
			getMouse().move(nextTree);
			sleepUntil(() -> !currentTree.exists()
					|| getLocalPlayer().getAnimation() == -1, 30000);
			currentTree = null;
		}
	}

	private void bank() {
		if (getBank().isOpen()) {
			getBank().depositAll(logName);
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
								- startTime), 23, 57);

	}

}
/*[ERROR]19:41:20: Exception has occurred while running! Please report error to developer if problem persists:
	org.apache.0.2.5.NumberIsTooLargeException: lower bound (0) must be strictly less than upper bound (-1)
	at org.apache.0.2.0d.UniformIntegerDistribution.<init>(UniformIntegerDistribution.java:78)
	at org.apache.0.2.0g.RandomDataGenerator.0(RandomDataGenerator.java:198)
	at org.dreambot.api.methods.Calculations.random(Calculations.java:34)
	at org.dreambot.api.methods.map.Area.getRandomTile(Area.java:108)
	at AIO_iChop.Main.onLoop(Main.java:77)
	at org.dreambot.api.script.AbstractScript.run(AbstractScript.java:223)
	at java.lang.Thread.run(Unknown Source)
*/
