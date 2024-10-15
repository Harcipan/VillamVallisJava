package filemanager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The Serializer class handles the serialization and deserialization of objects.
 * It provides methods to save an object to a file and load an object from a file.
 */
public class Serializer{
	private FileOutputStream fos;
	private ObjectOutputStream outs;
	private FileInputStream fis;
	private ObjectInputStream oins;
	
	/**
     * Saves the specified object to the given file path.
     *
     * @param obj      The object to be saved.
     * @param filePath The path of the file where the object will be saved.
     * @throws IOException If an I/O error occurs during saving.
     */
	public void saveData(Object obj, String filePath) throws IOException
	{
		fos = new FileOutputStream(filePath);
		outs = new ObjectOutputStream(fos);
		outs.writeObject(obj);
		outs.close();
	}
    /**
     * Loads an object from the specified file path.
     *
     * @param filePath The path of the file from which the object will be loaded.
     * @return The loaded object.
     * @throws ClassNotFoundException If the class of the serialized object cannot be found.
     * @throws IOException If an I/O error occurs during loading.
     */
	public Object loadData(String filePath) throws ClassNotFoundException, IOException
	{
		fis = new FileInputStream(filePath);
		oins = new ObjectInputStream(fis);
			
		Object obj = (Object)oins.readObject();
		oins.close();

		return obj;
	}

}
