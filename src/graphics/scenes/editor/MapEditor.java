package graphics.scenes.editor;

import gamemanager.GameLoop;
import gamemanager.SceneManager;
import graphics.scenes.Scene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapEditor extends Scene {
    private static final long serialVersionUID = 1L;

    private SceneManager manager;
    private int rows = 5; // Default map size
    private int cols = 5;
    private String[][] groundMap;
    private JPanel mapGridPanel;
    public static JComboBox<String> groundTypeSelector;
    private GameLoop gameLoop;

    public MapEditor(SceneManager manager, GameLoop gameLoop) {
        this.manager = manager;
        this.gameLoop = gameLoop;
        gameLoop.loadGame();
        rows = GameLoop.tileMap.mapData.length;
        cols = GameLoop.tileMap.mapData[0].length;
        this.groundMap = new String[rows][cols]; // Initialize map

        setLayout(new GridLayout(3,1));
        initializeUI();
        initializeMap();
    }

    private void initializeUI() {
        // Top Control Panel
        JPanel controlPanel = new JPanel(new FlowLayout());

        JTextField rowInput = new JTextField(5);
        JTextField colInput = new JTextField(5);
        JButton setSizeButton = new JButton("Set Map Size");
        JButton backToMenuButton = new JButton("Back to Menu");

        controlPanel.add(new JLabel("Rows:"));
        controlPanel.add(rowInput);
        controlPanel.add(new JLabel("Cols:"));
        controlPanel.add(colInput);
        controlPanel.add(setSizeButton);
        controlPanel.add(backToMenuButton);

        add(controlPanel);

        // Map Grid Panel
        mapGridPanel = new JPanel();
        add(mapGridPanel);

        // Ground Type Selector
        JPanel groundSelectorPanel = new JPanel(new FlowLayout());
        groundTypeSelector = new JComboBox<>();
        GameLoop.tileMap.groundTypes.forEach(ground -> groundTypeSelector.addItem(ground.name));
        JLabel currentTypeLabel = new JLabel("Select Ground Type:");
        groundSelectorPanel.add(currentTypeLabel);
        groundSelectorPanel.add(groundTypeSelector);
        add(groundSelectorPanel);

        // Set size button action
        setSizeButton.addActionListener(e -> {
            try {
                int newRows = Integer.parseInt(rowInput.getText());
                int newCols = Integer.parseInt(colInput.getText());
                setMapSize(newRows, newCols);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter integers.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Back to menu action
        backToMenuButton.addActionListener(e -> manager.showScene("MainMenu"));
    }

    private void initializeMap() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                groundMap[i][j] = "ground"; // Default ground type
            }
        }
        GameLoop.tileMap.changeTileMapSize(rows, cols, groundMap);
        updateMapGrid();
        //gameLoop.saveGame();
    }

    private void setMapSize(int newRows, int newCols) {
        this.rows = newRows;
        this.cols = newCols;
        this.groundMap = new String[rows][cols];
        initializeMap();
    }

    private void updateMapGrid() {
        mapGridPanel.removeAll();
        mapGridPanel.setLayout(new GridLayout(rows, cols));

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JButton tileButton = new JButton(groundMap[i][j]);
                int row = i;
                int col = j;

                // Tile button action
                tileButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String selectedType = (String) groundTypeSelector.getSelectedItem();
                        groundMap[row][col] = selectedType;
                        tileButton.setText(selectedType); // Update button text
                        GameLoop.tileMap.changeTileMapSize(rows, cols, groundMap);
                        gameLoop.saveGame();
                    }
                });

                mapGridPanel.add(tileButton);
            }
        }

        mapGridPanel.revalidate();
        mapGridPanel.repaint();
    }
}
