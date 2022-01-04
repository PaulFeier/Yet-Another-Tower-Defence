package data;

public class EnemyCube extends Enemy {

	public EnemyCube(int tileX, int tileY, TileGrid grid) {
		super(tileX, tileY, grid);
		this.setTexture("EnemyCube64");
		this.setHealth(15);
		this.setSpeed(115);
		this.setCash(10);		// dropeaza 10 lei
	}
}
