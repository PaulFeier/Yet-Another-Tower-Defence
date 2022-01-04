package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.DrawQuadTexRotate;
import static helpers.Artist.QuickLoad;
import static helpers.Clock.Delta;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;

public abstract class Tower implements Entity{
	
	private float x, y, timeSinceLastShot, firingSpeed, angle;
	private int width, height, range, cost;
	private static int damage;
	public Enemy target;
	private Texture[] textures;
	private CopyOnWriteArrayList<Enemy> enemies;
	private boolean targeted, mouseClicked;
	public ArrayList<Projectile> projectiles;
	public TowerType type;
	private static UI towersUI = new UI();
	public Tower displayedTower;
	private Texture towerUI, cannonUI, ballistaUI, freezerUI, wizardUI, chickenUI, orcUI;
	private UI buttonsUI = new UI();
	
	public Tower(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
		this.type = type;
		this.textures = type.textures;
		this.range = type.range;
		this.firingSpeed = type.firingSpeed;
		this.cost = type.cost;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = startTile.getWidth();
		this.height = startTile.getHeight();
		this.enemies = enemies;
		this.targeted = false;
		this.mouseClicked = false;
		this.timeSinceLastShot = 0f;
		this.projectiles = new ArrayList<Projectile>();
		this.angle = 0f;
		
		towerUI = QuickLoad("TowerUpgradeUI");
		cannonUI = QuickLoad("CannonFull");
		ballistaUI = QuickLoad("BallistaFull");
		freezerUI = QuickLoad("FreezeFull");
		wizardUI = QuickLoad("WizardFull");
		chickenUI = QuickLoad("ChickenFull");
		orcUI = QuickLoad("orc");
	}
	
	private Enemy acquireTarget() {
		Enemy closest = null;
		// distanta arbitrara, sa ajute la sortarea distantelor inamicului
		float closestDistance = 1000;
		// itereaza fiecare inamic din 'enemies' si returneaza cel mai aproape 
		for (Enemy e: enemies) {
			if (isInRange(e) && findDistance(e) < closestDistance && e.getHiddenHealth() >= 0) {
				closestDistance = findDistance(e);
				closest = e;
			}	
		}
		
		// daca un inamic exista si este returnat, 'targeted' == true
		if (closest != null)
			targeted = true;
		
		return closest;
	}
	
	private boolean isInRange(Enemy e) {
		float xDistance = Math.abs(e.getX() - x);
		float yDistance = Math.abs(e.getY() - y);
		if (xDistance < range && yDistance < range)
			return true;
		return false;
	}

	private float findDistance(Enemy e) {
		float xDistance = Math.abs(e.getX() - x);
		float yDistance = Math.abs(e.getY() - y);
		return xDistance + yDistance;
	}
	
	private float calculateAngle() {
		double angleTemp = Math.atan2(target.getY() - y, target.getX() - x);
		return (float) Math.toDegrees(angleTemp) + 90;	// aici este arbitrar, depinde de design
		
	}
	
	// subprogram abstract pentru shoot, trebuie sa fie overriden in subclase
	public abstract void shoot(Enemy target);
	
	public void shoot() {
		timeSinceLastShot = 0;
	}
	
	public void updateEnemyList(CopyOnWriteArrayList<Enemy> newList) {
		enemies = newList;
	}
	
	public void update() {
		if (!targeted || target.getHiddenHealth() <= 0) {	// daca nu este target, atunci gaseste un target
			target = acquireTarget();
		} 
		else {	// daca este targeted, calculeaza unghiul
			angle = calculateAngle();
			if (timeSinceLastShot > firingSpeed) {	// si trage daca poate
				shoot(target);
				timeSinceLastShot = 0;
			}
		}
		
		if (target == null || target.isAlive() == false)
			targeted = false;
		
		timeSinceLastShot += Delta();
		
		for (Projectile p: projectiles) 
			p.update();
		
		draw();
	}

	public void draw() {
		DrawQuadTex(textures[0], x, y, width, height);
		if (textures.length > 1)
			for (int i = 1; i < textures.length; i++)
				DrawQuadTexRotate(textures[i], x, y, width, height, angle);		// ca sa pot pune mai multe texturi pentru turnuri.
		drawDisplayedTower();
	}
	
	public static Tower getTowerAt(int x, int y) {
		for (Tower t : Player.towerList)
			if (t.getX() == x)
				if (t.getY() == y)
					return t;
		return null;
	}
	
	public void drawDisplayedTower() {
		if (displayedTower != null) {
			DrawQuadTex(towerUI, 1295, 450, 250, 167 * 2 + 200);
			towersUI.drawString(1325, 460, "" + TowerType.getName(getType()));
			//System.out.println(TowerType.getName(getType()));
			buttonsUI.addButton("Upgrade", "UpgradeButton", 1315, 627 + 50);
			towersUI.drawString(1415, 634 + 52, "" + getCost());
			
			switch(TowerType.getName(getType())) {
			case "Ballista":
				DrawQuadTex(ballistaUI, 1345, 535 + 24, 64, 64);
				updateButtons();
				break;
			case "Freezer":
				DrawQuadTex(freezerUI, 1345, 535 + 24, 64, 64);
				updateButtons();
				break;
			case "Cannon":
				DrawQuadTex(cannonUI, 1345, 535 + 24, 64, 64);
				updateButtons();
				break;
			case "Wizard":
				DrawQuadTex(wizardUI, 1345, 535 + 24, 64, 64);
				updateButtons();
				break;
			case "Chicken":
				DrawQuadTex(chickenUI, 1345, 535 + 24, 64, 64);
				updateButtons();
				break;
			case "Orc":
				DrawQuadTex(orcUI, 1345, 535 + 24, 64, 64);
				updateButtons();
				break;
			default:
				break;
			}
		}
	}
	
	public void displayTower(Tower t) {
		displayedTower = t;
	}
	
	public void resetDisplayTower() {
		displayedTower = null;
	}
	
	private void updateButtons() {
		buttonsUI.draw();
		if(Mouse.next()) {
			if (Mouse.isButtonDown(0) && !mouseClicked) {
				mouseClicked = Mouse.isButtonDown(0);
				if (buttonsUI.isButtonClicked("Upgrade") && Player.modifyCash(displayedTower.getCost() * -1)) {
					displayedTower.type.projectile.damage++;
					displayedTower.setCost(getCost() * 2);
					displayedTower.type.cost = getCost();
				}
			}
			mouseClicked = Mouse.isButtonDown(0);
		}
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
	
	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public TowerType getType() {
		return type;
	}

	public void setType(TowerType type) {
		this.type = type;
	}
	
	public static int getDamage() {
		return damage;
	}
	
}
