package bots;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;

import java.awt.*;

@ScriptManifest(category = Category.FISHING, name = "IcCookies' Lobster Fisher", author = "IcCookies", version = 1.0)
public class Fisher extends AbstractScript {

	@Override
	public void onStart() {
		log("Welcome to IcCookies' private script!");
	}

	Area fishArea = new Area(2924, 3180, 2925, 3175, 0);
	Area sailArea = new Area(3021, 3223, 3029, 3212, 0);
	Area shipTile = new Area(2956, 3143, 2956, 3143, 1);
	Area islandArea = new Area(2956, 3146, 2956, 3146, 0);
	Area one = new Area(2938, 3146, 2938, 3146, 0);
	Area two = new Area(2923, 3149, 2923, 3149, 0);
	Area three = new Area(2920, 3165, 2920, 3165, 0);

	// Add shiptile2956, 3143, 1 is the ship position
	@Override
	public int onLoop() {

		if (!getInventory().isFull()) {
			if (sailArea.contains(getLocalPlayer())) {
				NPC Seamman_Lorris = getNpcs().closest(
						npc -> npc != null && npc.hasAction("Talk-to"));
				if (Seamman_Lorris != null
						&& Seamman_Lorris.interact("Pay-fare")) {
					sleep(10000);
				}
			} else {
				if (getWalking().walk(sailArea.getRandomTile())) {
					sleep(Calculations.random(3000, 5500));
				}

			}
			if (shipTile.contains(getLocalPlayer())) {
				GameObject Gangplank = getGameObjects().closest(
						gameobject -> gameobject != null
								&& gameobject.hasAction("Cross"));
				if (Gangplank != null && Gangplank.interact("Cross")) {
					sleep(3000);
					if (islandArea.contains(getLocalPlayer())) {
						if (getWalking().walk(one.getRandomTile())) {
							sleep(Calculations.random(5000, 5526));
							if (getWalking().walk(two.getRandomTile())) {
								sleep(Calculations.random(5000, 5526));
								if (getWalking().walk(two.getRandomTile())) {
									sleep(Calculations.random(5000, 5526));
									if (getWalking().walk(fishArea.getRandomTile())) {
										sleep(Calculations.random(5000, 5526));

									}
								}

							}
						}
					}
				}
			}
		}
		return 0;
	}

	@Override
	public void onExit() {
		log("Thank you for using this script.");
	}

}