package coffeecatteam.theultimatetile.jsonparsers;

import coffeecatteam.coffeecatutils.Logger;
import coffeecatteam.coffeecatutils.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * @author CoffeeCatRailway
 * Created: 6/12/2018
 */
public class JsonParser {

    private String path;
    private boolean inJar;

    public JsonParser(String path, boolean inJar) {
        this.path = path;
        this.inJar = inJar;
    }

    public Object getValue(String key) throws IOException, ParseException {
        Logger.print("\nLoading json value [" + key + "]");
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        if (inJar) jsonObject = (JSONObject) parser.parse(FileUtils.loadFileInSideJar(path));
        else jsonObject = (JSONObject) parser.parse(FileUtils.loadFileOutSideJar(path));
        return jsonObject.get(key).toString();
    }

    public JSONArray getArray(String key) throws IOException, ParseException {
        Logger.print("\nLoading json array [" + key + "]");
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        if (inJar) jsonObject = (JSONObject) parser.parse(FileUtils.loadFileInSideJar(path));
        else jsonObject = (JSONObject) parser.parse(FileUtils.loadFileOutSideJar(path));

        return (JSONArray) jsonObject.get(key);
    }
}