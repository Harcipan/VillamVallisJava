package graphics.texture;

import graphics.transform.Vec2;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
/**
 * The TextureManager class is responsible for loading and retrieving textures from a texture map.
 * It loads a texture map from the specified file path and allows retrieval of sub-images (textures)
 * based on specified positions and sizes within the map.
 */
public class TextureManager {

    // The texture map loaded from the file
    static BufferedImage textureMap = loadTextures("assets/textureMap.png");

    /**
     * Retrieves a sub-image (texture) from the texture map at the specified position and size.
     *
     * @param position The position of the texture within the texture map, represented as a 2D vector (x, y).
     * @param size The size of the texture to extract, represented as a 2D vector (width, height).
     * @return The sub-image representing the requested texture, or null if the requested area is out of bounds.
     */
    public static BufferedImage getTextureFromMap(Vec2 position, Vec2 size) {
        // Check if the requested position and size are within the bounds of the texture map
        if (position.x * size.x > textureMap.getWidth() || position.y * size.y > textureMap.getHeight()) {
            return null; // Optionally, a RuntimeException can be thrown here for better error handling.
        }
        // Extract and return the sub-image from the texture map at the specified position and size
        return textureMap.getSubimage((int) position.x * (int) size.x, (int) position.y * (int) size.y,
                (int) size.x, (int) size.y);
    }

    /**
     * Loads a texture map from the specified file path.
     *
     * @param path The file path to the texture map image.
     * @return A BufferedImage representing the loaded texture map.
     * @throws RuntimeException if there is an error reading the texture map file.
     */
    public static BufferedImage loadTextures(String path) {
        BufferedImage texture;
        try {
            // Try to load the texture map image from the file
            texture = ImageIO.read(new File(path));
        } catch (IOException e) {
            // Throw a RuntimeException if there is an error loading the texture map
            throw new RuntimeException(e);
        }
        return texture;
    }
}
