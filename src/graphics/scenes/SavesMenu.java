package graphics.scenes;

import gamemanager.GameLoop;
import gamemanager.SceneManager;
import graphics.GameFrame;
import graphics.components.MenuButton;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * The SavesMenu class represents the menu screen where players can select from available save files
 * to continue their game. It dynamically loads save folders from the "saves" directory and displays them
 * as buttons. If no saves are found, a message is displayed informing the user.
 */
public class SavesMenu extends Scene {

    /**
     * Constructs the SavesMenu panel with the specified SceneManager and GameLoop.
     * Initializes the layout, adds components (labels, buttons), and loads the available saves.
     *
     * @param manager the SceneManager responsible for managing different scenes in the game.
     * @param gameLoop the GameLoop that handles the game state and loading functionality.
     */
    public SavesMenu(SceneManager manager, GameLoop gameLoop) {
        setLayout(new BorderLayout());

        // Header label
        JLabel menuText = new JLabel("Saves Menu", SwingConstants.CENTER);
        menuText.setFont(this.getFont().deriveFont(Font.BOLD, 50.0f));

        // Panel for save buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Load save folders and create buttons
        File savesDirectory = new File("saves");
        if (savesDirectory.exists() && savesDirectory.isDirectory()) {
            File[] saveFolders = savesDirectory.listFiles(File::isDirectory);
            if (saveFolders != null && saveFolders.length > 0) {
                for (File saveFolder : saveFolders) {
                    String saveName = saveFolder.getName();
                    JButton saveButton = new MenuButton(saveName);
                    saveButton.addActionListener(e -> {
                        gameLoop.loadGame();
                        GameLoop.savePath = "saves/" + saveName;
                        manager.showScene("GameScene");
                        GameLoop.playing = true;
                        GameFrame.getInstance().requestFocus();
                        gameLoop.continueGame();
                    });
                    buttonPanel.add(saveButton);
                    buttonPanel.add(Box.createVerticalStrut(10)); // Add spacing between buttons
                }
            } else {
                JLabel noSavesLabel = new JLabel("No saves available.", SwingConstants.CENTER);
                noSavesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                buttonPanel.add(noSavesLabel);
            }
        } else {
            JLabel noSavesLabel = new JLabel("Saves folder not found.", SwingConstants.CENTER);
            noSavesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            buttonPanel.add(noSavesLabel);
        }

        // Add components to the menu
        add(menuText, BorderLayout.NORTH);
        add(new JScrollPane(buttonPanel), BorderLayout.CENTER);
    }
}
