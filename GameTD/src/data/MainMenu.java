package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.HEIGHT;
import static helpers.Artist.QuickLoad;
import static helpers.Artist.WIDTH;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.StateManager;
import helpers.StateManager.GameState;

public class MainMenu {
	
	private Texture background;
	private UI menuUI;
	private boolean mouseClicked = false;
	
	public MainMenu() {
		background = QuickLoad("MainMenu");
		menuUI = new UI();
		menuUI.addButton("Play", "PlayButton", WIDTH / 2 - 148, (int) (HEIGHT * 0.53f));		//arbitrar
		menuUI.addButton("Editor", "EditorButton", WIDTH / 2 - 148, (int) (HEIGHT * 0.65f));	//arbitrar
		menuUI.addButton("Quit", "QuitButton", WIDTH / 2 - 148, (int) (HEIGHT * 0.77f));		//arbitrar
	}
	
	// Verific daca un buton este apasat de utilizator, si daca da fa o actiune
	private void updateButtons() {
		menuUI.draw();
		//menuUI.drawString(550, 850, "PRESS 'R' TO RESTART");
		if(Mouse.next()) {
			if (Mouse.isButtonDown(0) && !mouseClicked) {
				mouseClicked = Mouse.isButtonDown(0);
				if (menuUI.isButtonClicked("Play"))
					StateManager.setState(GameState.MAPS);	
				if (menuUI.isButtonClicked("Editor"))
					StateManager.setState(GameState.EDITOR);
				if (menuUI.isButtonClicked("Quit"))
					System.exit(0);
			}
			mouseClicked = Mouse.isButtonDown(0);
		}
		
		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_R && Keyboard.getEventKeyState())
				StateManager.resetGame();
		}
	}
	
	public void update() {
		if (Game.gameOver == 1) {
			Game.gameOver = 0;
			StateManager.setState(GameState.GAMEOVER);
		}
		else {
			DrawQuadTex(background, 0, 0, 2048, 1024);
			menuUI.draw();
			updateButtons();
		}
	}
}
