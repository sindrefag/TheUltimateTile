package coffeecatteam.tilegame.state;

import coffeecatteam.tilegame.Handler;

import java.awt.*;

public abstract class State {

    private static State currentState = null;

    public static void setState(State state) {
        currentState = state;
    }

    public static State getState() {
        return currentState;
    }

    protected Handler handler;

    public State(Handler handler) {
        this.handler = handler;
    }

    public abstract State init();

    public abstract void tick();

    public abstract void render(Graphics g);
}
