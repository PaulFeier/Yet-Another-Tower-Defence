package data;
/**
 * Enemy Cube's properties.
 * @author Paul
 *
 */
public class EnemyCube extends Enemy {

	public EnemyCube(int tileX, int tileY, TileGrid grid) {
		super(tileX, tileY, grid);
		this.setTexture("EnemyCube64");
		this.setHealth(15);
		this.setSpeed(95);
		this.setCash(10);		// dropeaza 10 lei
	}
}
