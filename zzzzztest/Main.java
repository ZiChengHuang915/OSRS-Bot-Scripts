package zzzzztest;

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
import org.dreambot.api.wrappers.interactive.NPC;

@ScriptManifest(name = "mining test main", author = "IcCookies", version = 1.0, description = "", category = Category.MINING)
public class Main extends AbstractScript implements PaintListener {

	private long startTime;

	private int yaw;
	private int pitch;

	private Area MineArea = new Area();

	@Override
	public void onStart() {
		startTime = System.currentTimeMillis();
	}

	@Override
	public int onLoop() {
		if (getInventory().isFull()) {
			getTrade().tradeWithPlayer("FrogSoup5");
			sleepUntil(() -> getTrade().isOpen(), 10000);
			getTrade().addItem("Iron ore", 27);
			getTrade().acceptTrade();
			sleepUntil(() -> getTrade().isOpen(1), 10000);
			getTrade().acceptTrade();
		} else {
			if (MineArea.contains(getLocalPlayer())) {
				GameObject iron1 = getGameObjects().closest(7455);
				if (iron1.exists() && iron1 != null && iron1.getTile(1, 1, 0)) {
					iron1.interactForceLeft("Mine");
					sleepUntil(() -> !iron1.exists(), 10000);
				} else {
					GameObject iron2 = getGameObjects().closest(7455);
					if (iron2.exists() && iron2 != null) {
						iron2.interactForceLeft("Mine");
						sleepUntil(() -> !iron2.exists(), 10000);
					}
				}
			} else {
				if (!MineArea.contains(getWalking().getDestination())) {
					getWalking().walk(MineArea.getRandomTile());
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
		return (100);
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
