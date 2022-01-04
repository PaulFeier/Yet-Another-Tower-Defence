package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.HEIGHT;
import static helpers.Artist.QuickLoad;
import static helpers.Artist.TILE_SIZE;
import static helpers.Leveler.loadMap;
import static helpers.Leveler.saveMap;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import UI.UI.Menu;
import helpers.StateManager;
import helpers.StateManager.GameState;

public class Editor {

	private TileGrid grid;
	private int index;
	private TileType[] types;
	private UI editorUI;
	private Menu tilePickerMenu;
	private Texture menuBackground;
	public int ok = 0;

	public Editor() {
		this.grid = loadMap("customMap");
		this.index = 0;
		this.types = new TileType[3];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
		this.menuBackground = QuickLoad("menu_background_editor");
		setupUI();
	}
	
	private void setupUI() {
		editorUI = new UI();
		editorUI.createMenu("TilePicker", 1280, 115, 192, 960, 2, 0);
		tilePickerMenu = editorUI.getMenu("TilePicker");
		tilePickerMenu.quickAdd("Grass", "Grass64");
		tilePickerMenu.quickAdd("Dirt", "Dirt64");
		tilePickerMenu.quickAdd("Water", "Water64");
		tilePickerMenu.quickAdd("Back", "BackButton");
	}

	public void update() {
		draw();
		editorUI.drawString(1310, 520, "PRESS 'S'");
		editorUI.drawString(1310, 550, "TO SAVE");
		// handle mouse
		if(Mouse.next()) {
			boolean mouseClicked = Mouse.isButtonDown(0);
			if (mouseClicked) {
				if (tilePickerMenu.isButtonClicked("Grass")) {
					index = 0;
				}
				else if (tilePickerMenu.isButtonClicked("Dirt")) {
					index = 1;
				}
				else if (tilePickerMenu.isButtonClicked("Water")) {
					index = 2;
				}
				else if (tilePickerMenu.isButtonClicked("Back")) {
					StateManager.setState(GameState.MAINMENU);
				}
				else {
					setTile();
				}
			}
		}

		// Handle keyboard
		while (Keyboard.next()) {			
			if (Keyboard.getEventKey() == Keyboard.KEY_S && Keyboard.getEventKeyState())
				saveMap("customMap", grid);
			
			if (Keyboard.getEventKey() == Keyboard.KEY_L && Keyboard.getEventKeyState())
				StateManager.resetGame();
		}
	}
	
	private void draw() {
		DrawQuadTex(menuBackground, 1280, 0, 192, 960);
		grid.draw();
		editorUI.draw();
	}
	
	public void setTile() {
		grid.setTile((int) Math.floor(Mouse.getX() / TILE_SIZE), (int) Math.floor((HEIGHT - Mouse.getY() - 1) / TILE_SIZE),
				types[index]);
	}
	
}
