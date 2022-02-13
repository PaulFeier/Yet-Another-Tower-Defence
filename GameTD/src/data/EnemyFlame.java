package data;
/**
 * Enemy Flame's properties.
 * @author Paul
 *
 */
public class EnemyFlame extends Enemy {

	public EnemyFlame(int tileX, int tileY, TileGrid grid) {
		super(tileX, tileY, grid);
		this.setTexture("EnemyFlame64");
		this.setHealth(5);
		this.setSpeed(145);
		this.setCash(10);		// dropeaza 5 lei
	}
}
