package iMining_Guild;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.PaintListener;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.widgets.message.Message;


@ScriptManifest(name = "iMining Guild", author = "IcCookies", version = 1.0, description = "Mines at the mining guild.", category = Category.MINING)
public class Main extends AbstractScript implements PaintListener {

	private int yaw;
	private int pitch;
	private long startTime;

	private Area falArea = new Area(3005, 3363, 3039, 3329, 0);
	private Area mineArea = new Area(3010, 9750, 3060, 9730, 0);

	private Area upstairArea = new Area(3018, 3341, 3022, 3337, 0);
	private Area downstairArea = new Area(3020, 9741, 3023, 9738, 0);

	private Area bankArea = new Area(3011, 3358, 3015, 3355, 0);
	
	private int oreNum;
	
	GameObject currentRock;
	GameObject nextRock;
	
	@Override
	public void onStart() {
		startTime = System.currentTimeMillis();
		oreNum = 0;
		getSkillTracker().start(Skill.MINING);
	}

	@Override
	public int onLoop() {
		if (falArea.contains(getLocalPlayer())) {
			if (!getInventory().isFull()) {
				if (upstairArea.contains(getLocalPlayer())) {
					climbdown();
				} else {
					getWalking().walk(upstairArea.getRandomTile());
					sleep(Calculations.random(100, 2000));
					int a = Calculations.random(1, 4);
					if (a == 1) {
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
					int a = Calculations.random(1, 4);
					if (a == 1) {
						yaw = Calculations.random(0, 2000);
						pitch = Calculations.random(128, 360);
						getCamera().mouseRotateTo(yaw, pitch);
					}
				}
			}
		} else if (mineArea.contains(getLocalPlayer())) {
			if (!getInventory().isFull()) {
				mine();
			} else {
				if (downstairArea.contains(getLocalPlayer())) {
					climbup();
				} else {
					getWalking().walk(downstairArea.getRandomTile());
					sleep(Calculations.random(100, 2000));
					int a = Calculations.random(1, 4);
					if (a == 1) {
						yaw = Calculations.random(0, 2000);
						pitch = Calculations.random(128, 360);
						getCamera().mouseRotateTo(yaw, pitch);
					}
				}
			}
		} else {
			log("Unknown Area.");
			stop();
		}
		return Calculations.random(300, 600);
	}

	private void climbup() {
		GameObject Stair1 = getGameObjects().closest("Ladder");
		if (Stair1 != null){
			Stair1.interact("Climb-up");
			sleep(Calculations.random(1000, 2000));
		}
	}

	private void mine() {
		if (currentRock == null){
			if (nextRock == null){
				currentRock = getGameObjects().closest(7489);
			}else{
				currentRock = nextRock;
			}
		}else{
			currentRock.interact("Mine");
			sleepUntil(() -> getLocalPlayer().getAnimation() != -1, 3000);
			nextRock = getGameObjects().closest(g -> g != null && g.getID() == 7489 && !g.equals(currentRock));
			currentRock = nextRock;
			if (Calculations.random(1, 4) == 1){
			getCamera().mouseRotateToEntity(currentRock);
			}
			getMouse().move(currentRock);
			sleepUntil(() -> getLocalPlayer().getAnimation() == -1, 30000);
		}
	}

	private void bank() {
		if (getBank().isOpen()){
			getBank().depositAllExcept("Rune pickaxe");
			sleepUntil(() -> !getBank().isFull(), 5000);
			getBank().close();
		}else{
			getBank().open();
			sleep(Calculations.random(1000, 2000));
		}
	}

	private void climbdown() {
		GameObject Stair2 = getGameObjects().closest("Ladder");
		if (Stair2 != null){
			Stair2.interact("Climb-down");
			sleep(Calculations.random(1000, 2000));
		}
	}

	public void onMessage(Message m) {
		if (m.getMessage().contains("You manage to mine some coal.")){
		oreNum++;
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
		String oreNumString = Integer.toString(oreNum);
		g.drawString(oreNumString, 23, 77);
		g.drawString("Experience gained: "
				+ getSkillTracker().getGainedExperience(Skill.MINING), 23, 97);
		g.drawString("Experience gained Per Hour: "
				+ getSkillTracker().getGainedExperiencePerHour(Skill.MINING), 23, 117);
		g.drawString("FPS:" + getClient().getFPS(), 23, 137);
	}
}
