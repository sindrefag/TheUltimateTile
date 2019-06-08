package coffeecatteam.theultimatetile.gfx.ui.button;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.screen.options.controls.Keybind;

public class WidgetButtonControl extends WidgetButton {

    private Keybind keybind;
    private String description;

    public WidgetButtonControl(TutEngine tutEngine, Vector2D position, Keybind keybind, ClickListener listener) {
        super(tutEngine, position, keybind.getId(), listener);
        this.keybind = keybind;
        this.description = keybind.getDescription();

        hasTooltip = true;
        tooltip.addText(description);
    }

    public Keybind getKeybind() {
        return keybind;
    }

    public void setKeybind(Keybind keybind) {
        this.keybind = keybind;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}