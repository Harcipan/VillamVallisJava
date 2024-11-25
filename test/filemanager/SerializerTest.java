package filemanager;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gamemanager.GameLoop;

public class SerializerTest {
	
	private GameLoop testGameLoop;
	private Serializer ser;
	
	@BeforeEach
	public void testSetup()
	{
        testGameLoop = new GameLoop();
        ser = new Serializer();
	}

    @Test()
    public void testLoadandSaveData() throws ClassNotFoundException, IOException {
        testGameLoop = new GameLoop();
        testGameLoop.money = 200;
        ser.saveData(testGameLoop, "saves/SerializeTest.dat");

        testGameLoop = (GameLoop) ser.loadData("saves/SerializeTest.dat");
    }
    
    @Test
    public void testLoadIOException() throws IOException, ClassNotFoundException
    {
        assertThrows(IOException.class, () -> ser.loadData("saves/nonExistentSerializeTest.dat"));
    }

    
}
