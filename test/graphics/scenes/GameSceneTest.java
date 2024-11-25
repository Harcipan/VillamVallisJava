package graphics.scenes;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

import gamemanager.GameLoop;
import gamemanager.SceneManager;
import graphics.GameFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameSceneTest {

	Robot robot;
    private GameScene gameScene;
    private GameLoop gameLoop;
    private SceneManager manager;
    private GameFrame gf;

    @BeforeEach
    public void setUp() throws AWTException {
        gameLoop = new GameLoop();
    	gf = new GameFrame();
        manager = new SceneManager(gf);
        gameScene = new GameScene(manager, gameLoop);
        manager.addScene("GameScene", gameScene);
        manager.showScene("GameScene");
		robot = new Robot();
    }
	
	@Test
	public void test()
	{
		robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.keyRelease(KeyEvent.VK_ESCAPE);
		robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.keyRelease(KeyEvent.VK_ESCAPE);
	}
}
