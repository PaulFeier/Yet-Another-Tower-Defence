package data;
/**
 * Wave properties used to help with getting the data from sql database.
 * @author Paul
 *
 */
public class WaveProperties {

	int id, enemiesPerWave, enemyHealth;
	String enemyType;
	
	public WaveProperties(int id, int enemiesPerWave, int enemyHealth, String enemyType) {
		this.id = id;
		this.enemiesPerWave = enemiesPerWave;
		this.enemyHealth = enemyHealth;
		this.enemyType = enemyType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEnemiesPerWave() {
		return enemiesPerWave;
	}

	public void setEnemiesPerWave(int enemiesPerWave) {
		this.enemiesPerWave = enemiesPerWave;
	}

	public int getEnemyHealth() {
		return enemyHealth;
	}

	public void setEnemyHealth(int enemyHealth) {
		this.enemyHealth = enemyHealth;
	}

	public String getEnemyType() {
		return enemyType;
	}

	public void setEnemyType(String enemyType) {
		this.enemyType = enemyType;
	}
	
	
	
}
