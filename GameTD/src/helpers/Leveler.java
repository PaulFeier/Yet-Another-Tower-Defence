package helpers;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import data.Tile;
import data.TileGrid;
import data.TileType;

public class Leveler {
	
	public static void saveMap(String mapName, TileGrid grid) {
		String mapData = "";
		for (int i = 0; i < grid.getTilesWide(); i++) {		
			for (int j = 0; j < grid.getTilesHigh(); j++) {		
				mapData += getTileID(grid.getTile(i, j)); 
			}
		}
		
		// Le ia pe coloane si in fisier va arata 00000000112000000120120 sau cv de genu
		// 0 - grass | 1 - dirt | 2 - water
		try {
			File file = new File(mapName);
			FileWriter fw = new FileWriter(file);
			fw.write(mapData);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static TileGrid loadMap(String mapName) {
		TileGrid grid = new TileGrid();
		try {
			Scanner br = new Scanner(new File(mapName));
			String data = br.next();
			for (int i = 0; i < grid.getTilesWide(); i++) {
				for (int j = 0 ; j < grid.getTilesHigh(); j++) {
					grid.setTile(i, j, getTileType(data.substring(i * grid.getTilesHigh() + j,
							i * grid.getTilesHigh() + j + 1)));
				}
			}
			//System.out.println(data);
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return grid;
	}
	
	public static TileType getTileType(String ID) {
		TileType type = TileType.NULL;
		
		switch (ID) {
		case "0":
			type = TileType.Grass;
			break;
		case "1":
			type = TileType.Dirt;
			break;
		case "2":
			type = TileType.Water;
			break;
		case "3":
			type = TileType.NULL;
			break;
		}
		
		return type;
	}
	
	public static String getTileID(Tile t) {
		String ID = "-E-";	// cand da asta, inseamna ca e eroare
		switch (t.getType()) {
		case Grass:
			ID = "0";
			break;
		case Dirt:
			ID = "1";
			break;
		case Water:
			ID = "2";
			break;
		case NULL:
			ID = "3";
			break;
		}
		
		return ID;
	}
	
}
