package coffeecatteam.theultimatetile.manager;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.gfx.ui.UIObject;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class UIManager {

    private Engine engine;
    private ArrayList<UIObject> objects;

    public UIManager(Engine engine) {
        this.engine = engine;
        objects = new ArrayList<>();
    }

    public void tick() {
        for (UIObject o : objects)
            o.tick();
    }

    public void render(Graphics2D g) {
        for (UIObject o : objects)
            o.render(g);
        postRender(g);
    }

    public void postRender(Graphics2D g) {
        for (UIObject o : objects)
            o.postRender(g);
    }

    public void onMouseMoved(MouseEvent e) {
        for (UIObject o : objects)
            o.onMouseMoved(e);
    }

    public void onMouseRelease(MouseEvent e) {
        for (UIObject o : objects)
            o.onMouseReleaseA(e);
    }

    public void mouseDragged(MouseEvent e) {
        for (UIObject o : objects)
            o.onMouseDragged(e);
    }

    public void addObject(UIObject o) {
        objects.add(o);
    }

    public void removeObject(UIObject o) {
        objects.remove(o);
    }
}
