package coffeecatteam.theultimatetile.gfx.ui.hyperlink;

import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UIObject;

import java.awt.*;
import java.awt.event.MouseEvent;

public class UIHyperlink extends UIObject {

    private ClickListener listener;

    private boolean hovering = false;
    public Color mainColor = Color.white, hoverColor = Color.cyan;
    private Color c = mainColor;

    private String text;
    private boolean underlined;
    private Font font;

    public UIHyperlink(Vector2D position, int height, String text, boolean underlined, Font font, ClickListener listener) {
        super(position, 0, height);
        this.listener = listener;

        this.text = text;
        this.underlined = underlined;
        this.font = font;
        bounds = new AABB((int) this.position.x, (int) this.position.y - height, width, height);
    }

    public UIHyperlink setColors(Color mainColor, Color hoverColor) {
        this.mainColor = mainColor;
        this.hoverColor = hoverColor;
        return this;
    }

    @Override
    public void tick() {
        if (this.hovering)
            this.c = hoverColor;
        else
            this.c = mainColor;
    }

    @Override
    public void render(Graphics2D g) {
        width = Text.getWidth(g, text, font);
        bounds.width = width;
        Text.drawString(g, text, (int) this.position.x, (int) this.position.y, false, underlined, c, font);
    }

    @Override
    public void onClick() {
        this.listener.onClick();
    }

    @Override
    public void onMouseMoved(MouseEvent e) {
        this.hovering = this.bounds.contains(e.getX(), e.getY());
    }

    @Override
    public void onMouseRelease(MouseEvent e) {
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
    }
}