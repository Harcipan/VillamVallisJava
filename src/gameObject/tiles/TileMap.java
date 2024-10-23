package gameObject.tiles;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameObject.GameObject;
import graphics.GameFrame;

public class TileMap extends GameObject{
	private Tile[][] tiles;

    public TileMap(int[][] mapData) {
        tiles = new Tile[mapData.length][mapData[0].length];   
        for(int i=0;i<mapData.length;i++)
        {
        	for(int j = 0;j<mapData[i].length;j++)
        	{
        		Image texture;
				try {
					if(mapData[i][j]==0)
					{
						texture = ImageIO.read(new File("assets/wheat.png"));
					}
					else
					{
						texture = ImageIO.read(new File("assets/dirt.png"));
					}
			        texture= texture.getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_FAST);
	        		tiles[i][j] = new Tile(texture);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
    }

	public void setTile(int x, int y, Tile tile) {
		tiles[y][x] = tile;
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
                if (tiles[y][x] != null) {
                    g.drawImage(tiles[y][x].getTexture(), x * TILE_SIZE+offsetX, y * TILE_SIZE+offsetY, null);
                }
            }
        }
    }
}
