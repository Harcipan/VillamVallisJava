package graphics.scenes;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gamemanager.GameLoop;
import graphics.SceneManager;
import graphics.components.MenuButton;
import interfaces.GameLoopCallback;
import interfaces.GameObserver;
/**
 * The GameScene class represents the main game scene, allowing interaction with the game.
 * It manages the display of the game and the settings overlay.
 */
@SuppressWarnings("serial")
public class GameScene extends JPanel implements KeyListener, GameObserver {
	SceneManager manager;
	boolean settingsActive;
	JPanel settingsPanel;
	private JLabel moneyText;
	private GameLoopCallback glCallback;
    /**
     * Initializes a new instance of GameScene.
     *
     * @param manager The SceneManager managing this scene.
     */
    public GameScene(SceneManager manager, GameLoop gameLoop) {
    	gameLoop.addObserver(this);
    	glCallback = (GameLoopCallback)gameLoop; 
        setLayout(new GridLayout(1, 1));
        JPanel jp = new JPanel();
        JLabel jl = new JLabel("This is the game scene!");
        //money = 0;
        setMoneyText(new JLabel("Here should be the money of the player."));
        jp.add(jl);jp.add(getMoneyText());
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
        
        //Extra: Implement after audio
        //settingsPanel.add(new MenuButton("Audio Settings"));
        JButton fullScreenButton = new MenuButton("FullScreen");
        fullScreenButton.addActionListener(e->{manager.toggleFullScreen();manager.hideOverlay(settingsPanel);});
        JButton saveExitButton = new JButton("Save&Exit");
        saveExitButton.addActionListener(e -> {glCallback.saveGame(); manager.showScene("MainMenu"); manager.hideOverlay(settingsPanel); settingsActive=false;});
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {manager.hideOverlay(settingsPanel); settingsActive=false;});

        settingsPanel.add(fullScreenButton);
        settingsPanel.add(saveExitButton);
        settingsPanel.add(closeButton);
        
        return settingsPanel;
    }

	@Override
	public void onScoreChange(int newScore) {
		System.out.println("Score updated: " + newScore);
		getMoneyText().setText("Money: "+newScore);
	}

	public JLabel getMoneyText() {
		return moneyText;
	}

	public void setMoneyText(JLabel moneyText) {
		this.moneyText = moneyText;
	}
}
