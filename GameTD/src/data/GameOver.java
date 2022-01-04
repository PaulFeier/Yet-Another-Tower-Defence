package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.QuickLoad;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.StateManager;
import helpers.StateManager.GameState;

public class GameOver {
	private Texture background;
	private UI menuUI;
	private boolean mouseClicked = false;
	
	public GameOver() {
		background = QuickLoad("GameOver");
		menuUI = new UI();
		menuUI.addButton("Back", "BackButton", 1200, 700);
	}
	
	public void updateButtons() {
		menuUI.draw();
		if(Mouse.next()) {
			if (Mouse.isButtonDown(0) && !mouseClicked) {
				mouseClicked = Mouse.isButtonDown(0);
				if (menuUI.isButtonClicked("Back"))
					StateManager.setState(GameState.MAINMENU);	
			}
			mouseClicked = Mouse.isButtonDown(0);
			}
		}
	
	public void update() {
		StateManager.resetGame();
		DrawQuadTex(background, 0, 0, 2048, 1024);
		updateButtons();
	}
}
