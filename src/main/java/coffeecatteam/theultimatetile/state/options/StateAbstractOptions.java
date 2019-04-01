package coffeecatteam.theultimatetile.state.options;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.TutLauncher;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.button.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.hyperlink.UIHyperlinkCopyright;
import coffeecatteam.theultimatetile.state.State;
import coffeecatteam.theultimatetile.state.StateManager;
import coffeecatteam.theultimatetile.state.StateOptions;
import coffeecatteam.theultimatetile.utils.DiscordHandler;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;

public abstract class StateAbstractOptions extends State {

    public StateAbstractOptions(TutEngine tutEngineIn) {
        super(tutEngineIn, StateOptions.BG);

        uiManager.addObject(new UIButton(tutEngineIn, new Vector2D(15, TutLauncher.HEIGHT - 95), "Back", new ClickListener() {
            @Override
            public void onClick() {
                StateManager.setCurrentState(tutEngine.stateOptions);
                try {
                    StateOptions.OPTIONS.save();
                } catch (IOException e) {
                    logger.error(e);
                }

                DiscordHandler.INSTANCE.updatePresence("Main Menu", "Options");
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        }));

        uiManager.addObject(new UIHyperlinkCopyright(new Vector2D(5, TutLauncher.HEIGHT - 20)));
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
