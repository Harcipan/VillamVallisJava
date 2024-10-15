package input;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import gamemanager.SceneManager;
import graphics.GamePanel;
import graphics.camera.Camera;
import interfaces.GameLoopCallback;

public class KeyHandler{

	private Set<Integer> pressedKeys = new HashSet<>();

    boolean movingUp;
    boolean movingDown;
    boolean movingLeft, movingRight;
    double lastKeyPressTime;
    GamePanel gp;
	SceneManager manager;
	boolean settingsActive;
	JPanel settingsPanel;
	Camera camera;
	GameLoopCallback glCallback;
    
	public KeyHandler(GamePanel gamePanel, SceneManager sceneManager, boolean sActive, JPanel sPanel, Camera c) {
    	movingUp = false;
    	movingDown = false;
    	movingLeft = false;
    	movingRight = false;
    	gp = gamePanel;
    	manager = sceneManager;
    	settingsActive = sActive;
    	settingsPanel = sPanel;
    	camera = c;
	}

	public void setGLCallback(GameLoopCallback glC) {
		glCallback = glC;
	}
	
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            //JPanel settingsPanel = new SettingsScene(manager);
        	if(settingsActive)
        	{
        		settingsActive = false;
                manager.hideOverlay(settingsPanel);
				glCallback.setPlaying(true);

			}
        	else {
                settingsActive = true;
                manager.showOverlay(settingsPanel);
				glCallback.setPlaying(false);
        	}
        }
    }

    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }
    
    public Set<Integer> getPressedKeys() {
        return pressedKeys;
    }

}
