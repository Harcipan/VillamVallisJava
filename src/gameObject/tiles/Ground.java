package gameObject.tiles;

import graphics.texture.TextureManager;
import graphics.transform.Vec2;

import java.awt.*;

import static gameObject.GameObject.TILE_SIZE;

public class Ground {

    public String name;
    public int growthSpeed;
    public boolean isCultivable;
    public int textureYPos;
    public transient Image texture;
    public transient Image wateredTexture;

    public Ground() {
        name = "ground";
        growthSpeed = 1;
        textureYPos = 0;
        isCultivable = true;
        this.texture = TextureManager.getTextureFromMap(new Vec2(5,textureYPos),new Vec2(TILE_SIZE, TILE_SIZE));
        this.wateredTexture = TextureManager.getTextureFromMap(new Vec2(5, textureYPos+1), new Vec2(TILE_SIZE, TILE_SIZE));
    }

    public void updateTexture()
    {
        this.texture = TextureManager.getTextureFromMap(new Vec2(5,textureYPos),new Vec2(TILE_SIZE, TILE_SIZE));
        this.wateredTexture = TextureManager.getTextureFromMap(new Vec2(5, textureYPos+1), new Vec2(TILE_SIZE, TILE_SIZE));
    }

}
