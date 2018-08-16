package coffeecatteam.theultimatetile.entities.creatures;

import coffeecatteam.theultimatetile.Handler;

import java.net.InetAddress;

public class EntityPlayerMP extends EntityPlayer {

    private InetAddress ipAddress;
    private int port;

    public EntityPlayerMP(Handler handler, String id, String username, InetAddress ipAddress, int port, boolean isLocal) {
        super(handler, id, username);
        this.ipAddress = ipAddress;
        this.port = port;
        this.isLocal = isLocal;
    }

    @Override
    public void tick() {
        super.tick();
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }
}
