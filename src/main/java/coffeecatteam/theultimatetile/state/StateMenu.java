package coffeecatteam.theultimatetile.state;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.ui.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.UIHyperlink;
import coffeecatteam.theultimatetile.manager.UIManager;
import coffeecatteam.theultimatetile.utils.Logger;

import java.awt.*;
import java.net.URI;

public class StateMenu extends State {

    private UIManager uiManager;

    public StateMenu(TheUltimateTile theUltimateTileIn) {
        super(theUltimateTileIn);
        uiManager = new UIManager(theUltimateTile);
        init();

        int yOff = 20;

        int spBtnWidth = 7 * 64;
        int spBtnHeight = 64;
        uiManager.addObject(new UIButton(theUltimateTile.getWidth() / 2 - spBtnWidth / 2, theUltimateTile.getHeight() / 2 - spBtnHeight / 2 + spBtnHeight - 50 + yOff, spBtnWidth, spBtnHeight, "Single Player", () -> {
            State.setState(new StateGame(theUltimateTile));
        }));

        int quitBtnWidth = 192;
        int quitBtnHeight = 64;
        uiManager.addObject(new UIButton(theUltimateTile.getWidth() / 2 - quitBtnWidth / 2, theUltimateTile.getHeight() / 2 - quitBtnHeight / 2 + quitBtnHeight + 120 + yOff, quitBtnWidth, quitBtnHeight, "Quit", () -> {
            Logger.print("Exiting...");
            System.exit(0);
        }));

        Font font = Assets.FONT_20;
        String crText = "Copyright (C) CoffeeCatTeam 2018";
        int crWidth = Text.getWidth(theUltimateTile.getGraphics(), crText, font);
        int crHeight = Text.getHeight(theUltimateTile.getGraphics(), font);
        int crx = 5;
        int cry = theUltimateTile.getHeight() - 10;
        uiManager.addObject(new UIHyperlink(crx, cry, crWidth, crHeight, crText, true, font, () -> {
            try {
                Desktop desktop = Desktop.getDesktop();
                URI oURL = new URI("https://github.com/CoffeeCatRailway/TheUltimateTile/blob/master/LICENSE.md");
                desktop.browse(oURL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
    }

    @Override
    public void init() {
        theUltimateTile.getMouseManager().setUiManager(uiManager);
    }

    @Override
    public void tick() {
        uiManager.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.BACKGROUND, 0, 0, theUltimateTile.getWidth(), theUltimateTile.getHeight(), null);
        uiManager.render(g);

        int w = 80 * 6;
        int h = 48 * 6;
        g.drawImage(Assets.TITLE, w / 6, 20, w, h, null);
    }
}
