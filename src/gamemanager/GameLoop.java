package gamemanager;

import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//import graphics.scenes.GameScene;
import filemanager.Serializer;
import gameObject.Player;
import gameObject.tiles.Tile;
import gameObject.tiles.TileMap;
import graphics.GamePanel;
import graphics.camera.Camera;
import graphics.transform.Vec2;
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
    private float moveSpeed = 2;
	private transient boolean firstTime = true;
	private transient ScheduledExecutorService saveScheduler;
	private transient Player player;
	public transient TileMap tileMap;
	int[][] tileMapSave;
	Vec2 cameraSave;



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
            observer.onMoneyChange(money);
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

		int[][] mapData = readWorldFile("assets/map.txt");
		this.tileMap = new TileMap(mapData);

		saveScheduler = Executors.newSingleThreadScheduledExecutor();
		saveScheduler.scheduleAtFixedRate(this::autoSave, 600, 600, TimeUnit.SECONDS);
	}

	// Read world file into mapData
	public int[][] readWorldFile(String filename) {
		List<int[]> lines = new ArrayList<>();
		try {
			File f = new File(filename);
			isr = new InputStreamReader(new FileInputStream(f));
			br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(" ");
				int[] row = new int[parts.length];
				for (int j = 0; j < parts.length; j++) {
					row[j] = Integer.parseInt(parts[j]);
				}
				lines.add(row);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines.toArray(new int[0][]);
	}


	// Method to handle automatic saving only if the game is playing
	private void autoSave() {
		if (playing) {
			System.out.println("Autosaving...");
			saveGame();
		}
	}

    public void loopSetup(KeyHandler keyHandler, Camera camera, GamePanel gp, Player player)
    {
        this.keyHandler = keyHandler;
        this.camera = camera;
        this.gp = gp;
		this.player = player;

		Camera.setMaxCamera((tileMap.mapData[0].length-1)*TileMap.TILE_SIZE/2,(tileMap.mapData.length-1)*TileMap.TILE_SIZE/2);
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
    	float deltaTime = 0;


        int TARGET_FPS = 300; // Target frames per second
        long OPTIMAL_TIME = 1000000000 / TARGET_FPS; // Time per frame in nanoseconds
        long lastFpsTime = System.currentTimeMillis();
        //int fps = 0;

		//inputT = new Thread(this::readInput);
		//inputT.start();
		while(true)
		{
			long now = System.nanoTime();
			deltaTime += (float)((now - lastTime) / nsPerFrame);
			lastTime = now;
			if (playing) {


				// Process movement when enough time has passed (i.e., one frame)
				while (deltaTime >= 100) {
					moveCamera((float) (deltaTime/100.0)); // Update movement
					deltaTime--;
					tileMap.addGrowthToAll(1);
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


	private void moveCamera(float deltaTime) {
        float deltaX = 0;
        float deltaY = 0;

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
			loadedGame = (GameLoop)ser.loadData("saves/gameSave.dat");
			System.out.println(loadedGame.money);
			player.setMoney(loadedGame.money);
			tileMap.setTiles(loadedGame.tileMapSave);
			camera.setwCenter(loadedGame.cameraSave);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		System.out.println(player.money);
	}

	public void saveGame()
	{
		System.out.println(player.money);
		money= player.money;
		tileMapSave = tileMap.getTiles();
		cameraSave = camera.getwCenter();
		try {
			ser.saveData(this, "saves/gameSave.dat");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setPlaying(boolean p)
	{
		playing = p;
	}

}
