package graphics.camera;

import gameObject.GameObject;
import graphics.transform.Mat4;
import graphics.transform.Vec2;

import java.awt.*;

/**
 * The Camera class is responsible for handling the player's view of the world.
 * It controls the camera position, view matrix, projection matrix, and
 * provides methods for translating screen coordinates to world coordinates.
 * The camera can be moved and its position is clamped within specified bounds.
 */
public class Camera extends GameObject {

    private Vec2 wCenter = new Vec2(); // Camera position (center of the world)
    public static int maxCameraX = TILE_SIZE;
    public static int maxCameraY = TILE_SIZE;

    /**
     * Constructs a new Camera with an initial position of (10, 10).
     */
    public Camera() {
        wCenter.x = 10; // Initial camera position
        wCenter.y = 10;
    }

    /**
     * Sets the maximum camera bounds. This prevents the camera from moving
     * beyond these limits.
     *
     * @param x the maximum X coordinate of the camera.
     * @param y the maximum Y coordinate of the camera.
     */
    public static void setMaxCamera(int x, int y) {
        maxCameraX = x;
        maxCameraY = y;
    }

    /**
     * Returns the View matrix for the camera, which transforms world
     * coordinates to camera space.
     *
     * @return the View matrix.
     */
    public Mat4 V() {
        return new Mat4().translate(-wCenter.x, -wCenter.y);
    }

    /**
     * Returns the Projection matrix for the camera, which scales world
     * coordinates to fit the screen.
     *
     * @return the Projection matrix.
     */
    public Mat4 P() {
        return new Mat4().scale(2 / ((float) maxCameraX), 2 / ((float) maxCameraY));
    }

    /**
     * Returns the inverse of the View matrix, which transforms camera space
     * back to world coordinates.
     *
     * @return the inverse View matrix.
     */
    public Mat4 Vinv() {
        return new Mat4().translate(wCenter.x, wCenter.y);
    }

    /**
     * Returns the inverse of the Projection matrix, which scales screen
     * coordinates back to world coordinates.
     *
     * @return the inverse Projection matrix.
     */
    public Mat4 Pinv() {
        return new Mat4().scale((float) 500 / 2, (float) 500 / 2);
    }

    /**
     * Converts screen space coordinates to world space coordinates using
     * the inverse of the projection and view matrices.
     *
     * @param screen the screen coordinates to convert.
     * @return the corresponding world coordinates.
     */
    public Vec2 screenToWorld(Vec2 screen) {
        Mat4 m = new Mat4().scale(screen.x, screen.y).multiply(Pinv()).multiply(Vinv());
        return new Vec2(m.m[0].x, m.m[1].y);
    }

    /**
     * Moves the camera by a specified amount in both X and Y directions.
     * The camera position is clamped to stay within the specified bounds.
     *
     * @param deltaX the amount to move the camera in the X direction.
     * @param deltaY the amount to move the camera in the Y direction.
     */
    public void moveCamera(float deltaX, float deltaY) {
        wCenter.x += deltaX;
        wCenter.y += deltaY;

        // Optionally clamp camera position to prevent going out of bounds
        wCenter.x = Math.max(0, Math.min(wCenter.x, maxCameraX));
        wCenter.y = Math.max(0, Math.min(wCenter.y, maxCameraY));
    }

    /**
     * Gets the current X position of the camera's center.
     *
     * @return the X position of the camera.
     */
    public float getCameraX() {
        return wCenter.x;
    }

    /**
     * Gets the current Y position of the camera's center.
     *
     * @return the Y position of the camera.
     */
    public float getCameraY() {
        return wCenter.y;
    }

    /**
     * Gets the current world center position of the camera.
     *
     * @return the world center position of the camera.
     */
    public Vec2 getwCenter() {
        return wCenter;
    }

    /**
     * Sets the camera's world center position.
     *
     * @param wCenter the new world center position for the camera.
     */
    public void setwCenter(Vec2 wCenter) {
        this.wCenter = wCenter;
    }

    /**
     * Draws the camera. Must be implemented because Camera is gameobject
     *
     * @param g the Graphics object to draw on.
     * @param i an X coordinate to be used when drawing.
     * @param j a Y coordinate to be used when drawing.
     */
    @Override
    public void draw(Graphics g, int i, int j) {
        // Empty method, can be overridden for custom drawing.
    }
}
