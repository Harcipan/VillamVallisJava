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

public class Player extends GameObject {
    public Image texture;
    Image texture1;
    Image texture2;

    public Inventory inventory;
    int prevW;
    int prevH;
    GameScene gameScene;
    
    public Player(GameScene gameScene) {
        this.gameScene = gameScene;
        inventory = new Inventory();
        texture = TextureManager.loadTextures("assets/player.png");
        prevW = GameFrame.getInstance().getWidth();
        prevH = GameFrame.getInstance().getHeight();
        texture1 = TextureManager.getTextureFromMap(new Vec2(0,0),new Vec2(TILE_SIZE, TILE_SIZE));
        texture2 = TextureManager.getTextureFromMap(new Vec2(1,1),new Vec2(TILE_SIZE, TILE_SIZE));
    }
    
    @Override
    public void draw(Graphics g, int camX, int camY) {
    	int CenterX = GameFrame.getInstance().getWidth()/2;
    	int CenterY = GameFrame.getInstance().getHeight()/2;
        // Draw the player based on its world coordinates and the camera position
        //Set<Integer> pressedKeys = gameScene.keyHandler.getPressedKeys();
        g.drawImage(texture,CenterX+camX, CenterY+camY, TILE_SIZE, TILE_SIZE, null);
        /*if(pressedKeys.contains(KeyEvent.VK_W))
        {
            g.drawImage(texture,CenterX+camX, CenterY+camY, TILE_SIZE, TILE_SIZE, null);
        }
        else
        {
            g.drawImage(texture2,CenterX+camX, CenterY+camY, TILE_SIZE, TILE_SIZE, null);
        }*/
        inventory.draw(g, camX, camY);
    }
}
