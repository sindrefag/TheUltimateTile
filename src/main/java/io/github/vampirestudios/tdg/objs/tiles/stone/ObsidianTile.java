package io.github.vampirestudios.tdg.objs.tiles.stone;

import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 3/02/2019
 */
public class ObsidianTile extends StoneTile {

    public ObsidianTile(MaewilEngine maewilEngine) {
        super(maewilEngine, "obsidian", true);
        this.setMapColor(WorldColors.STONE);
        this.setDrop(null);
    }

    @Override
    public ObsidianTile newCopy() {
        return super.newCopy(new ObsidianTile(maewilEngine));
    }
}
