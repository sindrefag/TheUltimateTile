package coffeecatteam.theultimatetile.items;

import coffeecatteam.theultimatetile.Handler;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.tiles.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ItemManager {

    private Handler handler;
    private ArrayList<ItemStack> items;

    public ItemManager(Handler handler) {
        this.handler = handler;
        items = new ArrayList<>();
    }

    public void tick() {
        Iterator<ItemStack> it = items.iterator();
        while (it.hasNext()) {
            ItemStack stack = it.next();
            stack.getItem().tick(stack.getCount());
            if (stack.getItem().isPickedUp())
                it.remove();
        }
    }

    public void render(Graphics g) {
        for (ItemStack stack : items) {
            stack.getItem().render(g);
            Text.drawString(g, String.valueOf(stack.getCount()), (int) (stack.getItem().getX() - this.handler.getCamera().getxOffset()), (int) (stack.getItem().getY() + 15 - this.handler.getCamera().getyOffset()), false, false, Color.white, Assets.FONT_20);
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
        stack.setHandler(handler);
        items.add(stack);
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public ArrayList<ItemStack> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemStack> items) {
        this.items = items;
    }
}
