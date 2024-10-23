package input;

import gameObject.tiles.Tile;
import gameObject.tiles.TileMap;
import graphics.scenes.GameScene;
import graphics.transform.Vec2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class MouseHandler {
    private final GameScene gameScene;
    int tileSize = 64;
    public MouseHandler(GameScene gameScene) {
        this.gameScene = gameScene;
        tileSize = gameScene.player.getTileSize();
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        int x = mouseEvent.getX();
        int y = mouseEvent.getY();

        Vec2 clickW = gameScene.screenToCamera(x, y);

        double worldX =(clickW.x*((double) gameScene.gp.getWidth() /2)+gameScene.camera.getCameraX()*2);
        double worldY = (clickW.y*((double) gameScene.gp.getHeight() /2)-gameScene.camera.getCameraY()*2);

        int tileX = (int)Math.floor(worldX/gameScene.player.getTileSize());
        int tileY = -(int)Math.floor(worldY/gameScene.player.getTileSize())-1;

        System.out.println("Mouse clicked at: " + tileX + ", " + tileY);
        //System.out.println(gameScene.tm.getTile(tileX, tileY));
        if(mouseEvent.getButton()==MouseEvent.BUTTON1) {
            if(inBounds(new Vec2(gameScene.camera.getCameraX()*2,gameScene.camera.getCameraY()*2),
                    new Vec2(tileX*tileSize, tileY*tileSize), tileSize*2)) {
                System.out.println("In bounds");
                if(gameScene.player.inventory==1)
                {
                    try {
                        gameScene.tm.setTile(tileX, tileY, new Tile(ImageIO.read(new File("assets/wheat.png"))));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else
                {
                    try {
                        Image texture = ImageIO.read(new File("assets/dirt.png"));
                        texture= texture.getScaledInstance(gameScene.player.getTileSize(), gameScene.player.getTileSize(), Image.SCALE_FAST);
                        gameScene.tm.setTile(tileX, tileY, new Tile(texture));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
           // System.out.println(gameScene.tm.getTile(tileX, tileY));
            System.out.println("Mouse clicked at: " + tileX + ", " + tileY);

        }
    }

    //check if coordinates are in bounds
    public boolean inBounds(Vec2 point1, Vec2 point2, int radius) {
        System.out.println(point1.x+" "+point1.y);
        System.out.println(point2.x+" "+point2.y);
        return Math.sqrt(Math.pow(point1.x - point2.x, 2) + Math.pow(point1.y - point2.y, 2)) < radius;
    }
}
