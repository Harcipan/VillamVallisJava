package gamemanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


//import graphics.scenes.GameScene;
import filemanager.Serializer;
import interfaces.GameLoopCallback;
import interfaces.GameObserver;
/**
 * The GameLoop class manages the game's main loop, handling user input and game state.
 * It allows saving and loading the game state.
 */
public class GameLoop implements Serializable, GameLoopCallback{
	private static final long serialVersionUID = 1L;
	public int money;
	private transient InputStreamReader isr;
	private transient BufferedReader br;
    private transient List<GameObserver> observers = new ArrayList<>();
    private transient Serializer ser;
    private transient GameLoop loadedGame;

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    public void setMoney(int score) {
        this.money = score;
        notifyObservers();
    }

    private void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.onScoreChange(money);
        }
    }
	
	//public GameScene GameScene;
	/**
     * Initializes a new instance of GameLoop with default values.
     */
	public GameLoop()
	{
		ser = new Serializer();
		money = 0;
	}
	
	public void newGame()
	{
		setMoney(0);
		new Thread(this::startGameLoop).start();

	}
	public void continueGame()
	{
		loadGame();
		new Thread(this::startGameLoop).start();
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
						saveGame();
					}
					else if(s.equals("load"))
					{
						loadGame();
					}
					else if(s.equals("money"))
					{
						System.out.println(money);
					}
					else
					{
				        try {
				            int number = Integer.parseInt(s);
				            setMoney(number);
				            //GameScene.setMoney(money);
				        } catch (NumberFormatException e) {
				            System.out.println("The input is not a valid number.");
				        }
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadGame()
	{
		try {
			loadedGame = (GameLoop)ser.loadData("saves/gameSave.txt");
			setMoney(loadedGame.money);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		System.out.println(money);
	}
	
	public void saveGame()
	{
		System.out.println(money);
		try {
			ser.saveData(this, "saves/gameSave.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
