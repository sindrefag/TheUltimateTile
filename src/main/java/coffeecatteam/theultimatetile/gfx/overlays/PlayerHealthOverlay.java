package coffeecatteam.theultimatetile.gfx.overlays;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.objs.entities.creatures.PlayerEntity;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.start.TutLauncher;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class PlayerHealthOverlay extends Overlay {

    public PlayerHealthOverlay(TutEngine tutEngine, PlayerEntity player) {
        super(tutEngine, player);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        int hWidth = 96;
        int hHeight = 96;

        int overMaxHealth = player.getMaxHealth() * 6;
        boolean overMaxHealthDouble = player.getCurrentHealth() > overMaxHealth;
        float from = (overMaxHealthDouble ? player.getCurrentHealth() : overMaxHealth);
        float to = Assets.HEARTS.length - 1;
        int hStage = (int) NumberUtils.map(player.getCurrentHealth(), 0, from, to, 0);

        Text.drawString(g, "HP: " + player.getCurrentHealth(), 10, TutLauncher.HEIGHT - hHeight, false, false, Color.red, Assets.FONTS.get("20"));
        Assets.HEARTS[hStage].draw(0, TutLauncher.HEIGHT - hHeight, hWidth, hHeight);
    }
}