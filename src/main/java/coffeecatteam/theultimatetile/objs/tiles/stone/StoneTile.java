package coffeecatteam.theultimatetile.objs.tiles.stone;

import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.objs.tiles.OverlapTile;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 24/12/2018
 */
public class StoneTile extends OverlapTile {

    public StoneTile(TutEngine tutEngine) {
        this(tutEngine, "stone", true);
    }

    public StoneTile(TutEngine tutEngine, String id, boolean overlap) {
        super(tutEngine, Assets.BROKEN_STONE, id, true, TileType.STONE, Assets.BROKEN_STONE_ALTS);
        this.setMapColor(WorldColors.STONE);

        if (overlap) {
            this.setConnect("broken_stone");
            this.setIgnore("stone");
        }

        this.setDrop(Items.ROCK);
    }

    @Override
    public StoneTile newCopy() {
        return super.newCopy(new StoneTile(tutEngine));
    }
}