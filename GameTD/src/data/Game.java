package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.QuickLoad;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import UI.UI.Menu;
import helpers.StateManager;
import helpers.StateManager.GameState;
public class Game {
	
	private TileGrid grid;
	private Player player;
	private WaveManager waveManager;
	private UI gameUI, backUI;
	private Menu towerPickerMenu, backButtonMenu;
	private Texture menuBackground, backButton;
	private Enemy[] enemyTypes;
	public static int mapNumber = 0, gameOver = 0, rounds1;
	
	public Game(TileGrid grid) {
		int x = 0, y = 0, ok1 = 0, ok2 = 0;
		this.grid = grid;
		enemyTypes = new Enemy[4];
		
		// ideea de la foru asta e urmatoarea, mapele mele se numesc Map1, Map2, Map3, ..., Map9
		// newMap1 pentru custom, caut cu if daca pozitia stringului din String[] S este la fel cu ultimu caracter
		// din numele hartii respective, si daca da, fac 2 foruri pentru dimensiunea gridului, a hartii,
		// care este de 20 x 15, adica de la 0 la 14 pe axa y si de la 0 la 19 pe axa x
		// si cand am dat de primu tile de dirt, de acolo spawnez inamicul.
		
		String[] S= {"1", "2", "3", "4", "5", "6", "7", "8", "9", "customMap"};
		for (String c : S) {
			if (ok2 == 1) {
				ok2 = 0;
				break;
			}
			
			
			if(c.equals("customMap") || StateManager.map.substring(StateManager.map.length() - 1, StateManager.map.length()).equals(c)) {
				for (int i = 0; i < 15; i++) {
					for (int j = 0; j < 20; j++) {
						if (i == 0 || i == 14 || j == 0 || j == 19) { 		// verific doar pt. borders
//							System.out.print(i + " " + j);
							if (grid.getTile(j, i).getType() == TileType.Dirt) {
								x = j;
								y = i;
								enemyTypes[0] = new EnemySlime(x, y, grid); 
								enemyTypes[1] = new EnemyCube(x, y, grid);
								enemyTypes[2] = new EnemyFlame(x, y, grid);
								enemyTypes[3] = new EnemyImp(x, y, grid);
								
								System.out.println(c);
								if (c.equals("customMap"))
									mapNumber = 10;
								else
									mapNumber = Integer.valueOf(c);
//								System.out.print(" -> START\n");
								ok1 = 1;
								break;
							}
//							System.out.println();
						}
					}
					if (ok1 == 1) {
						ok1 = 0;
						ok2 = 1;
						break;
					}
				}
			}
			
		}
		
		waveManager = new WaveManager(enemyTypes, 0.5f, 2);
	    player = new Player(grid, waveManager);
	    player.setup();
	    gameUI = new UI();
	    backUI = new UI();
	    setupUI();
	    this.menuBackground = QuickLoad("menu_background");
	    this.backButton = QuickLoad("BackButton");
	}
	
	private void setupUI() {
		gameUI = new UI();
		gameUI.createMenu("TowerPicker", 1280, 115, 192, 960, 2, 0);
		towerPickerMenu = gameUI.getMenu("TowerPicker");
		towerPickerMenu.quickAdd("Ballista", "BallistaFull");
		towerPickerMenu.quickAdd("Freezer", "FreezeFull");
		towerPickerMenu.quickAdd("Cannon", "CannonFull");
		towerPickerMenu.quickAdd("Wizard", "WizardFull");
		towerPickerMenu.quickAdd("Chicken", "ChickenFull");
		towerPickerMenu.quickAdd("Orc", "orc");
		backUI = new UI();
		backUI.createMenu("BackButton", 1340, 888, 128, 128, 2, 0);
		backButtonMenu = backUI.getMenu("BackButton");
		backButtonMenu.quickAdd("Back", "BackButton");
	}
	
	public void updateUI() {
		gameUI.draw();
		if(mapNumber == 10)		// pentru mapa custom
			gameUI.drawString(1310, 850, "Wave: " + waveManager.getWaveNumber()); 
		else {
			Integer rounds = 30 + 5 * (mapNumber - 1);
			rounds1 = rounds;
			gameUI.drawString(1310, 850, "Wave: " + waveManager.getWaveNumber() + "/" + rounds.toString());

			if (WaveManager.waveNr >= rounds)
			    StateManager.setState(GameState.GAMEWON);
		}
		
		gameUI.drawString(1310, 750, "Lei: " + Player.Cash);
		gameUI.drawString(1310, 800, "Lives: " + Player.Lives);
		gameUI.drawString(0, 0, "FPS: " + StateManager.framesInLastSecond);
		
		if(Mouse.next()) {
			boolean mouseClicked = Mouse.isButtonDown(0);
			if (mouseClicked) {
				if (towerPickerMenu.isButtonClicked("Ballista")) {
					player.pickTower(new TowerBallista(TowerType.Ballista, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
				}
				
				if (towerPickerMenu.isButtonClicked("Freezer")) {
					player.pickTower(new TowerIce(TowerType.Freezer, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
				}
				
				if (towerPickerMenu.isButtonClicked("Cannon")) {
					player.pickTower(new TowerCannon(TowerType.Cannon, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
				}
				
				if (towerPickerMenu.isButtonClicked("Wizard")) {
					player.pickTower(new TowerWizard(TowerType.Wizard, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
				}
				
				if (towerPickerMenu.isButtonClicked("Chicken")) {
					player.pickTower(new TowerChicken(TowerType.Chicken, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
				}
				
				if (towerPickerMenu.isButtonClicked("Orc")) {
					player.pickTower(new TowerOrc(TowerType.Orc, grid.getTile(0, 0),
							waveManager.getCurrentWave().getEnemyList()));
				}
				
				if (backButtonMenu.isButtonClicked("Back")) {
					//StateManager.resetGame();
					StateManager.setState(GameState.MAINMENU);
				}
			}
		}
	}
	
	public void update() {
		if (Player.Lives <= 0) {
			gameOver = 1;
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			StateManager.setState(GameState.GAMEOVER);
		}
		else {
			DrawQuadTex(menuBackground, 1280, 0, 192, 960);
			DrawQuadTex(backButton, 1340, 888, 64, 64);
			grid.draw();
			waveManager.update();
			player.update();
			updateUI();
		}
		
	}
}
