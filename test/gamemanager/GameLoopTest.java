package gamemanager;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import graphics.GameFrame;
import graphics.scenes.GameScene;
import interfaces.GameObserver;

public class GameLoopTest {

    private GameScene gameScene;
    private GameLoop gameLoop;

    @Before
    public void setUp() {
        gameLoop = new GameLoop();
        SceneManager manager = new SceneManager(new GameFrame());
        gameScene = new GameScene(manager, gameLoop);
    }

    @Test
    public void testOnChangeUpdatesMoneyText() {
        gameLoop.setMoney(100);
        assertEquals("Money: 100", gameScene.getMoneyText().getText());
    }

    @Test
    public void testGameSceneObserverNotifies() {
        gameLoop.setMoney(50);
        assertEquals("Money: 50", gameScene.getMoneyText().getText());
        gameLoop.setMoney(75);
        assertEquals("Money: 75", gameScene.getMoneyText().getText());
    }

    @Test
    public void testInitialMoney() {
        assertEquals(0, gameLoop.money);
    }

    @Test
    public void testSetMoney() {
        gameLoop.setMoney(20);
        assertEquals(20, gameLoop.money);
    }

    @Test
    public void testAddObserver() {
        TestObserver observer = new TestObserver();
        gameLoop.addObserver(observer);
        gameLoop.setMoney(30);
        assertTrue(observer.isNotified);
        assertEquals(30, observer.receivedScore);
    }

    @Test
    public void testRemoveObserver() {
        TestObserver observer = new TestObserver();
        gameLoop.addObserver(observer);
        gameLoop.removeObserver(observer);
        gameLoop.setMoney(40);
        assertFalse(observer.isNotified);
    }

    @Test
    public void testSaveGame() {
        gameLoop.setMoney(50);
        gameLoop.saveGame();
        // Here you can add logic to verify if the save operation worked correctly,
        // e.g., by checking the contents of the saved file. This is a simplified test.
    }

    @Test
    public void testLoadGame() {
        gameLoop.setMoney(60);
        gameLoop.saveGame();  // Save the state first
        gameLoop.loadGame();  // Load the state
        assertEquals(60, gameLoop.money);
    }
    
    @Test
    public void testNewGame() {
        gameLoop.newGame();
        assertEquals(0, gameLoop.money);
    }
    
    @Test
    public void testContinueGame() {
        gameLoop.setMoney(60);
        gameLoop.saveGame();  // Save the state first
        gameLoop.setMoney(120);
        gameLoop.continueGame();  // Load the state
        assertEquals(60, gameLoop.money);
    }
    

    private class TestObserver implements GameObserver {
        boolean isNotified = false;
        int receivedScore;

        @Override
        public void onScoreChange(int newScore) {
            isNotified = true;
            receivedScore = newScore;
        }
    }
    

}
