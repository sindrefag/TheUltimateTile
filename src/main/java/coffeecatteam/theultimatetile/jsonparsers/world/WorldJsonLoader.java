package coffeecatteam.theultimatetile.jsonparsers.world;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.io.FileUtils;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.inventory.items.Item;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.state.game.StateSelectGame;
import coffeecatteam.theultimatetile.tags.JsonToTag;
import coffeecatteam.theultimatetile.tags.TagCompound;
import coffeecatteam.theultimatetile.tags.TagException;
import coffeecatteam.theultimatetile.tile.Tile;
import coffeecatteam.theultimatetile.tile.Tiles;
import coffeecatteam.theultimatetile.manager.EntityManager;
import coffeecatteam.theultimatetile.utils.iinterface.IJSONLoader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

public class WorldJsonLoader implements IJSONLoader {

    public static final Map<String, String> BASE_FILES = new HashMap<>();

    static {
        BASE_FILES.put("world", "world_info");
        BASE_FILES.put("player", "player_info");
        BASE_FILES.put("items", "items");
        BASE_FILES.put("entity_s", "entities/statics");
        BASE_FILES.put("entity_c", "entities/creatures");
        BASE_FILES.put("tile_bg", "tiles/background");
        BASE_FILES.put("tile_fg", "tiles/foreground");
    }

    private String path;
    private static TutEngine tutEngine;

    // World
    private String name;
    private int width, height;
    private float spawnX;
    private float spawnY;

    private Tile[][] bg_tiles;
    private Tile[][] fg_tiles;

    // Player Info
    private String username;
    private int health, glubel, lvl;
    private int[] selected_slots;
    private ItemStack[] inventory, hotbar;

    public WorldJsonLoader(String path, TutEngine tutEngine) {
        this.path = path;
        this.tutEngine = tutEngine;
    }

    @Override
    public void load() throws IOException, ParseException {
        loadWorldInfo();
        tutEngine.getLogger().print("World [" + name + "] info loaded!");

        bg_tiles = new Tile[width][height];
        fg_tiles = new Tile[width][height];
        String bgLoadPath = path + "/" + BASE_FILES.get("tile_bg") + ".json";
        String fgLoadPath = path + "/" + BASE_FILES.get("tile_fg") + ".json";
        loadTiles(width, height, bgLoadPath, fgLoadPath, bg_tiles, fg_tiles);
        tutEngine.getLogger().print("World [" + name + "] tiles loaded!");

        /*
         * TEMP!!!
         */
        if (tutEngine instanceof TutEngine) {
            loadEntities();
            tutEngine.getLogger().print("World [" + name + "] entities loaded!");

            loadItems();
            tutEngine.getLogger().print("World [" + name + "] items loaded!");

            loadPlayerInfo();
            tutEngine.getLogger().print("World [" + name + "] player info loaded!");
        }
    }

    public void loadWorldInfo() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(FileUtils.loadFileOutSideJar(path + "/" + BASE_FILES.get("world") + ".json"));

        name = (String) jsonObject.get("name");
        tutEngine.getLogger().print("Loaded world name");

        JSONArray size = (JSONArray) jsonObject.get("size");
        width = NumberUtils.parseInt(size.get(0));
        height = NumberUtils.parseInt(size.get(1));
        tutEngine.getLogger().print("Loaded world size");

        JSONArray spawn = (JSONArray) jsonObject.get("spawn");
        spawnX = NumberUtils.parseFloat(spawn.get(0));
        spawnY = NumberUtils.parseFloat(spawn.get(1));
        tutEngine.getLogger().print("Loaded world player spawn");
    }

    public void loadTiles(int width, int height, String bgLoadPath, String fgLoadPath, Tile[][] bg_tiles, Tile[][] fg_tiles) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObjectBG = (JSONObject) parser.parse(FileUtils.loadFileOutSideJar(bgLoadPath));
        JSONObject jsonObjectFG = (JSONObject) parser.parse(FileUtils.loadFileOutSideJar(fgLoadPath));

        JSONObject bgTiles = (JSONObject) jsonObjectBG.get("bg_tile");
        for (int y = 0; y < height; y++) {
            JSONArray chunk = (JSONArray) bgTiles.get("chunk" + y);
            for (int x = 0; x < width; x++) {
                loadTile(chunk, true, x, bg_tiles, fg_tiles);
            }
        }
        tutEngine.getLogger().print("Loaded world background tiles");

        JSONObject fgTiles = (JSONObject) jsonObjectFG.get("fg_tile");
        for (int y = 0; y < height; y++) {
            JSONArray chunk = (JSONArray) fgTiles.get("chunk" + y);
            for (int x = 0; x < width; x++) {
                loadTile(chunk, false, x, bg_tiles, fg_tiles);
            }
        }
        tutEngine.getLogger().print("Loaded world foreground tiles");
    }

    private void loadTile(JSONArray chunk, boolean bg, int x, Tile[][] bg_tiles, Tile[][] fg_tiles) {
        JSONObject tileObj = (JSONObject) chunk.get(x);
        Tile tile = Tiles.getTile(tutEngine, (String) tileObj.get("id"));
        int tx = NumberUtils.parseInt(tileObj.get("x"));
        int ty = NumberUtils.parseInt(tileObj.get("y"));

        if (bg)
            bg_tiles[tx][ty] = tile;
        else
            fg_tiles[tx][ty] = tile;
    }

    public void loadEntities() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObjectStatic = (JSONObject) parser.parse(FileUtils.loadFileOutSideJar(path + "/" + BASE_FILES.get("entity_s") + ".json"));
        JSONObject jsonObjectCreature = (JSONObject) parser.parse(FileUtils.loadFileOutSideJar(path + "/" + BASE_FILES.get("entity_c") + ".json"));

        // Static
        if (jsonObjectStatic.containsKey("statics")) {
            JSONArray statics = (JSONArray) jsonObjectStatic.get("statics");
            for (Object aStatic : statics) {
                JSONObject entity = (JSONObject) aStatic;
                loadEntityObj(entity);
            }
            tutEngine.getLogger().print("Loaded world static entities");
        }

        // Creatures
        if (jsonObjectCreature.containsKey("creatures")) {
            JSONArray creatures = (JSONArray) jsonObjectCreature.get("creatures");
            for (Object creature : creatures) {
                JSONObject entity = (JSONObject) creature;
                loadEntityObj(entity);
            }
            tutEngine.getLogger().print("Loaded world creature entities");
        }
        tutEngine.getLogger().print("Loaded world entities");
    }

    public void loadItems() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObjectItems = (JSONObject) parser.parse(FileUtils.loadFileOutSideJar(path + "/" + BASE_FILES.get("items") + ".json"));

        if (jsonObjectItems.containsKey("items")) {
            JSONArray items = (JSONArray) jsonObjectItems.get("items");
            for (Object item1 : items) {
                JSONObject itemObj = (JSONObject) item1;
                String id = (String) itemObj.get("id");
                JSONArray pos = (JSONArray) itemObj.get("pos");
                float x = NumberUtils.parseFloat(pos.get(0));
                float y = NumberUtils.parseFloat(pos.get(1));
                int count = 1;
                if (itemObj.containsKey("count"))
                    count = NumberUtils.parseInt(itemObj.get("count"));
                Item item = Item.items.get(id);
                if (!item.isStackable())
                    count = 1;

                ((TutEngine) tutEngine).getItemManager().addItem(new ItemStack(item, count), x * Tile.TILE_WIDTH, y * Tile.TILE_HEIGHT);
            }
            tutEngine.getLogger().print("Loaded world items");
        }
    }

    public void loadPlayerInfo() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(FileUtils.loadFileOutSideJar(path + "/" + BASE_FILES.get("player") + ".json"));

        if (jsonObject.containsKey("username")) {
            username = (String) jsonObject.get("username");
            ((TutEngine) tutEngine).getEntityManager().getPlayer().setUsername(username);
            tutEngine.getLogger().print("loaded player username!");
        }

        health = NumberUtils.parseInt(jsonObject.get("health"));
        ((TutEngine) tutEngine).getEntityManager().getPlayer().setCurrentHealth(health);
        tutEngine.getLogger().print("loaded player health!");

        glubel = NumberUtils.parseInt(jsonObject.get("glubel"));
        ((TutEngine) tutEngine).getEntityManager().getPlayer().setGlubel(glubel);
        tutEngine.getLogger().print("loaded player glubel!");

        lvl = NumberUtils.parseInt(jsonObject.get("lvl"));
        ((TutEngine) tutEngine).getEntityManager().getPlayer().setLvl(lvl);
        tutEngine.getLogger().print("loaded player lvl!");

        selected_slots = new int[2];
        JSONArray selected_slotsJ = (JSONArray) jsonObject.get("selected_slots");
        selected_slots[0] = NumberUtils.parseInt(selected_slotsJ.get(0));
        selected_slots[1] = NumberUtils.parseInt(selected_slotsJ.get(1));
        ((TutEngine) tutEngine).getEntityManager().getPlayer().getInventoryPlayer().setInventorySelectedIndex(selected_slots[0]);
        ((TutEngine) tutEngine).getEntityManager().getPlayer().getInventoryPlayer().setHotbarSelectedIndex(selected_slots[1]);

        inventory = new ItemStack[12];
        JSONObject inventoryJ = (JSONObject) jsonObject.get("inventory");
        for (int i = 0; i < inventory.length; i++) {
            JSONObject slot = (JSONObject) inventoryJ.get("slot_" + i);
            String id = (String) slot.get("id");
            if (id.equals("null"))
                inventory[i] = null;
            else {
                int count = 1;
                if (slot.containsKey("count")) {
                    count = NumberUtils.parseInt(slot.get("count"));
                }
                Item item = Item.items.get(id);
                if (!item.isStackable())
                    count = 1;
                inventory[i] = new ItemStack(item, count);
            }
        }
        tutEngine.getLogger().print("loaded player inventory!");

        hotbar = new ItemStack[3];
        JSONObject hotbarJ = (JSONObject) jsonObject.get("hotbar");
        for (int i = 12; i < 15; i++) {
            JSONObject slot = (JSONObject) hotbarJ.get("slot_" + (i - 12));
            String id = (String) slot.get("id");
            if (id.equals("null"))
                hotbar[i - 12] = null;
            else {
                int count = 1;
                if (slot.containsKey("count")) {
                    count = NumberUtils.parseInt(slot.get("count"));
                }
                Item item = Item.items.get(id);
                if (!item.isStackable())
                    count = 1;
                hotbar[i - 12] = new ItemStack(item, count);
            }
        }
        tutEngine.getLogger().print("loaded player hotbar!");

        int invIndex = 0;
        for (int i = 0; i < inventory.length; i++) {
            ((TutEngine) tutEngine).getEntityManager().getPlayer().getInventoryPlayer().getSlots().get(invIndex).setStack(inventory[invIndex]);
            invIndex++;
            if (invIndex >= inventory.length)
                break;
        }

        int hotbarIndex = 12;
        for (int i = 0; i < hotbar.length; i++) {
            ((TutEngine) tutEngine).getEntityManager().getPlayer().getInventoryPlayer().getSlots().get(hotbarIndex).setStack(hotbar[hotbarIndex - 12]);
            hotbarIndex++;
            if (hotbarIndex >= hotbar.length + 12)
                break;
        }
    }

    /*
     * Load an entity object
     */
    private void loadEntityObj(JSONObject entityObj) {
        String id = (String) entityObj.get("id");

        TagCompound data = new TagCompound();
        if (entityObj.containsKey("tags")) {
            JSONObject tags = (JSONObject) entityObj.get("tags");
            try {
                data = JsonToTag.getTagFromJson(tags.toJSONString());
            } catch (TagException e) {
                e.printStackTrace();
            }
        }

        JSONArray pos = (JSONArray) entityObj.get("pos");
        float x = NumberUtils.parseFloat(pos.get(0));
        float y = NumberUtils.parseFloat(pos.get(1));

        int health = EntityManager.loadEntity(tutEngine, id).getMaxHealth();
        if (entityObj.containsKey("health")) {
            int healthJ = NumberUtils.parseInt(entityObj.get("health"));
            if (healthJ < 0)
                healthJ = 0;
            if (healthJ > health)
                healthJ = health;
            health = healthJ;
        }

        int count = 1;
        if (entityObj.containsKey("count")) {
            int countJ = NumberUtils.parseInt(entityObj.get("count"));
            if (countJ > 9)
                countJ = 9;
            if (countJ < 1)
                countJ = 1;
            count = countJ;
        }
        loadEntity(id, x, y, count, pos, health, data);
    }

    private void loadEntity(String id, float x, float y, int count, JSONArray pos, int health, TagCompound tags) {
        float ogX = NumberUtils.parseFloat(pos.get(0));
        for (int i = 0; i < count; i++) {
            Entity entity = EntityManager.loadEntity(tutEngine, id);
            entity.setTags(tags);
            entity.setCurrentHealth(health);
            ((TutEngine) tutEngine).getEntityManager().addEntity(entity, x, y, true);
            x++;
            if (x > ogX + 2) {
                x = ogX;
                y++;
            }
        }
    }

    public static void copyFiles(TutEngine tutEngine, String from, String dest) {
        for (String file : BASE_FILES.values())
            copy(tutEngine, StateSelectGame.class.getResourceAsStream(from + "/" + file + ".json"), dest + "/" + file + ".json");
    }


    public static boolean copy(TutEngine tutEngine, InputStream source, String destination) {
        boolean success = true;

        tutEngine.getLogger().print("Copying ->" + source + "\tto ->" + destination);

        try {
            if (!new File(destination).exists())
                new File(destination).mkdirs();

            Files.copy(source, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            success = false;
        }

        return success;
    }

    // World
    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getSpawnX() {
        return spawnX;
    }

    public float getSpawnY() {
        return spawnY;
    }

    public Tile[][] getBg_tiles() {
        return bg_tiles;
    }

    public Tile[][] getFg_tiles() {
        return fg_tiles;
    }

    // Player Info

    public String getUsername() {
        return username;
    }

    public int getHealth() {
        return health;
    }

    public int getGlubel() {
        return glubel;
    }

    public int getLvl() {
        return lvl;
    }

    public int[] getSelected_slots() {
        return selected_slots;
    }

    public ItemStack[] getInventory() {
        return inventory;
    }

    public ItemStack[] getHotbar() {
        return hotbar;
    }
}
