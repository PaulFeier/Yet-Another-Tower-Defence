package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.QuickLoad;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.StateManager;
import helpers.StateManager.GameState;

public class MapsMenu {

	private Texture background;
	private UI mapsMenuUI;
	private boolean mouseClicked;
	
	public MapsMenu() {
		background = QuickLoad("MapsBackground");
		mapsMenuUI = new UI();
		mapsMenuUI.addButton("1", "MapButtons", 225, 265);					//arbitrar
		mapsMenuUI.addButton("2", "MapButtons", 670, 265);					//arbitrar
		mapsMenuUI.addButton("3", "MapButtons", 1110, 265);					//arbitrar
		mapsMenuUI.addButton("4", "MapButtons", 225, 450);					//arbitrar
		mapsMenuUI.addButton("5", "MapButtons", 670, 450);					//arbitrar
		mapsMenuUI.addButton("6", "MapButtons", 1110, 450);					//arbitrar
		mapsMenuUI.addButton("7", "MapButtons", 225, 655);					//arbitrar
		mapsMenuUI.addButton("8", "MapButtons", 670, 655);					//arbitrar
		mapsMenuUI.addButton("9", "MapButtons", 1110, 655);					//arbitrar
		mapsMenuUI.addButton("Custom", "MapButtonCustom", 520, 840);		//arbitrar
		this.mouseClicked = false;
	}
	
	private void updateButtons() {
		// ideea de la foru asta e urmatoarea, mapele mele se numesc Map1, Map2, Map3, ..., Map9
		// newMap1 pentru custom, caut cu if daca pozitia stringului din String[] S este la fel cu ultimu caracter
		// din numele hartii respective, si daca da, setez in acea harta sa inceapa jocul schimband gamestatul pe game
		mouseClicked = false;
		String[] S= {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
		if(Mouse.next()) {
			if (Mouse.isButtonDown(0) && !mouseClicked) {
				mouseClicked = Mouse.isButtonDown(0);
				if (mapsMenuUI.isButtonClicked("Custom")) {
						StateManager.setMap("customMap");
						StateManager.setState(GameState.GAME);
					}
				else {
					for (String i : S) {
						if (mapsMenuUI.isButtonClicked(i)) {
							StateManager.setMap("Map" + i);
							StateManager.setState(GameState.GAME);
						}
					}
				}
			}
			mouseClicked = Mouse.isButtonDown(0);
		}
	}
	
	public void update() {
		DrawQuadTex(background, 0, 0, 2048, 1024);
		mapsMenuUI.draw();
		updateButtons();
	}
}
