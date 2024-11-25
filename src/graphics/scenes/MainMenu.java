package graphics.scenes;

import java.awt.*;

import javax.swing.*;

import gamemanager.SceneManager;
import graphics.components.MenuButton;
import interfaces.GameLoopCallback;

import javax.swing.*;
import java.awt.*;

/**
 * The MainMenu class represents the main menu screen of the game.
 * It provides buttons for various game functions such as starting a new game, continuing a game, accessing the editor,
 * changing settings, and exiting the game. It is used as part of the game's scene management.
 */
@SuppressWarnings("serial")
public class MainMenu extends JPanel {

    private GameLoopCallback glCallback;

    /**
     * Constructs the MainMenu panel with the specified SceneManager and GameLoopCallback.
     * Initializes the layout, adds components (labels, buttons), and sets up action listeners for each button.
     *
     * @param manager the SceneManager responsible for managing the different scenes in the game.
     * @param glCback the GameLoopCallback that handles game state management such as starting a new game or continuing.
     */
    public MainMenu(SceneManager manager, GameLoopCallback glCback) {
        this.glCallback = glCback;
        setLayout(new GridLayout(7, 1, 0, 0));

        // Creating and setting up the main menu label
        JLabel menuText = new JLabel("Main Menu");
        menuText.setFont(this.getFont().deriveFont(50.0f));
        menuText.setFont(menuText.getFont().deriveFont(Font.BOLD));

        // Adding label to a panel
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        textPanel.add(menuText, gbc);

        // Creating buttons for various actions
        JButton newGameButton = new MenuButton("New Game");
        JButton contGameButton = new MenuButton("Continue Game");
        JButton editorButton = new MenuButton("EditorMenu");
        JButton settingsButton = new MenuButton("Settings");
        JButton exitButton = new MenuButton("Exit");

        // Adding action listeners to buttons
        newGameButton.addActionListener(e -> {
            manager.showScene("GameScene");
            glCallback.newGame();
            glCallback.setPlaying(true);
        });

        contGameButton.addActionListener(e -> manager.showScene("SavesMenu"));
        editorButton.addActionListener(e -> manager.showScene("EditorMenu"));
        settingsButton.addActionListener(e -> {
            JPanel settingsPanel = createSettingsPanel(manager);
            manager.showOverlay(settingsPanel);
        });
        exitButton.addActionListener(e -> System.exit(0));

        // Adding components to the panel
        add(textPanel);
        add(newGameButton);
        add(contGameButton);
        add(editorButton);
        add(settingsButton);
        add(exitButton);
    }

    /**
     * Creates a settings panel with options for fullscreen mode and music volume adjustment.
     * This panel is shown as an overlay over the main menu.
     *
     * @param manager the SceneManager responsible for managing scenes and sound settings.
     * @return the JPanel containing the settings options.
     */
    private JPanel createSettingsPanel(SceneManager manager) {
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayout(3, 1, 10, 10));

        // Fullscreen button
        JButton fullScreenButton = new MenuButton("FullScreen");
        fullScreenButton.addActionListener(e -> {
            manager.toggleFullScreen();
            manager.hideOverlay(settingsPanel);
        });

        // Music volume slider
        JSlider musicVolume = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) (manager.soundPlayer.getVolume()));
        musicVolume.addChangeListener(e -> manager.soundPlayer.setVolume(musicVolume.getValue()));

        // Close button
        JButton closeButton = new MenuButton("Close");
        closeButton.addActionListener(e -> manager.hideOverlay(settingsPanel));

        // Adding components to the settings panel
        settingsPanel.add(fullScreenButton);
        JPanel musicSettings = new JPanel();
        musicSettings.add(new JLabel("Music Volume"));
        musicSettings.add(musicVolume);
        settingsPanel.add(musicSettings);
        settingsPanel.add(closeButton);

        return settingsPanel;
    }
}
