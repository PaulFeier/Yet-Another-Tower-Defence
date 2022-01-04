package data;

public class WaveManager {
	private float timeBetweenEnemies;
	private int waveNumber, enemiesPerWave;
	private Enemy[] enemyTypes;
	private Wave currentWave;
	public static float waveNr;
	
	public WaveManager(Enemy[] enemyTypes, float timeBetweenEnemies, int enemiesPerWave) {
		this.waveNumber = 0;
		this.enemiesPerWave = enemiesPerWave;
		this.enemyTypes = enemyTypes;
		this.timeBetweenEnemies = timeBetweenEnemies;
		this.currentWave = null;
		newWave();
	}
	
	public void update() {
		
		
		if (!currentWave.isCompleted())	// daca waveul nu e gata, continua
			currentWave.update();
		else {
			newWave();
			//System.out.println("Lei: " + Player.Cash);
			//System.out.println("Lives: " + Player.Lives);
		}
		
	}
	
	private void newWave() {
		currentWave = new Wave(enemyTypes, timeBetweenEnemies, enemiesPerWave + waveNumber++);
		Player.Cash += 5;
		waveNr = waveNumber;
		//System.out.println("Beginning Wave " + waveNumber);
	}

	public Wave getCurrentWave() {
		return currentWave;
	}

	public void setCurrentWave(Wave currentWave) {
		this.currentWave = currentWave;
	}

	public int getWaveNumber() {
		return waveNumber;
	}

	public void setWaveNumber(int waveNumber) {
		this.waveNumber = waveNumber;
	}

}
