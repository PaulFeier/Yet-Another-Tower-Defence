package data;

import java.util.concurrent.CopyOnWriteArrayList;

public class TowerChicken extends Tower{

	public TowerChicken(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
		super(type, startTile, enemies);
	}
	/**
	 * this overrides shoot with chicken's properties.
	 */
	@Override
	public void shoot(Enemy target) {
		super.projectiles.add(new ProjectileFireball(super.type.projectile, super.target,
				super.getX(), super.getY(), super.getWidth(), super.getHeight()));
		super.target.reduceHiddenHealth(super.type.projectile.damage);
	}
	
}
