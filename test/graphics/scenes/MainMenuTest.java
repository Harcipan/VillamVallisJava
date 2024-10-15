package graphics.scenes;

import javax.swing.AbstractButton;
import javax.swing.JButton;

import org.junit.Before;
import org.junit.Test;

import gamemanager.SceneManager;
import graphics.GameFrame;
import interfaces.GameLoopCallback;

public class MainMenuTest {
    private MainMenu mainMenu;
    private SceneManager sceneManager;
    private GameLoopCallback glCallback;

    @Before
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

        // Add assertions to verify that the new game logic was executed
        // For example, if newGame() changes some state, check that state
    }

    @Test
    public void testExitButton() {
        // Simulate pressing the "Exit" button
        /*for (java.awt.Component button : mainMenu.getComponents()) {
            if (button instanceof JButton && ((AbstractButton) button).getText().equals("Exit")) {
                ((JButton) button).doClick(); // Simulate button click
                break; // Exit after simulating the click
            }
        }*/

        // Add assertions to verify that the exit logic was executed
        // Note: System.exit(0) will terminate the JVM, consider mocking it
    }
}
