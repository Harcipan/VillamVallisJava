package graphics.scenes.editor;

import gameObject.Player;
import gameObject.tiles.Plant;
import gamemanager.GameLoop;
import gamemanager.SceneManager;
import graphics.camera.Camera;
import graphics.components.UIPanel;
import graphics.scenes.Scene;
import input.KeyHandler;
import input.MouseHandler;
import interfaces.GameLoopCallback;
import graphics.components.SettingsPanel;
import interfaces.GameObserver;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

public class PlantEditor extends Scene implements GameObserver {
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

    public PlantEditor(SceneManager manager, GameLoop gameLoop) {
        gameLoop.loadGame();
        setLayout(new GridLayout(4, 1));
        this.manager = manager;
        this.gameLoop = gameLoop;

        // Initialize the custom table model and table
        plantTableModel = new PlantTableModel(GameLoop.tileMap.plantTypes);
        plantTable = new JTable(plantTableModel);
        JScrollPane tableScrollPane = new JScrollPane(plantTable);
        // Add components to the scene
        add(new JLabel("This is the editor scene!"));
        add(tableScrollPane);

        // Add controls for adding and deleting plants
        JPanel controlPanel = new JPanel(new FlowLayout());

        JButton addPlantButton = new JButton("Add Plant");
        deleteTextField = new JTextField(5);
        JButton deletePlantButton = new JButton("Delete Plant");
        JButton copyPlantButton = new JButton("Copy Plant");
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
            Plant newPlant = new Plant();
            newPlant.name = "New Plant " + (GameLoop.tileMap.plantTypes.size() + 1);
            newPlant.growthSpeed = 0; // Example default data
            newPlant.textureYPos = 0; // Example default data
            GameLoop.tileMap.plantTypes.add(newPlant);
            plantTableModel.plants=GameLoop.tileMap.plantTypes;
            plantTableModel.fireTableDataChanged(); // Notify the table model of the new data
        });

        // Delete plant button functionality
        deletePlantButton.addActionListener(e -> {
            String nameToDelete = deleteTextField.getText().trim();
            if (nameToDelete.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a plant name to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean removed = GameLoop.tileMap.plantTypes.removeIf(plant -> plant.name.equals(nameToDelete));
            if (removed) {
                JOptionPane.showMessageDialog(this, "Plant deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                plantTableModel.plants=GameLoop.tileMap.plantTypes;
                plantTableModel.fireTableDataChanged(); // Refresh table
            } else {
                JOptionPane.showMessageDialog(this, "No plant found with the given name.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            plantTableModel.plants=GameLoop.tileMap.plantTypes;
            deleteTextField.setText(""); // Clear the text field
        });

        copyPlantButton.addActionListener(e -> {
            Plant copiedPlant = new Plant();
            String nameToCopy = deleteTextField.getText().trim();
            if (nameToCopy.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a plant name to copy.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for(Plant plant : GameLoop.tileMap.plantTypes) {
                if (plant.name.equals(nameToCopy)) {
                    copiedPlant.name = plant.name + " Copy";
                    copiedPlant.growthSpeed = plant.growthSpeed;
                    copiedPlant.textureYPos = plant.textureYPos;
                    break;
                }
            }

            GameLoop.tileMap.plantTypes.add(copiedPlant);
            plantTableModel.plants=GameLoop.tileMap.plantTypes;
            deleteTextField.setText(""); // Clear the text field
            plantTableModel.fireTableDataChanged(); // Refresh table
        });

        backToMenuButton.addActionListener(e -> {
            manager.showScene("MainMenu");
            gameLoop.saveGame();
        });

        // Initial population of the table
        plantTableModel.plants=GameLoop.tileMap.plantTypes;
        plantTableModel.fireTableDataChanged();
    }

    @Override
    public void onMoneyChange(int newScore) {
        // Handle money change events if needed
    }

    // Custom table model for the plant data
    private static class PlantTableModel extends AbstractTableModel {
        private List<Plant> plants;

        public PlantTableModel(List<Plant> plants) {
            this.plants = plants;
        }

        @Override
        public int getRowCount() {
            return plants.size();
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
                    return "Texture Position";
                default:
                    return "";
            }
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Plant plant = plants.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return plant.name;
                case 1:
                    return plant.growthSpeed;
                case 2:
                    return plant.textureYPos;
                default:
                    return null;
            }
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            Plant plant = plants.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    plant.name = aValue.toString();
                    break;
                case 1:
                    try {
                        plant.growthSpeed = Integer.parseInt(aValue.toString());
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Growth Stage must be an integer.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case 2:
                    plant.textureYPos = Integer.parseInt(aValue.toString());
                    break;
            }
            plants=GameLoop.tileMap.plantTypes;
            fireTableCellUpdated(rowIndex, columnIndex); // Notify table of the change
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex < 3; // Editable columns
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return String.class;
                case 1:
                    return Integer.class;
                case 2:
                    return Integer.class;
                default:
                    return String.class;
            }
        }
    }
}
