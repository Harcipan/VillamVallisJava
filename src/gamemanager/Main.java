package gamemanager;
import graphics.GameFrame;
import java.io.IOException;
/**
 * The Main class serves as the entry point for the game application.
 * It initializes the game frame and manages the game loop.
 */
public class Main {

	static GameFrame gf;
	static GameLoop gl;
	String testString = "hello";
	/**
     * The main method to start the game application.
     *
     * @param args Command line arguments.
     * @throws IOException If an I/O error occurs during game initialization.
     */
	public static void main(String[] args) throws IOException
	{
		
		//Sceen selector
		//gf.loadMainMenu();

		//only shows changes (exmp: loadMainM)
		//if setVisibility is used after change
		
		// TODO: Only load GameLoop after player selects
        // "Continue Game" or "New Game".
        gl = new GameLoop();
		gf = new GameFrame(gl);
		gf.setVisible(true);
        //gl.startGameLoop();
	}
}
