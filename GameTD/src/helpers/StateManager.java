package helpers;

import static helpers.Leveler.loadMap;

import data.Editor;
import data.Game;
import data.GameOver;
import data.GameWon;
import data.MainMenu;
import data.MapsMenu;

public class StateManager {
	
	public static enum GameState {
		MAINMENU, GAME, EDITOR, MAPS, GAMEOVER, GAMEWON
	}
	
	public static GameState gameState = GameState.MAINMENU;
	public static MainMenu mainMenu;
	public static Game game;
	public static Editor editor;
	public static MapsMenu maps;
	public static GameOver gameOver;
	public static GameWon gameWon;
	
	public static long nextSecond = System.currentTimeMillis() + 1000;	
	public static int framesInLastSecond = 0;
	public static int framesInCurrentSecond = 0;
	public static String map = "newMap1";			// default
			
	public static void update() {
		switch(gameState) {
		case MAINMENU:
			if (mainMenu == null) {
				game = null;
				mainMenu = new MainMenu();
			}
			mainMenu.update();
			break;
		case GAME:
			if (game == null)
				game = new Game(loadMap(map));
			
			game.update();
			break;
		case EDITOR:
			if (editor == null)
				editor = new Editor();
			editor.update();
			break;
			
		case MAPS:
			if(maps == null)
				maps = new MapsMenu();
			maps.update();
			break;
			
		case GAMEOVER:
			if(gameOver == null) {
				game = null;
				gameOver = new GameOver();
			}
			gameOver.update();
			break;
			
		case GAMEWON:
			if(gameWon == null) {
				game = null;
				gameWon = new GameWon();
			}
			gameWon.update();
			break;
		}
		
		long currentTime = System.currentTimeMillis();
		if (currentTime > nextSecond) {
			nextSecond += 1000;
			framesInLastSecond = framesInCurrentSecond;
			framesInCurrentSecond = 0;
		}
		
		framesInCurrentSecond++;
		
		//System.out.println("FPS: " + framesInLastSecond);
	}
	
	public static void setState(GameState newState) {
		gameState = newState;
	}
	
	public static void resetGame() {
		Game.mapNumber = 0;
		game = null;
	}
	
	public static void setMap(String map1) {
		map = map1;
	}
}
