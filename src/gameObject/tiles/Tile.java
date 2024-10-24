package gameObject.tiles;

import graphics.texture.TextureManager;
import graphics.transform.Vec2;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import static gameObject.GameObject.TILE_SIZE;

public class Tile {
    private transient Image[] texture = new Image[5];
    public int growthStage = 0;

    public Tile() {
        for(int i=0;i<5;i++)
        {
            this.texture[i] = TextureManager.getTextureFromMap(new Vec2(i,0),new Vec2(TILE_SIZE, TILE_SIZE));
        }
    }

    public Image getTexture() {
        if(growthStage!=0)
        {
            if(growthStage/100<4)
            {
                return texture[(growthStage/100)];
            }
            else
            {
                return texture[4];
            }
        }
        return null;
    }
}

