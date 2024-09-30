package graphics.scenes;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import graphics.SceneManager;
import graphics.components.MenuButton;
/**
 * The GameScene class represents the main game scene, allowing interaction with the game.
 * It manages the display of the game and the settings overlay.
 */
@SuppressWarnings("serial")
public class GameScene extends JPanel implements KeyListener {
	SceneManager manager;
	boolean settingsActive;
	JPanel settingsPanel;
    /**
     * Initializes a new instance of GameScene.
     *
     * @param manager The SceneManager managing this scene.
     */
    public GameScene(SceneManager manager) {
        setLayout(new GridLayout(1, 1));
        JPanel jp = new JPanel();
        JLabel jl = new JLabel("This is the game scene!");
        jp.add(jl);
        add(jp);
        this.manager=manager;
        settingsActive = false;
        settingsPanel = createSettingsPanel();
        this.addKeyListener(this);
        this.setFocusable(true); // Ensure the panel can receive key events
        this.requestFocusInWindow();
    }
    
    /**
     * Handles key pressed events.
     *
     * @param e The KeyEvent representing the key press.
     */
    public void keyPressed(KeyEvent e) {
        // Handle key press events
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            //JPanel settingsPanel = new SettingsScene(manager);
        	if(settingsActive)
        	{
        		settingsActive = false;
                manager.hideOverlay(settingsPanel);
        	}
        	else {
                settingsActive = true;
                manager.showOverlay(settingsPanel);
        	}
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    /**
     * Creates the settings panel for the game.
     *
     * @return The JPanel representing the settings panel.
     */
    private JPanel createSettingsPanel() {
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayout(4, 1, 10, 10));
        
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {manager.hideOverlay(settingsPanel); settingsActive=false;});
        JButton saveExitButton = new JButton("Save&Exit");
        saveExitButton.addActionListener(e -> {manager.showScene("MainMenu"); manager.hideOverlay(settingsPanel); settingsActive=false;});

        settingsPanel.add(new MenuButton("Audio Settings"));
        settingsPanel.add(new MenuButton("Graphics Settings"));
        settingsPanel.add(saveExitButton);
        settingsPanel.add(closeButton);
        
        return settingsPanel;
    }
}
