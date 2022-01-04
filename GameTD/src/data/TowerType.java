package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;

public enum TowerType {
	
	Cannon(new Texture[] {QuickLoad("CannonBase64"), QuickLoad("TowerCannon64")}, ProjectileType.CannonBall, 2, 700, 2.5f, 20),
	Ballista(new Texture[] {QuickLoad("BallistaBase64"), QuickLoad("BallistaTop64")}, ProjectileType.Arrow, 1, 800, 1.6f, 10),
	Freezer(new Texture[] {QuickLoad("FreezeBase"), QuickLoad("FreezeTop")}, ProjectileType.IceBall, 0, 1000, 2.0f, 25),
	Wizard(new Texture[] {QuickLoad("WizardBase"), QuickLoad("WizardTop")}, ProjectileType.Fireball, 2, 1000, 2.0f, 20),
	Chicken(new Texture[] {QuickLoad("MapButtonCustom"), QuickLoad("ChickenTop")}, ProjectileType.Egg, 1, 900, 1.8f, 12),
	Orc(new Texture[] {QuickLoad("MapButtonCustom"), QuickLoad("orc")}, ProjectileType.OrcBooger, 1, 1200, 2.0f, 15);
	
	Texture[] textures;
	int damage, range, cost;
	float firingSpeed;
	ProjectileType projectile;
	
	TowerType(Texture[] textures, ProjectileType projectile, int damage, int range, float firingSpeed, int cost) {
		this.textures = textures;
		this.projectile = projectile;
		this.damage = damage;
		this.range = range;
		this.firingSpeed = firingSpeed;
		this.cost = cost;
	}
	
	public static String getName(TowerType towerType) {
		switch(towerType) {
			case Cannon:
				return "Cannon";
			case Ballista:
				return "Ballista";
			case Freezer:
				return "Freezer";
			case Wizard:
				return "Wizard";
			case Chicken:
				return "Chicken";
			case Orc:
				return "Orc";
		}
		return "";
	}
	
}
