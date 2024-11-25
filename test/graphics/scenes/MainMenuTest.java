package graphics.scenes;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import org.junit.jupiter.api.Test;

import gamemanager.SceneManager;
import graphics.GameFrame;
import interfaces.GameLoopCallback;
import org.junit.jupiter.api.BeforeEach;

public class MainMenuTest {
    private MainMenu mainMenu;
    private SceneManager sceneManager;
    private GameLoopCallback glCallback;

    @BeforeEach
    public void setUp() {
        sceneManager = new SceneManager(new GameFrame()); // You may need to implement a mock or real SceneManager
        glCallback = new GameLoopCallback() {
            @Override
            public void newGame() {
                // Logic for new game
            }

            @Override
            public void continueGame() {
                // Logic for continuing the game
            }

			@Override
			public void saveGame() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void setPlaying(boolean p) {
				// TODO Auto-generated method stub
				
			}
        };
        mainMenu = new MainMenu(sceneManager, glCallback);
    }

    @Test
    public void testNewGameButton() {
        // Simulate pressing the "New Game" button
        for (java.awt.Component button : mainMenu.getComponents()) {
            if (button instanceof JButton && ((AbstractButton) button).getText().equals("Settings")) {
                ((JButton) button).doClick(); // Simulate button click
                break; // Exit after simulating the click
            }
        }
    }
}
