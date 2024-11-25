package graphics.scenes.editor;

import gamemanager.SceneManager;
import graphics.components.MenuButton;
import graphics.scenes.Scene;

import javax.swing.*;
import java.awt.*;

public class EditorMenu extends Scene {
    public EditorMenu(SceneManager manager) {
        setLayout(new BorderLayout());

        JLabel menuText = new JLabel("Editor Menu", SwingConstants.CENTER);
        menuText.setFont(this.getFont().deriveFont(50.0f));
        //set font to bold

        menuText.setFont(menuText.getFont().deriveFont(Font.BOLD));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));

        JButton plantEditorButton = new MenuButton("Plant Editor");
        JButton tileEditorButton = new MenuButton("Ground Editor");
        JButton mapEditorButton = new MenuButton("Map Editor");

        plantEditorButton.addActionListener(e -> manager.showScene("PlantEditor"));
        tileEditorButton.addActionListener(e -> manager.showScene("TileEditor"));
        mapEditorButton.addActionListener(e -> manager.showScene("MapEditor"));

        add(menuText, BorderLayout.NORTH);
        buttonPanel.add(plantEditorButton);
        buttonPanel.add(tileEditorButton);
        buttonPanel.add(mapEditorButton);
        add(buttonPanel, BorderLayout.CENTER);
    }
}
