package gameObject;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import graphics.GameFrame;

public class Player extends GameObject {
    public Image texture;
    
    int prevW;
    int prevH;
    
    public Player() {
        try {
            texture = ImageIO.read(new File("assets/player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        prevW = GameFrame.getInstance().getWidth();
        prevH = GameFrame.getInstance().getHeight();
    }
    
    @Override
    public void draw(Graphics g, int camX, int camY) {
    	int CenterX = GameFrame.getInstance().getWidth()/2;
    	int CenterY = GameFrame.getInstance().getHeight()/2;
        // Draw the player based on its world coordinates and the camera position
        g.drawImage(texture, CenterX+camX, CenterY+camY, TILE_SIZE, TILE_SIZE, null);  
    }
}
