package graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import gameObject.Player;
import gameObject.tiles.TileMap;
import graphics.camera.Camera;

public class GamePanel extends JPanel {
    private static final long serialVersionUID = 1L;
	public TileMap tileMap;
	private Player player;
	private Camera camera;

    // Constructor and other methods...
    public GamePanel(TileMap tileMap, Player player,Camera camera) {
        this.tileMap = tileMap;
        this.player = player;
        this.camera = camera;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;


        g2d.translate(-camera.getCameraX(), -camera.getCameraY());


        tileMap.draw(g2d, (int)camera.getCameraX(), (int)camera.getCameraY());
        player.draw(g2d, (int)(camera.getCameraX()), (int)camera.getCameraY());
    }


}