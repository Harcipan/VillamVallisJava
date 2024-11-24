package filemanager;

import gameObject.tiles.Plant;
import gameObject.tiles.Tile;
import gameObject.tiles.TileMap;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TileMapSerializer {

    public static JsonObject serializeTileMap(TileMap tileMap) {
        JsonObjectBuilder tileMapBuilder = Json.createObjectBuilder();

        // Serialize mapData
        JsonArrayBuilder mapDataArrayBuilder = Json.createArrayBuilder();
        for (int[] row : tileMap.mapData) {
            JsonArrayBuilder rowArrayBuilder = Json.createArrayBuilder();
            for (int growthStage : row) {
                rowArrayBuilder.add(growthStage);
            }
            mapDataArrayBuilder.add(rowArrayBuilder);
        }

        // Serialize tiles
        JsonArrayBuilder tilesArrayBuilder = Json.createArrayBuilder();
        for (Tile[] row : tileMap.tiles) {
            JsonArrayBuilder rowArrayBuilder = Json.createArrayBuilder();
            for (Tile tile : row) {
                rowArrayBuilder.add(serializeTile(tile));
            }
            tilesArrayBuilder.add(rowArrayBuilder);
        }
        JsonArrayBuilder plantArrayBuilder = Json.createArrayBuilder();
        for (Plant plant : tileMap.plantTypes) {
            plantArrayBuilder.add(serializePlant(plant));
        }

        // Add serialized data to tileMap JSON
        tileMapBuilder.add("mapData", mapDataArrayBuilder);
        tileMapBuilder.add("tiles", tilesArrayBuilder);
        tileMapBuilder.add("plantTypes", plantArrayBuilder);

        return tileMapBuilder.build();
    }

    private static JsonObject serializePlant(Plant plant) {
        JsonObjectBuilder plantBuilder = Json.createObjectBuilder();
        plantBuilder.add("name", plant.name);
        plantBuilder.add("growthStage", plant.growthStage);
        plantBuilder.add("growthSpeed", plant.growthSpeed);
        plantBuilder.add("isHarvestable", plant.isHarvestable);
        plantBuilder.add("isWatered", plant.isWatered);
        plantBuilder.add("textureYPos", plant.textureYPos);
        return plantBuilder.build();
    }

    private static JsonObject serializeTile(Tile tile) {
        JsonObjectBuilder tileBuilder = Json.createObjectBuilder();
        tileBuilder.add("growthStage", tile.growthStage);
        tileBuilder.add("isWatered", tile.isWatered);
        tileBuilder.add("isHarvestable", tile.isHarvestable);
        tileBuilder.add("growthSpeed", tile.growthSpeed);
        tileBuilder.add("cultivable", tile.isCultivable);
        tileBuilder.add("type", tile.type);
        tileBuilder.add("plantTextureYPos", tile.plantTextureYPos);
        return tileBuilder.build();
    }

    public static void writeTileMapToFile(TileMap tileMap, String filePath) {
        JsonObject tileMapJson = serializeTileMap(tileMap);
        try (FileWriter fileWriter = new FileWriter(filePath);
             JsonWriter jsonWriter = Json.createWriter(fileWriter)) {
            jsonWriter.writeObject(tileMapJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

