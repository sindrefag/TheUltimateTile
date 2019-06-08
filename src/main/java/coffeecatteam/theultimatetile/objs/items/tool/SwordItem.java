package coffeecatteam.theultimatetile.objs.items.tool;

import coffeecatteam.theultimatetile.objs.entities.creatures.PlayerEntity;
import coffeecatteam.theultimatetile.start.TutEngine;

/**
 * @author CoffeeCatRailway
 * Created: 28/03/2019
 */
public class SwordItem extends ToolItem {

    public SwordItem(TutEngine tutEngine, String id, int damage) {
        super(tutEngine, id, damage, ToolType.SOWRD);
    }

    @Override
    public boolean onInteracted(PlayerEntity player) {
        return false;
    }

    @Override
    public ToolItem newCopy() {
        return super.newCopy(new SwordItem(tutEngine, id, damage));
    }
}