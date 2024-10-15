package graphics.scenes;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import gameObject.Player;
import gameObject.tiles.TileMap;
import gamemanager.GameLoop;
import gamemanager.SceneManager;
import graphics.GameFrame;
import graphics.GamePanel;
import graphics.camera.Camera;
import graphics.components.MenuButton;
import input.KeyHandler;
import interfaces.GameLoopCallback;
import interfaces.GameObserver;
/**
 * The GameScene class represents the main game scene, allowing interaction with the game.
 * It manages the display of the game and the settings overlay.
 */
public class GameScene extends JPanel implements KeyListener, GameObserver {
	private static final long serialVersionUID = 1L;
	SceneManager manager;
	boolean settingsActive;
	JPanel settingsPanel;
	private JLabel moneyText;
	private GameLoopCallback glCallback;
	private Player player;
    JLayeredPane layeredPane; // To show the UI on top of stuff
    GamePanel gp;
    JPanel UIpanel;
    Camera camera;
    KeyHandler keyHandler;
    GameLoop gameLoop;
    /**
     * Initializes a new instance of GameScene.
     *
     * @param manager The SceneManager managing this scene.
     */
    public GameScene(SceneManager manager, GameLoop gameLoop) {
        setLayout(new GridLayout(1, 1));
        UIpanel = new JPanel();
        JLabel jl = new JLabel("This is the game scene!");
        setMoneyText(new JLabel("Here should be the money of the player."));
        UIpanel.add(jl);UIpanel.add(getMoneyText());
        
        
        int[][] tileMap = {
        	    {0, 0, 1, 1, 0, 0, 1, 1, 0},
        	    {1, 1, 1, 0, 1, 0, 1, 1, 0},
        	    {1, 1, 1, 0, 1, 0, 1, 1, 0},
        	    {0, 1, 1, 1, 0, 0, 1, 1, 0},
        	    {1, 1, 1, 0, 1, 0, 1, 1, 0},
        	    {0, 0, 0, 0, 0, 0, 1, 1, 0},
        	    {1, 1, 1, 0, 1, 0, 1, 1, 0},
        	    {1, 1, 1, 0, 1, 0, 1, 1, 0},
        	    {0, 0, 0, 0, 0, 0, 1, 1, 0}
        	};
        TileMap tm = new TileMap(tileMap);
        player = new Player();
        camera = new Camera();
        gp = new GamePanel(tm,player,camera);
     

        // Create a JLayeredPane to stack gp and jp
        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);  // No layout manager, we'll set bounds manually

        // Set the size of the game panel (gp) and add it to the default layer
        gp.setBounds(0, 0, GameFrame.getInstance().getWidth(), GameFrame.getInstance().getHeight());
        
        layeredPane.add(gp, JLayeredPane.DEFAULT_LAYER);

        // Set the size of the UI panel (jp) and add it to a higher layer
        UIpanel.setBounds(0, 0,GameFrame.getInstance().getWidth(), GameFrame.getInstance().getHeight());
        UIpanel.setBackground(new Color(0,0,0,0));
        UIpanel.setOpaque(false); //without this the UIpanel is covering the game because it isn't truly transparent
        layeredPane.add(UIpanel, JLayeredPane.PALETTE_LAYER);

        
        // Add the layeredPane to the GameScene
        add(layeredPane);
        this.manager=manager;
        settingsActive = false;
        settingsPanel = createSettingsPanel();

        keyHandler = new KeyHandler(gp, manager, settingsActive, settingsPanel, camera);

        gameLoop.loopSetup(keyHandler, camera, gp);
        glCallback = (GameLoopCallback)gameLoop;
        gameLoop.addObserver(this);

        keyHandler.setGLCallback(glCallback);

        /*Thread gameLoopT = new Thread(gameLoop::startGameLoop);
        gameLoopT.start();*/

        this.addKeyListener(this);
        this.setFocusable(true); // Ensure the panel can receive key events
        this.requestFocusInWindow();
        
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int newWidth = GameFrame.getInstance().getWidth();
                int newHeight = GameFrame.getInstance().getHeight();
                
                gp.setBounds(0, 0, newWidth, newHeight);
                UIpanel.setBounds(0, 0, newWidth, newHeight);
            }
        });
    }
    @Override
    public void keyPressed(KeyEvent e) {
        keyHandler.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    	keyHandler.keyReleased(e);
        //pressedKeys.remove(e.getKeyCode());
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
        settingsPanel.setBackground(new Color(170,170,170,255));
        settingsPanel.setLayout(new GridLayout(5, 1, 10, 10));
        
        //Extra: Implement after audio
        //settingsPanel.add(new MenuButton("Audio Settings"));

        JButton closeButton = new MenuButton("Close");
        closeButton.addActionListener(e -> {manager.hideOverlay(settingsPanel); settingsActive=false; glCallback.setPlaying(true);});
        JButton fullScreenButton = new MenuButton("FullScreen");
        fullScreenButton.addActionListener(e->{manager.toggleFullScreen();manager.hideOverlay(settingsPanel);});
        JButton sizeButton = new MenuButton("Size");
        sizeButton.addActionListener(e->{manager.setWindowSize(800,800);manager.hideOverlay(settingsPanel);});
        JButton saveMainButton = new MenuButton("Save&ExitToMain");
        saveMainButton.addActionListener(e -> {glCallback.saveGame(); manager.showScene("MainMenu");
            manager.hideOverlay(settingsPanel); settingsActive=false; glCallback.setPlaying(false);});
        JButton saveExitButton = new MenuButton("Save&Exit");
        saveExitButton.addActionListener(e -> {glCallback.saveGame();try {
			Thread.sleep(1000); //have to save
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}System.exit(0);});

        settingsPanel.add(closeButton);
        settingsPanel.add(sizeButton);
        settingsPanel.add(fullScreenButton);
        settingsPanel.add(saveMainButton);
        settingsPanel.add(saveExitButton);
        
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
	
	public GameLoop getGameLoop()
	{
		return gameLoop;
	}

}
