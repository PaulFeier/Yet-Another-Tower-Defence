package data;

public class WaveManager {
	private float timeBetweenEnemies;
	private int waveNumber;
	private Enemy[] enemyTypez;
	private Wave currentWave;
	public static float waveNr;
	
	/**
	 * The constructor method for the WaveManager class
	 * @param enemyTypez -> this is an array of objects known as enemyType. There are only 4 enemy types
	 * @param timeBetweenEnemies -> this calculates at which rate the enemies should spawn. For example, this spawns them
	 * at a rate of 0.5 seconds.
	 */
	public WaveManager(Enemy[] enemyTypez, float timeBetweenEnemies) {
		this.waveNumber = 0;
		this.enemyTypez = enemyTypez;
		this.timeBetweenEnemies = timeBetweenEnemies;
		this.currentWave = null;
		newWave();
	}
	/**
	 * updates the waves and checks if the current wave is completed. In other words, if there are 0 enemies remaining in the
	 * said wave, the game should progress to the next wave.
	 */
	public void update() {
		
		if (!currentWave.isCompleted())	// daca waveul nu e gata, continua
		currentWave.update();
		else 
			newWave();
		
	}
	/**
	 * This method generates new waves as the game progresses by taking the predefined waves which 
	 * were assigned in the database. It needs the enemyType which is a String, the number of enemies per wave which is an int
	 * and the health that needs to be added as a surplus. All of these are present in the database and this method
	 * just incorporates them.
	 */
	public void newWave() {
		try {
			//System.out.println((int) WaveManager.waveNr);
			WaveProperties wp = Sql.getWaves((int) waveNr + 1);
			//System.out.println(wp.getEnemiesPerWave() + wp.getEnemyType() + wp.getId());
			currentWave = new Wave(wp.getEnemyType(), timeBetweenEnemies, wp.getEnemiesPerWave(), enemyTypez, wp.getEnemyHealth());
			Player.Cash += 5;
			waveNumber++;
			waveNr = waveNumber;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
