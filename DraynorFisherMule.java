package bots;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.bank.BankMode;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.NPC;

import java.awt.*;
import java.util.Random;

@ScriptManifest(category = Category.FISHING, name = "iDraynorFish Mule", author = "IcCookies", version = 1.0)
public class DraynorFisherMule extends AbstractScript {

	Area bankArea = new Area(3092, 3245, 3095, 3240, 0);

	@Override
	public void onStart() {

	}

	@Override
	public int onLoop() {
		if (bankArea.contains(getLocalPlayer())) {
			getTrade().tradeWithPlayer("ziziawesome");
			sleepUntil(() -> getTrade().isOpen(1), 10000);
			sleepUntil(() -> getTrade().contains(false, 1, "Raw anchovies"),
					10000);
			getTrade().acceptTrade();
			sleepUntil(() -> getTrade().isOpen(2), 10000);
			getTrade().acceptTrade();
		}else{
			if (!bankArea.contains(getWalking().getDestination())) {
				getWalking().walk(bankArea.getRandomTile());
				sleep(Calculations.random(100, 2000));
				}
		}
		return 100;
	}

	@Override
	public void onExit() {

	}

	@Override
	public void onPaint(Graphics graphics) {

	}

}
