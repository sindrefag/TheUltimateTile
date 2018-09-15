package coffeecatteam.theultimatetile.manager;

import coffeecatteam.theultimatetile.manager.iinterface.ITickableManager;
import coffeecatteam.theultimatetile.state.options.Keybinds;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener, ITickableManager {

    private boolean[] keys, justPressed, cantPress;
    public boolean up, down, left, right, sprint;
    public boolean attack, inventory, pause;

    private int currentKeyPressedCode;
    private char currentKeyPressedChar = '~';

    public KeyManager() {
        keys = new boolean[256];
        justPressed = new boolean[keys.length];
        cantPress = new boolean[keys.length];
    }

    @Override
    public void tick() {
        for (int i = 0; i < keys.length; i++) {
            if (cantPress[i] && !keys[i])
                cantPress[i] = false;
            else if (justPressed[i]) {
                cantPress[i] = true;
                justPressed[i] = false;
            }
            if (!cantPress[i] && keys[i])
                justPressed[i] = true;
        }

        up = keys[Keybinds.W.getKey()];
        down = keys[Keybinds.S.getKey()];
        left = keys[Keybinds.A.getKey()];
        right = keys[Keybinds.D.getKey()];
        sprint = keys[Keybinds.CONTROL.getKey()];

        attack = keys[Keybinds.SPACE.getKey()];
        inventory = keys[Keybinds.E.getKey()];
        pause = keys[Keybinds.ESCAPE.getKey()];
    }

    public boolean keyJustPressed(int keyCode) {
        if (keyCode < 0 || keyCode >= keys.length)
            return false;
        return justPressed[keyCode];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() < 0 || e.getKeyCode() >= keys.length)
            return;
        keys[e.getKeyCode()] = true;

        if (e.getKeyCode() != KeyEvent.VK_ENTER) {
            currentKeyPressedCode = e.getKeyCode();
            currentKeyPressedChar = e.getKeyChar();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() < 0 || e.getKeyCode() >= keys.length)
            return;
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public int getCurrentKeyPressedCode() {
        return currentKeyPressedCode;
    }

    public char getCurrentKeyPressedChar() {
        return currentKeyPressedChar;
    }
}
