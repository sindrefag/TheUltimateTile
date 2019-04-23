package coffeecatteam.theultimatetile.objs.entities.creatures.passive;

import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityPassive;

public class EntityFox extends EntityPassive {

    public EntityFox(TutEngine tutEngine) {
        super(tutEngine, "fox");
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new EntityFox(tutEngine));
    }
}
