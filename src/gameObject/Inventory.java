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
    Image[] texture = new Image[6];
    Image inventoryTexture;

    // Note: Always preload textures, otherwise the game will lag
    public Inventory() {
        currentTool = 0;
        for(int i=0;i<5;i++)
        {
            texture[i] = TextureManager.getTextureFromMap(new Vec2(i,1),new Vec2(TILE_SIZE, TILE_SIZE));
        }
        inventoryTexture = TextureManager.loadTextures("assets/inventory.png");
    }

    @Override
    public void draw(Graphics g, int camX, int camY) {
        // Draw the player based on its world coordinates and the camera position
        int width = GameFrame.getInstance().getWidth();
        int height = GameFrame.getInstance().getHeight();

        // Draw the player based on its world coordinates and the camera position
        g.drawImage(inventoryTexture,
                (width - TILE_SIZE * 4) / 2 + camX, height - TILE_SIZE * 2 + camY, TILE_SIZE * 4, TILE_SIZE, null);

        for(int i=1;i<4;i++)
        {
            g.drawImage(texture[i],(width - TILE_SIZE * 4) / 2 + camX +(i-1)*TILE_SIZE, height-TILE_SIZE*2+camY, TILE_SIZE, TILE_SIZE, null);
        }

        g.drawImage(texture[0],(width - TILE_SIZE * 4) / 2 + camX +currentTool*TILE_SIZE,height-TILE_SIZE*2+camY, TILE_SIZE, TILE_SIZE, null);
        //g.fillRect(width/4+camX+currentTool*width/20, height-100+camY, width/20, width/10);
        //g.drawImage(texture, CenterX+camX, CenterY+camY, TILE_SIZE, TILE_SIZE, null);
    }
}
