package io.github.vampirestudios.tdg.objs.items;

import io.github.vampirestudios.tdg.start.MaewilEngine;

public class BasicItem extends Item {

    public BasicItem(MaewilEngine maewilEngine, String id) {
        super(maewilEngine, id);
    }

    @Override
    public BasicItem newCopy() {
        return super.newCopy(new BasicItem(maewilEngine, id));
    }
}
