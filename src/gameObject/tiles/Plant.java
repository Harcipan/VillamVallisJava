package gameObject.tiles;

import graphics.texture.TextureManager;
import graphics.transform.Vec2;

import java.awt.*;

import static gameObject.GameObject.TILE_SIZE;

/**
 * Represents a plant in the game, including its growth stages, growth speed,
 * and texture representation for each stage.
 *
 * <p>
 * The plant grows through five stages, and the appropriate texture is displayed
 * based on its current growth stage. The class also provides methods to retrieve
 * and update these textures dynamically.
 * </p>
 */
public class Plant {

    /**
     * The name of the plant, used to identify its type.
     */
    public String name;

    /**
     * The growth speed of the plant, determining how quickly it progresses through its growth stages.
     */
    public int growthSpeed;

    /**
     * The vertical position in the texture map where the plant's textures are located.
     */
    public int textureYPos;

    /**
     * An array of textures representing the plant's appearance at each of its five growth stages.
     */
    public transient Image[] texture = new Image[5];

    /**
     * Default constructor for the {@link Plant} class.
     * Initializes the plant with default values, including a name of "plant",
     * a growth speed of 1, and loading textures for each growth stage.
     */
    public Plant() {
        name = "plant";
        growthSpeed = 1;
        textureYPos = 0;
        for (int i = 0; i < 5; i++) {
            this.texture[i] = TextureManager.getTextureFromMap(new Vec2(i, textureYPos), new Vec2(TILE_SIZE, TILE_SIZE));
        }
    }

    /**
     * Retrieves the texture for the plant based on its current growth stage.
     *
     * @param growthStage the current growth stage of the plant, expressed as an integer.
     *                    The method maps the growth stage to one of the five available textures.
     * @return the texture corresponding to the plant's growth stage, or {@code null} if the growth stage is 0.
     */
    public Image getPlantTexture(int growthStage) {
        if (growthStage != 0) {
            if (growthStage / 1000 < 4) {
                return texture[growthStage / 1000];
            } else {
                return texture[4];
            }
        }
        return null;
    }

    /**
     * Updates the textures for the plant, reloading the textures for all five growth stages.
     * This method should be called if the plant's textureYPos changes. (using plant editor)
     */
    public void updateTexture() {
        for (int i = 0; i < 5; i++) {
            this.texture[i] = TextureManager.getTextureFromMap(new Vec2(i, textureYPos), new Vec2(TILE_SIZE, TILE_SIZE));
        }
    }
}
