package gameObject.tiles;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tile {
    private Image texture;

    public Tile(Image texture) {
        this.texture = texture;
    }

    public Image getTexture() {
        return texture;
    }

    public Tile loadTile(String path, boolean isWalkable) {
        try {
            Image texture = ImageIO.read(new File(path));
            return new Tile(texture);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

