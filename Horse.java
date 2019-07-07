package bots;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.Entity;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;

@ScriptManifest(name = "Horse Stuff", author = "IcCookies", version = 1.0, description = "Say neigh to gambling!", category = Category.MINING)
public class Horse extends AbstractScript {

	@Override
	public void onStart() {
	}

	@Override
	public int onLoop() {
		if (getInventory().contains(2524)) {
			getInventory().get(2524).interact();
			sleep(Calculations.random(200, 300));
		} else if (getInventory().contains(2520)) {
			getInventory().get(2520).interact();
			sleep(Calculations.random(200, 300));
		} else if (getInventory().contains(2526)) {
			getInventory().get(2526).interact();
			sleep(Calculations.random(200, 300));
		}

		return Calculations.random(300, 600);
	}

}
