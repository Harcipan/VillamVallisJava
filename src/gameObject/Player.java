package gameObject;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import graphics.GameFrame;
import graphics.texture.TextureManager;

public class Player extends GameObject {
    public Image texture;

    public Inventory inventory;
    int prevW;
    int prevH;
    
    public Player() {
        inventory = new Inventory();
        texture = TextureManager.loadTextures("assets/player.png");
        prevW = GameFrame.getInstance().getWidth();
        prevH = GameFrame.getInstance().getHeight();
    }
    
    @Override
    public void draw(Graphics g, int camX, int camY) {
    	int CenterX = GameFrame.getInstance().getWidth()/2;
    	int CenterY = GameFrame.getInstance().getHeight()/2;
        // Draw the player based on its world coordinates and the camera position
        g.drawImage(texture, CenterX+camX, CenterY+camY, TILE_SIZE, TILE_SIZE, null);
        inventory.draw(g, camX, camY);
    }
}
