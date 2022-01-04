package data;

import static helpers.Artist.TILE_SIZE;
import static helpers.Clock.Delta;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Wave {
	
	private float timeSinceLastSpawn, spawnTime;
	private Enemy[] enemyTypes;
	private CopyOnWriteArrayList<Enemy> enemyList;
	private int enemiesPerWave, enemiesSpawned;
	private boolean waveCompleted;
	public static String seconds;
	
	public Wave(Enemy[] enemyTypes, float spawnTime, int enemiesPerWave) {
		this.enemyTypes = enemyTypes;
		this.spawnTime = spawnTime;
		this.enemiesPerWave = enemiesPerWave;
		this.enemiesSpawned = 0;
		timeSinceLastSpawn = 0;
		enemyList = new CopyOnWriteArrayList<Enemy>();
		this.waveCompleted = false;
		
		spawn();
	}
	
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
	
	private void spawn() {
		int enemyChosen = 0;
		Random random = new Random();
		enemyChosen = random.nextInt(enemyTypes.length);
		
		try {
			enemyList.add(new Enemy(enemyTypes[enemyChosen].getTexture(), enemyTypes[enemyChosen].getStartTile(),
						enemyTypes[enemyChosen].getTileGrid(), TILE_SIZE, TILE_SIZE, enemyTypes[enemyChosen].getSpeed(),
							enemyTypes[enemyChosen].getHealth(), enemyTypes[enemyChosen].getCash()));
			enemiesSpawned++;
		} catch(NullPointerException e) {
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
