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
import java.awt.*;
import java.util.Optional;

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

    private DefaultListModel<String> plantListModel; // Model for JList
    private JList<String> plantList; // JList to display plant names
    private JTextField plantNameField; // TextField for plant name input

    public EditorScene(SceneManager manager, GameLoop gameLoop) {
        setLayout(new BorderLayout());
        this.manager = manager;
        this.gameLoop = gameLoop;

        // Initialize list model and JList
        plantListModel = new DefaultListModel<>();
        plantList = new JList<>(plantListModel);
        JScrollPane scrollPane = new JScrollPane(plantList);

        // Initialize input field
        plantNameField = new JTextField(20);

        // Add components to the scene
        add(new JLabel("This is the editor scene!"), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Add buttons and input field
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addPlantButton = new JButton("Add Plant");
        JButton refreshListButton = new JButton("Refresh List");
        JButton deletePlantButton = new JButton("Delete Plant");

        buttonPanel.add(addPlantButton);
        buttonPanel.add(refreshListButton);
        buttonPanel.add(plantNameField);
        buttonPanel.add(deletePlantButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners
        addPlantButton.addActionListener(e -> {
            Plant newPlant = new Plant();
            newPlant.name = "New Plant " + (GameLoop.tileMap.plantTypes.size() + 1);
            GameLoop.tileMap.plantTypes.add(newPlant);
            GameFrame.getInstance().requestFocus(); // Return focus to the game frame
        });

        refreshListButton.addActionListener(e -> refreshPlantList());

        deletePlantButton.addActionListener(e -> deletePlantByName());

        // Initial population of the plant list
        refreshPlantList();
    }

    private void refreshPlantList() {
        // Clear the list model and repopulate it
        plantListModel.clear();
        for (Plant plant : GameLoop.tileMap.plantTypes) {
            plantListModel.addElement(plant.name);
        }
    }

    private void deletePlantByName() {
        String nameToDelete = plantNameField.getText().trim();
        if (nameToDelete.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a plant name to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Find and remove the plant with the given name
        Optional<Plant> plantToRemove = GameLoop.tileMap.plantTypes.stream()
                .filter(plant -> plant.name.equalsIgnoreCase(nameToDelete))
                .findFirst();

        if (plantToRemove.isPresent()) {
            GameLoop.tileMap.plantTypes.remove(plantToRemove.get());
            refreshPlantList();
            plantNameField.setText(""); // Clear the input field
            JOptionPane.showMessageDialog(this, "Plant deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Plant not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Return focus to the game frame
        GameFrame.getInstance().requestFocus();
    }

    @Override
    public void onMoneyChange(int newScore) {
        // Handle money change events if needed
    }
}
