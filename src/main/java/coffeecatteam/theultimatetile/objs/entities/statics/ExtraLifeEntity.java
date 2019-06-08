package coffeecatteam.theultimatetile.objs.entities.statics;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.start.TutEngine;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author CoffeeCatRailway
 * Created: 12/12/2018
 */
public class ExtraLifeEntity extends StaticEntity {

    private int healAmt;

    public ExtraLifeEntity(TutEngine tutEngine) {
        super(tutEngine, "extra_life", Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, HitType.NONE);
        setCurrentTexture("main");
        isCollidable = false;

        healAmt = NumberUtils.getRandomInt(Entity.DEFAULT_HEALTH / 4, Entity.DEFAULT_HEALTH);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        if (this.isTouching(tutEngine.getPlayer())) {
            tutEngine.getPlayer().heal(healAmt);
            this.hurt(getCurrentHealth());
        }
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new ExtraLifeEntity(tutEngine));
    }
}