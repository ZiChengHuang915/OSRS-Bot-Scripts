package iRunescape;

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
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.widgets.message.Message;

@ScriptManifest(name = "iRunescape", author = "IcCookies", version = 1.0, description = "Does various skills.", category = Category.MISC)
public class Main extends AbstractScript implements PaintListener {

	private boolean mineCopper = true;
	private boolean copper1 = true;
	private boolean tin1 = true;
	
	private long startTime;
	private int yaw;
	private int pitch;
	
	private Area CopperTinArea = new Area();
	private Area VWestBankArea = new Area();

	private String Task = new String();

	@Override
	public void onStart() {
		int a = Calculations.random(1, 3);
		if (a == 1){
			Task = "Mining";
		}else if (a == 2){
			Task = "Smithing";
		}
		startTime = System.currentTimeMillis();
	}

	@Override
	public int onLoop() {
		if (Task == "Mining"){
			checkForPick();
			if (getSkills().getBoostedLevels(Skill.MINING) < 15){
				if (!getInventory().isFull()){
					if (CopperTinArea.contains(getLocalPlayer())){
						mineCopperTin();
					}else{
						getWalking().walk(CopperTinArea.getRandomTile());
						sleep(Calculations.random(100, 2000));
						int a = Calculations.random(1, 4);
						if (a == 1) {
							yaw = Calculations.random(0, 2000);
							pitch = Calculations.random(128, 360);
							getCamera().mouseRotateTo(yaw, pitch);
						}
					}
				}else{
					if (VWestBankArea.contains(getLocalPlayer())){
						bank();
					}else{
						getWalking().walk(VWestBankArea.getRandomTile());
						sleep(Calculations.random(100, 2000));
						int a = Calculations.random(1, 4);
						if (a == 1) {
							yaw = Calculations.random(0, 2000);
							pitch = Calculations.random(128, 360);
							getCamera().mouseRotateTo(yaw, pitch);
						}
					}
				}
			}else if (getSkills().getBoostedLevels(Skill.MINING) >= 15 && getSkills().getBoostedLevels(Skill.MINING) < 60){
				
			}else{
				
			}
			
		}else if (Task == "Smithing"){
			
		}
		return Calculations.random(100, 110);
	}

	private void checkForPick(){
		if (!getInventory().contains("Bronze pickaxe") || !getInventory().contains("Rune pickaxe")){
			bank();
		}
		if (getSkills().getBoostedLevels(Skill.MINING) > 40  && !getInventory().contains("Rune pickaxe")){
			bank();
		}
	}
	
	private void mineCopperTin(){
		if (mineCopper){
			GameObject Copper1 = getGameObjects().closest(7453);
			GameObject Copper2 = getGameObjects().closest(7484); 
			if (copper1){
				if (Copper1.exists() && Copper1 != null){
					Copper1.interact("Mine");
				}
				copper1 = false;
			}else{
				if (Copper2.exists() && Copper1 != null){
					Copper2.interact("Mine");
				}
				copper1 = true;
			}
		}else{
			GameObject Tin1 = getGameObjects().closest(7486);
			GameObject Tin2 = getGameObjects().closest(7485); 
			if (tin1){
				if (Tin1.exists() && Tin1 != null){
					Tin1.interact("Mine");
				}
				tin1 = false;
			}else{
				if (Tin2.exists() && Tin2 != null){
					Tin2.interact("Mine");
				}
				tin1 = true;
			}
		}
		if (Calculations.random(1, 500) == 1){
			changeTask();
		}
	}
	
	private void bank(){
		if (Task == "Mining"){
			if (getSkills().getBoostedLevels(Skill.MINING) < 41 && !getInventory().contains("Bronze pickaxe")){
				getBank().depositAllItems();
				getBank().withdraw("Bronze pickaxe");
			}else if (getSkills().getBoostedLevels(Skill.MINING) >= 41 && !getInventory().contains("Rune pickaxe")){
				getBank().depositAllItems();
				sleep(500, 600);
				getBank().withdraw("Rune pickaxe");
			}else{
				getBank().depositAll("Copper ore");
				getBank().depositAll("Tin ore");
				getBank().depositAll("Iron ore");
				getBank().depositAll("Coal");
			}
		}else if (Task == "Smithing"){
			
		}
	}
	
	
	private void changeTask(){
		int a = Calculations.random(1, 3);
		if (a == 1){
			Task = "Mining";
		}else if (a == 2){
			Task = "Mining";
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
