package io.github.vampirestudios.tdg.objs.tiles;

import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.world.colormap.WorldColors;

public class DirtTile extends ConnectingTile {

    public DirtTile(MaewilEngine maewilEngine) {
        super(maewilEngine, Assets.GRASS, "dirt", false, Tile.TileType.GROUND, Assets.GRASS_ALTS);
        this.setMapColor(WorldColors.DIRT);
        this.connectsTo("grass");
        this.ignores("dirt");
    }

    @Override
    public DirtTile newCopy() {
        return super.newCopy(new DirtTile(maewilEngine));
    }
}
