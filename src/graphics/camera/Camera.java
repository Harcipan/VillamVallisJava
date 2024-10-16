package graphics.camera;

import gameObject.GameObject;
import graphics.transform.Vec2;

import java.awt.*;

public class Camera extends GameObject {

    private Vec2 wCenter = new Vec2(); // Camera position (center of the world)
    public static int maxCameraX = 4*TILE_SIZE;
    public static int maxCameraY = 4*TILE_SIZE;
	public Camera() {

        wCenter.x = 200; // Initial camera position
        wCenter.y = 200;
	}
    
	
    public void moveCamera(float deltaX, float deltaY) {
        wCenter.x += deltaX;
        wCenter.y += deltaY;

        // Optionally clamp camera position to prevent going out of bounds
        wCenter.x = Math.max(TILE_SIZE, Math.min(wCenter.x, maxCameraX));
        wCenter.y = Math.max(TILE_SIZE, Math.min(wCenter.y, maxCameraY));
    }

	public double getCameraX() {
		return wCenter.x;
	}

	public double getCameraY() {
		return wCenter.y;
	}

    @Override
    public void draw(Graphics g, int i, int j) {

    }
}
