package data;

import static helpers.Artist.HEIGHT;
import static helpers.Artist.TILE_SIZE;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import helpers.Clock;

public class Player {

	private TileGrid grid;
	private TileType[] types;	// tipurile de tiles din joc (grass, dirt, water) cum schimb indexu, se schimba si tileu
	public static WaveManager waveManager;
	public static ArrayList<Tower> towerList;
	private Tower tempTower;
	private boolean leftMouseButtonDown, holdingTower;
	public static int Cash, Lives;
	
	public Player(TileGrid grid, WaveManager waveManager) {
		this.grid = grid;
		this.types = new TileType[3];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
		Player.waveManager = waveManager;
		towerList = new ArrayList<Tower>();
		this.leftMouseButtonDown = false;
		this.holdingTower = false;
		this.tempTower = null;
		Cash = 0;
		Lives = 0;
	}
	/**
	 * initialise cash and lives
	 */
	// initializez cash si lives values pentru jucator
	public void setup() {
		Cash = 100;
		Lives = 10;
	}
	
	/**
	 * Verifiy if the player has enough money to buy the clicked tower.
	 * @param amount -> if cash is < amount, can't afford to buy.
	 * @return true if can buy, false if can't.
	 */
	// verfic daca pot cumpara turnul respectiv
	public static boolean modifyCash(int amount) {
		if (Cash + amount >= 0) {
			Cash += amount;		// fac asa ca daca vreau sa cumpar un turn, ii pun valoarea cu - in fata
			return true;
		}
		return false;
	}
	/**
	 * When an enemy dies, the player loses lives.
	 * @param amount
	 */
	public static void modifyLives(int amount) {
		Lives += amount;	// fac asa ca atunci cand pierd viata, voi aduna cu - .
	}
	/**
	 * update method used to help with placing the towers on the grid.
	 */
	public void update() {
		
		//Updateaza holdingTower
		if (holdingTower) {
			tempTower.setX(getMouseTile().getX());
			tempTower.setY(getMouseTile().getY());
			tempTower.draw();
		}
		
		//Updatateaza toate turnurile din joc
		for (Tower t : towerList) {
	            t.update();
	            t.draw();		
	            t.updateEnemyList(waveManager.getCurrentWave().getEnemyList());
		}
		
		// handle mouse
		// cand apelez update(), intra in if-u asta si pot controla sa apas doar o singura data pe mouse
		if (Mouse.isButtonDown(0) && !leftMouseButtonDown) {	// 0 - LMB | 1 - RMB
			for (Tower t : towerList) {
			    t.resetDisplayTower();
			}
			placeTower();
		}
		
		leftMouseButtonDown = Mouse.isButtonDown(0);
		
		// Handle keyboard
		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState())
				Clock.ChangeMultiplier(0.2f);
			if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState())
				Clock.ChangeMultiplier(-0.2f);
		}
	}
	/**
	 * helps placing the tower on the map.
	 */
	private void placeTower() {
		Tile currentTile = getMouseTile();
		if (holdingTower && tempTower != null) {
			if (!currentTile.getOccupied() && modifyCash(tempTower.getCost() * -1)) {
				towerList.add(tempTower);
				currentTile.setOccupied(true);
				holdingTower = false;
				tempTower = null;
			}
		} else {
			Tower t1 = Tower.getTowerAt((int) getMouseTile().getX(), (int) getMouseTile().getY());
			if(t1 != null)
                t1.displayTower(t1);
		}
	}
	
	public void pickTower(Tower t) {
		tempTower = t;
		holdingTower = true;
	}
	
	private Tile getMouseTile() {
		return grid.getTile((int) Math.floor(Mouse.getX() / TILE_SIZE),
				(int) Math.floor((HEIGHT - Mouse.getY() - 1) / TILE_SIZE));
	}
}
