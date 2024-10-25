package gameObject.tiles;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameObject.GameObject;
import graphics.GameFrame;
import graphics.texture.TextureManager;
import graphics.transform.Vec2;

public class TileMap extends GameObject{
	public final Tile[][] tiles;
	private final transient Image dirtTexture;
	private final transient Image wateredTexture;
	public int[][] mapData = {
			{0, 0, 100, 1, 0, 0, 1, 1, 0},
			{1, 1, 1, 0, 100, 0, 1, 1, 0},
			{1, 1, 1, 0, 1, 0, 1, 1, 0},
			{0, 1, 1, 1, 0, 0, 1, 1, 0},
			{1, 1, 1, 0, 1, 0, 1, 1, 0},
			{0, 0, 0, 0, 0, 0, 1, 1, 0},
			{1, 1, 1, 0, 1, 0, 1, 1, 0},
			{1, 1, 1, 0, 1, 0, 1, 1, 0},
			{0, 0, 0, 0, 0, 0, 1, 1, 0}
	};
    public TileMap() {
		dirtTexture = TextureManager.getTextureFromMap(new Vec2(5, 0), new Vec2(TILE_SIZE, TILE_SIZE));
		wateredTexture = TextureManager.getTextureFromMap(new Vec2(5, 1), new Vec2(TILE_SIZE, TILE_SIZE));
        tiles = new Tile[mapData.length][mapData[0].length];   
        for(int i=0;i<mapData.length;i++)
        {
        	for(int j = 0;j<mapData[i].length;j++)
        	{
				tiles[i][j] = new Tile();
				tiles[i][j].growthStage = mapData[i][j];
        	}
        }
    }

	public void addGrowthToAll(int growth)
	{
        for (Tile[] tile : tiles) {
            for (Tile value : tile) {
				if(!value.isHarvestable)
				{
					if (value.growthStage > 0) {
						if(value.isWatered)
						{
							value.growthStage += growth*2;
						}
						else
						{
							value.growthStage += growth;
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
				else {
					g.drawImage(dirtTexture, x * TILE_SIZE+offsetX, y * TILE_SIZE+offsetY, null);
				}
				g.drawImage(tiles[y][x].getTexture(), x * TILE_SIZE+offsetX, y * TILE_SIZE+offsetY, null);
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
