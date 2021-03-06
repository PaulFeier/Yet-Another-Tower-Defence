package data;

import java.util.concurrent.CopyOnWriteArrayList;

public class TowerIce extends Tower {

	public TowerIce(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
		super(type, startTile, enemies);
	}
	/**
	 * this overrides shoot with freezer's properties.
	 */
	@Override
	public void shoot(Enemy target) {
		super.projectiles.add(new ProjectileIceBall(super.type.projectile, super.target,
				super.getX(), super.getY(), super.getWidth(), super.getHeight()));
		super.target.reduceHiddenHealth(super.type.damage);
	}
}