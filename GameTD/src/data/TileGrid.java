package data;

import static helpers.Artist.TILE_SIZE;

public class TileGrid {
	
	public Tile[][] map;
	private int tilesWide, tilesHigh;
	
	public TileGrid() {
		this.tilesWide = 20;	// [20][15] deoarece 1280 / TILE_SIZE = 20
		this.tilesHigh = 15;	// [20][15] deoarece 960 / TILE_SIZE = 15;
		map = new Tile[tilesWide][tilesHigh];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Grass);	// fac frameul sa fie cu totul de grass -- default
			}
		}
	}
	public TileGrid(int[][] newMap) {
		this.tilesWide = newMap[0].length;
		this.tilesHigh = newMap.length;
		map = new Tile[tilesWide][tilesHigh];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) { 
				if (newMap[j][i] == 0)
					map[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Grass);
				else if (newMap[j][i] == 1)
					map[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Dirt);
				else
					map[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Water);
			}
		}
	}
	
	public void setTile(int xCoord, int yCoord, TileType type) { 
		try {
			map[xCoord][yCoord] = new Tile(xCoord * TILE_SIZE, yCoord * TILE_SIZE, TILE_SIZE, TILE_SIZE, type);
		} catch (ArrayIndexOutOfBoundsException e) {
			e.fillInStackTrace();
		}
		
	}
	
	public Tile getTile(int xPlace, int yPlace) {
		if (xPlace < tilesWide && yPlace < tilesHigh && xPlace > -1 && yPlace > -1)
			return map[xPlace][yPlace];
		else
			return new Tile(0, 0, 0, 0, TileType.NULL);
	}
	
	public void draw() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j].draw();
			}
		}
	}
	
	public int getTilesWide() {
		return tilesWide;
	}
	public void setTilesWide(int tilesWide) {
		this.tilesWide = tilesWide;
	}
	public int getTilesHigh() {
		return tilesHigh;
	}
	public void setTilesHigh(int tilesHigh) {
		this.tilesHigh = tilesHigh;
	}
	
}
