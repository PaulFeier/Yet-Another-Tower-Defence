package data;

import static helpers.Artist.TILE_SIZE;
import static helpers.Clock.Delta;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
/**
 * main Wave class
 * @author Paul
 */
public class Wave {
	
	private float timeSinceLastSpawn, spawnTime;
	private String enemyTypes;
	private Enemy[] enemyTypez;
	private CopyOnWriteArrayList<Enemy> enemyList;
	private int enemiesPerWave, enemiesSpawned, surplusHealth;
	private boolean waveCompleted;
	public static String seconds;
	
	public Wave(String enemyTypes, float spawnTime, int enemiesPerWave, Enemy[] enemyTypez, int surplusHealth) {
		this.enemyTypes = enemyTypes;
		this.spawnTime = spawnTime;
		this.enemiesPerWave = enemiesPerWave;
		this.enemiesSpawned = 0;
		timeSinceLastSpawn = 0;
		this.surplusHealth = surplusHealth;
		this.enemyTypez = enemyTypez;
		enemyList = new CopyOnWriteArrayList<Enemy>();
		this.waveCompleted = false;
		
		spawn();
	}
	
	/**
	 * update method, suppose all enemies are dead until for loop says otherwise.
	 */
	public void update() {
		//presupune ca toti inamicii sunt morti pana cand for loop-ul spune altceva
		
		boolean allEnemiesDead = true;
		if (enemiesSpawned < enemiesPerWave) {	// verific cati enemies se genereaza
			timeSinceLastSpawn += Delta();
			if (timeSinceLastSpawn > spawnTime) {
				spawn();
				timeSinceLastSpawn = 0;
			}
		}
		
		for (Enemy e : enemyList) {
			if (e.isAlive()) {
				allEnemiesDead = false;
				e.update();
				e.draw();
			}
			else 
				enemyList.remove(e);	
		}   
		
		if (allEnemiesDead) {
			waveCompleted = true;
		}
	}
	
	/**
	 * This class spawns the type of enemies that are present in the database under the table named
	 * EnemyWaves via adding them to the enemyList.
	 */
	
	private void spawn() {
		try {
			int enemyChosen = 0;
			Random random = new Random();
			enemyChosen = random.nextInt(4);
			String[] S = {"slime", "cube", "flame", "imp"};
			float surplusHealthF = surplusHealth;
			int c = 0;
			for (String i: S) {
				if (enemyTypes.equals(i)) {
					enemyList.add(new Enemy(enemyTypez[c].getTexture(), 
						enemyTypez[c].getStartTile(), enemyTypez[c].getTileGrid(), 
							TILE_SIZE, TILE_SIZE, enemyTypez[c].getSpeed(),
								enemyTypez[c].getHealth() + surplusHealthF, enemyTypez[c].getCash()));
					enemiesSpawned++;
				}
				c++;
			}
			if (enemyTypes.equals("random")) {
				enemyList.add(new Enemy(enemyTypez[enemyChosen].getTexture(), 
						enemyTypez[enemyChosen].getStartTile(), enemyTypez[enemyChosen].getTileGrid(), 
							TILE_SIZE, TILE_SIZE, enemyTypez[enemyChosen].getSpeed(),
								enemyTypez[enemyChosen].getHealth() + surplusHealthF, 
									enemyTypez[enemyChosen].getCash()));
				enemiesSpawned++;
			}
			
		} catch (NullPointerException e) {
			e.fillInStackTrace();
		}
		
	}
	
	public boolean isCompleted() {
		return waveCompleted;
	}

	public CopyOnWriteArrayList<Enemy> getEnemyList() {
		return enemyList;
	}

	public void setEnemyList(CopyOnWriteArrayList<Enemy> enemyList) {
		this.enemyList = enemyList;
	}
	
}
