// MAIN CLASS
package data;

import static helpers.Artist.BeginSession;

import org.lwjgl.opengl.Display;

import helpers.Clock;
import helpers.StateManager;

public class Boot {
	/**
	 * This method is the main class which starts the game.
	 */
	public Boot() {
		
		BeginSession();
		while (!Display.isCloseRequested()) { // cat timp merge jocul
			Clock.Update();
			StateManager.update();
			Display.update();
			Display.sync(60);

		}
		Display.destroy(); // cand iese din while, inchide jocul
	}

	public static void main(String[] args) {
		new Boot();
	}
}
