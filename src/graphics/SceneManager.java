package graphics;

import java.awt.CardLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;

import graphics.scenes.GameScene;

import javax.swing.JLayeredPane;
/**
 * The SceneManager class manages the different scenes (JPanels) of the game.
 * It handles switching between scenes and showing overlays.
 */
public class SceneManager {
	private JFrame frame; // The main game frame
    private JPanel sceneContainer; // A panel to hold different scenes
    private CardLayout cardLayout; // To switch between scenes
    JLayeredPane layeredPane; // To show the settings on top of stuff
    private boolean fullscreen;

    /**
     * Initializes a new instance of SceneManager.
     *
     * @param frame The main game frame.
     */
    public SceneManager(JFrame frame) {
        this.frame = frame;
        fullscreen = false;
        // Create the JLayeredPane and set it as the content pane
        layeredPane = new JLayeredPane();
        frame.setContentPane(layeredPane);
        layeredPane.setLayout(null);

        // Create the scene container with CardLayout for switching scenes
        this.cardLayout = new CardLayout();
        this.sceneContainer = new JPanel(cardLayout);
        sceneContainer.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        layeredPane.add(sceneContainer, JLayeredPane.DEFAULT_LAYER);
    }
    

    /**
     * Adds a new scene to the manager.
     *
     * @param sceneName The name of the scene.
     * @param scene     The JPanel representing the scene.
     */
    public void addScene(String sceneName, JPanel scene) {
        sceneContainer.add(scene, sceneName); // Add scene with a name
    }

    /**
     * Displays a specific scene by name.
     *
     * @param sceneName The name of the scene to display.
     */
    public void showScene(String sceneName) {
        cardLayout.show(sceneContainer, sceneName); // Switch to the desired scene
        if (sceneContainer.getComponent(sceneContainer.getComponentCount() - 1) instanceof GameScene) {
            ((GameScene) sceneContainer.getComponent(sceneContainer.getComponentCount() - 1)).requestFocus();
        }
        
    }
    
    /**
     * Displays an overlay panel on top of the current scene.
     *
     * @param overlayPanel The JPanel to display as an overlay.
     */
    public void showOverlay(JPanel overlayPanel) {
    	overlayPanel.setVisible(true);
        // Get the dimensions of the layeredPane
        int paneWidth = layeredPane.getWidth();
        int paneHeight = layeredPane.getHeight();

        // Calculate the x and y positions to center the overlay
        int overlayWidth = 400; // Width of the overlay
        int overlayHeight = 300; // Height of the overlay

        int xPosition = (paneWidth - overlayWidth) / 2; // Center horizontally
        int yPosition = (paneHeight - overlayHeight) / 2; // Center vertically

        // Set the bounds of the overlayPanel
        overlayPanel.setBounds(xPosition, yPosition, overlayWidth, overlayHeight); // Set the position and size of the overlay

        layeredPane.add(overlayPanel, JLayeredPane.PALETTE_LAYER); // Add to a higher layer
    }

    /**
     * Hides the specified overlay panel.
     *
     * @param overlayPanel The JPanel to hide.
     */
    public void hideOverlay(JPanel overlayPanel) {
    	overlayPanel.setVisible(false);
        layeredPane.remove(overlayPanel); // Remove the settings panel from the layered pane
    }
    
    public void toggleFullScreen()
    {
    	//is this safe??? source: OpenAI ChatGPT 4o
        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if(fullscreen)
        {
        	graphicsDevice.setFullScreenWindow(null);
        	frame.setSize(500, 500); 
        	fullscreen = false;
        }
        else
        {
        	fullscreen = true;
            if (graphicsDevice.isFullScreenSupported()) {
                graphicsDevice.setFullScreenWindow(frame);
            } else {
                System.out.println("Fullscreen not supported. Running in windowed mode.");
                frame.setSize(500, 500); // Set a default size
            }
        }

        sceneContainer.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        // "Commit" changes
        frame.setVisible(true);
    }
}
