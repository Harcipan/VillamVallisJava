package graphics.scenes;

import gamemanager.GameLoop;
import gamemanager.SceneManager;
import graphics.GameFrame;
import graphics.components.MenuButton;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SavesMenu extends Scene {
    private GameLoop gameLoop;
    public SavesMenu(SceneManager manager, GameLoop gameLoop) {
        setLayout(new BorderLayout());
        this.gameLoop=gameLoop;

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
                    saveButton.addActionListener(e -> {gameLoop.loadGame(); GameLoop.savePath = "saves/" + saveName; manager.showScene("GameScene");
                    GameLoop.playing = true; GameFrame.getInstance().requestFocus(); gameLoop.continueGame();});
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

    // Method to handle save loading
    private void loadSave(String saveName) {
        System.out.println("Loading save: " + saveName);
        // Add logic here to load the save
    }
}
