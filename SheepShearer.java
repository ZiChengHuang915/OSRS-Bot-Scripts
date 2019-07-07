package bots;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.methods.map.Tile;

import java.awt.*;

/**
 * Modified by IcCookies on 2015 November 3:44pm
 */
@ScriptManifest(category = Category.MONEYMAKING, name = "IcCookies' SheepShearer", author = "IcCookies", version = 1.0)
public class SheepShearer extends AbstractScript {

	Tile BankArea = new Tile(3208, 3220, 2);
	Tile Floor3Area = new Tile(3205, 3209, 2);
	private State state;

	@Override
	public void onStart() {
		log("Welcome to IcCookies' private script!");
	}

	public enum State {

	}

	@Override
	public int onLoop() {

		if (!getInventory().isFull()) {
			if (BankArea.getTile() == getLocalPlayer().getTile()) {
				log("success");
				if (Floor3Area.distance(getLocalPlayer().getTile()) > 5) {
					if (getWalking().walk(Floor3Area)) {
						sleep(Calculations.random(3000, 5500));
					}
				}
			}
			/*
			 * if (Floor3Area.contains(getLocalPlayer())) { GameObject Staircase
			 * = getGameObjects().closest( gameObject -> gameObject != null); if
			 * (Staircase != null) { Staircase.interact("Open"); } }
			 * 
			 * }
			 */
			if (getInventory().isFull()) {

			}
		}
			return 600;
		
		}
	

	@Override
	public void onExit() {
		log("Thank you for using this script.");
	}
}