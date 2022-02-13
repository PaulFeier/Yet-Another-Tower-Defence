package data;

import static helpers.Artist.QuickLoad;

public class ProjectileIceBall extends Projectile {

	public ProjectileIceBall(ProjectileType type, Enemy target, float x, float y, int width, int height) {
		super(type, target, x, y, width, height);
	}
	/**
	 * Overrides projDamage() in order to execute properly for the freeze tower.
	 */
	@Override
	public void projDamage() {
		try {
			if (super.getTarget().getTexture() != QuickLoad("EnemyFlame64"))
				super.getTarget().setSpeed(45f);
			super.projDamage();
		} catch (NullPointerException e) {
			e.fillInStackTrace();
		}
		
	}
	
}
