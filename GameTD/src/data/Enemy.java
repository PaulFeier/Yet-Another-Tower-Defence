package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.QuickLoad;
import static helpers.Artist.TILE_SIZE;
import static helpers.Clock.Delta;

import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

import UI.UI;
/**
 * The super class for the enemy. This has a lot of things regarding the enemy's properties.
 * @author Paul
 *
 */
public class Enemy implements Entity {
	private int width, height, currentCheckpoint, cash;
	private float speed, x, y, health, startHealth, hiddenHealth;
	private Texture texture, hpBackground, hpForeground, hpBorder;
	private Tile startTile;
	private boolean first, alive, reward = true;
	private TileGrid grid;
	private ArrayList<Checkpoint> checkpoints;
	private int[] directions;
	private UI enemyHealth;
	
	/**
	 * Default constructor
	 * @param tileX
	 * @param tileY
	 * @param grid
	 */
	public Enemy(int tileX, int tileY, TileGrid grid) {
		this.texture = QuickLoad("EnemySlime64");
		this.hpBackground = QuickLoad("HPBackground");
		this.hpForeground = QuickLoad("HPForeground");
		this.hpBorder = QuickLoad("HPBorder");
		this.startTile = grid.getTile(tileX, tileY);
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = TILE_SIZE;
		this.height = TILE_SIZE;
		this.cash = 0;
		this.grid = grid;
		this.health = 50; 
		this.startHealth = health;
		this.hiddenHealth = health;
		this.speed = 50;
		this.first = true;
		this.alive = true;
		this.checkpoints = new ArrayList<Checkpoint>();
		this.directions = new int[2];
		this.directions[0] = 0;		// [0] = X direction
		this.directions[1] = 0;		// [1] = Y direction
		directions = findNextDirection(startTile);
		this.currentCheckpoint = 0;
		populateCheckpointList();
	}
	/**
	 * Main constructor
	 * @param texture
	 * @param startTile
	 * @param grid
	 * @param width
	 * @param height
	 * @param speed
	 * @param health
	 * @param cash
	 */
	public Enemy(Texture texture, Tile startTile, TileGrid grid, int width, int height, float speed, float health, int cash) {
		
		this.texture = texture;
		this.hpBackground = QuickLoad("HPBackground");
		this.hpForeground = QuickLoad("HPForeground");
		this.hpBorder = QuickLoad("HPBorder");
		this.startTile = startTile;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = width;
		this.height = height;
		this.cash = cash;
		this.grid = grid;
		this.health = health; 
		this.startHealth = health;
		this.hiddenHealth = health;
		this.speed = speed;
		this.first = true;
		this.alive = true;
		this.checkpoints = new ArrayList<Checkpoint>();
		this.directions = new int[2];
		this.directions[0] = 0;		// [0] = X direction
		this.directions[1] = 0;		// [1] = Y direction
		directions = findNextDirection(startTile);
		this.currentCheckpoint = 0;
		populateCheckpointList();
		enemyHealth = new UI();
	}
	/**
	 * The update method. First, it verifies if it's the first time this class is updated and if so do nothing.
	 * Then checks if there are more checkpoints before going ahead. If it hasn't reached a checkpoint, continues on the
	 * direction it was already going. By the way the direction the enemy can go can only be up, right, down, left in this
	 * very order.
	 */
	public void update() {
		// verific daca este prima daca cand clasa asta este updatata, si daca da, nu face nimic
		if (first) 
			first = false;
		else {
			
			if (checkpointReached()) {
				// verific daca sunt mai multe checkpoints pana sa trec mai departe
				if (currentCheckpoint + 1 == checkpoints.size())
					endOfMazeReached();
				else	
					currentCheckpoint++;
			}
			else { 
				// daca nu e la checkpoint, continua pe directia in care se indrepta
				x += Delta() * checkpoints.get(currentCheckpoint).getxDirection() * speed;
				y += Delta() * checkpoints.get(currentCheckpoint).getyDirection() * speed;
			}
		}
	}
	
	/**
	 * This method is only called if an enemy has reached the end, or in other words, if it's reached the last checkpoint.
	 */
	private void endOfMazeReached() {
		Player.modifyLives(-1);
		die();
	}
	
	/**
	 * this method verifies if the enemy has reached a checkpoint. Needs to be 5 (arbitrary) pixels away from said checkpoint.
	 * @return true if reached, false if didn't reach.
	 */
	private boolean checkpointReached() {
		boolean reached = false;
		Tile t = checkpoints.get(currentCheckpoint).getTile();
		// verific daca poz la care a ajuns e la 5(arbitrar) pixeli distanta
		if (x > t.getX() - 5 && 
				x < t.getX() + 5 &&
				y > t.getY() - 5 && 
				y < t.getY() + 5) {
			
			reached = true;
			x = t.getX();
			y = t.getY();
		}
		return reached;
	}
	/**
	 * Add first checkpoint based on startTile. Verify if the next direction / checkpoint exists, finishes after 100 (arbitrary)
	 * checkpoints.
	 */
	private void populateCheckpointList() {
		// adaug primul checkpoint manual bazat pe startTile
		checkpoints.add(findNextCheckpoint(startTile, directions = findNextDirection(startTile)));
		
		int counter = 0;
		boolean cont = true;
		
		while (cont) {
			int[] currentD = findNextDirection(checkpoints.get(counter).getTile());
			// verific daca urmatoare directie/checkpoint exista, se termina dupa 100 de checkpoints (arbitrar)
			if (currentD[0] == 2 || counter == 100) {
				cont = false;
			}
			else {
				checkpoints.add(findNextCheckpoint(checkpoints.get(counter).getTile(),
						directions = findNextDirection(checkpoints.get(counter).getTile())));
			}
			counter++;
		}
	}
	/**
	 * Finds next checkpoint.
	 * @param s
	 * @param dir
	 * @return found checkpoint
	 */
	public Checkpoint findNextCheckpoint(Tile s, int[] dir) {
		Tile next = null;
		Checkpoint c = null;
		
		//bool care decide daca urmatorul checkpoint e gasit
		boolean found = false;
		
		//int pt a incrementa fiecare loop
		int counter = 1;
		
		while (!found) {
			if (s.getXPlace() + dir[0] * counter == grid.getTilesWide() ||
					s.getYPlace() + dir[1] * counter == grid.getTilesHigh() ||
					s.getType() != grid.getTile(s.getXPlace() + dir[0] * counter,
							s.getYPlace() + dir[1] * counter).getType()) {
				found = true;
				// scad contor cu 1 pt a gasi tile inainte de new tiletype
				counter--;
				next = grid.getTile(s.getXPlace() + dir[0] * counter,
						s.getYPlace() + dir[1] * counter);
			}
			counter++;
		}
		
		c = new Checkpoint(next, dir[0], dir[1]);
		return c;
	}
	/**
	 * finds next direction by matching the current tile type with the next tile type. if the tile the enemy that sits on
	 * is dirt and it was going right, and the tile on it's right is grass, it will look to change direction. first it changes
	 * to up, if up is also grass, then check right, right is grass, changes to down, if down is dirt, it goes down. lastly it 
	 * were the check if left is also what it needs but since it found down it goes down.
	 * @param s
	 * @return next direction.
	 */
	private int[] findNextDirection(Tile s) {		// asta va calcula in ce directie o va lua mobul
		int[] dir = new int[2];
		Tile up = grid.getTile(s.getXPlace(), s.getYPlace() - 1);
		Tile right = grid.getTile(s.getXPlace() + 1, s.getYPlace());
		Tile down = grid.getTile(s.getXPlace(), s.getYPlace() + 1);
		Tile left	 = grid.getTile(s.getXPlace() - 1, s.getYPlace());
		// verific daca tiletype-ul curent este la fel cu tiletype-ul de sus, dreapta, jos, stanga
		
		if (s.getType() == up.getType() && directions[1] != 1) {
			dir[0] = 0;
			dir[1] = -1;
		}
		else if (s.getType() == right.getType() && directions[0] != -1) {
			dir[0] = 1;
			dir[1] = 0;
		}
		else if (s.getType() == down.getType() && directions[1] != -1) {
			dir[0] = 0;
			dir[1] = 1;
		}
		else if (s.getType() == left.getType() && directions[0] != 1) {
			dir[0] = -1;
			dir[1] = 0;
		}
		else {
			dir[0] = 2;
			dir[1] = 2;
		}
		
		return dir;
	}
	
	/**
	 * this method is used to calculate the damage the enemy takes.
	 * @param amount -> if tower A has X damage, the said enemy receives X damage and it's life is modified by + (- X)
	 */
	public void damage(int amount) {
		
		health -= amount;
		if (health <= 0 && reward == true) {
			die();
			//System.out.println("Lei: " + Player.Cash);
			Player.modifyCash(cash);
			reward = false; // sa nu primesc mai multi bani pt. acelasi mob.
		}
	}
	
	/**
	 * if the enemy dies, alive variable is set to false and when update is called, it gets removed from the arraylist.
	 */
	private void die() {
		alive = false;
	}
	/**
	 * this method draws the enemy with all of it's textures and also it's health points.
	 */
	public void draw() {
		float healthPercentage = health / startHealth;
		// textura inamicului
		DrawQuadTex(texture, x, y, width, height);
		// HP textures
		DrawQuadTex(hpBackground, x, y - 16, width, 8);
		DrawQuadTex(hpForeground, x, y - 16 , TILE_SIZE * healthPercentage, 8);
		DrawQuadTex(hpBorder, x, y - 16, width, 8);
		
		int xOffset = getXOffsetByHealth(getHealth());
		enemyHealth.drawString((int) (x + xOffset), (int) (y - 45), "" + (int) getHealth());
		
//		if (getHealth() > 9 && getHealth() < 100)
//			enemyHealth.drawString((int) (x + 18), (int) (y - 45), "" + (int) getHealth());
//		else if (getHealth() <= 9)
//			enemyHealth.drawString((int) (x + 23), (int) (y - 45), "" + (int) getHealth());
//		else if (getHealth() > 99 && getHealth() < 1000)
//			enemyHealth.drawString((int) (x + 11), (int) (y - 45), "" + (int) getHealth());
//		else if (getHealth() > 999 && getHealth() < 10000)
//			enemyHealth.drawString((int) (x + 1), (int) (y - 45), "" + (int) getHealth());
//		else
//			enemyHealth.drawString((int) (x), (int) (y - 45), "" + (int) getHealth());
	
	}
	/**
	 * helps with the spacing of the health points of the enemy
	 * @param health2
	 * @return
	 */
	public static int getXOffsetByHealth(float health2) {
		if (health2 > 9 && health2 < 100)
			return 18;
		else if (health2 <= 9)
			return 23;
		else if (health2 > 99 && health2 < 1000)
			return 11;
		else if (health2 > 999 && health2 < 10000)
			return 1;
		else
			return 0;
	}

	public void reduceHiddenHealth(float amount) {
		hiddenHealth -= amount;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	public void setTexture(String textureName) {
		this.texture = QuickLoad(textureName);
	}

	public Tile getStartTile() {
		return startTile;
	}

	public void setStartTile(Tile startTile) {
		this.startTile = startTile;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}
	
	public TileGrid getTileGrid() {
		return grid;
	}
	
	public boolean isAlive() {
		return alive;
	}

	public float getHiddenHealth() {
		return hiddenHealth;
	}

	public void setHiddenHealth(float hiddenHealth) {
		this.hiddenHealth = hiddenHealth;
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}
}
