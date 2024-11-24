package filemanager;

import gameObject.tiles.Plant;
import gameObject.tiles.Tile;
import gameObject.tiles.TileMap;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TileMapSerializer {



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
                JsonObject plantJson = plantTypesArray.getJsonObject(i);
                Plant plant = deserializePlant(plantJson);
                tileMap.plantTypes.add(plant);
            }

            return tileMap;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Plant deserializePlant(JsonObject plantJson) {
        Plant plant = new Plant();
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
        tile.isCultivable = tileJson.getBoolean("cultivable");
        tile.type = tileJson.getString("type");
        tile.hasPlant = tileJson.getString("hasPlant");
        tile.plantTextureYPos = tileJson.getInt("plantTextureYPos");
        tile.updateTexture();
        return tile;
    }

    //------SERIALIZATION------//


    // Serialize the TileMap back to a pretty-printed JSON file
    public static void serializeTileMap(TileMap tileMap, String filePath) {
        JsonObjectBuilder tileMapBuilder = Json.createObjectBuilder();

       // Serialize mapData
        JsonArrayBuilder mapDataBuilder = Json.createArrayBuilder();
        for (int[] row : tileMap.mapData) {
            JsonArrayBuilder rowBuilder = Json.createArrayBuilder();
            for (int value : row) {
                rowBuilder.add(value);
            }
            mapDataBuilder.add(rowBuilder);
        }
        tileMapBuilder.add("mapData", mapDataBuilder);

        // Serialize tiles
        JsonArrayBuilder tilesBuilder = Json.createArrayBuilder();
        for (int i = 0; i < tileMap.tiles.length; i++) {
            JsonArrayBuilder rowBuilder = Json.createArrayBuilder();
            for (int j = 0; j < tileMap.tiles[i].length; j++) {
                JsonObjectBuilder tileBuilder = Json.createObjectBuilder();
                Tile tile = tileMap.tiles[i][j];
                tileBuilder.add("growthStage", tile.growthStage)
                        .add("isWatered", tile.isWatered)
                        .add("isHarvestable", tile.isHarvestable)
                        .add("growthSpeed", tile.growthSpeed)
                        .add("cultivable", tile.isCultivable)
                        .add("type", tile.type)
                        .add("hasPlant", tile.hasPlant)
                        .add("plantTextureYPos", tile.plantTextureYPos);
                rowBuilder.add(tileBuilder);
            }
            tilesBuilder.add(rowBuilder);
        }
        tileMapBuilder.add("tiles", tilesBuilder);

        // Serialize plantTypes
        JsonArrayBuilder plantTypesBuilder = Json.createArrayBuilder();
        for (Plant plant : tileMap.plantTypes) {
            JsonObjectBuilder plantBuilder = Json.createObjectBuilder();
            plantBuilder
                    .add("growthSpeed", plant.growthSpeed)
                    .add("name", plant.name)
                    .add("textureYPos", plant.textureYPos);
            plantTypesBuilder.add(plantBuilder);
        }
        tileMapBuilder.add("plantTypes", plantTypesBuilder);

        // Write the JSON to the file with pretty printing
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            JsonObject tileMapJson = tileMapBuilder.build();
            JsonWriter jsonWriter = Json.createWriterFactory(
                    java.util.Map.of(JsonGenerator.PRETTY_PRINTING, true)
            ).createWriter(fileWriter);
            jsonWriter.writeObject(tileMapJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
