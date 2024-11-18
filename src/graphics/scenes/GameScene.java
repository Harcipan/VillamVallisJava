package graphics.scenes;

import java.awt.GridLayout;
import java.awt.event.*;

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
import graphics.components.SettingsPanel;
import graphics.components.UIPanel;
import input.KeyHandler;
import interfaces.GameLoopCallback;
import interfaces.GameObserver;
/**
 * The GameScene class represents the main game scene, allowing interaction with the game.
 * It manages the display of the game and the settings overlay.
 */
public class GameScene extends Scene implements GameObserver{
	private static final long serialVersionUID = 1L;
	SceneManager manager;
	JPanel settingsPanel;
	private JLabel moneyText;
	private GameLoopCallback glCallback;
    JLayeredPane layeredPane; // To show the UI on top of stuff
    public GamePanel gp;
    UIPanel ui;
    public Camera camera;
    public KeyHandler keyHandler;
    public TileMap tm;

    /**
     * Initializes a new instance of GameScene.
     *
     * @param manager The SceneManager managing this scene.
     */
    public GameScene(SceneManager manager, GameLoop gameLoop) {
        setLayout(new GridLayout(1, 1));
        JLabel jl = new JLabel("This is the game scene!");
        setMoneyText(new JLabel("Here should be the money of the player."));
        ui = new UIPanel();
        ui.add(getMoneyText());



        tm = gameLoop.tileMap;
        player = new Player(this);
        camera = new Camera();
        gp = new GamePanel(tm,player,camera);
     

        // Create a JLayeredPane to stack gp and jp
        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);  // No layout manager, we'll set bounds manually

        // Set the size of the game panel (gp) and add it to the default layer
        gp.setBounds(0, 0, GameFrame.getInstance().getWidth(), GameFrame.getInstance().getHeight());
        
        layeredPane.add(gp, JLayeredPane.DEFAULT_LAYER);

        // Set the size of the UI panel (jp) and add it to a higher layer
        layeredPane.add(ui, JLayeredPane.PALETTE_LAYER);

        
        // Add the layeredPane to the GameScene
        add(layeredPane);
        this.manager=manager;
        settingsActive = false;


        glCallback = (GameLoopCallback)gameLoop;
        gameLoop.addObserver(this);

        settingsPanel = new SettingsPanel(glCallback, manager, this);
        keyHandler = new KeyHandler(gp, manager, settingsActive, settingsPanel, camera, this);
        keyHandler.setGLCallback(glCallback);
        gameLoop.loopSetup(keyHandler, camera, gp, player);
        
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int newWidth = GameFrame.getInstance().getWidth();
                int newHeight = GameFrame.getInstance().getHeight();

                gp.setBounds(0, 0, newWidth, newHeight);
                ui.setBounds(0, 0, newWidth, newHeight);
            }
        });
    }

	@Override
    public void onMoneyChange(int newScore) {
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
