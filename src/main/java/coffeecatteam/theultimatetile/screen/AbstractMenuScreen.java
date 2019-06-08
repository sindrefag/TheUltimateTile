package coffeecatteam.theultimatetile.screen;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.button.WidgetButton;
import coffeecatteam.theultimatetile.gfx.ui.hyperlink.WidgetHyperlinkCopyright;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.start.TutLauncher;
import coffeecatteam.theultimatetile.utils.DiscordHandler;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class AbstractMenuScreen extends Screen {

    public AbstractMenuScreen(TutEngine tutEngine, Tile[] centre) {
        this(tutEngine, centre, true);
    }

    public AbstractMenuScreen(TutEngine tutEngine, Tile[] centre, boolean initUI) {
        super(tutEngine, centre);

        if (initUI) {
            uiManager.addObject(new WidgetButton(tutEngine, new Vector2D(15, TutLauncher.HEIGHT - 95), "Main Menu", new ClickListener() {
                @Override
                public void onClick() {
                    initBack();
                    ScreenManager.setCurrentScreen(tutEngine.stateMenu);

                    DiscordHandler.INSTANCE.updatePresence("Main Menu");
                }

                @Override
                public void update(GameContainer container, StateBasedGame game, int delta) {
                }
            }));

            uiManager.addObject(new WidgetHyperlinkCopyright(new Vector2D(5, TutLauncher.HEIGHT - 20)));
        }
    }

    public void initBack() {
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        uiManager.update(container, game, delta);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        this.renderBG(container, game, g);
        uiManager.render(container, game, g);
    }
}
