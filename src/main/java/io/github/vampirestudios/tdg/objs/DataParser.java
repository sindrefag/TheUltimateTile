package io.github.vampirestudios.tdg.objs;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.io.FileUtils;
import coffeecatteam.coffeecatutils.logger.CatLogger;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
import io.github.vampirestudios.tdg.utils.Identifier;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public abstract class DataParser<E extends IHasData<E>> {

    protected String dataFolderName;
    protected CatLogger logger;

    public DataParser(String dataFolderName) {
        this.dataFolderName = dataFolderName;
        String loggerName = this.dataFolderName.substring(0, 1).toUpperCase() + this.dataFolderName.substring(1);
        this.logger = new CatLogger("TUT-" + loggerName + "-Parser");
    }

    public E loadData(E obj) throws IOException, ParseException {
        JSONObject data = getData(new Identifier("maewil", dataFolderName + "/" + obj.getId()), true);

        E custom = customLoadData(data, obj);
        if (custom != null) {
            return custom;
        } else {
            DataTypes.TileItemTexture type = DataTypes.TileItemTexture.getByName(String.valueOf(data.get("type")));
            Identifier texturePath = new Identifier("maewil", "textures/" + data.get("texture").toString().replace("\"'", ""));
            int spriteSize = NumberUtils.parseInt(data.get("size"));
            logger.info("Loading an object with the type [" + type + "-" + type.typeName + "] with an id of [" + obj.getName().replace("\"", "") + "]");

            switch (type) {
                default:
                case SINGLE:
                    obj = singleData(data, obj, texturePath, spriteSize);
                    break;
                case MULTIPLE:
                    obj = multipleData(data, obj, texturePath, spriteSize);
                    break;
                case ANIMATED:
                    obj = animatedData(data, obj, texturePath, spriteSize);
                    break;
            }

            return obj.newCopy();
        }
    }

    E customLoadData(JSONObject data, E obj) {
        return null;
    }

    E singleData(JSONObject data, E obj, Identifier texturePath, int spriteSize) {
        return null;
    }

    E multipleData(JSONObject data, E obj, Identifier texturePath, int spriteSize) {
        return null;
    }

    E animatedData(JSONObject data, E obj, Identifier texturePath, int spriteSize) {
        return null;
    }

    private static JSONObject getData(Identifier fileName, boolean inJar) throws IOException, ParseException {
        Identifier path = new Identifier(fileName.getNamespace(), fileName.getPath() + ".json");
        JSONParser parser = new JSONParser();
        if (inJar) {
            return (JSONObject) parser.parse(FileUtils.loadFileInSideJar(path.toDataString()));
        } else {
            return (JSONObject) parser.parse(FileUtils.loadFileOutSideJar(path.toDataString()));
        }
    }

    static JSONObject getDataCatchException(Identifier fileName, boolean inJar) {
        try {
            return getData(fileName, inJar);
        } catch (IOException | ParseException e) {
            MaewilLauncher.LOGGER.error(e.getMessage());
            return new JSONObject();
        }
    }
}
