package input;

import gamemanager.GameLoop;
import graphics.GameFrame;
import graphics.scenes.GameScene;
import graphics.transform.Vec2;

import java.awt.event.MouseEvent;

import java.awt.event.MouseEvent;

/**
 * The MouseHandler class is responsible for handling mouse events in the game.
 * It translates mouse clicks into actions such as planting, watering, or harvesting on the game grid.
 */
public class MouseHandler {

    private final GameScene gameScene;
    private final GameFrame gameFrame;
    private int tileSize = 64;

    /**
     * Constructs a MouseHandler instance with the given GameFrame.
     * Initializes the tileSize based on the player's current tile size.
     *
     * @param gameFrame the GameFrame instance that contains the game scene.
     */
    public MouseHandler(GameFrame gameFrame) {
        this.gameScene = gameFrame.gameScene;
        this.gameFrame = gameFrame;
        this.tileSize = gameScene.player.getTileSize();
    }

    /**
     * Handles mouse click events and performs the corresponding actions based on the current tool and the clicked tile.
     * The actions include planting, watering, or harvesting depending on the game state.
     *
     * @param mouseEvent the mouse event triggered by a mouse click.
     */
    public void mouseClicked(MouseEvent mouseEvent) {
        // Get mouse position relative to the screen and convert it to world coordinates
        int x = mouseEvent.getX();
        int y = mouseEvent.getY();

        Vec2 clickW = gameFrame.screenToCamera(x, y);

        double worldX = (clickW.x * ((double) gameScene.gp.getWidth() / 2) + gameScene.camera.getCameraX() * 2);
        double worldY = (clickW.y * ((double) gameScene.gp.getHeight() / 2) - gameScene.camera.getCameraY() * 2);

        // Calculate tile coordinates based on world coordinates
        int tileX = (int) Math.floor(worldX / gameScene.player.getTileSize());
        int tileY = -(int) Math.floor(worldY / gameScene.player.getTileSize()) - 1;

        System.out.println("Mouse clicked at: " + tileX + ", " + tileY);

        // Check if left mouse button was clicked
        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
            // Ensure the click is within bounds of the map
            if (inBounds(new Vec2(gameScene.camera.getCameraX() * 2, gameScene.camera.getCameraY() * 2),
                    new Vec2(tileX * tileSize, tileY * tileSize), tileSize * 2) && tileX >= 0 && tileY >= 0 &&
                    tileX < GameLoop.tileMap.mapData.length && tileY < GameLoop.tileMap.mapData[0].length) {

                System.out.println("In bounds");

                // Handle different actions based on the current tool
                if (gameScene.player.inventory.currentTool == 0 && GameLoop.tileMap.getTile(tileX, tileY).isCultivable &&
                        GameLoop.tileMap.getTile(tileX, tileY).growthStage == 0) {
                    // Plant a seed if the current tool is a seed and the tile is cultivable
                    GameLoop.tileMap.getTile(tileX, tileY).growthStage++;
                    int cT = GameScene.player.inventory.currentPlant;
                    int numberOfPlants = GameLoop.tileMap.plantTypes.size();
                    if (numberOfPlants > 0) {
                        GameLoop.tileMap.getTile(tileX, tileY).hasPlant =
                                GameLoop.tileMap.plantTypes.get(cT % numberOfPlants).name;
                        System.out.println(GameLoop.tileMap.plantTypes.get(cT % numberOfPlants).name);
                        System.out.println("plantypes size: " + numberOfPlants + " " + cT % numberOfPlants + " " + cT);
                    }
                }
                else if (GameScene.player.inventory.currentTool == 2 && GameLoop.tileMap.getTile(tileX, tileY).isCultivable) {
                    // Water the tile if the current tool is a watering can
                    GameLoop.tileMap.getTile(tileX, tileY).isWatered = true;
                }
                else if (GameLoop.tileMap.getTile(tileX, tileY).isHarvestable && GameScene.player.inventory.currentTool == 1) {
                    // Harvest the tile if the current tool is a sickle
                    GameLoop.tileMap.harvestTile(tileX, tileY);
                    gameScene.player.setMoney(gameScene.player.money + 1);
                }
            }
        }
    }

    /**
     * Checks if the distance between two points is within the specified radius.
     *
     * @param point1 the first point to check.
     * @param point2 the second point to check.
     * @param radius the radius within which the points should be.
     * @return true if the points are within the radius, false otherwise.
     */
    public boolean inBounds(Vec2 point1, Vec2 point2, int radius) {
        System.out.println(point1.x + " " + point1.y);
        System.out.println(point2.x + " " + point2.y);
        return Math.sqrt(Math.pow(point1.x - point2.x, 2) + Math.pow(point1.y - point2.y, 2)) < radius;
    }
}
