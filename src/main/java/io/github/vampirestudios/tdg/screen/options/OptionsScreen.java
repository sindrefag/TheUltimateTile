package io.github.vampirestudios.tdg.screen.options;

import coffeecatteam.coffeecatutils.position.Vector2D;
import io.github.vampirestudios.tdg.gfx.Text;
import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.gfx.ui.ClickListener;
import io.github.vampirestudios.tdg.gfx.ui.WidgetCheckBox;
import io.github.vampirestudios.tdg.gfx.ui.button.WidgetButton;
import io.github.vampirestudios.tdg.jsonparsers.OptionsJsonParser;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.objs.tiles.Tiles;
import io.github.vampirestudios.tdg.screen.AbstractMenuScreen;
import io.github.vampirestudios.tdg.screen.ScreenManager;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
import io.github.vampirestudios.tdg.utils.DiscordHandler;
import org.json.simple.parser.ParseException;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;

public class OptionsScreen extends AbstractMenuScreen {

    public static OptionsJsonParser OPTIONS;
    static final Tile[] BG = new Tile[]{
            Tiles.DIRT
    };

    private WidgetCheckBox debug, fps, vsync;

    public OptionsScreen(MaewilEngine maewilEngine, boolean initUI) {
        super(maewilEngine, BG, initUI);

        OPTIONS = new OptionsJsonParser("./data/options.json");
        try {
            OPTIONS.load();
        } catch (IOException | ParseException e) {
            MaewilLauncher.LOGGER.error(e);
        }

        if (initUI) {
            uiManager.addObject(debug = new WidgetCheckBox(new Vector2D(MaewilLauncher.WIDTH / 2f + 120, 20), 70, OPTIONS.debugMode()));
            uiManager.addObject(fps = new WidgetCheckBox(new Vector2D(MaewilLauncher.WIDTH / 2f + 100, 90), 70, OPTIONS.fpsCounter()));
            uiManager.addObject(vsync = new WidgetCheckBox(new Vector2D(MaewilLauncher.WIDTH / 2f + 100, 160), 70, OPTIONS.vSync()));

            uiManager.addObject(new WidgetButton(maewilEngine, true, 243, "Controls", new ClickListener() {
                @Override
                public void onClick() {
                    ScreenManager.setCurrentScreen(maewilEngine.controlOptions);
                    try {
                        OPTIONS.load();
                    } catch (IOException | ParseException e) {
                        logger.error(e);
                    }
                    setPresence("Controls");
                }

                @Override
                public void update(GameContainer container, StateBasedGame game, int delta) {
                }
            }));

            uiManager.addObject(new WidgetButton(maewilEngine, true, 322, "Sounds", new ClickListener() {
                @Override
                public void onClick() {
                    ScreenManager.setCurrentScreen(maewilEngine.optionsSpounds);
                    try {
                        OPTIONS.load();
                    } catch (IOException | ParseException e) {
                        logger.error(e);
                    }
                    setPresence("Sounds");
                }

                @Override
                public void update(GameContainer container, StateBasedGame game, int delta) {
                }
            }));
        }
    }

    private void setPresence(String presence) {
        DiscordHandler.INSTANCE.updatePresence("Main Menu", "Options/" + presence);
    }

    @Override
    public void initBack() {
        try {
            OPTIONS.save();
        } catch (IOException e) {
            logger.error(e);
        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        super.update(container, game, delta);
        OPTIONS.setDebugMode(debug.isChecked());
        OPTIONS.setFpsCounter(fps.isChecked());
        OPTIONS.setVSync(vsync.isChecked());
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        super.render(container, game, g);
        Font font = Assets.FONTS.get("40");
        String debugText = "Debug Mode: ";
        Text.drawString(g, debugText, (int) (debug.getPosition().x - Text.getWidth(debugText, font)), (int) (debug.getPosition().y + Text.getHeight(debugText, font)) + 25, false, Color.white, font);
        String fpsText = "Show FPS: ";
        Text.drawString(g, fpsText, (int) (fps.getPosition().x - Text.getWidth(fpsText, font)), (int) (fps.getPosition().y + Text.getHeight(fpsText, font)) + 25, false, Color.white, font);
        String vSyncText = "Use VSync: ";
        Text.drawString(g, vSyncText, (int) (vsync.getPosition().x - Text.getWidth(fpsText, font)), (int) (vsync.getPosition().y + Text.getHeight(fpsText, font)) + 25, false, Color.white, font);
    }
}
