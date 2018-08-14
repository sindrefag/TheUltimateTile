package coffeecatteam.tilegame.entities.statics;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.entities.Entity;
import coffeecatteam.tilegame.gfx.Assets;
import coffeecatteam.tilegame.items.ItemStack;
import coffeecatteam.tilegame.items.Items;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Random;

public class EntityRock extends EntityStatic {

    private BufferedImage texture;

    public EntityRock(Handler handler, String id, BufferedImage texture) {
        super(handler, id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT);

        this.texture = texture;

        bounds.x = 0;
        bounds.y = height / 2;
        bounds.width = width;
        bounds.height = height / 2;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(texture, (int) (this.x - handler.getCamera().getxOffset()), (int) (this.y - handler.getCamera().getyOffset()), width, height, null);
    }

    @Override
    public void die(Iterator<Entity> it) {
        super.die(it);
        int amt = new Random().nextInt(2) + 1;
        for (int i = 0; i < amt; i++)
            handler.getWorld().getItemManager().addItem(new ItemStack(Items.ROCK), (int) (x + new Random().nextInt(3)), (int) (y + new Random().nextInt(3)));
    }
}
