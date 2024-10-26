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
    Image[][] playerTextures = new Image[8][4];
    int animPhase = 0;
    int animTimer = 0;
    boolean moving;
    int lookDirection = 0;

    public int money;

    public Inventory inventory;
    int prevW;
    int prevH;
    GameScene gameScene;
    
    public Player(GameScene gameScene) {
        money = 0;
        this.gameScene = gameScene;
        inventory = new Inventory();
        prevW = GameFrame.getInstance().getWidth();
        prevH = GameFrame.getInstance().getHeight();
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<4;j++)
            {
                playerTextures[i][j] = TextureManager.getTextureFromMap(new Vec2(j,2+i),new Vec2(TILE_SIZE, TILE_SIZE));
            }
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
        moving = true;
        if(pressedKeys.contains(KeyEvent.VK_W) && pressedKeys.contains(KeyEvent.VK_A))
        {
            g.drawImage(playerTextures[3][animPhase],CenterX+camX, CenterY+camY, TILE_SIZE, TILE_SIZE, null);
            lookDirection = 3;
        }
        else if(pressedKeys.contains(KeyEvent.VK_W) && pressedKeys.contains(KeyEvent.VK_D))
        {
            g.drawImage(playerTextures[5][animPhase],CenterX+camX, CenterY+camY, TILE_SIZE, TILE_SIZE, null);
            lookDirection = 5;
        }
        else if(pressedKeys.contains(KeyEvent.VK_S) && pressedKeys.contains(KeyEvent.VK_A))
        {
            g.drawImage(playerTextures[1][animPhase],CenterX+camX, CenterY+camY, TILE_SIZE, TILE_SIZE, null);
            lookDirection = 1;
        }
        else if(pressedKeys.contains(KeyEvent.VK_S) && pressedKeys.contains(KeyEvent.VK_D))
        {
            g.drawImage(playerTextures[7][animPhase],CenterX+camX, CenterY+camY, TILE_SIZE, TILE_SIZE, null);
            lookDirection = 7;
        }
        else
        if(pressedKeys.contains(KeyEvent.VK_S))
        {
            g.drawImage(playerTextures[0][animPhase],CenterX+camX, CenterY+camY, TILE_SIZE, TILE_SIZE, null);
            lookDirection = 0;
        }
        else if(pressedKeys.contains(KeyEvent.VK_W))
        {
            g.drawImage(playerTextures[4][animPhase],CenterX+camX, CenterY+camY, TILE_SIZE, TILE_SIZE, null);
            lookDirection = 4;
        }
        else if(pressedKeys.contains(KeyEvent.VK_A))
        {
            g.drawImage(playerTextures[2][animPhase],CenterX+camX, CenterY+camY, TILE_SIZE, TILE_SIZE, null);
            lookDirection = 2;
        }
        else if(pressedKeys.contains(KeyEvent.VK_D))
        {
            g.drawImage(playerTextures[6][animPhase],CenterX+camX, CenterY+camY, TILE_SIZE, TILE_SIZE, null);
            lookDirection = 6;
        }
        else
        {
            moving = false;
            g.drawImage(playerTextures[lookDirection][animPhase],CenterX+camX, CenterY+camY, TILE_SIZE, TILE_SIZE, null);
        }
        // Animation scheduler
        animTimer++;
        if (animTimer >= 100) {
            if(moving)
            {
                animPhase = (animPhase + 1) % 2 +2;
            }
            else
            {
                animPhase = (animPhase + 1) % 2;
            }
            animTimer = 0;
        }
        inventory.draw(g, camX, camY);
    }

    public void setMoney(int m)
    {
        money = m;
        gameScene.onMoneyChange(money);
    }
}
