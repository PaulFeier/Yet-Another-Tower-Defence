package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.QuickLoad;
import static helpers.Artist.TILE_SIZE;
import static helpers.Clock.Delta;

import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

import UI.UI;

public class Enemy implements Entity {
	private int width, height, currentCheckpoint, cash;
	private float speed, x, y, health, startHealth, hiddenHealth;
	public static float healths;	// concept pentru a creste viata din 2 in 2 wave-uri.
	private Texture texture, hpBackground, hpForeground, hpBorder;
	private Tile startTile;
	private boolean first, alive, reward = true;
	private TileGrid grid;
	private ArrayList<Checkpoint> checkpoints;
	private int[] directions;
	public static int contor = 0;
	private UI enemyHealth;
	
	//constructor default
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
	
	public Enemy(Texture texture, Tile startTile, TileGrid grid, int width, int height, float speed, float health, int cash) {
		
		health += contor;
		contor += 1;
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
	
	public void update() {
		// verific daca este prima daca cand clasa asta este updatata, si daca da fa nimic
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
	
	// apelata cand ultimul checkpoint este atins de un inamic
	private void endOfMazeReached() {
		Player.modifyLives(-1);
		die();
	}
	
	
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
	
	private void populateCheckpointList() {
		// adaug primul checkpoint manual bazat pe startTile
		checkpoints.add(findNextCheckpoint(startTile, directions = findNextDirection(startTile)));
		
		int counter = 0;
		boolean cont = true;
		
		while (cont) {
			int[] currentD = findNextDirection(checkpoints.get(counter).getTile());
			// verific daca urmatoare directie/checkpoint exista, se termina dupa 20 de checkpoints (arbitrar)
			if (currentD[0] == 2 || counter == 20) {
				cont = false;
			}
			else {
				checkpoints.add(findNextCheckpoint(checkpoints.get(counter).getTile(),
						directions = findNextDirection(checkpoints.get(counter).getTile())));
			}
			counter++;
		}
	}
	
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
	
	//ia damage din surse externe
	public void damage(int amount) {
		//if (WaveManager.waveNr / 10 % 10 >= 1)
			//amount += 5 * (WaveManager.waveNr / 10 % 10);
		health -= amount;
		if (health <= 0 && reward == true) {
			die();
			//System.out.println("Lei: " + Player.Cash);
			Player.modifyCash(cash);
			reward = false; // sa nu primesc mai multi bani pt. acelasi mob.
		}
	}
	
	// cand moare inamicul, ii face sa dispara textura de pe ecran
	private void die() {
		alive = false;
	}
	
	public void draw() {
		float healthPercentage = health / startHealth;
		// textura inamicului
		DrawQuadTex(texture, x, y, width, height);
		// HP textures
		DrawQuadTex(hpBackground, x, y - 16, width, 8);
		DrawQuadTex(hpForeground, x, y - 16 , TILE_SIZE * healthPercentage, 8);
		DrawQuadTex(hpBorder, x, y - 16, width, 8);
		if (getHealth() > 9 && getHealth() < 100)
			enemyHealth.drawString((int) (x + 18), (int) (y - 45), "" + (int) getHealth());
		else if (getHealth() <= 9)
			enemyHealth.drawString((int) (x + 23), (int) (y - 45), "" + (int) getHealth());
		else if (getHealth() > 99 && getHealth() < 1000)
			enemyHealth.drawString((int) (x + 11), (int) (y - 45), "" + (int) getHealth());
		else if (getHealth() > 999 && getHealth() < 10000)
			enemyHealth.drawString((int) (x + 1), (int) (y - 45), "" + (int) getHealth());
		else
			enemyHealth.drawString((int) (x), (int) (y - 45), "" + (int) getHealth());
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
