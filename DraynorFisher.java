package bots;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.bank.BankMode;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.widgets.message.Message;

import java.awt.*;
import java.util.Random;

@ScriptManifest(category = Category.FISHING, name = "iDraynorFish", author = "IcCookies", version = 1.0)
public class DraynorFisher extends AbstractScript {

	private int yaw;
	private int pitch;

	private boolean main = true;

	@Override
	public void onStart() {

	}

	Area fishArea = new Area(3085, 3233, 3089, 3224, 0);
	Area bankArea = new Area(3092, 3245, 3095, 3240, 0);

	@Override
	public int onLoop() {
		if (main) {
			if (getInventory().isFull()) {
				if (bankArea.contains(getLocalPlayer())) {
					bank();
				} else {
					if (!bankArea.contains(getWalking().getDestination())) {
						getWalking().walk(bankArea.getRandomTile());
						sleep(Calculations.random(100, 2000));
						Random srand = new Random();
						double chances = srand.nextDouble();
						if (chances <= 0.3) {
							yaw = Calculations.random(0, 2000);
							pitch = Calculations.random(128, 360);
							getCamera().mouseRotateTo(yaw, pitch);
						}
					}
				}
			} else {
				if (fishArea.contains(getLocalPlayer())) {
					NPC fish = getNpcs().closest("Fishing spot");
					if (fish != null && fish.hasAction("Net")) {
						fish.interact("Net");
						sleepUntil(() -> getLocalPlayer().getAnimation() != -1,
								2000);
						int what = Calculations.random(1, 3);
						int what2 = Calculations.random(1, 3);
						if (what == 1) {
							getCamera().mouseRotateTo(yaw, pitch);
							if (what2 == 1) {
								int Y = getCamera().getYaw();
								int P = getCamera().getPitch();
								sleep(Calculations.random(300, 500));
								getCamera().mouseRotateTo(
										Y + Calculations.random(10, 100),
										P + Calculations.random(10, 100));
							}
						}
						sleepUntil(() -> getLocalPlayer().getAnimation() == -1,
								Calculations.random(3000, 8000));
						int what3 = Calculations.random(1, 3);
						int what4 = Calculations.random(1, 3);
						if (what3 == 1) {
							getCamera().mouseRotateTo(yaw, pitch);
							if (what4 == 1) {
								int Y = getCamera().getYaw();
								int P = getCamera().getPitch();
								sleep(Calculations.random(300, 500));
								getCamera().mouseRotateTo(
										Y + Calculations.random(10, 100),
										P + Calculations.random(10, 100));
							}
						}
						sleepUntil(() -> getLocalPlayer().getAnimation() == -1,
								Calculations.random(20000, 30000));
					}
				} else {
					if (!fishArea.contains(getWalking().getDestination())) {
						getWalking().walk(fishArea.getRandomTile());
						sleep(Calculations.random(100, 2000));
						Random srand = new Random();
						double chances = srand.nextDouble();
						if (chances <= 0.3) {
							yaw = Calculations.random(0, 2000);
							pitch = Calculations.random(128, 360);
							getCamera().mouseRotateTo(yaw, pitch);
						}
					}
				}
			}
		} else {
			if (bankArea.contains(getLocalPlayer())) {
				getBank().open();
				sleepUntil(() -> getBank().isOpen(), 3000);
				getBank().depositAll("Raw shrimps");
				getBank().depositAll("Raw anchovies");
				getBank().setWithdrawMode(BankMode.NOTE);
				getBank().withdrawAll("Raw anchovies");
				getBank().close();
				getTrade().tradeWithPlayer("johnny81");
				sleepUntil(() -> getTrade().isOpen(), 5000);
				getTrade().addItem("Raw anchovies",
						getInventory().count("Raw anchovies"));
				getTrade().acceptTrade();
				sleepUntil(() -> getTrade().isOpen(2), 5000);
				getTrade().acceptTrade();
				if (!getInventory().contains("Raw anchovies")) {
					main = true;
				}
			} else {
				if (!bankArea.contains(getWalking().getDestination())) {
					getWalking().walk(bankArea.getRandomTile());
					sleep(Calculations.random(100, 2000));
					Random srand = new Random();
					double chances = srand.nextDouble();
					if (chances <= 0.3) {
						yaw = Calculations.random(0, 2000);
						pitch = Calculations.random(128, 360);
						getCamera().mouseRotateTo(yaw, pitch);
					}
				}
			}
		}
		return 100;
	}

	private void bank() {
		if (getBank().isOpen()) {
			getBank().depositAll("Raw shrimps");
			getBank().depositAll("Raw anchovies");
		} else {
			getBank().open();
		}
	}

	public void onMessage(Message m) {
		if (m.getMessage().contains("Duck")) {
			main = false;
		}
	}

	@Override
	public void onExit() {

	}

	@Override
	public void onPaint(Graphics graphics) {

	}

}
