package data;

public class EnemyFlame extends Enemy {

	public EnemyFlame(int tileX, int tileY, TileGrid grid) {
		super(tileX, tileY, grid);
		this.setTexture("EnemyFlame64");
		this.setHealth(5);
		this.setSpeed(165);
		this.setCash(10);		// dropeaza 5 lei
	}
}
