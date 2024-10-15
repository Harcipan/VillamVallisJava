package gamemanager;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//import graphics.scenes.GameScene;
import filemanager.Serializer;
import graphics.GamePanel;
import graphics.camera.Camera;
import input.KeyHandler;
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
    private transient Thread gameLoopT = new Thread(this::startGameLoop);
    //private transient Thread inputT;
    private transient boolean playing;
    private transient KeyHandler keyHandler;
    private transient Camera camera;
    private transient GamePanel gp;
    private double moveSpeed = 2;
	private transient boolean firstTime = true;


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
    public GameLoop() {
		ser = new Serializer();
		money = 0;
		playing = true;
	}

    public void loopSetup(KeyHandler keyHandler, Camera camera, GamePanel gp)
    {
        this.keyHandler = keyHandler;
        this.camera = camera;
        this.gp = gp;
    }

	public void newGame()
	{
		setMoney(0);
		if(firstTime)
		{
			gameLoopT = new Thread(this::startGameLoop);
			gameLoopT.start();
			firstTime=false;
		}
	}
	public void continueGame()
	{
		loadGame();
		if(firstTime)
		{
			gameLoopT = new Thread(this::startGameLoop);
			gameLoopT.start();
			firstTime=false;
		}
	}

	 /**
     * Starts the game loop, processing user input and updating game state.
     */
	public void startGameLoop()
	{
    	long lastTime = System.nanoTime();
    	final double nsPerFrame = 1_000_000_000.0 / 60.0; // 60 frames per second
    	double deltaTime = 0;


        int TARGET_FPS = 300; // Target frames per second
        long OPTIMAL_TIME = 1000000000 / TARGET_FPS; // Time per frame in nanoseconds
        long lastFpsTime = System.currentTimeMillis();
        //int fps = 0;

		//inputT = new Thread(this::readInput);
		//inputT.start();
		while(true)
		{
			long now = System.nanoTime();
			deltaTime += (now - lastTime) / nsPerFrame;
			lastTime = now;
			if (playing) {


				// Process movement when enough time has passed (i.e., one frame)
				while (deltaTime >= 100) {

					moveCamera(deltaTime/100.0); // Update movement
					deltaTime--;
					//System.out.println(playing);
				}

				gp.repaint(); // Really IMPORTANT for smoother movement

				//lastLoopTime = now;
				//fps++;

				// Manage FPS
				/*if (System.currentTimeMillis() - lastFpsTime >= 1000) {
					//System.out.println("FPS: " + fps);
					lastFpsTime += 1000;
					//fps = 0;
				}*/

				// Sleep to maintain FPS
				// for smoother gameplay

			}
			try {
				long sleepTime = (now - System.nanoTime() + OPTIMAL_TIME) / 1000000; // Convert to milliseconds
				if (sleepTime > 0) {
					Thread.sleep(sleepTime);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//System.out.println("Game Paused");
    	}

	}

	public void readInput()
	{
		isr = new InputStreamReader(System.in);
		br = new BufferedReader(isr);



		String s = "";
		try {

			while((s = br.readLine())!=null && playing)
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

	}


	private void moveCamera(double deltaTime) {
        double deltaX = 0;
        double deltaY = 0;

        Set<Integer> pressedKeys = keyHandler.getPressedKeys();
        if (pressedKeys.contains(KeyEvent.VK_W)) {
            deltaY -= moveSpeed * deltaTime;
        }
        if (pressedKeys.contains(KeyEvent.VK_S)) {
            deltaY += moveSpeed * deltaTime;
        }
        if (pressedKeys.contains(KeyEvent.VK_A)) {
            deltaX -= moveSpeed * deltaTime;
        }
        if (pressedKeys.contains(KeyEvent.VK_D)) {
            deltaX += moveSpeed * deltaTime;
        }
        camera.moveCamera(deltaX, deltaY);
        gp.repaint();
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

	public void setPlaying(boolean p)
	{
		playing = p;
		if(playing)
		{
			//gameLoopT = new Thread(this::startGameLoop);
			//gameLoopT.start();
		}
	}


	//is this how this should be???
	//(doesn't actually matter because I won't use input stream)
	public void finalize()
	{
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
