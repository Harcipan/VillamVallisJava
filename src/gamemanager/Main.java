package gamemanager;
import graphics.GameFrame;
import java.io.IOException;

/**
 * The Main class serves as the entry point for the game application.
 * It initializes the game frame and manages the game loop.
 */
public class Main {

	static GameFrame gf;
	/**
     * The main method to start the game application.
     *
     */
	public static void main(String[] args) throws IOException
	{
		gf = new GameFrame();
		gf.setVisible(true);
	}
}
