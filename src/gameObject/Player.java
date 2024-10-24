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
    Image[] playerTextures = new Image[4];
    public int money;

    public Inventory inventory;
    int prevW;
    int prevH;
    GameScene gameScene;
    
    public Player(GameScene gameScene) {
        money = 0;
        this.gameScene = gameScene;
        inventory = new Inventory();
        texture = TextureManager.loadTextures("assets/player.png");
        prevW = GameFrame.getInstance().getWidth();
        prevH = GameFrame.getInstance().getHeight();
        texture1 = TextureManager.getTextureFromMap(new Vec2(0,0),new Vec2(TILE_SIZE, TILE_SIZE));
        texture2 = TextureManager.getTextureFromMap(new Vec2(1,1),new Vec2(TILE_SIZE, TILE_SIZE));
        for(int i=0;i<4;i++)
        {
            playerTextures[i] = TextureManager.getTextureFromMap(new Vec2(i,2),new Vec2(TILE_SIZE, TILE_SIZE));
        }
    }
    
    @Override
    public void draw(Graphics g, int camX, int camY) {
    	int CenterX = GameFrame.getInstance().getWidth()/2;
    	int CenterY = GameFrame.getInstance().getHeight()/2;
        // Draw the player based on its world coordinates and the camera position
        Set<Integer> pressedKeys = gameScene.keyHandler.getPressedKeys();
        //g.drawImage(texture,CenterX+camX, CenterY+camY, TILE_SIZE, TILE_SIZE, null);
        // Draw player with animation
        if(pressedKeys.contains(KeyEvent.VK_A))
        {
            g.drawImage(playerTextures[0],CenterX+camX, CenterY+camY, TILE_SIZE, TILE_SIZE, null);
        }
        else if(pressedKeys.contains(KeyEvent.VK_D))
        {
            g.drawImage(playerTextures[1],CenterX+camX, CenterY+camY, TILE_SIZE, TILE_SIZE, null);
        }
        else if(pressedKeys.contains(KeyEvent.VK_S))
        {
            g.drawImage(playerTextures[2],CenterX+camX, CenterY+camY, TILE_SIZE, TILE_SIZE, null);
        }
        else if(pressedKeys.contains(KeyEvent.VK_W))
        {
            g.drawImage(playerTextures[3],CenterX+camX, CenterY+camY, TILE_SIZE, TILE_SIZE, null);
        }
        else
        {
            g.drawImage(playerTextures[0],CenterX+camX, CenterY+camY, TILE_SIZE, TILE_SIZE, null);
        }
        inventory.draw(g, camX, camY);
    }

    public void setMoney(int m)
    {
        money = m;
        gameScene.onMoneyChange(money);
    }
}
