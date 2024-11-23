package gameObject.tiles;

import graphics.texture.TextureManager;
import graphics.transform.Vec2;

import java.awt.Image;

import static gameObject.GameObject.TILE_SIZE;

public class Tile {
    private transient Image[] texture = new Image[5];
    public int growthStage = 0;
    public boolean isWatered;
    public boolean isHarvestable;
    public boolean isCultivable;
    public int growthSpeed;
    public String type;
    public int plantTextureYPos;


    public Tile() {
        for(int i=0;i<5;i++)
        {
            this.texture[i] = TextureManager.getTextureFromMap(new Vec2(i,plantTextureYPos),new Vec2(TILE_SIZE, TILE_SIZE));
        }
        isWatered = false; isHarvestable = false; isCultivable = true;
        growthSpeed = 1; type = "ground"; plantTextureYPos = 0;
    }
    
    public Image getPlantTexture() {
        if(growthStage!=0)
        {
            if(growthStage/1000<4)
            {
                return texture[(growthStage/1000)];
            }
            else
            {
                return texture[4];
            }
        }
        return null;
    }

    public void updateTexture()
    {
        for(int i=0;i<5;i++)
        {
            this.texture[i] = TextureManager.getTextureFromMap(new Vec2(i,plantTextureYPos),new Vec2(TILE_SIZE, TILE_SIZE));
        }
    }
}

