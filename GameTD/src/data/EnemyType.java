package data;

public enum EnemyType {
	Cube, Slime, Flame;
	
	public static String enemyName(EnemyType enemyType) {
		switch(enemyType) {
		case Cube:
			return "Cube";
		case Slime:
			return "Slime";
		case Flame:
			return "Flame";
		}
		return "";
	}
}
