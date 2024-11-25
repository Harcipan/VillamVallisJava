package gameObject;

import gamemanager.GameLoop;
import graphics.GameFrame;
import graphics.texture.TextureManager;
import graphics.transform.Vec2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * The {@code Inventory} class represents the player's inventory in the game.
 * It includes the tools, the inventory UI, and the logic for selecting and drawing the inventory items.
 */
public class Inventory extends GameObject {

    /**
     * The currently selected tool index in the inventory.
     * The index corresponds to the tool's position in the {@code texture} array.
     */
    public int currentTool;

    /**
     * An array of preloaded textures for the tools and the tool selector.
     */
    Image[] texture = new Image[6];

    /**
     * The background texture of the inventory UI.
     */
    Image inventoryTexture;

    /**
     * The index of the currently selected plant type in the inventory.
     */
    public int currentPlant = 0;

    /**
     * The total number of available plant types. 
     * Resizes when modifying plantTypes in plant editor
     */
    public static int numberOfPlants = 2;

    /**
     * Constructs an {@code Inventory} object and preloads all required textures.
     * Textures are preloaded to minimize lag during gameplay.
     */
    public Inventory() {
        currentTool = 0;
        for (int i = 0; i < 5; i++) {
            texture[i] = TextureManager.getTextureFromMap(new Vec2(i, 1), new Vec2(TILE_SIZE, TILE_SIZE));
        }
        inventoryTexture = TextureManager.getTextureFromMap(new Vec2(2, 2), new Vec2(TILE_SIZE * 2, TILE_SIZE));
    }

    /**
     * Draws the inventory UI and its components (tools, tool selector, and inventory background).
     * The inventory is drawn at a fixed position relative to the screen.
     *
     * @param g the {@link Graphics} object used for rendering
     * @param camX the x-coordinate of the camera's position
     * @param camY the y-coordinate of the camera's position
     */
    @Override
    public void draw(Graphics g, int camX, int camY) {
        // Draw the player based on its world coordinates and the camera position
        int width = GameFrame.getInstance().getWidth();
        int height = GameFrame.getInstance().getHeight();

        // Draw the inventory background
        g.drawImage(inventoryTexture,
                (width - TILE_SIZE * 4) / 2 + camX, height - TILE_SIZE * 2 + camY, TILE_SIZE * 4, TILE_SIZE, null);

        // Draw the tools
        for (int i = 1; i < 4; i++) {
            g.drawImage(texture[i], (width - TILE_SIZE * 4) / 2 + camX + (i - 1) * TILE_SIZE, height - TILE_SIZE * 2 + camY, TILE_SIZE, TILE_SIZE, null);
        }

        // Highlight the selected tool with the tool selector
        g.drawImage(texture[0], (width - TILE_SIZE * 4) / 2 + camX + currentTool * TILE_SIZE, height - TILE_SIZE * 2 + camY, TILE_SIZE, TILE_SIZE, null);
    }
}
