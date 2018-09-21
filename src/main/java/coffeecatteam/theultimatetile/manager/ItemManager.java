package coffeecatteam.theultimatetile.manager;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.manager.iinterface.IRenderabelManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ItemManager implements IRenderabelManager {

    private TheUltimateTile theUltimateTile;
    private ArrayList<ItemStack> items;

    public ItemManager(TheUltimateTile theUltimateTile) {
        this.theUltimateTile = theUltimateTile;
        items = new ArrayList<>();
    }

    @Override
    public void tick() {
        Iterator<ItemStack> it = items.iterator();
        while (it.hasNext()) {
            ItemStack stack = it.next();
            stack.getItem().tick(stack.getCount());
            if (stack.getItem().isPickedUp())
                it.remove();
        }
    }

    @Override
    public void render(Graphics g) {
        for (ItemStack stack : items) {
            stack.getItem().render(g);
            if (stack.getCount() > 1)
                Text.drawString(g, String.valueOf(stack.getCount()), (int) (stack.getItem().getX() - this.theUltimateTile.getCamera().getxOffset()), (int) (stack.getItem().getY() + 15 - this.theUltimateTile.getCamera().getyOffset()), false, false, Color.white, Assets.FONT_20);
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
        stack.setTheUltimateTile(theUltimateTile);
        items.add(stack);
    }

    public ArrayList<ItemStack> getItems() {
        return items;
    }

    public void reset() {
        items.clear();
    }
}
