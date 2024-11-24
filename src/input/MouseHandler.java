package input;

import gamemanager.GameLoop;
import graphics.GameFrame;
import graphics.scenes.GameScene;
import graphics.transform.Vec2;

import java.awt.event.MouseEvent;

public class MouseHandler {
    private final GameScene gameScene;
    private final GameFrame gameFrame;
    int tileSize = 64;
    public MouseHandler(GameFrame gameFrame) {
        this.gameScene = gameFrame.gameScene;
        this.gameFrame = gameFrame;
        tileSize = gameScene.player.getTileSize();
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        int x = mouseEvent.getX();
        int y = mouseEvent.getY();

        Vec2 clickW = gameFrame.screenToCamera(x, y);

        double worldX =(clickW.x*((double) gameScene.gp.getWidth() /2)+gameScene.camera.getCameraX()*2);
        double worldY = (clickW.y*((double) gameScene.gp.getHeight() /2)-gameScene.camera.getCameraY()*2);

        int tileX = (int)Math.floor(worldX/gameScene.player.getTileSize());
        int tileY = -(int)Math.floor(worldY/gameScene.player.getTileSize())-1;

        System.out.println("Mouse clicked at: " + tileX + ", " + tileY);
        //System.out.println(gameScene.tm.getTile(tileX, tileY));
        if(mouseEvent.getButton()==MouseEvent.BUTTON1) {
            if(inBounds(new Vec2(gameScene.camera.getCameraX()*2,gameScene.camera.getCameraY()*2),
                    new Vec2(tileX*tileSize, tileY*tileSize), tileSize*2) && tileX>=0 && tileY>=0 &&
                    tileX< GameLoop.tileMap.mapData.length && tileY<GameLoop.tileMap.mapData[0].length){
                System.out.println("In bounds");
                if(gameScene.player.inventory.currentTool==0&&GameLoop.tileMap.getTile(tileX, tileY).isCultivable&&
                GameLoop.tileMap.getTile(tileX,tileY).growthStage==0)
                {
                    GameLoop.tileMap.getTile(tileX, tileY).growthStage++;
                    int cT = GameScene.player.inventory.currentPlant;
                    GameLoop.tileMap.getTile(tileX, tileY).hasPlant=
                            GameLoop.tileMap.plantTypes.get(cT%GameLoop.tileMap.plantTypes.size()).name;
                    System.out.println(GameLoop.tileMap.plantTypes.get(cT%GameLoop.tileMap.plantTypes.size()).name);
                    System.out.println("plantypes size: "+GameLoop.tileMap.plantTypes.size()+" "+cT%GameLoop.tileMap.plantTypes.size()+" "+cT);
                }
                else if(gameScene.player.inventory.currentTool==2 && GameLoop.tileMap.getTile(tileX, tileY).isCultivable)
                {
                    GameLoop.tileMap.getTile(tileX, tileY).isWatered = true;
                }
                else if(GameLoop.tileMap.getTile(tileX, tileY).isHarvestable)
                {
                    GameLoop.tileMap.harvestTile(tileX,tileY);
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
