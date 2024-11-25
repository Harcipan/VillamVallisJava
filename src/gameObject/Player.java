package gameObject;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.imageio.ImageIO;

import graphics.GameFrame;
import graphics.scenes.GameScene;
import graphics.texture.TextureManager;
import graphics.transform.Vec2;
/**
 * The {@code Player} class represents the player character in the game.
 * It handles player-specific properties such as animation, movement, inventory, and money.
 * The player is drawn based on the player's movement and the current animation state.
 */
public class Player extends GameObject {

    /**
     * A 2D array of images for the player's textures, representing different animation states.
     * The first index represents the direction (8 possible directions),
     * and the second index represents the animation frame (4 frames per direction).
     */
    Image[][] playerTextures = new Image[8][4];

    /**
     * The current animation frame of the player.
     */
    int animPhase = 0;

    /**
     * The timer for controlling the animation frame changes.
     */
    int animTimer = 0;

    /**
     * A flag indicating whether the player is moving.
     */
    boolean moving;

    /**
     * The current direction the player is facing (0-7 corresponding to the 8 directions).
     */
    int lookDirection = 0;

    /**
     * The amount of money the player has.
     */
    public int money;

    /**
     * The player's inventory.
     */
    public Inventory inventory;

    /**
     * The previous window width for detecting size changes.
     */
    int prevW;

    /**
     * The previous window height for detecting size changes.
     */
    int prevH;

    /**
     * The game scene where the player is present.
     */
    GameScene gameScene;

    /**
     * Constructs a {@code Player} object associated with the specified game scene.
     * Initializes player textures, inventory, and other properties.
     *
     * @param gameScene the game scene in which the player exists
     */
    public Player(GameScene gameScene) {
        money = 0;
        this.gameScene = gameScene;
        inventory = new Inventory();
        prevW = GameFrame.getInstance().getWidth();
        prevH = GameFrame.getInstance().getHeight();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                playerTextures[i][j] = TextureManager.getTextureFromMap(new Vec2(j, 2 + i), new Vec2(TILE_SIZE, TILE_SIZE));
            }
        }
    }

    /**
     * Draws the player character with the appropriate animation based on movement.
     * It draws the player's texture based on the current direction and animation phase.
     * The player's inventory is also drawn on top of the player.
     *
     * @param g the {@link Graphics} object used for rendering
     * @param camX the x-coordinate of the camera's position
     * @param camY the y-coordinate of the camera's position
     */
    @Override
    public void draw(Graphics g, int camX, int camY) {
        int CenterX = GameFrame.getInstance().getWidth() / 2;
        int CenterY = GameFrame.getInstance().getHeight() / 2;

        Set<Integer> pressedKeys = gameScene.keyHandler.getPressedKeys();

        // Determine which direction the player is moving and draw the appropriate texture
        moving = true;
        if (pressedKeys.contains(KeyEvent.VK_W) && pressedKeys.contains(KeyEvent.VK_A)) {
            g.drawImage(playerTextures[3][animPhase], CenterX + camX, CenterY + camY, TILE_SIZE, TILE_SIZE, null);
            lookDirection = 3;
        } else if (pressedKeys.contains(KeyEvent.VK_W) && pressedKeys.contains(KeyEvent.VK_D)) {
            g.drawImage(playerTextures[5][animPhase], CenterX + camX, CenterY + camY, TILE_SIZE, TILE_SIZE, null);
            lookDirection = 5;
        } else if (pressedKeys.contains(KeyEvent.VK_S) && pressedKeys.contains(KeyEvent.VK_A)) {
            g.drawImage(playerTextures[1][animPhase], CenterX + camX, CenterY + camY, TILE_SIZE, TILE_SIZE, null);
            lookDirection = 1;
        } else if (pressedKeys.contains(KeyEvent.VK_S) && pressedKeys.contains(KeyEvent.VK_D)) {
            g.drawImage(playerTextures[7][animPhase], CenterX + camX, CenterY + camY, TILE_SIZE, TILE_SIZE, null);
            lookDirection = 7;
        } else if (pressedKeys.contains(KeyEvent.VK_S)) {
            g.drawImage(playerTextures[0][animPhase], CenterX + camX, CenterY + camY, TILE_SIZE, TILE_SIZE, null);
            lookDirection = 0;
        } else if (pressedKeys.contains(KeyEvent.VK_W)) {
            g.drawImage(playerTextures[4][animPhase], CenterX + camX, CenterY + camY, TILE_SIZE, TILE_SIZE, null);
            lookDirection = 4;
        } else if (pressedKeys.contains(KeyEvent.VK_A)) {
            g.drawImage(playerTextures[2][animPhase], CenterX + camX, CenterY + camY, TILE_SIZE, TILE_SIZE, null);
            lookDirection = 2;
        } else if (pressedKeys.contains(KeyEvent.VK_D)) {
            g.drawImage(playerTextures[6][animPhase], CenterX + camX, CenterY + camY, TILE_SIZE, TILE_SIZE, null);
            lookDirection = 6;
        } else {
            moving = false;
            g.drawImage(playerTextures[lookDirection][animPhase], CenterX + camX, CenterY + camY, TILE_SIZE, TILE_SIZE, null);
        }

        // Animation scheduler: change animation frame based on movement
        animTimer++;
        if (animTimer >= 100) {
            if (moving) {
                animPhase = (animPhase + 1) % 2 + 2;
            } else {
                animPhase = (animPhase + 1) % 2;
            }
            animTimer = 0;
        }

        // Draw the inventory UI
        inventory.draw(g, camX, camY);
    }

    /**
     * Sets the player's money and notifies the game scene about the change.
     *
     * @param m the new amount of money the player has
     */
    public void setMoney(int m) {
        money = m;
        gameScene.onMoneyChange(money);
    }
}
