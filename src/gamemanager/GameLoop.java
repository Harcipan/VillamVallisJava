package gamemanager;

import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import java.io.IOException;

import filemanager.Serializer;
import filemanager.TileMapSerializer;
import gameObject.tiles.TileMap;
import graphics.camera.Camera;
import graphics.scenes.GameScene;
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
    public static boolean playing;
    private transient KeyHandler keyHandler;
    private transient JPanel gp;
    private float moveSpeed = 2;
	private transient boolean firstTime = true;
	private transient ScheduledExecutorService saveScheduler;
	public static TileMap tileMap;
	Vec2 cameraSave;
	public static String savePath = "saves/game1";



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

		tileMap= new TileMap(new String[3][3]);

		saveScheduler = Executors.newSingleThreadScheduledExecutor();
		saveScheduler.scheduleAtFixedRate(this::autoSave, 600, 600, TimeUnit.SECONDS);

	}


	// Method to handle automatic saving only if the game is playing
	private void autoSave() {
		if (playing) {
			System.out.println("Autosaving...");
			saveGame();
		}
	}

    public void loopSetup(KeyHandler keyHandler, JPanel gp)
    {
        this.keyHandler = keyHandler;
        this.gp = gp;
    }

	public void newGame()
	{
		setMoney(0);
		tileMap = new TileMap(new String[5][5]);
		//saveGame();
		savePath = "assets/newGame";
		loadGame();
		savePath = generateUniqueSavePath("saves/newGame");

		Camera.setMaxCamera((tileMap.mapData[0].length-1)*TileMap.TILE_SIZE/2,(tileMap.mapData.length-1)*TileMap.TILE_SIZE/2);
		if(firstTime)
		{
			gameLoopT = new Thread(this::startGameLoop);
			gameLoopT.start();
			firstTime=false;
		}
	}

	private String generateUniqueSavePath(String basePath) {
		File savesDirectory = new File("saves");
		if (!savesDirectory.exists()) {
			savesDirectory.mkdirs();
		}
		int counter = 1;
		String newSavePath = basePath;
		while (new File(newSavePath).exists()) {
			newSavePath = basePath + counter;
			counter++;
		}
		savesDirectory = new File(newSavePath);
		savesDirectory.mkdirs();
		return newSavePath;
	}

	public void continueGame()
	{
		loadGame();

		Camera.setMaxCamera((tileMap.mapData[0].length-1)*TileMap.TILE_SIZE/2,(tileMap.mapData.length-1)*TileMap.TILE_SIZE/2);
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
					tileMap.addGrowthToAll();
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
		GameScene.camera.moveCamera(deltaX, deltaY);
        gp.repaint();
    }

	public void loadGame()
	{
		try {
			loadedGame = (GameLoop)ser.loadData(savePath+"/gameSave.dat");
			System.out.println(loadedGame.money);
			GameScene.player.setMoney(loadedGame.money);
			//tileMap.setTiles(loadedGame.tileMapSave);
			tileMap=loadMap();

			//gp.tileMap = tileMap;

			GameScene.camera.setwCenter(loadedGame.cameraSave);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		System.out.println(GameScene.player.money);
	}

	public void saveGame()
	{
		System.out.println(GameScene.player.money);
		money= GameScene.player.money;
		//tileMapSave = tileMap.getTiles();
		String filePath = savePath+"/tileMap.json";
		//TileMapSerializer.writeTileMapToFile(tileMap, filePath);
		TileMapSerializer.serializeTileMap(tileMap, filePath);
		cameraSave = GameScene.camera.getwCenter();
		try {
			ser.saveData(this, savePath+"/gameSave.dat");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	TileMap loadMap()
	{
		String filePath = savePath+"/tileMap.json";

		// Deserialize TileMap from file
		TileMap deserializedTileMap = TileMapSerializer.deserializeTileMap(filePath);


		if (deserializedTileMap != null) {
			System.out.println("TileMap deserialized successfully!");
			System.out.println("First tile growth stage: " + deserializedTileMap.tiles[0][0].growthStage);
			System.out.println("Is the first tile watered? " + deserializedTileMap.tiles[0][0].isWatered);
			System.out.println("Is the first tile isCultivable? " + deserializedTileMap.tiles[0][0].isCultivable);
			System.out.println("first tile texturepos" + deserializedTileMap.tiles[0][0].plantTextureYPos);
			//System.out.println(deserializedTileMap.plantTypes.getFirst().name);
		} else {
			System.out.println("Failed to deserialize TileMap.");
		}

		return deserializedTileMap;
	}

	public void setPlaying(boolean p)
	{
		playing = p;
	}

}
