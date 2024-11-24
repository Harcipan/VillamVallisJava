package input;

import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import gameObject.Inventory;
import gamemanager.GameLoop;
import gamemanager.SceneManager;
import graphics.camera.Camera;
import graphics.scenes.GameScene;
import graphics.scenes.Scene;
import interfaces.GameLoopCallback;

public class KeyHandler{

	private Set<Integer> pressedKeys = new HashSet<>();

    boolean movingUp;
    boolean movingDown;
    boolean movingLeft;
	boolean movingRight;
    
	public KeyHandler() {
    	movingUp = false;
    	movingDown = false;
    	movingLeft = false;
    	movingRight = false;
	}
	
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
		int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ESCAPE) {
            //JPanel settingsPanel = new SettingsScene(manager);
        	if(Scene.settingsActive)
        	{
				Scene.settingsActive = false;
                SceneManager.hideOverlay(GameScene.settingsPanel);
				GameLoop.playing = true;

			}
        	else {
				Scene.settingsActive = true;
				SceneManager.showOverlay(GameScene.settingsPanel);
				GameLoop.playing = false;
        	}
        }
		else if (keyCode >= KeyEvent.VK_1 && keyCode <= KeyEvent.VK_4) {
			GameScene.player.inventory.currentTool = keyCode - KeyEvent.VK_1;
			if(keyCode == KeyEvent.VK_1)
			{
				Inventory.numberOfPlants = GameLoop.tileMap.plantTypes.size();
				GameScene.player.inventory.currentPlant=(GameScene.player.inventory.currentPlant+1)% Inventory.numberOfPlants;
				System.out.println(GameScene.player.inventory.currentPlant);
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
