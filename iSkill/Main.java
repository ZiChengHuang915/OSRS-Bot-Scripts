package iSkill;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.PaintListener;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.widgets.message.Message;

@ScriptManifest(name = "iSkill", author = "IcCookies", version = 1.0, description = "Does various skills.", category = Category.MISC)
public class Main extends AbstractScript implements PaintListener {

	private long startTime;
	private int yaw;
	private int pitch;

	private int doWhatTimer;
	private int doWhat;

	private Area fishArea = new Area(3238, 3155, 3250, 3143, 0);
	private Area mineArea = new Area(3222, 3148, 3225, 3146, 0);
	private Area chopArea = new Area(3123, 3217, 3137, 3207, 0);
	
	private String doWhatTimerString = new String();

	@Override
	public void onStart() {
		doWhat = Calculations.random(1, 4);
		doWhatTimer = 0;
		startTime = System.currentTimeMillis();
	}

	@Override
	public int onLoop() {
		if (doWhatTimer > Calculations.random(5, 10)) {
			doWhatTimer = 0;
			doWhat = Calculations.random(1, 4);
		}

		if (doWhat == 1) {/////////////////////////////////////////////////////////////////////////////////////
			if (getInventory().contains("Tin ore")) {
				getInventory().dropAll("Tin ore");
			}
			if (getInventory().contains("Logs")) {
				getInventory().dropAll("Logs");
			}
			NPC fishthing = getNpcs().closest("Fishing spot");
			if (fishArea.contains(getLocalPlayer())) {
				if (getInventory().isFull()) {
					getInventory().dropAll("Raw shrimps");
				} else {
					if (fishthing != null && fishthing.hasAction("Net")) {
						fishthing.interact("Net");
						sleepUntil(() -> getLocalPlayer().getAnimation() != -1, 3000);
						if (Calculations.random(1, 3) == 1){
							getMouse().moveMouseOutsideScreen();
						}
						sleepUntil(() -> getLocalPlayer().getAnimation() == -1, 30000);
						if (Calculations.random(1, 3) == 1){
							sleep(Calculations.random(5000, 10000));
						}
					}
				}
			} else {
				getWalking().walk(fishArea.getRandomTile());
				sleep(Calculations.random(100, 2000));
				int a = Calculations.random(1, 4);
				if (a == 1) {
					yaw = Calculations.random(0, 2000);
					pitch = Calculations.random(128, 360);
					getCamera().mouseRotateTo(yaw, pitch);
				}
			}
		} else if (doWhat == 2) {/////////////////////////////////////////////////////////////////////////////////
			if (getInventory().contains("Raw shrimps")) {
				getInventory().dropAll("Raw shrimps");
			}
			if (getInventory().contains("Logs")) {
				getInventory().dropAll("Logs");
			}
			GameObject Tin1 = getGameObjects().closest(7486);
			if (mineArea.contains(getLocalPlayer())) {
				if (getInventory().isFull()) {
					getInventory().dropAll("Tin ore");
				} else {
					if (Tin1 != null && Tin1.getTile().equals(new Tile(3223, 3148, 0)) && Tin1.exists()){
						Tin1.interact("Mine");
						sleepUntil(() -> getLocalPlayer().getAnimation() != -1, 3000);
						sleepUntil(() -> getLocalPlayer().getAnimation() == -1, 30000);
					}
				}
			} else {
				getWalking().walk(mineArea.getRandomTile());
				sleep(Calculations.random(100, 2000));
				int a = Calculations.random(1, 4);
				if (a == 1) {
					yaw = Calculations.random(0, 2000);
					pitch = Calculations.random(128, 360);
					getCamera().mouseRotateTo(yaw, pitch);
				}
			}
		} else if (doWhat == 3) {//////////////////////////////////////////////////////////////////////////////////
			if (getInventory().contains("Raw shrimps")) {
				getInventory().dropAll("Raw shrimps");
			}
			if (getInventory().contains("Tin ore")) {
				getInventory().dropAll("Tin ore");
			}
			GameObject Tree = getGameObjects().closest("Tree");
			if (chopArea.contains(getLocalPlayer())){
				if (getInventory().isFull()){
					getInventory().dropAll("Logs");
				}else{
					if(Tree != null && Tree.hasAction("Chop down")){
						Tree.interact("Chop down");
						sleepUntil(() -> getLocalPlayer().getAnimation() != -1, 3000);
						sleepUntil(() -> getLocalPlayer().getAnimation() == -1, 30000);
					}
				}
			}else{
				getWalking().walk(chopArea.getRandomTile());
				sleep(Calculations.random(100, 2000));
				int a = Calculations.random(1, 4);
				if (a == 1) {
					yaw = Calculations.random(0, 2000);
					pitch = Calculations.random(128, 360);
					getCamera().mouseRotateTo(yaw, pitch);
				}
			}
		}

		return Calculations.random(100, 110);
	}

	public void onMessage(Message m) {
		if (m.getMessage().contains("You get some logs.") || m.getMessage().contains("You manage to mine some tin.") || m.getMessage().contains("You catch some shrimps.")) {
		doWhatTimer++;
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
		doWhatTimerString = Integer.toString(doWhatTimer);
		g.drawString("Timer:" + doWhatTimerString, 23, 77);
		if (doWhat == 1) {
			g.drawString("Fishing!", 23, 97);
		} else if (doWhat == 2) {
			g.drawString("Mining!", 23, 97);
		} else if (doWhat == 3) {
			g.drawString("Woodcutting!", 23, 97);
		}
	}

}
