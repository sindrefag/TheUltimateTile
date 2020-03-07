package io.github.vampirestudios.tdg.objs;

import coffeecatteam.coffeecatutils.NumberUtils;
import io.github.vampirestudios.tdg.gfx.Animation;
import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.gfx.image.SpriteSheet;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.utils.Identifier;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.newdawn.slick.Image;
public class TileDataParser extends DataParser<Tile> {

    public TileDataParser() {
        super("tiles");
    }

    @Override
    Tile singleData(JSONObject data, Tile obj, Identifier texturePath, int spriteSize) {
        Image texture = Assets.getSpriteExact(texturePath, 0, 0, spriteSize, spriteSize);
        obj.setTexture(texture);
        return obj;
    }

    @Override
    Tile multipleData(JSONObject data, Tile obj, Identifier texturePath, int spriteSize) {
        JSONArray textures = (JSONArray) data.get("textures");
        Image[] alts = new Image[textures.size()];
        SpriteSheet altSheet = new SpriteSheet(texturePath);

        for (int i = 0; i < alts.length; i++) {
            int textureIndex = NumberUtils.parseInt(textures.get(i));
            alts[i] = altSheet.crop(textureIndex * spriteSize, 0, spriteSize, spriteSize);
        }

        obj.setHasAlts(true);
        obj.setTextureAlts(alts);
        if (data.containsKey("altChance"))
            obj.setAltChance(NumberUtils.parseInt(data.get("altChance")));
        else
            obj.setAltChance(Tile.DEFAULT_ALT_CHANCE);
        return obj;
    }

    @Override
    Tile animatedData(JSONObject data, Tile obj, Identifier texturePath, int spriteSize) {
        int animSpeed = NumberUtils.parseInt(data.get("speed"));
        JSONArray frameIndexes = (JSONArray) data.get("frames");
        SpriteSheet animSheet = new SpriteSheet(texturePath);
        Image[] frames = new Image[frameIndexes.size()];

        for (int i = 0; i < frames.length; i++) {
            Image frame = animSheet.crop(i * spriteSize, 0, spriteSize, spriteSize);
            frames[i] = frame.copy();
        }

        Animation animation = new Animation(animSpeed, frames);
        obj.setAnimation(animation);
        return obj;
    }
}
