package io.github.vampirestudios.tdg.gfx;


import org.lwjgl.glfw.GLFW;

/**
 * This class handles all of the keyboard and mouse input. To access these
 * methods
 *
 */
public interface IInputHandler {

    /**
     * Returns true if the mouse cursor is currently inside of the window. This
     * can be useful to avoid unnecessary mouse position checking
     *
     * @return If the mouse is in the window
     */
    boolean isMouseInWindow();

    /**
     * Returns true if the given mouse button is currently being held down
     *
     * @param button The button to query. Use the {@link GLFW} class to easily
     *               get mouse buttons' ids
     * @return If the button is down
     * @see #wasMousePressed(int)
     */
    boolean isMouseDown(int button);

    /**
     * Returns true if the mouse was singly pressed inbetween the last tick and
     * this one. This should be used for things like buttons where you don't
     * want a held press to trigger something multiple times.
     *
     * @param button The button toe query. Use the {@link GLFW} class to easily
     *               get mouse buttons' ids
     * @return If the button was pressed lately
     * @see #isMouseDown(int)
     */
    boolean wasMousePressed(int button);

    /**
     * Return true if the given key is currently being held down
     *
     * @param key The key to query. Use the {@link GLFW} class to easily get
     *            keys' ids
     * @return If the key is down
     * @see #wasKeyPressed(int)
     */
    boolean isKeyDown(int key);

    /**
     * Returns true if a keyboard key was singly pressed inbetween the last tick
     * and this one. This should be used for things like buttons where you don't
     * want a held press to trigger something multiple times.
     *
     * @param key The key to query. Use the {@link GLFW} class to easily get
     *            keys' ids
     * @return If the key was pressed lately
     * @see #isKeyDown(int)
     */
    boolean wasKeyPressed(int key);

    /**
     * Returns the amount of delta that the mouse wheel has been turned since
     * the last tick. A negative amount indicates a turning in the other
     * direction.
     *
     * @return The mouse wheel change
     * @see #getHorizontalMouseWheelChange()
     */
    int getMouseWheelChange();

    /**
     * Returns the amount of delta that the mouse wheel has been shifted
     * horizontally since the last tick. A negative amount indicates a shifting
     * to the left. This is, obviously, not supported by all mouses.
     *
     * @return The horizontal mouse wheel change
     * @see #getMouseWheelChange()
     */
    int getHorizontalMouseWheelChange();

    /**
     * Gets the mouse's current x coordinate on screen.
     *
     * @return The mouse's x coordinate
     */
    int getMouseX();

    /**
     * Gets the mouse's current y coordinate on screen
     *
     * @return The mouse's y coordinate
     */
    int getMouseY();

    /**
     * A setter for wether keyboard repeat events should be allowed. Setting
     * this to true means that, when holding down a keyboard key, {@link
     * #wasKeyPressed(int)} will be triggered again after a short while. This is
     * automatically enabled in Guis and can be useful for when you want to,
     * say, remove a big amount of text by just holding down the backspace key.
     * <p>
     * Note that this method should actually be called "allowKeyboardRepeats".
     * Oh well.
     *
     * @param allow If repeated keyboard events should be allowed
     */
    void allowKeyboardEvents(boolean allow);
}