package iSpammer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.PaintListener;
import org.dreambot.api.utilities.Timer;

@ScriptManifest(name = "iSpammer", author = "IcCookies", version = 1.0, description = "This script can't stop talking. See thread for more details.", category = Category.MINING)
public class Main extends AbstractScript implements PaintListener {

	public String say = new String();
	public int interval;
	private long startTime;
	public boolean start = false;

	@Override
	public void onStart() {
		
		GUI GUI = new GUI(this);
		startTime = System.currentTimeMillis();
	}

	@Override
	public int onLoop() {
		if (start = true) {
			getKeyboard().type(say);
			sleep(interval);
		}
		return Calculations.random(100);
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
