package graphics;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import graphics.scenes.GameScene;
import graphics.scenes.MainMenu;
/**
 * The GameFrame class represents the main window of the game application.
 * It initializes the frame and sets up the game scenes.
 */
@SuppressWarnings("serial")
public class GameFrame extends JFrame {
    /**
     * Initializes a new instance of GameFrame.
     * Sets the title and icon of the frame, and initializes the scene manager.
     */
	public GameFrame()
	{
		super("Villam Vallis");
		setMinimumSize(new Dimension(500, 500));
		ImageIcon i = new ImageIcon("assets/wheat.png");
		Image img = i.getImage();
		this.setIconImage(img);
		
        // Create SceneManager and add scenes
        SceneManager sceneManager = new SceneManager(this);
        sceneManager.addScene("MainMenu", new MainMenu(sceneManager));
        sceneManager.addScene("GameScene", new GameScene(sceneManager));

        // Show initial scene (Main Menu)
        sceneManager.showScene("MainMenu");

        pack();
	}
}
