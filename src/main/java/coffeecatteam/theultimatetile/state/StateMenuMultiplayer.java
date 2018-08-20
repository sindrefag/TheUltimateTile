package coffeecatteam.theultimatetile.state;

import coffeecatteam.theultimatetile.Handler;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.ui.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.UIManager;
import coffeecatteam.theultimatetile.net.packet.Packet00Login;
import coffeecatteam.theultimatetile.utils.Logger;

import javax.swing.*;
import java.awt.*;

public class StateMenuMultiplayer extends State {

    private UIManager uiManager;

    private String username = "Random_Player";
    private String ip = "localhost";

    private int x = 25;

    private int nameBtnWidth = 64 * 6;
    private int nameBtnHeight = 64;
    private int nameBtnY = 50;

    private int ipBtnWidth = 64 * 5;
    private int ipBtnHeight = 64;
    private int ipBtnY = handler.getHeight() / 2 - ipBtnHeight / 2;

    private int joinBtnWidth = 64 * 6;
    private int joinBtnHeight = 64;

    private int backBtnWidth = 64 * 3;
    private int backBtnHeight = 64;

    public StateMenuMultiplayer(Handler handler, String username) {
        super(handler);
        uiManager = new UIManager(handler);
        init();
        this.username = username;

        uiManager.addObject(new UIButton(x, ipBtnY, ipBtnWidth, ipBtnHeight, "Server IP", () -> {
            ip = JOptionPane.showInputDialog("Enter server ip:", ip);
            this.handler.getGame().getClient().setIpAddress(ip);
            Logger.print("IP set to [" + ip + "]");
        }));

        uiManager.addObject(new UIButton(x, handler.getHeight() - joinBtnHeight - 50, joinBtnWidth, joinBtnHeight, "Join Server", () -> {
            if (!ip.equalsIgnoreCase("")) {

//                    handler.getGame().getClient().sendData("ping");
                Packet00Login packetLogin = new Packet00Login(this.username);
                packetLogin.writeData(this.handler.getGame().getClient());

                Logger.print("Joining Server [" + ip + "] as [" + this.username + "]");
                State.setState(this.handler.getGame().stateGame);
            }
        }));

        uiManager.addObject(new UIButton(handler.getWidth() - backBtnWidth - x, handler.getHeight() - backBtnHeight - 50, backBtnWidth, backBtnHeight, "Back", () -> {
            State.setState(this.handler.getGame().stateMenu);
        }));
    }

    @Override
    public void init() {
        handler.getMouseManager().setUiManager(uiManager);
    }

    @Override
    public void tick() {
        uiManager.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.BACKGROUND, 0, 0, handler.getWidth(), handler.getHeight(), null);
        uiManager.render(g);

        Font font = Assets.FONT_30;
        Text.drawString(g, "Username: " + username, x, nameBtnY + nameBtnHeight + Text.getHeight(g, font), Color.white, font);
        Text.drawString(g, "IP: " + ip, x, ipBtnY + ipBtnHeight + Text.getHeight(g, font), Color.white, font);
    }
}
