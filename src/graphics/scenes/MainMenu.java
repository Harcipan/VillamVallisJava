package graphics.scenes;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import graphics.SceneManager;
import graphics.components.MenuButton;

@SuppressWarnings("serial")
public class MainMenu extends JPanel {
    public MainMenu(SceneManager manager) {
        setLayout(new GridLayout(6, 1, 0, 30));

        JLabel menuText = new JLabel("Main Menu");
        JPanel textPanel = new JPanel();
        // Use GridBagLayout for more control over positioning
        textPanel.setLayout(new GridBagLayout());
        //textPanel.setBackground(new Color(10, 100, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Column
        gbc.gridy = 0; // Row
        gbc.weightx = 1.0; // Allow horizontal expansion
        gbc.weighty = 1.0; // Allow vertical expansion
        gbc.anchor = GridBagConstraints.CENTER; // Center the label
        textPanel.add(menuText, gbc);
        
        JButton newGameButton = new MenuButton("New Game");
        JButton contGameButton = new MenuButton("Continue Game");
        JButton settingsButton = new MenuButton("Settings");
        JButton exitButton = new MenuButton("Exit");

        newGameButton.addActionListener(e -> manager.showScene("GameScene"));
        contGameButton.addActionListener(e -> manager.showScene("GameScene"));
        /*settingsButton.addActionListener(e -> {
            JPanel settingsPanel = new SettingsScene(manager);
            manager.showOverlay(settingsPanel);
        });*/
        settingsButton.addActionListener(e -> {
            JPanel settingsPanel = createSettingsPanel(manager);
            manager.showOverlay(settingsPanel);
        });
        exitButton.addActionListener(e -> System.exit(0));

        add(textPanel);
        add(newGameButton);
        add(contGameButton);
        add(settingsButton);
        add(exitButton);
    }
    
    // Create the settings panel
    private JPanel createSettingsPanel(SceneManager manager) {
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayout(3, 1, 10, 10));
        
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> manager.hideOverlay(settingsPanel));

        settingsPanel.add(new MenuButton("Audio Settings"));
        settingsPanel.add(new MenuButton("Graphics Settings"));
        settingsPanel.add(closeButton);
        
        return settingsPanel;
    }
}
