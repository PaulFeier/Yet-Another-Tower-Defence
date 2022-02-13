package data;

public class Checkpoint {
	
	private Tile tile;
	private int xDirection, yDirection;
	/**
	 * Constructor for the Checkpoint Class. A checkpoint is a tile's border. The enemies can only travel on dirt tiles.
	 * If it has reached a tile which is not dirt, it will change the direction of its movement.
	 * @param tile
	 * @param xDirection
	 * @param yDirection
	 */
	public Checkpoint(Tile tile, int xDirection, int yDirection) {
		this.tile = tile;
		this.xDirection = xDirection;
		this.yDirection = yDirection;
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

	public int getxDirection() {
		return xDirection;
	}

	public void setxDirection(int xDirection) {
		this.xDirection = xDirection;
	}

	public int getyDirection() {
		return yDirection;
	}

	public void setyDirection(int yDirection) {
		this.yDirection = yDirection;
	}
}
