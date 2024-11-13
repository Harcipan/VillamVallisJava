package graphics.scenes;

import java.awt.*;

import javax.swing.*;

import gamemanager.SceneManager;
import graphics.components.MenuButton;
import interfaces.GameLoopCallback;

@SuppressWarnings("serial")
public class MainMenu extends JPanel {
	private GameLoopCallback glCallback;
    public MainMenu(SceneManager manager, GameLoopCallback glCback) {
    	this.glCallback = glCback;
        setLayout(new GridLayout(6, 1, 0, 30));

        JLabel menuText = new JLabel("Main Menu");
        menuText.setFont(this.getFont().deriveFont(50.0f));
        //set font to bold
        menuText.setFont(menuText.getFont().deriveFont(Font.BOLD));


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
        JButton editorButton = new MenuButton("Editor");
        JButton settingsButton = new MenuButton("Settings");
        JButton exitButton = new MenuButton("Exit");

        newGameButton.addActionListener(e -> {manager.showScene("GameScene"); glCallback.newGame(); glCallback.setPlaying(true);});
        //TODO: add logic to disable if there are no saves to continue or serial version isn't matching
        contGameButton.addActionListener(e -> {manager.showScene("GameScene"); glCallback.continueGame();glCallback.setPlaying(true);});
        editorButton.addActionListener(e -> manager.showScene("EditorScene"));
        settingsButton.addActionListener(e -> {
            JPanel settingsPanel = createSettingsPanel(manager);
            manager.showOverlay(settingsPanel);
        });
        exitButton.addActionListener(e -> System.exit(0));

        add(textPanel);
        add(newGameButton);
        add(contGameButton);
        add(editorButton);
        add(settingsButton);
        add(exitButton);
    }
    
    private JPanel createSettingsPanel(SceneManager manager) {
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayout(3, 1, 10, 10));
        

        //Extra: Implement after audio
        //settingsPanel.add(new MenuButton("Audio Settings"));
        
        JButton fullScreenButton = new MenuButton("FullScreen");
        fullScreenButton.addActionListener(e->{manager.toggleFullScreen();manager.hideOverlay(settingsPanel);});
        //slider to change music volume
        JSlider musicVolume = new JSlider(JSlider.HORIZONTAL, 0, 100, (int)(manager.soundPlayer.getVolume()));
        musicVolume.addChangeListener(e -> manager.soundPlayer.setVolume(musicVolume.getValue()));

        JButton closeButton = new MenuButton("Close");
        closeButton.addActionListener(e -> manager.hideOverlay(settingsPanel));

        settingsPanel.add(fullScreenButton);
        JPanel musicSettings = new JPanel();
        musicSettings.add(new JLabel("Music Volume"));
        musicSettings.add(musicVolume);
        settingsPanel.add(musicSettings);
        settingsPanel.add(closeButton);
        
        return settingsPanel;
    }
}
