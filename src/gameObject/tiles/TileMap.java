package gameObject.tiles;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import gameObject.GameObject;
import graphics.GameFrame;
import graphics.texture.TextureManager;
import graphics.transform.Vec2;

public class TileMap extends GameObject{
	public final Tile[][] tiles;
	private final transient Image dirtTexture;
	private final transient Image wateredTexture;
	private final transient Image defaultTexture;
	public int[][] mapData;
	// List to store all plant types
	public List<Plant> plantTypes;
	public List<Ground> groundTypes;


    public TileMap(int[][] mapData) {
		this.mapData = mapData;
		dirtTexture = TextureManager.getTextureFromMap(new Vec2(5, 0), new Vec2(TILE_SIZE, TILE_SIZE));
		wateredTexture = TextureManager.getTextureFromMap(new Vec2(5, 1), new Vec2(TILE_SIZE, TILE_SIZE));
		defaultTexture = TextureManager.getTextureFromMap(new Vec2(0, 1), new Vec2(TILE_SIZE, TILE_SIZE));
        tiles = new Tile[mapData.length][mapData[0].length];
		plantTypes = new ArrayList<>();
		groundTypes = new ArrayList<>();
        for(int i=0;i<mapData.length;i++)
        {
        	for(int j = 0;j<mapData[i].length;j++)
        	{
				tiles[i][j] = new Tile();
				tiles[i][j].growthStage = mapData[i][j];
        	}
        }
    }

	public void addGrowthToAll()
	{
        for (Tile[] tile : tiles) {
            for (Tile value : tile) {
				if(value.isCultivable)
				{
					if (value.growthStage > 0) {
						if(value.isWatered)
						{
							value.growthStage += value.growthSpeed*2;
						}
						else
						{
							value.growthStage += value.growthSpeed;
						}

						if(value.growthStage>4000)
						{
							value.isHarvestable = true;
						}
					}
				}
            }
        }
	}

	public void harvestTile(int x, int y) {
		tiles[y][x].growthStage = 0;
		tiles[y][x].isHarvestable = false;
		tiles[y][x].isWatered = false;
	}

	public Tile getTile(int x, int y) {
		return tiles[y][x];
	}

    @Override
    public void draw(Graphics g, int camX, int camY) {
        int offsetX = GameFrame.getInstance().getWidth() / 2 - camX;
        int offsetY = GameFrame.getInstance().getHeight() / 2 - camY;
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
				if(tiles[y][x].isWatered)
				{
					g.drawImage(wateredTexture, x * TILE_SIZE+offsetX, y * TILE_SIZE+offsetY, null);
				}
				else if(tiles[y][x].type.equals("ground")) {
					g.drawImage(dirtTexture, x * TILE_SIZE+offsetX, y * TILE_SIZE+offsetY, null);
				}
				else
				{
					g.drawImage(defaultTexture, x * TILE_SIZE+offsetX, y * TILE_SIZE+offsetY, null);
				}
				// Draw plant if there is one
				//g.drawImage(tiles[y][x].getPlantTexture(), x * TILE_SIZE+offsetX, y * TILE_SIZE+offsetY, null);
				for(Plant plant : plantTypes)
				{
					if(plant.name.equals(tiles[y][x].hasPlant))
					{
						g.drawImage(plant.getPlantTexture(tiles[y][x].growthStage), x * TILE_SIZE+offsetX, y * TILE_SIZE+offsetY, null);
					}
				}
            }
        }
    }

	public int[][] getTiles()
	{
		for(int i=0;i<mapData.length;i++)
		{
			for(int j = 0;j<mapData[i].length;j++)
			{
				mapData[i][j] = tiles[i][j].growthStage;
			}
		}
		return mapData;
	}

	public void setTiles(int[][] mapData)
	{
		for(int i=0;i<mapData.length;i++)
		{
			for(int j = 0;j<mapData[i].length;j++)
			{
				tiles[i][j].growthStage = mapData[i][j];
			}
		}
	}
}
