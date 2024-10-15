package graphics;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import gamemanager.GameLoop;
import gamemanager.SceneManager;
import graphics.scenes.GameScene;
import graphics.scenes.MainMenu;
import interfaces.GameLoopCallback;
/**
 * The GameFrame class represents the main window of the game application.
 * It initializes the frame and sets up the game scenes.
 */
public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static GameFrame instance;
	JPanel MainMenu;
	public JPanel GameScene;
	
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
		ImageIcon i = new ImageIcon("assets/wheat.png");
		Image img = i.getImage();
		this.setIconImage(img);
		
        // Create SceneManager and add scenes
        GameLoop gameLoop = new GameLoop();
        SceneManager sceneManager = new SceneManager(this);
        MainMenu = new MainMenu(sceneManager,((GameLoopCallback)(gameLoop)));
        GameScene = new GameScene(sceneManager,gameLoop);
        sceneManager.addScene("MainMenu", MainMenu);
        sceneManager.addScene("GameScene", GameScene);

        // Show initial scene (Main Menu)
        sceneManager.showScene("MainMenu");

        pack();
	}
	
	public static GameFrame getInstance()
	{
        return instance;
	}
	
}
