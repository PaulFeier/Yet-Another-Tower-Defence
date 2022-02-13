package data;

public class ProjectileSpitball extends Projectile{

	public ProjectileSpitball(ProjectileType type, Enemy target, float x, float y, int width, int height) {
		super(type, target, x, y, width, height);
	}
	/**
	 * Overrides projDamage() in order to execute properly for the orc.
	 */
	@Override
	public void projDamage() {
		try {
			super.projDamage();
		} catch (NullPointerException e) {
			e.fillInStackTrace();
		}
	}
}
