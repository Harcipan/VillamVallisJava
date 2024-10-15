package interfaces;

public interface GameLoopCallback {
	void newGame();
	void continueGame();
	void saveGame();
	public void setPlaying(boolean p);
}
