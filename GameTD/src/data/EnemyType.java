package data;
/**
 * Used to help with easier identification of an enemy. I don't recall using it though.
 * @author Paul
 *
 */
public enum EnemyType {
	Cube, Slime, Flame, Imp;
	
	public static String enemyName(EnemyType enemyType) {
		switch(enemyType) {
		case Cube:
			return "Cube";
		case Slime:
			return "Slime";
		case Flame:
			return "Flame";
		case Imp:
			return "Imp";
		}
		return "";
	}
}
