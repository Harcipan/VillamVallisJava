package filemanager;
import gameObject.tiles.Tile;
import gameObject.tiles.TileMap;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.FileReader;
import java.io.IOException;

public class TileMapDeserializer {

    public static TileMap deserializeTileMap(String filePath) {
        try (FileReader fileReader = new FileReader(filePath);
             JsonReader jsonReader = Json.createReader(fileReader)) {

            JsonObject tileMapJson = jsonReader.readObject();

            // Deserialize mapData
            JsonArray mapDataArray = tileMapJson.getJsonArray("mapData");
            int[][] mapData = new int[mapDataArray.size()][];
            for (int i = 0; i < mapDataArray.size(); i++) {
                JsonArray rowArray = mapDataArray.getJsonArray(i);
                mapData[i] = new int[rowArray.size()];
                for (int j = 0; j < rowArray.size(); j++) {
                    mapData[i][j] = rowArray.getInt(j);
                }
            }

            // Create TileMap instance
            TileMap tileMap = new TileMap(mapData);

            // Deserialize tiles
            JsonArray tilesArray = tileMapJson.getJsonArray("tiles");
            for (int i = 0; i < tilesArray.size(); i++) {
                JsonArray rowArray = tilesArray.getJsonArray(i);
                for (int j = 0; j < rowArray.size(); j++) {
                    JsonObject tileJson = rowArray.getJsonObject(j);
                    Tile tile = deserializeTile(tileJson);
                    tileMap.tiles[i][j] = tile; // Replace the default Tile with the deserialized one
                }
            }

            return tileMap;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Tile deserializeTile(JsonObject tileJson) {
        Tile tile = new Tile();
        tile.growthStage = tileJson.getInt("growthStage", 0);
        tile.isWatered = tileJson.getBoolean("isWatered", false);
        tile.isHarvestable = tileJson.getBoolean("isHarvestable", false);
        return tile;
    }
}
