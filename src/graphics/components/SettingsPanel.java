package graphics.components;

import gamemanager.SceneManager;
import graphics.scenes.GameScene;
import graphics.scenes.Scene;
import interfaces.GameLoopCallback;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {
    public SettingsPanel(GameLoopCallback glCallback, SceneManager manager, Scene scene) {
        this.setBackground(new Color(170,170,170,255));
        this.setLayout(new GridLayout(5, 1, 10, 10));

        //Extra: Implement after audio
        //settingsPanel.add(new MenuButton("Audio Settings"));

        JButton closeButton = new MenuButton("Close");
        closeButton.addActionListener(e -> {manager.hideOverlay(this); scene.settingsActive=false;
            glCallback.setPlaying(true);});
        JButton fullScreenButton = new MenuButton("FullScreen");
        fullScreenButton.addActionListener(e->{manager.toggleFullScreen();manager.hideOverlay(this);
            glCallback.setPlaying(true);});
        JButton sizeButton = new MenuButton("Size");
        sizeButton.addActionListener(e->{manager.setWindowSize(1000,600);manager.hideOverlay(this);
            glCallback.setPlaying(true);});
        JButton saveMainButton = new MenuButton("Save&ExitToMain");
        saveMainButton.addActionListener(e -> {glCallback.saveGame(); manager.showScene("MainMenu");
            manager.hideOverlay(this); scene.settingsActive=false; glCallback.setPlaying(false);});
        JButton saveExitButton = new MenuButton("Save&Exit");
        saveExitButton.addActionListener(e -> {glCallback.saveGame();try {
            Thread.sleep(1000); //have to save
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }System.exit(0);});

        this.add(closeButton);
        this.add(sizeButton);
        this.add(fullScreenButton);
        this.add(saveMainButton);
        this.add(saveExitButton);
    }
}
