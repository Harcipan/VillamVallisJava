package gameObject;

import java.awt.Graphics;

public abstract class GameObject {
    public static final int TILE_SIZE = 64;
    public abstract void draw(Graphics g, int i, int j);
    public int getTileSize() {
        return TILE_SIZE;
    }
}
