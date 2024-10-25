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
                    new Vec2(tileX*tileSize, tileY*tileSize), tileSize*2) && tileX>=0 && tileY>=0 &&
                    tileX<gameScene.tm.mapData.length && tileY<gameScene.tm.mapData[0].length){
                System.out.println("In bounds");
                if(gameScene.player.inventory.currentTool==0)
                {
                    gameScene.tm.getTile(tileX, tileY).growthStage++;
                }
                else if(gameScene.player.inventory.currentTool==2)
                {
                    gameScene.tm.getTile(tileX, tileY).isWatered = true;
                }
                else if(gameScene.tm.getTile(tileX, tileY).isHarvestable)
                {
                    gameScene.tm.harvestTile(tileX,tileY);
                    gameScene.player.setMoney(gameScene.player.money+1);
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
