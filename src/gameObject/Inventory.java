package gameObject;

import graphics.GameFrame;
import graphics.texture.TextureManager;
import graphics.transform.Vec2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Inventory extends GameObject{
    public int currentTool;
    public Inventory() {
        currentTool = 0;
    }

    @Override
    public void draw(Graphics g, int camX, int camY) {
        // Draw the player based on its world coordinates and the camera position
        int width = GameFrame.getInstance().getWidth();
        int height = GameFrame.getInstance().getHeight();

        // Draw the player based on its world coordinates and the camera position
        g.drawImage(TextureManager.loadTextures("assets/inventory.png"),
                width/4+camX, height-height/10+camY, width-width/2, height/20, null);

        for(int i=0;i<6;i++)
        {
            g.drawImage(TextureManager.getTextureFromMap(new Vec2(i,1),new Vec2(TILE_SIZE, TILE_SIZE)),
                    width/4+camX+i*width/20, height-height/10+camY, width/20, height/20, null);
        }

        g.drawImage(TextureManager.loadTextures("assets/player.png"),
                width/4+camX+currentTool*width/20,height-height/10+camY, width/20, height/20, null);
        //g.fillRect(width/4+camX+currentTool*width/20, height-100+camY, width/20, width/10);
        //g.drawImage(texture, CenterX+camX, CenterY+camY, TILE_SIZE, TILE_SIZE, null);
    }
}