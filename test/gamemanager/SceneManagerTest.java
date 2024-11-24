package gamemanager;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JPanel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import graphics.GameFrame;
import graphics.scenes.GameScene;

/**
 * The SceneManagerTest class tests overlay, scene and fullscreen methods
 */
public class SceneManagerTest {
    private GameScene gameScene;
    private GameLoop gameLoop;
    private SceneManager manager;
    private GameFrame gf;

    @BeforeEach
    public void setUp() {
        gameLoop = new GameLoop();
    	gf = new GameFrame();
        manager = new SceneManager(gf);
        gameScene = new GameScene(manager, gameLoop);
        manager.addScene("GameScene", gameScene);
        manager.showScene("GameScene");
    }

    @Test
    public void testShowOverlay() throws Exception {
        JPanel overlayPanel = new JPanel();
        manager.showOverlay(overlayPanel);
        assertTrue(overlayPanel.isVisible()); // Check if the overlay is visible
    }

    @Test
    public void testSoundStopVol()
    {
    	manager.soundPlayer.setVolume(0);
    	manager.soundPlayer.stopSound(manager.clip);
    }
    
    @Test
    public void testHideOverlay() throws Exception {
    	int compCount = manager.layeredPane.getComponentCount();
        JPanel overlayPanel = new JPanel();
        manager.showOverlay(overlayPanel); // Show the overlay first.
        assertTrue(overlayPanel.isVisible()); // It should be visible now
        assertEquals(compCount+1, manager.layeredPane.getComponentCount());

        manager.hideOverlay(overlayPanel); // Now hide it.
        assertFalse(overlayPanel.isVisible()); // Check if the overlay is no longer visible
        assertEquals(compCount, manager.layeredPane.getComponentCount());
    }
    
    @Test
    public void testToggleFScreen()
    {
    	manager.toggleFullScreen();
    	GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    	assertEquals(graphicsDevice.getFullScreenWindow(), gf); // Check if the current fullscreen window is the same as the frame
    	manager.toggleFullScreen();

    	
    }
}
