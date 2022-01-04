package data;

public class EnemyImp extends Enemy {

	public EnemyImp(int tileX, int tileY, TileGrid grid) {
		super(tileX, tileY, grid);
		this.setTexture("EnemyImp64");
		this.setHealth(12);
		this.setSpeed(135);
		this.setCash(8);		// dropeaza 8 lei
	}
}
