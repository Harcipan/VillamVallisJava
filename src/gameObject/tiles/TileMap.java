package gameObject.tiles;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import gameObject.GameObject;
import graphics.GameFrame;
import graphics.texture.TextureManager;
import graphics.transform.Vec2;

/**
 * Represents a map of tiles in the game, managing the grid of tiles, their properties, and rendering logic.
 * <p>
 * Each tile can have attributes such as ground type, plant type, growth stage, and more.
 * The map can dynamically resize, and tiles can grow, be harvested, or display textures.
 * </p>
 */
public class TileMap extends GameObject {

	/**
	 * A 2D array representing the tiles in the map.
	 */
	public Tile[][] tiles;

	/**
	 * The default texture to use when no specific texture is assigned to a tile.
	 */
	private final transient Image defaultTexture;

	/**
	 * A 2D array representing the map's data (e.g., tile types as strings).
	 */
	public String[][] mapData;

	/**
	 * A list of all plant types available in the game.
	 */
	public List<Plant> plantTypes;

	/**
	 * A list of all ground types available in the game.
	 */
	public List<Ground> groundTypes;

	/**
	 * Constructs a new TileMap with the given map data.
	 * Initializes tiles and assigns their types based on the map data.
	 *
	 * @param mapData a 2D array of strings representing the initial types of tiles in the map.
	 */
	public TileMap(String[][] mapData) {
		this.mapData = mapData;
		defaultTexture = TextureManager.getTextureFromMap(new Vec2(0, 1), new Vec2(TILE_SIZE, TILE_SIZE));
		tiles = new Tile[mapData.length][mapData[0].length];
		plantTypes = new ArrayList<>();
		groundTypes = new ArrayList<>();
		for (int i = 0; i < mapData.length; i++) {
			for (int j = 0; j < mapData[i].length; j++) {
				tiles[i][j] = new Tile();
				tiles[i][j].type = mapData[i][j];
			}
		}
	}

	/**
	 * Changes the size of the tile map and updates tiles according to new map data.
	 *
	 * @param row the number of rows in the new map.
	 * @param col the number of columns in the new map.
	 * @param mapData the new map data representing tile types.
	 */
	public void changeTileMapSize(int row, int col, String[][] mapData) {
		System.out.println("Changing tile map size to " + row + "x" + col);
		this.mapData = mapData;
		Tile[][] newTiles = new Tile[row][col];
		for (int i = 0; i < mapData.length; i++) {
			for (int j = 0; j < mapData[i].length; j++) {
				newTiles[i][j] = new Tile();
				newTiles[i][j].type = mapData[i][j];
				for (Ground ground : groundTypes) {
					if (ground.name.equals(mapData[i][j])) {
						newTiles[i][j].isCultivable = ground.isCultivable;
						newTiles[i][j].growthSpeed = ground.growthSpeed;
					}
				}
			}
		}
		tiles = newTiles;
	}

	/**
	 * Adds growth to all cultivable tiles in the map.
	 * If a tile is watered, its growth is accelerated.
	 * If a tile's growth stage exceeds 4000, it becomes harvestable.
	 */
	public void addGrowthToAll() {
		for (Tile[] row : tiles) {
			for (Tile tile : row) {
				if (tile.isCultivable) {
					if (tile.growthStage > 0) {
						if (tile.isWatered) {
							tile.growthStage += tile.growthSpeed * 2;
						} else {
							tile.growthStage += tile.growthSpeed;
						}
						if (tile.growthStage > 4000) {
							tile.isHarvestable = true;
						}
					}
				}
			}
		}
	}

	/**
	 * Harvests a tile by resetting its growth stage, harvestable state, and watered state.
	 *
	 * @param x the x-coordinate of the tile to harvest.
	 * @param y the y-coordinate of the tile to harvest.
	 */
	public void harvestTile(int x, int y) {
		tiles[y][x].growthStage = 0;
		tiles[y][x].isHarvestable = false;
		tiles[y][x].isWatered = false;
	}

	/**
	 * Retrieves the tile at the specified position.
	 *
	 * @param x the x-coordinate of the tile.
	 * @param y the y-coordinate of the tile.
	 * @return the {@link Tile} at the specified position.
	 */
	public Tile getTile(int x, int y) {
		return tiles[y][x];
	}

	/**
	 * Draws the tile map, including ground textures and plants, onto the provided graphics context.
	 *
	 * @param g the {@link Graphics} object used for drawing.
	 * @param camX the x-coordinate of the camera's position.
	 * @param camY the y-coordinate of the camera's position.
	 */
	@Override
	public void draw(Graphics g, int camX, int camY) {
		int offsetX = GameFrame.getInstance().getWidth() / 2 - camX;
		int offsetY = GameFrame.getInstance().getHeight() / 2 - camY;
		for (int y = 0; y < tiles.length; y++) {
			for (int x = 0; x < tiles[y].length; x++) {
				// Draw ground
				for (Ground ground : groundTypes) {
					if (ground.name.equals(tiles[y][x].type)) {
						if (tiles[y][x].isWatered) {
							g.drawImage(ground.wateredTexture, x * TILE_SIZE + offsetX, y * TILE_SIZE + offsetY, null);
						} else {
							g.drawImage(ground.texture, x * TILE_SIZE + offsetX, y * TILE_SIZE + offsetY, null);
						}
					} else {
						g.drawImage(defaultTexture, x * TILE_SIZE + offsetX, y * TILE_SIZE + offsetY, null);
					}
				}
				// Draw plant if there is one
				for (Plant plant : plantTypes) {
					if (plant.name.equals(tiles[y][x].hasPlant)) {
						g.drawImage(plant.getPlantTexture(tiles[y][x].growthStage), x * TILE_SIZE + offsetX, y * TILE_SIZE + offsetY, null);
					}
				}
			}
		}
	}
}
