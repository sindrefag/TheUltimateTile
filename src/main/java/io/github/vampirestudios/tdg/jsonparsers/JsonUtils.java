package io.github.vampirestudios.tdg.jsonparsers;

import coffeecatteam.coffeecatutils.io.FileUtils;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class JsonUtils {

    public static Object getValue(String key, String path, boolean inJar) throws IOException, ParseException {
        MaewilLauncher.LOGGER.info("Loading json value [" + key + "]");
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        if (inJar) jsonObject = (JSONObject) parser.parse(FileUtils.loadFileInSideJar(path));
        else jsonObject = (JSONObject) parser.parse(FileUtils.loadFileOutSideJar(path));
        return jsonObject.get(key).toString();
    }

    public static JSONArray getArray(String key, String path, boolean inJar) throws IOException, ParseException {
        MaewilLauncher.LOGGER.info("Loading json array [" + key + "]");
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        if (inJar) jsonObject = (JSONObject) parser.parse(FileUtils.loadFileInSideJar(path));
        else jsonObject = (JSONObject) parser.parse(FileUtils.loadFileOutSideJar(path));

        return (JSONArray) jsonObject.get(key);
    }
}
