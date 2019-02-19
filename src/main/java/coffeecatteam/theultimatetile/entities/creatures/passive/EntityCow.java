package coffeecatteam.theultimatetile.entities.creatures.passive;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.entities.creatures.EntityPassive;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.manager.ItemManager;

public class EntityCow extends EntityPassive {

    public EntityCow(TutEngine tutEngine, String id) {
        super(tutEngine, id);
        this.drop = ItemManager.RAW_STEAK;
    }

    @Override
    protected void init() {
        animIdle = new Animation(animSpeed, Assets.COW_IDLE);
        animUp = new Animation(animUpDownSpeed, Assets.COW_UP);
        animDown = new Animation(animUpDownSpeed, Assets.COW_DOWN);
        animLeft = new Animation(animSpeed, Assets.COW_LEFT);
        animRight = new Animation(animSpeed, Assets.COW_RIGHT);
    }
}
