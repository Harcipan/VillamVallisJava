package gamemanager;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JPanel;

import graphics.GameFrame;
import graphics.scenes.GameScene;
import sound.SoundPlayer;

import javax.swing.JLayeredPane;
/**
 * The SceneManager class manages the different scenes (JPanels) of the game.
 * It handles switching between scenes and showing overlays.
 */
public class SceneManager {
	private GameFrame frame; // The main game frame
    private JPanel sceneContainer; // A panel to hold different scenes
    private transient List<JPanel> scenes = new ArrayList<>();
    private final CardLayout cardLayout; // To switch between scenes
    public static JLayeredPane layeredPane; // To show the settings on top of stuff
    private boolean fullscreen;
    public SoundPlayer soundPlayer;
    Clip clip;

    
    /**
     * Initializes a new instance of SceneManager.
     *
     * @param frame The main game frame.
     */
    public SceneManager(GameFrame frame) {
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

        // Sound initialization
        soundPlayer = new SoundPlayer();
        clip = soundPlayer.playSound("assets/sound/backgroundMusic/InstrumentalSuno.wav");
        soundPlayer.setVolume(clip, -30.0f);
        soundPlayer.loopSound(clip);

        // Add ComponentListener to handle resizing
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateComponentSizes();
            }
        });
    }

    /**
     * Updates the size and position of components to match the frame's current size.
     */
    private void updateComponentSizes() {
        // Update sceneContainer to match frame size
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        sceneContainer.setBounds(0, 0, frameWidth, frameHeight);

        // Resize overlays if needed
        for (Component component : layeredPane.getComponents()) {
            if (component instanceof JPanel && component.isVisible() && component != sceneContainer) {
                // Center the visible overlay in the frame
                int overlayWidth = component.getWidth();
                int overlayHeight = component.getHeight();
                int xPosition = (frameWidth - overlayWidth) / 2;
                int yPosition = (frameHeight - overlayHeight) / 2;
                component.setBounds(xPosition, yPosition, overlayWidth, overlayHeight);
            }
        }
    }



    /**
     * Adds a new scene to the manager.
     *
     * @param sceneName The name of the scene.
     * @param scene     The JPanel representing the scene.
     */
    public void addScene(String sceneName, JPanel scene) {
        sceneContainer.add(scene, sceneName); // Add scene with a name
        scenes.add(scene);
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
    public static void showOverlay(JPanel overlayPanel) {
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
    public static void hideOverlay(JPanel overlayPanel) {
    	overlayPanel.setVisible(false);
        layeredPane.remove(overlayPanel); // Remove the settings panel from the layered pane
    }
    
    public void setWindowSize(int sizeX, int sizeY)
    {
    	GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if(fullscreen)
        {
        	graphicsDevice.setFullScreenWindow(null);

            frame.setSize(sizeX, sizeY); 
        	fullscreen = false;
        }
        else
        {
        	frame.setSize(sizeX, sizeY); 
        }
        sceneContainer.setBounds(0, 0, frame.getWidth(), frame.getHeight());
    }

    /**
     * Toggles the fullscreen mode of the application window.
     * If currently in fullscreen mode, it exits to windowed mode with a default size.
     * If not in fullscreen, it attempts to set the window to fullscreen mode.
     *
     * <p>
     * Updates the bounds of the `sceneContainer` to match the window size after toggling.
     * </p>
     *
     * <p>
     * Note: This method checks if fullscreen mode is supported before applying it.
     * If not supported, the application remains in windowed mode with a default size.
     * </p>
     */
    public void toggleFullScreen()
    {
    	//is this safe??? source: OpenAI ChatGPT 4o
        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if(fullscreen)
        {
            graphicsDevice.setFullScreenWindow(null);
            //get maxheight and maxwidth of screen
            //GraphicsConfiguration[] gc= graphicsDevice.getConfigurations();
            //System.out.println(gc[0].getBounds());
            //gc[0].getBounds().width, gc[0].getBounds().height
        	frame.setSize(500,500);

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
        // Resize the sceneContainer and its current scene
        sceneContainer.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        // "Commit" changes
        frame.setVisible(true);
    }

    /**
     * Retrieves the {@link GameScene} instance from the list of scenes.
     *
     * <p>
     * This method iterates through the scenes and returns the first instance of
     * {@link GameScene} it encounters. If no {@link GameScene} is found, it returns {@code null}.
     * </p>
     *
     * @return the {@link GameScene} instance if present, or {@code null} if no such scene exists
     */
    public GameScene getGameScene()
    {
    	for(JPanel scene : scenes)
    	{
    		if(scene instanceof GameScene)
    		{
    			return (GameScene)scene;
    		}
    	}
    	return null;
    }
}
