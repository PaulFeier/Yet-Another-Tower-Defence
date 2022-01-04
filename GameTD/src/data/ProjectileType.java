package data;

import static helpers.Artist.QuickLoad;

import org.newdawn.slick.opengl.Texture;

public enum ProjectileType {
	
	CannonBall(QuickLoad("CannonBall64"), 2, 700),
	IceBall(QuickLoad("IceProjectile"), 0, 800),
	Arrow(QuickLoad("BallistaProjectile32"), 1, 1000),
	Fireball(QuickLoad("WizardProjectile"), 2, 1000),
	Egg(QuickLoad("Egg"), 1, 900),
	OrcBooger(QuickLoad("OrcProjectile"), 1, 1200);
	
	Texture texture;
	int damage;
	float speed;
	
	ProjectileType(Texture texture, int damage, float speed) {
		this.texture = texture;
		this.damage = damage;
		this.speed = speed;
	}
	
}
