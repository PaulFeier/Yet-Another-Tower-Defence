package data;

public class EnemySlime extends Enemy {

	public EnemySlime(int tileX, int tileY, TileGrid grid) {
		super(tileX, tileY, grid);
		this.setTexture("EnemySlime64");
		this.setHealth(10);
		this.setSpeed(140);
		this.setCash(5);		// dropeaza 5 lei
	}
	
}
