package graphics.scenes;

import gameObject.Player;
import gameObject.tiles.TileMap;
import gamemanager.GameLoop;
import gamemanager.SceneManager;
import graphics.GameFrame;
import graphics.GamePanel;
import graphics.camera.Camera;
import graphics.components.UIPanel;
import input.KeyHandler;
import input.MouseHandler;
import interfaces.GameLoopCallback;
import graphics.components.SettingsPanel;
import interfaces.GameObserver;

import javax.swing.*;
import java.awt.*;

public class EditorScene extends Scene implements GameObserver {
    private static final long serialVersionUID = 1L;
    SceneManager manager;
    //public boolean settingsActive;
    SettingsPanel settingsPanel;
    private JLabel moneyText;
    private GameLoopCallback glCallback;
    public Player player;
    JLayeredPane layeredPane; // To show the UI on top of stuff
    UIPanel ui;
    public Camera camera;
    public KeyHandler keyHandler;
    MouseHandler mouseHandler;
    public GameLoop gameLoop;
    public TileMap tm;

    public EditorScene(SceneManager manager, GameLoop gameLoop) {
        setLayout(new GridLayout(1, 1));
        JLabel jl = new JLabel("This is the editor scene!");
        this.add(jl);

        this.manager=manager;
        GamePanel gp = new GamePanel(tm,player,camera);

        glCallback = (GameLoopCallback)gameLoop;
        gameLoop.addObserver(this);
        settingsPanel = new SettingsPanel(glCallback, manager, this);

    }

    @Override
    public void onMoneyChange(int newScore) {

    }

    //TODO: List out, edit and define plant types

}
