package io.github.vampirestudios.tdg.objs;

import coffeecatteam.coffeecatutils.NumberUtils;
import io.github.vampirestudios.tdg.gfx.Animation;
import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.gfx.image.SpriteSheet;
import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.tiles.Tiles;
import io.github.vampirestudios.tdg.utils.Identifier;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.newdawn.slick.Image;

import java.util.HashMap;
import java.util.Map;

public class EntityDataParser extends DataParser<Entity> {

    private Map<String, HashMap<String, Animation>> LOADED = new HashMap<>();

    public EntityDataParser() {
        super("entities");
    }

    @Override
    public Entity customLoadData(JSONObject data, Entity obj) {
        Entity entity = obj.newCopy();

        if (!LOADED.containsKey(entity.getId())) {
            DataTypes.Entity type = DataTypes.Entity.getByName(String.valueOf(data.get("type")));
            SpriteSheet texture = getTexture(data);
            logger.info("Loading entity of type [" + type + "-" + type.typeName + "] with id [" + entity.getName() + "]");

            HashMap<String, Animation> textures = new HashMap<>();
            switch (type) {
                case STATIC:
                    if (data.containsKey("sprites")) {
                        JSONObject sprites = (JSONObject) data.get("sprites");
                        for (Object key : sprites.keySet()) {
                            JSONObject sprite = (JSONObject) sprites.get(key);
                            SpriteSheet spriteTexture;
                            if (sprite.containsKey("texture")) spriteTexture = getTexture(sprite).copy();
                            else spriteTexture = texture.copy();

                            System.out.println(key);
                            if (sprite.containsKey("length")) textures.put((String) key, getAnimation(spriteTexture.getSheet(), sprite));
                            else textures.put((String) key, getAnimation(spriteTexture.getSheet(), sprite, false, "spritePos"));
                        }
                    }
                    break;
                case CREATURE:
                    JSONObject animations = (JSONObject) data.get("animations");
                    JSONArray size = (JSONArray) data.get("spriteSize");
                    int width = NumberUtils.parseInt(size.get(0));
                    int height = NumberUtils.parseInt(size.get(1));
                    for (Object key : animations.keySet()) {
                        JSONObject anim = (JSONObject) animations.get(key);
                        textures.put(String.valueOf(key), getAnimation(texture.getSheet().copy(), anim, width, height));
                    }
                    break;
                case CUSTOM:
                    textures.put(entity.getId(), new Animation(Tiles.AIR.getTexture()));
                    break;
            }

            entity.setTextures(textures);
            LOADED.put(entity.getId(), textures);
        } else {
            logger.info("Getting textures for entity with an id of [" + entity.getName().replace("\"", "") + "]");
            entity.setTextures(LOADED.get(entity.getId()));
        }

        return entity.newCopy();
    }

    private SpriteSheet getTexture(JSONObject data) {
        SpriteSheet texture = new SpriteSheet(Assets.MISSING_TEXTURE);
        if (data.containsKey("texture")) {
            String texturePath = "textures/";

            if (data.get("texture") instanceof JSONArray) {
                JSONArray texturePaths = (JSONArray) data.get("texture");
                texturePath += String.valueOf(texturePaths.get(NumberUtils.getRandomInt(0, texturePaths.size() - 1))).replace("\"", "");
            } else {
                texturePath += data.get("texture").toString();
            }

            texture = new SpriteSheet(new Identifier("maewil", texturePath));
        }
        return texture;
    }

    private Animation getAnimation(Image texture, JSONObject data) {
        JSONArray size = (JSONArray) data.get("spriteSize");
        int width = NumberUtils.parseInt(size.get(0));
        int height = NumberUtils.parseInt(size.get(1));
        return getAnimation(texture, data, width, height);
    }

    private Animation getAnimation(Image texture, JSONObject data, int width, int height) {
        return getAnimation(texture, data, width, height, true, "startPos");
    }

    private Animation getAnimation(Image texture, JSONObject data, boolean xyExact, String posKey) {
        JSONArray size = (JSONArray) data.get("spriteSize");
        int width = NumberUtils.parseInt(size.get(0));
        int height = NumberUtils.parseInt(size.get(1));
        return getAnimation(texture, data, width, height, xyExact, posKey);
    }

    private Animation getAnimation(Image texture, JSONObject data, int width, int height, boolean xyExact, String posKey) {
        int length = 1;
        if (data.containsKey("length"))
            length = NumberUtils.parseInt(data.get("length"));

        int speed = Animation.DEFAULT_SPEED;
        if (data.containsKey("speed"))
            speed = NumberUtils.parseInt(data.get("speed"));

        JSONArray pos = (JSONArray) data.get(posKey);
        int startPosX = NumberUtils.parseInt(pos.get(0));
        int startPosY = NumberUtils.parseInt(pos.get(1));

        Image[] images = new Image[length];
        for (int i = 0; i < length; i++) {
            int x = startPosX;
            int y = startPosY;
            if (xyExact) {
                x = startPosX * width + width * i;
                y = startPosY * height;
            }

            Image img = texture.getSubImage(x, y, width, height);
            images[i] = img.copy();
        }

        return new Animation(speed, images);
    }
}
