package data;
/**
 * Enum for type of tiles, it's got a boolean, if it's false, you cannot place a tower there, if it's true you can.
 * @author Paul
 *
 */
public enum TileType {
	
	Grass("Grass64", true), Dirt("Dirt64", false), Water("Water64", false), NULL("Water64", false);		// daca poti pune tower, e pe true
	
	String textureName;
	boolean buildable;
	
	TileType(String textureName, boolean buildable) {
		this.textureName = textureName;
		this.buildable = buildable;
	}
}
