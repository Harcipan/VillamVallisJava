package gameObject.tiles;

import graphics.texture.TextureManager;
import graphics.transform.Vec2;

import java.awt.*;

import static gameObject.GameObject.TILE_SIZE;

public class Plant {
    public int growthStage;
    public boolean isWatered;
    public boolean isHarvestable;
    public String name;
    public int growthSpeed;
    public int textureYPos;
    public transient Image[] texture = new Image[5];

    public Plant() {
        //growthStage = 0;
        //isWatered = false;
        //isHarvestable = false;
        name = "plant";
        growthSpeed = 1;
        textureYPos = 0;
        for(int i=0;i<5;i++)
        {
            this.texture[i] = TextureManager.getTextureFromMap(new Vec2(i,textureYPos),new Vec2(TILE_SIZE, TILE_SIZE));
        }
    }

    public Image getPlantTexture(int growthStage) {
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
            this.texture[i] = TextureManager.getTextureFromMap(new Vec2(i,textureYPos),new Vec2(TILE_SIZE, TILE_SIZE));
        }
    }
}
