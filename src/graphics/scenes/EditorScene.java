package graphics.scenes;

import gameObject.Player;
import gameObject.tiles.Plant;
import gamemanager.GameLoop;
import gamemanager.SceneManager;
import graphics.GameFrame;
import graphics.camera.Camera;
import graphics.components.UIPanel;
import input.KeyHandler;
import input.MouseHandler;
import interfaces.GameLoopCallback;
import graphics.components.SettingsPanel;
import interfaces.GameObserver;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

public class EditorScene extends Scene implements GameObserver {
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

    public EditorScene(SceneManager manager, GameLoop gameLoop) {
        //gameLoop.loadGame();
        setLayout(new BorderLayout());
        this.manager = manager;
        this.gameLoop = gameLoop;

        // Initialize the custom table model and table
        plantTableModel = new PlantTableModel(GameLoop.tileMap.plantTypes);
        plantTable = new JTable(plantTableModel);
        JScrollPane tableScrollPane = new JScrollPane(plantTable);

        // Add components to the scene
        add(new JLabel("This is the editor scene!"), BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);

        // Add controls for adding and deleting plants
        JPanel controlPanel = new JPanel(new FlowLayout());

        JButton addPlantButton = new JButton("Add Plant");
        deleteTextField = new JTextField(5);
        JButton deletePlantButton = new JButton("Delete Plant");
        JButton backToMenuButton = new JButton("Back to Menu");

        controlPanel.add(addPlantButton);
        controlPanel.add(new JLabel("Name to Delete:"));
        controlPanel.add(deleteTextField);
        controlPanel.add(deletePlantButton);
        controlPanel.add(backToMenuButton);

        add(controlPanel, BorderLayout.SOUTH);

        // Add plant button functionality
        addPlantButton.addActionListener(e -> {
            Plant newPlant = new Plant();
            newPlant.name = "New Plant " + (GameLoop.tileMap.plantTypes.size() + 1);
            newPlant.growthStage = 0; // Example default data
            newPlant.isWatered = false; // Example default data
            GameLoop.tileMap.plantTypes.add(newPlant);
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
                plantTableModel.fireTableDataChanged(); // Refresh table
            } else {
                JOptionPane.showMessageDialog(this, "No plant found with the given name.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            deleteTextField.setText(""); // Clear the text field
        });

        backToMenuButton.addActionListener(e -> {
            manager.showScene("MainMenu");
            gameLoop.saveGame();
        });

        // Initial population of the table
        plantTableModel.fireTableDataChanged();
    }

    @Override
    public void onMoneyChange(int newScore) {
        // Handle money change events if needed
    }

    // Custom table model for the plant data
    private static class PlantTableModel extends AbstractTableModel {
        private final List<Plant> plants;

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
                    return "Growth Stage";
                case 2:
                    return "Is Watered";
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
                    return plant.growthStage;
                case 2:
                    return plant.isWatered;
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
                        plant.growthStage = Integer.parseInt(aValue.toString());
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Growth Stage must be an integer.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case 2:
                    plant.isWatered = Boolean.parseBoolean(aValue.toString());
                    break;
            }
            fireTableCellUpdated(rowIndex, columnIndex); // Notify table of the change
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex < 3; // Editable columns
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
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
