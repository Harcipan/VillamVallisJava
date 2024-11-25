package gameObject.tiles;

import graphics.texture.TextureManager;
import graphics.transform.Vec2;

import java.awt.*;

import static gameObject.GameObject.TILE_SIZE;

/**
 * Represents the ground type in the game, including its properties such as name,
 * growth speed, and whether it is cultivable. It also manages the texture
 * representation of the ground, including its watered state.
 * Different ground types can be added in the ground editor
 *
 * <p>
 * This class provides functionality for updating the texture of the ground, which
 * changes based on the watering status.
 * </p>
 */
public class Ground {

    /**
     * The name of the ground type.
     */
    public String name;

    /**
     * The growth speed of the ground (affects plant growth on the ground).
     */
    public int growthSpeed;

    /**
     * Indicates whether the ground is cultivable (i.e., whether it can have plants grown on it).
     */
    public boolean isCultivable;

    /**
     * The vertical position of the texture in the sprite sheet.
     */
    public int textureYPos;

    /**
     * The texture representing the ground.
     */
    public transient Image texture;

    /**
     * The texture representing the watered state of the ground.
     */
    public transient Image wateredTexture;

    /**
     * Default constructor for the {@link Ground} class.
     * Initializes the ground with default values such as "ground" for the name,
     * growth speed of 1, and cultivable set to true. It also loads the texture
     * based on the default settings.
     */
    public Ground() {
        name = "ground";
        growthSpeed = 1;
        textureYPos = 0;
        isCultivable = true;
        this.texture = TextureManager.getTextureFromMap(new Vec2(5, textureYPos), new Vec2(TILE_SIZE, TILE_SIZE));
        this.wateredTexture = TextureManager.getTextureFromMap(new Vec2(5, textureYPos + 1), new Vec2(TILE_SIZE, TILE_SIZE));
    }

    /**
     * Updates the texture of the ground, including both the normal texture and the watered texture.
     * This method should be called when textureYPos changes. For example after using ground editor.
     */
    public void updateTexture() {
        this.texture = TextureManager.getTextureFromMap(new Vec2(5, textureYPos), new Vec2(TILE_SIZE, TILE_SIZE));
        this.wateredTexture = TextureManager.getTextureFromMap(new Vec2(5, textureYPos + 1), new Vec2(TILE_SIZE, TILE_SIZE));
    }
}

