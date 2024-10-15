package graphics.camera;

public class Camera {

    private double cameraX, cameraY; // Camera position
    public static int maxCameraX = 1000;
    public static int maxCameraY = 1000;
	public Camera() {

        this.cameraX = 0; // Initial camera position
        this.cameraY = 0;
	}
	
    public void moveCamera(double deltaX, double deltaY) {
        cameraX += deltaX;
        cameraY += deltaY;

        // Optionally clamp camera position to prevent going out of bounds
        cameraX = Math.max(-maxCameraX, Math.min(cameraX, maxCameraX));
        cameraY = Math.max(-maxCameraY, Math.min(cameraY, maxCameraY));
    }

	public double getCameraX() {
		return cameraX;
	}

	public double getCameraY() {
		return cameraY;
	}
}
