package gameObject.tiles;

import graphics.texture.TextureManager;
import graphics.transform.Vec2;

import java.awt.Image;

import static gameObject.GameObject.TILE_SIZE;
/**
 * Represents a tile in the game, including its properties such as growth stage,
 * watering status, cultivability, and whether it is harvestable. It also manages
 * the texture representation of the tile, including its plant growth stage.
 *
 * <p>
 * The tile can hold a plant, grow over multiple stages, and can be watered and harvested.
 * </p>
 */
public class Tile {

    /**
     * An array of textures representing the different growth stages of the plant on this tile.
     * The array contains textures for the 5 possible stages of the plant's growth.
     * This is a transient field, so it will not be serialized.
     */
    private transient Image[] texture = new Image[5];

    /**
     * The current growth stage of the plant on this tile.
     * Ranges from 0 to 4, representing different stages of plant growth.
     */
    public int growthStage = 0;

    /**
     * Indicates whether the tile has been watered.
     * If true, the tile's plant grows 2 times faster.
     */
    public boolean isWatered;

    /**
     * Indicates whether the tile's plant is harvestable.
     * If true, the plant can be harvested for money.
     */
    public boolean isHarvestable;

    /**
     * Indicates whether the tile is cultivable.
     * If true, the tile can be used to grow plants.
     */
    public boolean isCultivable;

    /**
     * The growth speed of the plant on this tile,
     * which determines how quickly the plant progresses through its growth stages.
     */
    public int growthSpeed;

    /**
     * The type of the tile, such as "ground".
     */
    public String type;

    /**
     * A string representing the type of plant that may be present on this tile.
     * Default is "Plant", which can be replaced with a specific plant type.
     */
    public String hasPlant;

    /**
     * The vertical position in the texture map for the plant's texture.
     */
    public int plantTextureYPos = 0;

    /**
     * Default constructor for the {@link Tile} class.
     * Initializes the tile with default values such as a growth stage of 0,
     * watered state as false, harvestable state as false, and cultivable state as true.
     * The textures for the 5 growth stages are loaded using the texture manager.
     */
    public Tile() {
        isWatered = false;
        isHarvestable = false;
        isCultivable = true;
        growthSpeed = 1;
        type = "ground";
        hasPlant = "Plant";
    }
}
