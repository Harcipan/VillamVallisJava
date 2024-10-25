package input;

import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import gamemanager.SceneManager;
import graphics.GamePanel;
import graphics.camera.Camera;
import graphics.scenes.GameScene;
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
	GameScene gameScene;
    
	public KeyHandler(GamePanel gamePanel, SceneManager sceneManager, boolean sActive, JPanel sPanel, Camera c, GameScene gameScene) {
    	movingUp = false;
    	movingDown = false;
    	movingLeft = false;
    	movingRight = false;
    	gp = gamePanel;
    	manager = sceneManager;
    	settingsActive = sActive;
    	settingsPanel = sPanel;
    	camera = c;
		this.gameScene = gameScene;
	}

	public void setGLCallback(GameLoopCallback glC) {
		glCallback = glC;
	}
	
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
		int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ESCAPE) {
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
		else if (keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_3) {
			gameScene.player.inventory.currentTool = keyCode - KeyEvent.VK_0;
		}
    }

    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }
    
    public Set<Integer> getPressedKeys() {
        return pressedKeys;
    }

}
