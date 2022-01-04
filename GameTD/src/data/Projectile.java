package data;

import static helpers.Artist.CheckCollision;
import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.TILE_SIZE;
import static helpers.Clock.Delta;

import org.newdawn.slick.opengl.Texture;

public abstract class Projectile implements Entity {
	private Texture texture;
	private float x, y, speed, xVelocity, yVelocity;
	private int damage, width, height;
	private Enemy target;
	private boolean alive;
	
	public Projectile(ProjectileType type, Enemy target, float x, float y, int width, int height) {
		this.texture = type.texture;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = type.speed;
		this.damage = type.damage;
		this.target = target;
		this.alive = true;
		this.xVelocity = 0f;
		this.yVelocity = 0f;
		calculateDirection();
	}
	
	private void calculateDirection() {
		try {
			float totalAllowedMovement = 1.0f;
			float xDistanceFromTarget = Math.abs(target.getX() - x - TILE_SIZE / 4 + TILE_SIZE / 2);
			float yDistanceFromTarget = Math.abs(target.getY() - y - TILE_SIZE / 4 + TILE_SIZE / 2);
			float totalDistanceFromTarget = xDistanceFromTarget + yDistanceFromTarget;
			float xPercentOfMovement = xDistanceFromTarget / totalDistanceFromTarget;
			xVelocity = xPercentOfMovement;
			yVelocity = totalAllowedMovement - xPercentOfMovement;
			
			// setez directia bazata pe pozitia targetului relativ fata de turn
			if (target.getX() < x)	// daca e in stanga sau dreapta il face sa fie cum trebuie
				xVelocity *= -1;	
			if (target.getY() < y)	// daca e in sus sau jos il face sa fie cum trebuie
				yVelocity *= -1;
			
		} catch (NullPointerException e) {
			e.fillInStackTrace();
		}
		
	}
	
	// da damage inamicului
	public void projDamage() {
		target.damage(damage);
		alive = false;
	}
	
	// verifica daca bulletu o atins inamnicu, si daca da, face ca bulletu sa dispara
	public void update() {
		if (alive) {
			calculateDirection();
			try {
					x += Delta() * speed * xVelocity;
					y += Delta() * speed * yVelocity;
					
					if (CheckCollision(x, y, width, height, target.getX(), target.getY(),
							target.getWidth(), target.getHeight()) == true)
						projDamage();
						
					draw();
			} catch (NullPointerException e) {
				e.fillInStackTrace();
			}
		}
		
	}

	public void draw() {
		DrawQuadTex(texture, x, y, TILE_SIZE / 2, TILE_SIZE / 2);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public Enemy getTarget() {
		return target;
	}
	
	public void setAlive(boolean status) {
		alive = status;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	
}
