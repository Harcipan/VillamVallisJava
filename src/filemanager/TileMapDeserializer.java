package filemanager;
import gameObject.tiles.Plant;
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

            // Deserialize plantTypes
            JsonArray plantTypesArray = tileMapJson.getJsonArray("plantTypes");
            for (int i = 0; i < plantTypesArray.size(); i++) {
                JsonArray rowArray = plantTypesArray.getJsonArray(i);
                for (int j = 0; j < rowArray.size(); j++) {
                    JsonObject plantJson = rowArray.getJsonObject(j);
                    Plant plant = deserializePlant(plantJson);
                    tileMap.plantTypes.add(plant); // Replace the default Tile with the deserialized one
                }
            }

            return tileMap;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Plant deserializePlant(JsonObject plantJson) {
        Plant plant = new Plant();
        plant.growthStage = plantJson.getInt("growthStage");
        plant.isWatered = plantJson.getBoolean("isWatered");
        plant.isHarvestable = plantJson.getBoolean("isHarvestable");
        plant.growthSpeed = plantJson.getInt("growthSpeed");
        plant.name = plantJson.getString("name");
        plant.textureYPos = plantJson.getInt("textureYPos");
        plant.updateTexture();

        return plant;
    }

    private static Tile deserializeTile(JsonObject tileJson) {
        Tile tile = new Tile();
        tile.growthStage = tileJson.getInt("growthStage");
        tile.isWatered = tileJson.getBoolean("isWatered");
        tile.isHarvestable = tileJson.getBoolean("isHarvestable");
        tile.growthSpeed = tileJson.getInt("growthSpeed");
        tile.isHarvestable = tileJson.getBoolean("cultivable");
        tile.type = tileJson.getString("type");
        tile.plantTextureYPos = tileJson.getInt("plantTextureYPos");
        tile.updateTexture();
        return tile;
    }
}
