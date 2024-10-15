package filemanager;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import gamemanager.GameLoop;

public class SerializerTest {
	
	private GameLoop testGameLoop;
	private Serializer ser;
	
	@Before
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
        assertNotNull("The loaded object is null!", testGameLoop);
        assertEquals("The money value was not loaded correctly!", 200, testGameLoop.money);
    }
    
    @Test(expected=IOException.class)
    public void testLoadIOException() throws IOException, ClassNotFoundException
    {
		testGameLoop = (GameLoop) ser.loadData("saves/nonExistentSerializeTest.dat");
    }
    
    //TO-DO: get IOExc from save
    @Test//(expected=IOException.class)
    public void testSaveOException() throws IOException
    {
		//ser.saveData(testGameLoop, "saves/nonExistentSerializeTest.dat");
    }
    
}
