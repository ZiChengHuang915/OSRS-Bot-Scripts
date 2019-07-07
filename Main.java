package bots;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;

import java.awt.*;

/**
 * Modified by IcCookies on 2015 October 17 12:34pm
 */
@ScriptManifest(category = Category.WOODCUTTING, name = "IcCookies' Woodcutter", author = "IcCookies", version = 1.11)
public class Main extends AbstractScript {

	Area bankArea = new Area(3092, 3494, 3094, 3489, 0);
	Area treeArea = new Area(3041, 3453, 3066, 3433, 0);

	@Override
	public void onStart() {
		log("Welcome to IcCookies' private script!");
	}

	@Override
	public int onLoop() {
		/**
		 * Chopping trees
		 */
		if (!getInventory().isFull()) {
			if (treeArea.contains(getLocalPlayer())) {
				chopTree("Evergreen"); // change "Tree" to the name of your
										// tree.
			} else {
				if (getWalking().walk(treeArea.getRandomTile())) {
					sleep(Calculations.random(3000, 5500));
				}
			}
		}

		/**
		 * Banking
		 */

		if (getInventory().isFull()) { // it is time to bank
			if (bankArea.contains(getLocalPlayer())) {
				bank();
			} else {
				if (getWalking().walk(bankArea.getRandomTile())) {
					sleep(Calculations.random(3000, 6000));
				}
			}
		}

		return 600;
	}

	@Override
	public void onExit() {
		log("Thank you for using this script.");
	}

	@Override
	public void onPaint(Graphics graphics) {

	}

	private void chopTree(String nameOfTree) {
		GameObject Evergreen = getGameObjects().closest(
				gameObject -> gameObject != null && gameObject.getName().equals(nameOfTree));
		if (Evergreen != null && Evergreen.interact("Chop down")) {
			int countLog = getInventory().count("Logs");
			sleepUntil(() -> getInventory().count("Logs") > countLog, 6000);

		}
	}

	private void bank() {
		NPC Banker = getNpcs().closest(
				npc -> npc != null && npc.hasAction("Bank"));
		if (Banker != null && Banker.interact("Bank")) {
			if (sleepUntil(() -> getBank().isOpen(), 5000)) {
				if (getBank().depositAllExcept(
						item -> item != null && item.getName().contains("axe"))) {
					if (sleepUntil(() -> !getInventory().isFull(), 5000)) {
						if (getBank().close()) {
							sleepUntil(() -> !getBank().isOpen(), 5000);
						}
					}
				}
			}
		}
	}

}//gameObject.getName().equals(nameOfTree)) you'd have gameObject.getID() == idOfTree
