package coffeecatteam.theultimatetile.objs.items;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.objs.ItemDataParser;
import org.json.simple.parser.ParseException;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Items {

    public static final HashMap<String, Item> ITEMS = new HashMap<>();

    /*
     * General
     */
    public static Item STICK, LEAF, ROCK;
    public static Item ROTTEN_FLESH, BONE, BOUNCY_BALL, WOOL_BUNDLE;
    public static Item BOOK;

    public static Item COIN_PENNY, COIN_IRON, COIN_GOLD;

    /*
     * Tools/Weapons
     */
    public static Item WOODEN_SWORD, WOODEN_PICK, WOODEN_HOE;
    public static Item STONE_SWORD, STONE_PICK, STONE_HOE;

    /*
     * Minerals
     */
    public static Item COAL, IRON_INGOT, GOLD_INGOT, DIAMOND;

    /*
     * Foods
     */
    public static Item CARROT, WHEAT, POTATO, TOMATO, CORN;
    public static Item BREAD, APPLE;
    public static Item RAW_PORK, COOKED_PORK, RAW_STEAK, COOKED_STEAK;

    public static void init(TutEngine tutEngine) throws IOException, ParseException {
        ItemDataParser parser = new ItemDataParser();
        
        /*
         * General
         */
        register(STICK = parser.loadData(new ItemBasic(tutEngine, "stick")));
        register(LEAF = parser.loadData(new ItemBasic(tutEngine, "leaf")));
        register(ROCK = parser.loadData(new ItemBasic(tutEngine, "rock")));
        register(ROTTEN_FLESH = parser.loadData(new ItemBasic(tutEngine, "rotten_flesh")));
        register(BONE = parser.loadData(new ItemBasic(tutEngine, "bone")));
        register(BOUNCY_BALL = parser.loadData(new ItemBasic(tutEngine, "bouncy_ball")));
        register(WOOL_BUNDLE = parser.loadData(new ItemBasic(tutEngine, "wool_bundle")));
        register(BOOK = parser.loadData(new ItemBasic(tutEngine, "book")));

        register(COIN_PENNY = parser.loadData(new ItemCoin(tutEngine, "coin_penny", 0.25f)));
        register(COIN_IRON = parser.loadData(new ItemCoin(tutEngine, "coin_iron", 0.50f)));
        register(COIN_GOLD = parser.loadData(new ItemCoin(tutEngine, "coin_gold", 1.00f)));

        /*
         * Tools/Weapons
         */
        register(WOODEN_SWORD = parser.loadData(new ItemTool(tutEngine, "wooden_sword", 5, ItemTool.ToolType.SOWRD)));
        register(WOODEN_PICK = parser.loadData(new ItemTool(tutEngine, "wooden_pick", 7, ItemTool.ToolType.PICKAXE)));
        register(WOODEN_HOE = parser.loadData(new ItemTool(tutEngine, "wooden_hoe", 4, ItemTool.ToolType.HOE)));

        register(STONE_SWORD = parser.loadData(new ItemTool(tutEngine, "stone_sword", 7, ItemTool.ToolType.SOWRD)));
        register(STONE_PICK = parser.loadData(new ItemTool(tutEngine, "stone_pick", 9, ItemTool.ToolType.PICKAXE)));
        register(STONE_HOE = parser.loadData(new ItemTool(tutEngine, "stone_hoe", 6, ItemTool.ToolType.HOE)));

        /*
         * Minerals
         */
        register(COAL = parser.loadData(new ItemBasic(tutEngine, "coal")));
        register(IRON_INGOT = parser.loadData(new ItemBasic(tutEngine, "iron_ingot")));
        register(GOLD_INGOT = parser.loadData(new ItemBasic(tutEngine, "gold_ingot")));
        register(DIAMOND = parser.loadData(new ItemBasic(tutEngine, "diamond")));

        /*
         * Foods
         */
        register(CARROT = parser.loadData(new ItemFood(tutEngine, "carrot", 4)));
        register(WHEAT = parser.loadData(new ItemFood(tutEngine, "wheat", 2)));
        register(POTATO = parser.loadData(new ItemFood(tutEngine, "potato", 4)));
        register(TOMATO = parser.loadData(new ItemFood(tutEngine, "tomato", 4)));
        register(CORN = parser.loadData(new ItemFood(tutEngine, "corn", 5)));

        register(BREAD = parser.loadData(new ItemFood(tutEngine, "bread", 5)));
        register(APPLE = parser.loadData(new ItemFood(tutEngine, "apple", 6)));

        register(RAW_PORK = parser.loadData(new ItemFood(tutEngine, "raw_pork", 2)));
        register(COOKED_PORK = parser.loadData(new ItemFood(tutEngine, "cooked_pork", 4)));
        register(RAW_STEAK = parser.loadData(new ItemFood(tutEngine, "raw_steak", 3)));
        register(COOKED_STEAK = parser.loadData(new ItemFood(tutEngine, "cooked_steak", 6)));

        TutEngine.getTutEngine().getLogger().print("Items registered!");
    }

    private static void register(Item item) {
        ITEMS.put(item.getId(), item);
    }

    public static Item getItemById(String id) {
        return ITEMS.get(id);
    }

    /*
     * Item Manager
     */
    private TutEngine tutEngine;
    private static final List<ItemStack> ITEM_STACKS = new ArrayList<>();

    public Items(TutEngine tutEngine) {
        this.tutEngine = tutEngine;
    }

    public void update(GameContainer container, int delta) {
        Iterator<ItemStack> it = ITEM_STACKS.iterator();
        while (it.hasNext()) {
            ItemStack stack = it.next();
            stack.getItem().update(stack.getCount());
            if (stack.getItem().isPickedUp())
                it.remove();
        }
    }

    public void render(Graphics g) {
        for (ItemStack stack : ITEM_STACKS) {
            stack.getItem().render(g);
            if (stack.getCount() > 1)
                Text.drawString(g, String.valueOf(stack.getCount()), (int) (stack.getItem().getPosition().x - this.tutEngine.getCamera().getxOffset()), (int) (stack.getItem().getPosition().y + 15 - this.tutEngine.getCamera().getyOffset()), false, false, Color.white, Assets.FONTS.get("20"));
        }
    }

    public void addItem(ItemStack stack, float x, float y) {
        addItem(stack, (int) x, (int) y);
    }

    public void addItem(ItemStack stack, int x, int y) {
        stack.setPosition(x, y);
        addItem(stack);
    }

    public void addItem(ItemStack stack) {
        ITEM_STACKS.add(stack);
    }

    public List<ItemStack> getITEMS() {
        return ITEM_STACKS;
    }

    public void reset() {
        ITEM_STACKS.clear();
    }
}