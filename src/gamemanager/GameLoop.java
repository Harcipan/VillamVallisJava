package gamemanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

import filemanager.Serializer;
/**
 * The GameLoop class manages the game's main loop, handling user input and game state.
 * It allows saving and loading the game state.
 */
@SuppressWarnings("serial")
public class GameLoop implements Serializable {
	public int money;
	private transient InputStreamReader isr;
	private transient BufferedReader br;
	/**
     * Initializes a new instance of GameLoop with default values.
     */
	public GameLoop()
	{
		money = 0;
	}
	 /**
     * Starts the game loop, processing user input and updating game state.
     */
	public void startGameLoop()
	{
		isr = new InputStreamReader(System.in);
		br = new BufferedReader(isr);
		
		String s = "";
			try {
				while((s = br.readLine()) != null)
				{
					if(s.equals("save"))
					{
						System.out.println(money);
						Serializer ser = new Serializer();
						ser.saveData(this, "saves/gameSave.txt");
					}
					else if(s.equals("load"))
					{
						Serializer ser = new Serializer();
						GameLoop loadedGame;
						try {
							loadedGame = (GameLoop)ser.loadData("saves/gameSave.txt");
							this.money = loadedGame.money;
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
						System.out.println(money);
					}
					else if(s.equals("money"))
					{
						System.out.println(money);
					}
					else
					{
				        try {
				            int number = Integer.parseInt(s);
				            money=number;
				        } catch (NumberFormatException e) {
				            System.out.println("The input is not a valid number.");
				        }
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
