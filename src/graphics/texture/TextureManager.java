package graphics.texture;

import graphics.transform.Vec2;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TextureManager {
    static BufferedImage textureMap = loadTextures("assets/textureMap.png");

    public static BufferedImage getTextureFromMap(Vec2 position, Vec2 size){
        //get part of the texture map to an image
        return textureMap.getSubimage((int)position.x*(int)size.x, (int)position.y*(int)size.y, (int)size.x, (int)size.y);
    }

    public static BufferedImage loadTextures(String path) {
        BufferedImage texture;
        try {
            texture = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return texture;
    }

}
