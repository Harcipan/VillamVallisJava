package graphics.scenes;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;

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
import graphics.transform.Vec2;
import input.KeyHandler;
import input.MouseHandler;
import interfaces.GameLoopCallback;
import interfaces.GameObserver;
/**
 * The GameScene class represents the main game scene, allowing interaction with the game.
 * It manages the display of the game and the settings overlay.
 */
public class GameScene extends JPanel implements KeyListener, GameObserver, MouseListener {
	private static final long serialVersionUID = 1L;
	SceneManager manager;
	boolean settingsActive;
	JPanel settingsPanel;
	private JLabel moneyText;
	private GameLoopCallback glCallback;
	public Player player;
    JLayeredPane layeredPane; // To show the UI on top of stuff
    public GamePanel gp;
    JPanel UIpanel;
    public Camera camera;
    public KeyHandler keyHandler;
    MouseHandler mouseHandler;
    public GameLoop gameLoop;
    public TileMap tm;

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
        
        

        tm = new TileMap();
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
        UIpanel.setBounds(0, 0,GameFrame.getInstance().getWidth(), GameFrame.getInstance().getHeight());
        UIpanel.setBackground(new Color(0,0,0,0));
        UIpanel.setOpaque(false); //without this the UIpanel is covering the game because it isn't truly transparent
        layeredPane.add(UIpanel, JLayeredPane.PALETTE_LAYER);

        
        // Add the layeredPane to the GameScene
        add(layeredPane);
        this.manager=manager;
        settingsActive = false;
        settingsPanel = createSettingsPanel();

        keyHandler = new KeyHandler(gp, manager, settingsActive, settingsPanel, camera, this);
        mouseHandler = new MouseHandler(this);

        gameLoop.loopSetup(keyHandler, camera, gp, player, tm);
        glCallback = (GameLoopCallback)gameLoop;
        gameLoop.addObserver(this);

        keyHandler.setGLCallback(glCallback);

        /*Thread gameLoopT = new Thread(gameLoop::startGameLoop);
        gameLoopT.start();*/

        this.addKeyListener(this);
        this.setFocusable(true); // Ensure the panel can receive key events
        this.requestFocusInWindow();
        this.addMouseListener(this);
        
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
    public void mouseClicked(MouseEvent e) {
        //convert screen coordinates to world coordinates
        System.out.println("Mouse clicked at: " + (e.getX()+2* camera.getCameraX()-gp.getWidth()/2) + ", " +
                (e.getY()+2* camera.getCameraY()-gp.getHeight()/2));
        Vec2 cameraPos = screenToCamera(e.getX(), e.getY());
        System.out.println("Normalizalt eszkoz position: " + cameraPos.x + ", " + cameraPos.y);
        mouseHandler.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public Vec2 screenToCamera(int screenX, int screenY) {
        // Camera coordinates (cameraX, cameraY) and GamePanel dimensions
        double cameraX = camera.getCameraX();
        double cameraY = camera.getCameraY();

        // Get the size of the GamePanel (since you are using it as your game canvas)
        float gamePanelWidth = gp.getWidth();
        float gamePanelHeight = gp.getHeight();

        // Convert the screen coordinates to game world coordinates

        float cX = 2.0f * screenX / gamePanelWidth - 1;	// flip y axis
        float cY = 1.0f - 2.0f * screenY / gamePanelHeight;

        double worldX =(cX*(gamePanelWidth/2)+cameraX*2);
        double worldY = (cY*(gamePanelHeight/2)-cameraY*2);

        int clickedTileX = (int)Math.floor(worldX/player.getTileSize());
        int clickedTileY = -(int)Math.floor(worldY/player.getTileSize())-1;

        System.out.println("Ablak position: " + cX*(gamePanelWidth/2) + ", " + cX*(gamePanelHeight/2));
        System.out.println("World position: " + worldX + ", " + worldY);
        System.out.println("WCamera " + camera.screenToWorld(new Vec2(cX, cY)).x + ", " + camera.screenToWorld(new Vec2(cX, cY)).y);
        System.out.println("You pressed the " + Math.floor(worldX/player.getTileSize()) +
                ". X and " + Math.floor(worldY/player.getTileSize()) + ".Y tile");
        //check if the clicked tile is inside the map
        if(!(clickedTileX<0 || clickedTileY<0 || clickedTileX>=tm.mapData.length || clickedTileY>=tm.mapData[0].length))
        {
            if(tm.mapData[-(int)Math.floor(worldY/player.getTileSize())-1][(int)Math.floor(worldX/player.getTileSize())]==1)
            {
                System.out.println("You clicked on a dirt");
            }
            else
            {
                System.out.println("You clicked on a wheat");
            }
        }


        return new Vec2(cX, cY);
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
        closeButton.addActionListener(e -> {manager.hideOverlay(settingsPanel); settingsActive=false;
            glCallback.setPlaying(true);});
        JButton fullScreenButton = new MenuButton("FullScreen");
        fullScreenButton.addActionListener(e->{manager.toggleFullScreen();manager.hideOverlay(settingsPanel);
            glCallback.setPlaying(true);});
        JButton sizeButton = new MenuButton("Size");
        sizeButton.addActionListener(e->{manager.setWindowSize(1000,600);manager.hideOverlay(settingsPanel);
            glCallback.setPlaying(true);});
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
