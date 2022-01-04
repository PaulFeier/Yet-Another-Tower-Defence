package Testing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import data.Player;
import helpers.StateManager.GameState;

class Tests {

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
	@Test
	public void testingLives() {
		for (int i = 0; i < Player.Lives; i++) {
			if (i == Player.Lives - 1) {
				Assertions.assertEquals(GameState.GAMEOVER, Player.Lives <= 0);
			}
		}
	}
	
	@Test
	public void GameStates() {
		
	}
	
}
