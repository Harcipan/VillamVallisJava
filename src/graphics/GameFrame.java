package graphics;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import gamemanager.GameLoop;
import gamemanager.SceneManager;
import graphics.scenes.EditorScene;
import graphics.scenes.GameScene;
import graphics.scenes.MainMenu;
import graphics.transform.Vec2;
import input.KeyHandler;
import input.MouseHandler;
import interfaces.GameLoopCallback;
import interfaces.GameObserver;

/**
 * The GameFrame class represents the main window of the game application.
 * It initializes the frame and sets up the game scenes.
 */
public class GameFrame extends JFrame implements KeyListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private static GameFrame instance;
	JPanel MainMenu;
	public JPanel GameScene;
	JPanel EditorScene;
	KeyHandler keyHandler;
	public GameScene gameScene;
	MouseHandler mouseHandler;
	GameLoop gameLoop;
    /**
     * Initializes a new instance of GameFrame.
     * Sets the title and icon of the frame, and initializes the scene manager.
     */
	public GameFrame()
	{
		super("Villam Vallis");
		if (instance == null) {
            instance = this;
        }

		setMinimumSize(new Dimension(500, 500));
		ImageIcon i = new ImageIcon("assets/vvj.icon");
		Image img = i.getImage();
		this.setIconImage(img);
		
        // Create SceneManager and add scenes
        gameLoop = new GameLoop();
        SceneManager sceneManager = new SceneManager(this);
        MainMenu = new MainMenu(sceneManager,((GameLoopCallback)(gameLoop)));
        GameScene = new GameScene(sceneManager,gameLoop);
		EditorScene = new EditorScene(sceneManager,gameLoop);
        sceneManager.addScene("MainMenu", MainMenu);
		sceneManager.addScene("GameScene", GameScene);
		sceneManager.addScene("EditorScene", EditorScene);

        // Show initial scene (Main Menu)
        sceneManager.showScene("MainMenu");
		sceneManager.toggleFullScreen();

		gameScene = sceneManager.getGameScene();
		keyHandler = gameScene.keyHandler;
		mouseHandler = new MouseHandler(this);
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.setFocusable(true);
		this.requestFocusInWindow();

        pack();
	}
	
	public static GameFrame getInstance()
	{
        return instance;
	}

	public Vec2 screenToCamera(int screenX, int screenY) {
		// Camera coordinates (cameraX, cameraY) and GamePanel dimensions
		double cameraX = gameScene.camera.getCameraX();
		double cameraY = gameScene.camera.getCameraY();

		// Get the size of the GamePanel (since you are using it as your game canvas)
		float gamePanelWidth = gameScene.gp.getWidth();
		float gamePanelHeight = gameScene.gp.getHeight();

		// Convert the screen coordinates to game world coordinates

		float cX = 2.0f * screenX / gamePanelWidth - 1;	// flip y axis
		float cY = 1.0f - 2.0f * screenY / gamePanelHeight;

		double worldX =(cX*(gamePanelWidth/2)+cameraX*2);
		double worldY = (cY*(gamePanelHeight/2)-cameraY*2);

		int clickedTileX = (int)Math.floor(worldX/gameScene.player.getTileSize());
		int clickedTileY = -(int)Math.floor(worldY/gameScene.player.getTileSize())-1;

		System.out.println("Ablak position: " + cX*(gamePanelWidth/2) + ", " + cX*(gamePanelHeight/2));
		System.out.println("World position: " + worldX + ", " + worldY);
		System.out.println("WCamera " + gameScene.camera.screenToWorld(new Vec2(cX, cY)).x + ", " + gameScene.camera.screenToWorld(new Vec2(cX, cY)).y);
		System.out.println("You pressed the " + Math.floor(worldX/gameScene.player.getTileSize()) +
				". X and " + Math.floor(worldY/gameScene.player.getTileSize()) + ".Y tile");
		//check if the clicked tile is inside the map
		if(!(clickedTileX<0 || clickedTileY<0 || clickedTileX>=GameLoop.tileMap.mapData.length || clickedTileY>=GameLoop.tileMap.mapData[0].length))
		{
			if(GameLoop.tileMap.mapData[-(int)Math.floor(worldY/gameScene.player.getTileSize())-1][(int)Math.floor(worldX/gameScene.player.getTileSize())]==1)
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
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		keyHandler.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyHandler.keyReleased(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//convert screen coordinates to world coordinates
		System.out.println("Mouse clicked at: " + (e.getX()+2* gameScene.camera.getCameraX()-gameScene.gp.getWidth()/2) + ", " +
				(e.getY()+2* gameScene.camera.getCameraY()-gameScene.gp.getHeight()/2));
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
}
