package graphics.camera;

public class Camera {

    private double cameraX, cameraY; // Camera position (center of the world)
    public static int maxCameraX = 4*64;
    public static int maxCameraY = 4*64;
	public Camera() {

        this.cameraX = 200; // Initial camera position
        this.cameraY = 200;
	}
	
    public void moveCamera(double deltaX, double deltaY) {
        cameraX += deltaX;
        cameraY += deltaY;

        // Optionally clamp camera position to prevent going out of bounds
        cameraX = Math.max(64, Math.min(cameraX, maxCameraX));
        cameraY = Math.max(64, Math.min(cameraY, maxCameraY));
    }

	public double getCameraX() {
		return cameraX;
	}

	public double getCameraY() {
		return cameraY;
	}
}
