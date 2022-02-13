package Testing;

import static helpers.Artist.CheckCollision;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import data.Enemy;
import data.Player;
import data.WaveManager;
import helpers.StateManager.GameState;

class Tests {
	/**
	 * tests modifyCash()
	 */
	@Test
	public void testingCash() {
		
		Assertions.assertEquals(true, Player.modifyCash(1));
		Assertions.assertEquals(true, Player.modifyCash(10));
		Assertions.assertEquals(true, Player.modifyCash(50));
		Assertions.assertEquals(true, Player.modifyCash(100));
		Assertions.assertEquals(true, Player.modifyCash(101));
		Assertions.assertEquals(true, Player.modifyCash(-1));
		Assertions.assertEquals(true, Player.modifyCash(-10));
		Assertions.assertEquals(true, Player.modifyCash(-50));
		Assertions.assertEquals(true, Player.modifyCash(-100));
		Assertions.assertEquals(false, Player.modifyCash(-102));
		Assertions.assertEquals(false, Player.modifyCash(-200));
		
	}
	/**
	 * tests if the player dies when lives reach 0
	 */
	@Test
	public void testingLives() {
		for (int i = 0; i < Player.Lives; i++) {
			if (i == Player.Lives - 1) {
				Assertions.assertEquals(GameState.GAMEOVER, Player.Lives <= 0);
			}
		}
	}
	/**
	 * tests modifyCash() #2
	 */
	@Test
	public void TestingCash2() {
		Player.Cash = 500;
		Assertions.assertEquals(false, Player.modifyCash(-501));
		Assertions.assertEquals(true, Player.modifyCash(-499));
	}
	
	/**
	 * tests if the player can buy a tower
	 */
	@Test
	public void TowerTest() {
		Player.Cash = 100;
		Enemy[] e = new Enemy[2];
		Player.waveManager = new WaveManager(e, 2);
		Assertions.assertEquals(Player.Cash, 105);
	}
	/**
	 * tests the spacing on the health above the enemy health bar.
	 */
	@Test
	public void TestGetXOffsetByHealth() {
		float x = 10;
		Assertions.assertEquals(18, Enemy.getXOffsetByHealth(x));
		x = 7;
		Assertions.assertEquals(23, Enemy.getXOffsetByHealth(x));
		x = 101;
		Assertions.assertEquals(11, Enemy.getXOffsetByHealth(x));
		x = 1000;
		Assertions.assertEquals(1, Enemy.getXOffsetByHealth(x));
		x = 10000;
		Assertions.assertEquals(0, Enemy.getXOffsetByHealth(x));
	}
	
	/**
	 * tests checking collision.
	 */
	@Test
	public void TestCheckCollision() {
		float x1, y1, width1, height1, x2, y2, width2, height2;
//		 if (x1 + width1 > x2 && x1 < x2 + width2 && y1 + height1 > y2 && y1 < y2 + height2) 
//				return true;
//			return false;
		x1 = width1 = x2 = width2 = y1 = height1 = y2 = height2 = 1;
		Assertions.assertEquals(true, CheckCollision(x1, y1, width1, height1, x2, y2, width2, height2));
		x1 = width1 = width2 = y1 = height1 = height2 = 1;
		x2 = y2 = 2;
		Assertions.assertEquals(false, CheckCollision(x1, y1, width1, height1, x2, y2, width2, height2));
		x1 = width1 = width2 = y1 = height1 = height2 = y2 = 1;
		x2 = 0;
		Assertions.assertEquals(false, CheckCollision(x1, y1, width1, height1, x2, y2, width2, height2));
	}
}
