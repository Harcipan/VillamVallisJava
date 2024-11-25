package filemanager;

import gameObject.tiles.Ground;
import gameObject.tiles.Plant;
import gameObject.tiles.Tile;
import gameObject.tiles.TileMap;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Provides functionality for serializing and deserializing {@link TileMap} objects to and from JSON files.
 */
public class TileMapSerializer {

    /**
     * Deserializes a {@link TileMap} object from a JSON file.
     *
     * @param filePath the path to the JSON file containing the tile map data.
     * @return a {@link TileMap} object populated with data from the file, or {@code null} if an error occurs.
     */
    public static TileMap deserializeTileMap(String filePath) {
        try (FileReader fileReader = new FileReader(filePath);
             JsonReader jsonReader = Json.createReader(fileReader)) {

            JsonObject tileMapJson = jsonReader.readObject();

            // Deserialize mapData
            JsonArray mapDataArray = tileMapJson.getJsonArray("mapData");
            String[][] mapData = new String[mapDataArray.size()][];
            for (int i = 0; i < mapDataArray.size(); i++) {
                JsonArray rowArray = mapDataArray.getJsonArray(i);
                mapData[i] = new String[rowArray.size()];
                for (int j = 0; j < rowArray.size(); j++) {
                    mapData[i][j] = rowArray.getString(j);
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

            // Deserialize groundTypes
            JsonArray groundTypesArray = tileMapJson.getJsonArray("groundTypes");
            for (int i = 0; i < groundTypesArray.size(); i++) {
                JsonObject groundJson = groundTypesArray.getJsonObject(i);
                Ground ground = deserializeGround(groundJson);
                tileMap.groundTypes.add(ground);
            }

            // Update tile properties based on ground types
            for (Tile[] t : tileMap.tiles) {
                for (Tile t2 : t) {
                    for (Ground ground : tileMap.groundTypes) {
                        if (ground.name.equals(t2.type)) {
                            t2.isCultivable = ground.isCultivable;
                            t2.growthSpeed = ground.growthSpeed;
                        }
                    }
                }
            }

            return tileMap;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Deserializes a {@link Plant} object from a JSON object.
     *
     * @param plantJson the JSON object containing plant data.
     * @return a {@link Plant} object populated with the data.
     */
    private static Plant deserializePlant(JsonObject plantJson) {
        Plant plant = new Plant();
        plant.growthSpeed = plantJson.getInt("growthSpeed");
        plant.name = plantJson.getString("name");
        plant.textureYPos = plantJson.getInt("textureYPos");
        plant.updateTexture();

        return plant;
    }

    /**
     * Deserializes a {@link Tile} object from a JSON object.
     *
     * @param tileJson the JSON object containing tile data.
     * @return a {@link Tile} object populated with the data.
     */
    private static Tile deserializeTile(JsonObject tileJson) {
        Tile tile = new Tile();
        tile.growthStage = tileJson.getInt("growthStage");
        tile.isWatered = tileJson.getBoolean("isWatered");
        tile.isHarvestable = tileJson.getBoolean("isHarvestable");
        tile.type = tileJson.getString("type");
        tile.hasPlant = tileJson.getString("hasPlant");
        //tile.updateTexture();
        return tile;
    }

    /**
     * Deserializes a {@link Ground} object from a JSON object.
     *
     * @param groundJson the JSON object containing ground data.
     * @return a {@link Ground} object populated with the data.
     */
    private static Ground deserializeGround(JsonObject groundJson) {
        Ground ground = new Ground();
        ground.name = groundJson.getString("name");
        ground.growthSpeed = groundJson.getInt("growthSpeed");
        ground.isCultivable = groundJson.getBoolean("cultivable");
        ground.textureYPos = groundJson.getInt("textureYPos");
        ground.updateTexture();
        return ground;
    }

    /**
     * Serializes a {@link TileMap} object to a JSON file.
     *
     * @param tileMap  the {@link TileMap} object to serialize.
     * @param filePath the path to the file where the JSON data will be saved.
     */
    public static void serializeTileMap(TileMap tileMap, String filePath) {
        JsonObjectBuilder tileMapBuilder = Json.createObjectBuilder();

        // Serialize mapData
        JsonArrayBuilder mapDataBuilder = Json.createArrayBuilder();
        for (String[] row : tileMap.mapData) {
            JsonArrayBuilder rowBuilder = Json.createArrayBuilder();
            for (String value : row) {
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
                        .add("type", tile.type)
                        .add("hasPlant", tile.hasPlant);
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

        // Serialize groundTypes
        JsonArrayBuilder groundTypesBuilder = Json.createArrayBuilder();
        for (Ground ground : tileMap.groundTypes) {
            JsonObjectBuilder plantBuilder = Json.createObjectBuilder();
            plantBuilder
                    .add("name", ground.name)
                    .add("growthSpeed", ground.growthSpeed)
                    .add("cultivable", ground.isCultivable)
                    .add("textureYPos", ground.textureYPos);
            groundTypesBuilder.add(plantBuilder);
        }
        tileMapBuilder.add("groundTypes", groundTypesBuilder);

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
