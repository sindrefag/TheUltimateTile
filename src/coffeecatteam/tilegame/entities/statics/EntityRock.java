package coffeecatteam.tilegame.entities.statics;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.gfx.Assets;
import coffeecatteam.tilegame.items.Item;

import java.awt.*;
import java.util.Random;

public class EntityRock extends EntityStatic {

    public EntityRock(Handler handler, float x, float y) {
        super(handler, x, y, 64, 64);

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
        g.drawImage(Assets.ROCK, (int) (this.x - handler.getCamera().getxOffset()), (int) (this.y - handler.getCamera().getyOffset()), width, height, null);
    }

    @Override
    public void die() {
        int amt = new Random().nextInt(5) + 1;
        for (int i = 0; i < amt; i++)
            handler.getWorld().getItemManager().addItem(Item.ROCK.createNew((int) (x + new Random().nextInt(5) * Item.WIDTH), (int) (y + new Random().nextInt(5) * Item.HEIGHT)));
    }
}