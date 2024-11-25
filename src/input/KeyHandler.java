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

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * The KeyHandler class is responsible for handling keyboard input in the game.
 * It tracks the keys that are currently pressed and performs actions based on the key events.
 */
public class KeyHandler {

	private Set<Integer> pressedKeys = new HashSet<>();

	// Movement control flags
	boolean movingUp;
	boolean movingDown;
	boolean movingLeft;
	boolean movingRight;

	/**
	 * Constructs a KeyHandler instance with initial movement flags set to false.
	 */
	public KeyHandler() {
		movingUp = false;
		movingDown = false;
		movingLeft = false;
		movingRight = false;
	}

	/**
	 * Handles key press events and performs actions based on the pressed key.
	 * It updates the state of the movement flags and triggers appropriate actions
	 * like opening/closing the settings menu or switching tools.
	 *
	 * @param e the KeyEvent triggered by a key press.
	 */
	public void keyPressed(KeyEvent e) {
		pressedKeys.add(e.getKeyCode());
		int keyCode = e.getKeyCode();

		// Toggle settings menu with the Escape key
		if (keyCode == KeyEvent.VK_ESCAPE) {
			if (Scene.settingsActive) {
				Scene.settingsActive = false;
				SceneManager.hideOverlay(GameScene.settingsPanel);
				GameLoop.playing = true;
			} else {
				Scene.settingsActive = true;
				SceneManager.showOverlay(GameScene.settingsPanel);
				GameLoop.playing = false;
			}
		}
		// Switch tools based on number keys (1-4)
		else if (keyCode >= KeyEvent.VK_1 && keyCode <= KeyEvent.VK_4) {
			GameScene.player.inventory.currentTool = keyCode - KeyEvent.VK_1;
			if (keyCode == KeyEvent.VK_1) {
				Inventory.numberOfPlants = GameLoop.tileMap.plantTypes.size();
				GameScene.player.inventory.currentPlant = (GameScene.player.inventory.currentPlant + 1) % Inventory.numberOfPlants;
				System.out.println(GameScene.player.inventory.currentPlant);
			}
		}
	}

	/**
	 * Handles key release events by removing the key code from the pressed keys set.
	 *
	 * @param e the KeyEvent triggered by a key release.
	 */
	public void keyReleased(KeyEvent e) {
		pressedKeys.remove(e.getKeyCode());
	}

	/**
	 * Returns the set of currently pressed keys.
	 *
	 * @return a set of key codes for the keys that are currently pressed.
	 */
	public Set<Integer> getPressedKeys() {
		return pressedKeys;
	}
}
