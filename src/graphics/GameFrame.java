package graphics;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import gamemanager.GameLoop;
import gamemanager.SceneManager;
import graphics.scenes.*;
import graphics.scenes.editor.EditorMenu;
import graphics.scenes.editor.MapEditor;
import graphics.scenes.editor.PlantEditor;
import graphics.scenes.editor.GroundEditor;
import graphics.transform.Vec2;
import input.KeyHandler;
import input.MouseHandler;
import interfaces.GameLoopCallback;

/**
 * The GameFrame class represents the main window of the game application.
 * It initializes the frame, manages the game scenes, handles user input,
 * and updates the game state accordingly.
 */
public class GameFrame extends JFrame implements KeyListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private static GameFrame instance;
	private JPanel MainMenu;
	public JPanel GameScene;
	private JPanel EditorMenu;
	private JPanel SavesMenu;
	private JPanel PlantEditor;
	private JPanel TileEditor;
	private JPanel MapEditor;
	private KeyHandler keyHandler;
	public GameScene gameScene;
	private MouseHandler mouseHandler;
	public GameLoop gameLoop;

	/**
	 * Initializes a new instance of GameFrame.
	 * Sets the title and icon of the frame, initializes the scene manager,
	 * and sets up the key and mouse listeners.
	 */
	public GameFrame() {
		super("Villam Vallis");

		if (instance == null) {
			instance = this;
		}

		setMinimumSize(new Dimension(500, 500));
		ImageIcon icon = new ImageIcon("assets/vvj.icon");
		Image img = icon.getImage();
		this.setIconImage(img);

		// Initialize SceneManager and add scenes
		gameLoop = new GameLoop();
		SceneManager sceneManager = new SceneManager(this);
		MainMenu = new MainMenu(sceneManager, ((GameLoopCallback)(gameLoop)));
		GameScene = new GameScene(sceneManager, gameLoop);
		SavesMenu = new SavesMenu(sceneManager, gameLoop);
		EditorMenu = new EditorMenu(sceneManager);
		PlantEditor = new PlantEditor(sceneManager, gameLoop);
		TileEditor = new GroundEditor(sceneManager, gameLoop);
		MapEditor = new MapEditor(sceneManager, gameLoop);

		// Add scenes to SceneManager
		sceneManager.addScene("MainMenu", MainMenu);
		sceneManager.addScene("GameScene", GameScene);
		sceneManager.addScene("SavesMenu", SavesMenu);
		sceneManager.addScene("EditorMenu", EditorMenu);
		sceneManager.addScene("PlantEditor", PlantEditor);
		sceneManager.addScene("TileEditor", TileEditor);
		sceneManager.addScene("MapEditor", MapEditor);

		// Show the initial scene (Main Menu)
		sceneManager.showScene("MainMenu");

		gameScene = sceneManager.getGameScene();
		keyHandler = gameScene.keyHandler;
		mouseHandler = new MouseHandler(this);

		// Set up listeners
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.setFocusable(true);
		this.requestFocusInWindow();

		pack();
	}

	/**
	 * Retrieves the singleton instance of GameFrame.
	 *
	 * @return The singleton instance of GameFrame.
	 */
	public static GameFrame getInstance() {
		return instance;
	}

	/**
	 * Converts screen coordinates to camera coordinates, which are then used
	 * to determine the world position and tile clicked.
	 *
	 * @param screenX The X-coordinate on the screen.
	 * @param screenY The Y-coordinate on the screen.
	 * @return The normalized camera coordinates.
	 */
	public Vec2 screenToCamera(int screenX, int screenY) {
		// Camera coordinates and GamePanel dimensions
		double cameraX = gameScene.camera.getCameraX();
		double cameraY = gameScene.camera.getCameraY();
		float gamePanelWidth = gameScene.gp.getWidth();
		float gamePanelHeight = gameScene.gp.getHeight();

		// Convert screen coordinates to game world coordinates
		float cX = 2.0f * screenX / gamePanelWidth - 1; // Flip Y axis
		float cY = 1.0f - 2.0f * screenY / gamePanelHeight;

		double worldX = (cX * (gamePanelWidth / 2) + cameraX * 2);
		double worldY = (cY * (gamePanelHeight / 2) - cameraY * 2);

		int clickedTileX = (int)Math.floor(worldX / gameScene.player.getTileSize());
		int clickedTileY = -(int)Math.floor(worldY / gameScene.player.getTileSize()) - 1;

		// Debug information
		System.out.println("Screen position: " + cX * (gamePanelWidth / 2) + ", " + cY * (gamePanelHeight / 2));
		System.out.println("World position: " + worldX + ", " + worldY);
		System.out.println("Camera World position: " + gameScene.camera.screenToWorld(new Vec2(cX, cY)).x + ", " +
				gameScene.camera.screenToWorld(new Vec2(cX, cY)).y);
		System.out.println("Tile clicked: X = " + clickedTileX + ", Y = " + clickedTileY);

		// Check if the clicked tile is within the map bounds
		if (!(clickedTileX < 0 || clickedTileY < 0 || clickedTileX >= GameLoop.tileMap.mapData.length ||
				clickedTileY >= GameLoop.tileMap.mapData[0].length)) {
			if (GameLoop.tileMap.mapData[-(int)Math.floor(worldY / gameScene.player.getTileSize()) - 1]
					[clickedTileX].equals("dirt")) {
				System.out.println("You clicked on dirt");
			} else {
				System.out.println("You clicked on wheat");
			}
		}

		return new Vec2(cX, cY);
	}

	// KeyListener methods
	@Override
	public void keyTyped(KeyEvent e) { }

	@Override
	public void keyPressed(KeyEvent e) {
		keyHandler.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyHandler.keyReleased(e);
	}

	// MouseListener methods
	@Override
	public void mouseClicked(MouseEvent e) {
		// Convert screen coordinates to camera coordinates
		System.out.println("Mouse clicked at: " + (e.getX() + 2 * gameScene.camera.getCameraX() - gameScene.gp.getWidth() / 2) + ", " +
				(e.getY() + 2 * gameScene.camera.getCameraY() - gameScene.gp.getHeight() / 2));
		Vec2 cameraPos = screenToCamera(e.getX(), e.getY());
		System.out.println("Normalized screen position: " + cameraPos.x + ", " + cameraPos.y);
		mouseHandler.mouseClicked(e);
	}

	@Override
	public void mousePressed(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }
}

