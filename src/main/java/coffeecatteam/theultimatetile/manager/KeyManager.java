package coffeecatteam.theultimatetile.manager;

import coffeecatteam.theultimatetile.state.options.StateOptions;
import coffeecatteam.theultimatetile.state.options.controls.Keybind;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.event.KeyEvent;

public class KeyManager {

    private boolean[] keys, justPressed, cantPress;
    public boolean moveUp, moveDown, moveLeft, moveRight, useSprint, useAttack;

    private int currentKeyPressedCode;
    private char currentKeyPressedChar = '~';

    public KeyManager() {
        keys = new boolean[256];
        justPressed = new boolean[keys.length];
        cantPress = new boolean[keys.length];
    }

    public void update(GameContainer container, StateBasedGame game, int delta) {
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

        moveUp = container.getInput().isKeyDown(StateOptions.OPTIONS.controls().get(Keybind.W).getKeyCode());
        moveDown = container.getInput().isKeyDown(StateOptions.OPTIONS.controls().get(Keybind.S).getKeyCode());
        moveLeft = container.getInput().isKeyDown(StateOptions.OPTIONS.controls().get(Keybind.A).getKeyCode());
        moveRight = container.getInput().isKeyDown(StateOptions.OPTIONS.controls().get(Keybind.D).getKeyCode());
        useSprint = container.getInput().isKeyDown(StateOptions.OPTIONS.controls().get(Keybind.CONTROL).getKeyCode());
        useAttack = container.getInput().isKeyDown(StateOptions.OPTIONS.controls().get(Keybind.SPACE).getKeyCode());
    }

    public boolean keyJustPressed(int key) {
        if (key < 0 || key >= keys.length)
            return false;
        return justPressed[key];
    }

    public void keyPressed(int key, char c) {
        if (key < 0 || key >= keys.length)
            return;
        keys[key] = true;

        if (key != KeyEvent.VK_ENTER) {
            currentKeyPressedCode = key;
            currentKeyPressedChar = c;
        }
    }

    public void keyReleased(int key, char c) {
        if (key < 0 || key >= keys.length)
            return;
        keys[key] = false;
    }

    public int getCurrentKeyPressedCode() {
        return currentKeyPressedCode;
    }

    public void setCurrentKeyPressedCode(int currentKeyPressedCode) {
        this.currentKeyPressedCode = currentKeyPressedCode;
    }

    public char getCurrentKeyPressedChar() {
        return currentKeyPressedChar;
    }

    public void setCurrentKeyPressedChar(char currentKeyPressedChar) {
        this.currentKeyPressedChar = currentKeyPressedChar;
    }
}
