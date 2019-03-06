package coffeecatteam.theultimatetile.objs.entities.statics.nature;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.statics.EntityNature;
import coffeecatteam.theultimatetile.objs.items.Items;
import org.newdawn.slick.Image;

public class EntityRock extends EntityNature {

    public EntityRock(TutEngine tutEngine, String id, Image texture) {
        super(tutEngine, id, texture, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, EntityHitType.STONE);

        bounds.x = 0;
        bounds.y = height / 2f + height / 3f;
        bounds.width = width;
        bounds.height = height / 3;

        drops.add(Items.ROCK);
    }
}