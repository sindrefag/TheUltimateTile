package coffeecatteam.theultimatetile.entities;

import coffeecatteam.theultimatetile.Handler;
<<<<<<< HEAD
import coffeecatteam.theultimatetile.entities.player.EntityPlayer;
import coffeecatteam.theultimatetile.entities.player.EntityPlayerMP;
import coffeecatteam.theultimatetile.net.packet.Packet00Login;
=======
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;
>>>>>>> parent of 8ca733f... Got players load on same server localy
import coffeecatteam.theultimatetile.tiles.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class EntityManager {

    // EntityManager
    private Handler handler;
    private EntityPlayer player;
    private ArrayList<Entity> entities;
    private Comparator<Entity> renderSorter = (Entity a, Entity b) -> {
        if (a.getY() + a.getHeight() < b.getY() + b.getHeight())
            return -1;
        return 1;
    };

    public EntityManager(Handler handler, EntityPlayer player) {
        this.handler = handler;
        this.player = player;

        if (handler.getGame().getServer() != null) {
            player = new EntityPlayerMP(handler, player.getUsername(), null, -1 ,true);
            Packet00Login packetLogin = new Packet00Login(player.getUsername());
            handler.getGame().getServer().addConnection((EntityPlayerMP) player, packetLogin);
            packetLogin.writeData(handler.getGame().getClient());
        }

        entities = new ArrayList<>();
        addEntity(this.player);
    }

    public void tick() {
        Iterator<Entity> it = entities.iterator();
        while (it.hasNext()) {
            Entity e = it.next();
            e.tick();
            if (!e.isActive())
                e.die(it);
        }
        entities.sort(renderSorter);
    }

    public void render(Graphics g) {
        for (Entity e : entities)
            e.renderA(g);

        /* Post Render */
        player.postRender(g);
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }

    public void addEntity(Entity e, float x, float y, boolean atTile) {
        e.setX(x * Tile.TILE_WIDTH);
        e.setY(y * Tile.TILE_HEIGHT);
        if (!atTile) {
            e.setX(e.getX() / Tile.TILE_WIDTH);
            e.setY(e.getY() / Tile.TILE_HEIGHT);
        }
        entities.add(e);
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public void setPlayer(EntityPlayer player) {
        this.player = player;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }
}
