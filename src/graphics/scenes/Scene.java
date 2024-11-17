package graphics.scenes;

import gameObject.Player;

import javax.swing.*;

public class Scene extends JPanel {
    public boolean settingsActive;
    public Player player;
    public Scene() {
        super();
        settingsActive = false;
    }
}
