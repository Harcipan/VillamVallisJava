package graphics.scenes.editor;

import gameObject.Player;
import gameObject.tiles.Ground;
import gameObject.tiles.Plant;
import gamemanager.GameLoop;
import gamemanager.SceneManager;
import graphics.camera.Camera;
import graphics.components.SettingsPanel;
import graphics.components.UIPanel;
import graphics.scenes.Scene;
import input.KeyHandler;
import input.MouseHandler;
import interfaces.GameLoopCallback;
import interfaces.GameObserver;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

public class GroundEditor extends Scene implements GameObserver {
    private static final long serialVersionUID = 1L;
    SceneManager manager;
    SettingsPanel settingsPanel;
    private JLabel moneyText;
    private GameLoopCallback glCallback;
    public Player player;
    JLayeredPane layeredPane;
    UIPanel ui;
    public Camera camera;
    public KeyHandler keyHandler;
    MouseHandler mouseHandler;
    public GameLoop gameLoop;

    private JTable plantTable;
    private PlantTableModel plantTableModel;
    private JTextField deleteTextField;

    public GroundEditor(SceneManager manager, GameLoop gameLoop) {
        gameLoop.loadGame();
        setLayout(new GridLayout(4, 1));
        this.manager = manager;
        this.gameLoop = gameLoop;

        // Initialize the custom table model and table
        plantTableModel = new PlantTableModel(GameLoop.tileMap.groundTypes);
        plantTable = new JTable(plantTableModel);
        JScrollPane tableScrollPane = new JScrollPane(plantTable);
        // Add components to the scene
        add(new JLabel("This is the ground editor scene!"));
        add(tableScrollPane);

        // Add controls for adding and deleting plants
        JPanel controlPanel = new JPanel(new FlowLayout());

        JButton addPlantButton = new JButton("Add Ground");
        deleteTextField = new JTextField(5);
        JButton deletePlantButton = new JButton("Delete Ground");
        JButton copyPlantButton = new JButton("Copy Ground");
        JButton backToMenuButton = new JButton("Back to Menu");

        controlPanel.add(addPlantButton);
        controlPanel.add(new JLabel("Name to Delete:"));
        controlPanel.add(deleteTextField);
        controlPanel.add(copyPlantButton);
        controlPanel.add(deletePlantButton);
        controlPanel.add(backToMenuButton);

        add(controlPanel);

        // Add plant button functionality
        addPlantButton.addActionListener(e -> {
            Ground newGround = new Ground();
            newGround.name = "New Ground " + (GameLoop.tileMap.groundTypes.size() + 1);
            newGround.growthSpeed = 0;
            newGround.isCultivable = true;
            newGround.textureYPos = 0;
            GameLoop.tileMap.groundTypes.add(newGround);
            plantTableModel.grounds=GameLoop.tileMap.groundTypes;
            plantTableModel.fireTableDataChanged(); // Notify the table model of the new data
        });

        // Delete plant button functionality
        deletePlantButton.addActionListener(e -> {
            String nameToDelete = deleteTextField.getText().trim();
            if (nameToDelete.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a ground name to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean removed = GameLoop.tileMap.groundTypes.removeIf(ground -> ground.name.equals(nameToDelete));
            if (removed) {
                JOptionPane.showMessageDialog(this, "Ground deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                plantTableModel.grounds=GameLoop.tileMap.groundTypes;
                plantTableModel.fireTableDataChanged(); // Refresh table
            } else {
                JOptionPane.showMessageDialog(this, "No ground found with the given name.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            plantTableModel.grounds=GameLoop.tileMap.groundTypes;
            deleteTextField.setText(""); // Clear the text field
        });

        copyPlantButton.addActionListener(e -> {
            Ground copiedGround = new Ground();
            String nameToCopy = deleteTextField.getText().trim();
            if (nameToCopy.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a ground name to copy.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for(Plant plant : GameLoop.tileMap.plantTypes) {
                if (plant.name.equals(nameToCopy)) {
                    copiedGround.name = plant.name + " Copy";
                    copiedGround.growthSpeed = plant.growthSpeed;
                    copiedGround.textureYPos = plant.textureYPos;
                    break;
                }
            }

            GameLoop.tileMap.groundTypes.add(copiedGround);
            plantTableModel.grounds=GameLoop.tileMap.groundTypes;
            deleteTextField.setText(""); // Clear the text field
            plantTableModel.fireTableDataChanged(); // Refresh table
        });

        backToMenuButton.addActionListener(e -> {
            manager.showScene("MainMenu");
            gameLoop.saveGame();
        });

        // Initial population of the table
        plantTableModel.grounds=GameLoop.tileMap.groundTypes;
        plantTableModel.fireTableDataChanged();
    }

    @Override
    public void onMoneyChange(int newScore) {
        // Handle money change events if needed
    }

    // Custom table model for the plant data
    private static class PlantTableModel extends AbstractTableModel {
        private List<Ground> grounds;

        public PlantTableModel(List<Ground> grounds) {
            this.grounds = grounds;
        }

        @Override
        public int getRowCount() {
            return grounds.size();
        }

        @Override
        public int getColumnCount() {
            return 3; // Example: name, growthStage, isWatered
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Name";
                case 1:
                    return "Growth Speed";
                case 2:
                    return "cultivable";
                case 3:
                    return "Texture Position";
                default:
                    return "";
            }
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Ground ground = grounds.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return ground.name;
                case 1:
                    return ground.growthSpeed;
                case 2:
                    return ground.isCultivable;
                case 3:
                    return ground.textureYPos;
                default:
                    return null;
            }
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            Ground ground = grounds.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    ground.name = aValue.toString();
                    break;
                case 1:
                    try {
                        ground.growthSpeed = Integer.parseInt(aValue.toString());
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Growth Stage must be an integer.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case 2:
                    ground.isCultivable = Boolean.parseBoolean(aValue.toString());
                case 3:
                    ground.textureYPos = Integer.parseInt(aValue.toString());
                    break;
            }
            grounds=GameLoop.tileMap.groundTypes;
            fireTableCellUpdated(rowIndex, columnIndex); // Notify table of the change
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex < 4; // Editable columns
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return String.class;
                case 1:
                    return Integer.class;
                case 2:
                    return Boolean.class;
                default:
                    return String.class;
            }
        }
    }
}
