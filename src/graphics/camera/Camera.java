package graphics.camera;

import gameObject.GameObject;
import graphics.transform.Mat4;
import graphics.transform.Vec2;
import graphics.transform.Vec3;

import java.awt.*;

public class Camera extends GameObject {

    private Vec2 wCenter = new Vec2(); // Camera position (center of the world)
    public static int maxCameraX = TILE_SIZE;
    public static int maxCameraY = TILE_SIZE;
	public Camera() {
        wCenter.x = 10; // Initial camera position
        wCenter.y = 10;
	}

    //Set max camera position
    public static void setMaxCamera(int x, int y) {
        maxCameraX = x;
        maxCameraY = y;
    }

    public Mat4 V() {
        return new Mat4().translate(-wCenter.x, -wCenter.y);
    }

    public Mat4 P()
    {
        return new Mat4().scale(2/((float)maxCameraX), 2/((float)maxCameraY));
    }

    public Mat4 Vinv()
    {
        return new Mat4().translate(wCenter.x, wCenter.y);
    }

    public Mat4 Pinv()
    {
        return new Mat4().scale((float)500/2, (float)500/2);
    }


    public Vec2 screenToWorld(Vec2 screen) {
        Mat4 m = new Mat4().scale(screen.x,screen.y).multiply(Pinv()).multiply(Vinv());
        return new Vec2(m.m[0].x, m.m[1].y);
    }

    public void moveCamera(float deltaX, float deltaY) {
        wCenter.x += deltaX;
        wCenter.y += deltaY;

        // Optionally clamp camera position to prevent going out of bounds
        wCenter.x = Math.max(0, Math.min(wCenter.x, maxCameraX));
        wCenter.y = Math.max(0, Math.min(wCenter.y, maxCameraY));
    }

	public float getCameraX() {
		return wCenter.x;
	}

	public float getCameraY() {
		return wCenter.y;
	}

    public Vec2 getwCenter() {
        return wCenter;
    }

    public void setwCenter(Vec2 wCenter) {
        this.wCenter = wCenter;
    }

    @Override
    public void draw(Graphics g, int i, int j) {

    }
}
