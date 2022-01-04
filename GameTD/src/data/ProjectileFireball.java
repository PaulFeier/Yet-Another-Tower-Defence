package data;

public class ProjectileFireball extends Projectile{

	public ProjectileFireball(ProjectileType type, Enemy target, float x, float y, int width, int height) {
		super(type, target, x, y, width, height);
	}
	
	@Override
	public void projDamage() {
		try {
			super.projDamage();
		} catch (NullPointerException e) {
			e.fillInStackTrace();
		}
	}
}
