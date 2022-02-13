package data;

public class ProjectileEgg extends Projectile{

	public ProjectileEgg(ProjectileType type, Enemy target, float x, float y, int width, int height) {
		super(type, target, x, y, width, height);
	}
	/**
	 * Overrides projDamage() in order to execute properly for the chicken.
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
